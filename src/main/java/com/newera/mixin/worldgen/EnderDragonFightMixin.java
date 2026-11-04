package com.newera.mixin.worldgen;

import com.newera.docs.worldgen.IEnderDragonFightCustom;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.end.DragonRespawnStage;
import net.minecraft.world.level.dimension.end.EnderDragonFight;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(EnderDragonFight.class)
public abstract class EnderDragonFightMixin extends SavedData implements IEnderDragonFightCustom {

    @Shadow private boolean dragonKilled;
    @Shadow private boolean hasPreviouslyKilledDragon;
    @Shadow private DragonRespawnStage respawnStage;
    @Shadow private BlockPos exitPortalLocation;
    @Shadow private ServerLevel level;
    @Shadow private BlockPos origin;

    @Shadow protected abstract void respawnDragon(List<EndCrystal> crystals);
    @Shadow public abstract void tryRespawn();

    @Override
    public BlockPos getCustomPortalLocation() {
        return this.exitPortalLocation;
    }

    @Override
    public void setCustomPortalLocation(BlockPos pos) {
        this.exitPortalLocation = pos;
        this.setDirty();
    }

    /**
     * Tìm đỉnh cột Bedrock trung tâm tại (0, Y, 0).
     * Do cột Bedrock trung tâm có chiều cao 10 block (baseY đến baseY + 9),
     * đỉnh cột Bedrock chính là baseY + 9 -> baseY = topBedrockY - 9.
     */
    private int getTopCentralBedrockY() {
        BlockPos center = this.origin != null ? this.origin : BlockPos.ZERO;
        int cx = center.getX();
        int cz = center.getZ();
        for (int y = 110; y >= 40; y--) {
            if (this.level.getBlockState(new BlockPos(cx, y, cz)).is(Blocks.BEDROCK)) {
                return y;
            }
        }
        return -1;
    }

    /**
     * Chặn triệt để hiện tượng vanilla tự động hạ độ cao bệ đá trong spawnExitPortal!
     * Vanilla có vòng lặp while (is(BEDROCK)) exitPortalLocation = exitPortalLocation.below()
     * khiến mỗi lần cập nhật bệ đá lại bị tụt xuống 1 nấc.
     * Bằng cách gán exitPortalLocation chuẩn ngay từ HEAD, vòng lặp tụt độ cao của vanilla bị triệt tiêu hoàn toàn.
     */
    @Inject(method = "spawnExitPortal", at = @At("HEAD"))
    private void onSpawnExitPortal(boolean active, CallbackInfo ci) {
        int topBedrockY = getTopCentralBedrockY();
        if (topBedrockY != -1) {
            int baseY = topBedrockY - 9;
            BlockPos lockedPos = new BlockPos(0, baseY + 1, 0);
            if (this.exitPortalLocation == null || !this.exitPortalLocation.equals(lockedPos)) {
                this.exitPortalLocation = lockedPos;
                this.setDirty();
            }
        }
    }

    /**
     * Báo cho vanilla biết cổng tùy chỉnh đã mở hay chưa (tránh việc scanState gọi nhầm spawnExitPortal).
     */
    @Inject(method = "hasActiveExitPortal", at = @At("HEAD"), cancellable = true)
    private void onHasActiveExitPortal(CallbackInfoReturnable<Boolean> cir) {
        int topBedrockY = getTopCentralBedrockY();
        if (topBedrockY != -1) {
            int baseY = topBedrockY - 9;
            int portalY = baseY + 1;
            // Kiểm tra các khối cổng tại vị trí bệ đá
            if (this.level.getBlockState(new BlockPos(0, portalY, -4)).is(Blocks.END_PORTAL) ||
                this.level.getBlockState(new BlockPos(0, portalY, 4)).is(Blocks.END_PORTAL)) {
                cir.setReturnValue(true);
            }
        }
    }

    /**
     * Chặn vanilla đặt quả trứng rồng thứ 2 ở sai vị trí khi giết rồng lần đầu.
     * Vanilla có đoạn mã: if (!this.hasPreviouslyKilledDragon) setBlockAndUpdate(getHeightmapPos(...), DRAGON_EGG).
     * Bằng cách đặt hasPreviouslyKilledDragon = true tại HEAD, vanilla sẽ bỏ qua nhánh đặt trứng đó.
     */
    @Inject(method = "setDragonKilled", at = @At("HEAD"))
    private void onDragonKilledHead(EnderDragon dragon, CallbackInfo ci) {
        this.hasPreviouslyKilledDragon = true;
    }

    /**
     * Khi rồng bị tiêu diệt (cả lần đầu tiên lẫn các lần hồi sinh sau này):
     * Luôn luôn sinh ra ĐÚNG 1 quả trứng rồng duy nhất tại đỉnh cột Bedrock trung tâm (topBedrockY + 1).
     */
    @Inject(method = "setDragonKilled", at = @At("TAIL"))
    private void onDragonKilledTail(EnderDragon dragon, CallbackInfo ci) {
        int topBedrockY = getTopCentralBedrockY();
        if (topBedrockY != -1) {
            BlockPos center = this.origin != null ? this.origin : BlockPos.ZERO;
            BlockPos eggPos = new BlockPos(center.getX(), topBedrockY + 1, center.getZ());
            this.level.setBlockAndUpdate(eggPos, Blocks.DRAGON_EGG.defaultBlockState());
            this.setDirty();
        }
    }

    @Inject(method = "tryRespawn", at = @At("HEAD"), cancellable = true)
    private void onTryRespawnCustomPodium(CallbackInfo ci) {
        if (!this.dragonKilled || this.respawnStage != null) {
            return;
        }

        BlockPos center = this.origin != null ? this.origin : BlockPos.ZERO;
        int cx = center.getX();
        int cz = center.getZ();

        // Xác định độ cao bệ đá cố định tuyệt đối từ cột Bedrock trung tâm
        int topBedrockY = getTopCentralBedrockY();
        int baseY;
        if (topBedrockY != -1) {
            baseY = topBedrockY - 9;
        } else if (this.exitPortalLocation != null) {
            baseY = this.exitPortalLocation.getY() - 1;
        } else {
            baseY = center.getY();
        }

        // Vành Bedrock đặt pha lê nằm ở baseY + 1 -> Pha lê nằm ở baseY + 2
        int crystalY = baseY + 2;

        // 8 vị trí cắm pha lê bắt buộc trên vành Bedrock:
        BlockPos[] slots = new BlockPos[] {
            new BlockPos(cx - 2, crystalY, cz - 4), // Bắc 1
            new BlockPos(cx + 2, crystalY, cz - 4), // Bắc 2
            new BlockPos(cx + 4, crystalY, cz - 2), // Đông 1
            new BlockPos(cx + 4, crystalY, cz + 2), // Đông 2
            new BlockPos(cx + 2, crystalY, cz + 4), // Nam 1
            new BlockPos(cx - 2, crystalY, cz + 4), // Nam 2
            new BlockPos(cx - 4, crystalY, cz + 2), // Tây 1
            new BlockPos(cx - 4, crystalY, cz - 2)  // Tây 2
        };

        List<EndCrystal> allCrystals = new ArrayList<>();
        for (BlockPos slot : slots) {
            List<EndCrystal> near = findCrystalsNear(slot);
            if (near.isEmpty()) {
                // Nếu thiếu bất kỳ vị trí nào trong 8 vị trí -> Chưa đủ điều kiện hồi sinh
                return;
            }
            allCrystals.add(near.get(0));
        }

        // Đủ ĐÚNG 8 pha lê ở cả 8 vị trí -> Bắt đầu nghi thức hồi sinh rồng!
        if (allCrystals.size() == 8) {
            this.exitPortalLocation = new BlockPos(cx, baseY + 1, cz);
            this.setDirty();
            this.respawnDragon(allCrystals);
            ci.cancel();
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTickCheckRespawn(CallbackInfo ci) {
        // Quét định kỳ mỗi 20 tick (1 giây) khi rồng đã bị tiêu diệt để phản hồi ngay lập tức
        if (this.dragonKilled && this.respawnStage == null && this.level.getGameTime() % 20L == 0L) {
            this.tryRespawn();
        }
    }

    private List<EndCrystal> findCrystalsNear(BlockPos pos) {
        // Hộp quét mở rộng bao phủ trọn vẹn vị trí pha lê trên đỉnh khối đá nền
        AABB box = new AABB(
            pos.getX() - 0.5, pos.getY() - 1.0, pos.getZ() - 0.5,
            pos.getX() + 1.5, pos.getY() + 2.5, pos.getZ() + 1.5
        );
        return this.level.getEntitiesOfClass(EndCrystal.class, box);
    }
}
