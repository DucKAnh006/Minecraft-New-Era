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

    @Inject(method = "hasActiveExitPortal", at = @At("HEAD"), cancellable = true)
    private void onHasActiveExitPortal(CallbackInfoReturnable<Boolean> cir) {
        int topBedrockY = getTopCentralBedrockY();
        if (topBedrockY != -1) {
            int baseY = topBedrockY - 9;
            int portalY = baseY + 1;

            if (this.level.getBlockState(new BlockPos(0, portalY, -4)).is(Blocks.END_PORTAL) ||
                this.level.getBlockState(new BlockPos(0, portalY, 4)).is(Blocks.END_PORTAL)) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "setDragonKilled", at = @At("HEAD"))
    private void onDragonKilledHead(EnderDragon dragon, CallbackInfo ci) {
        this.hasPreviouslyKilledDragon = true;
    }

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

        int topBedrockY = getTopCentralBedrockY();
        int baseY;
        if (topBedrockY != -1) {
            baseY = topBedrockY - 9;
        } else if (this.exitPortalLocation != null) {
            baseY = this.exitPortalLocation.getY() - 1;
        } else {
            baseY = center.getY();
        }

        int crystalY = baseY + 2;

        BlockPos[] slots = new BlockPos[] {
            new BlockPos(cx - 2, crystalY, cz - 4),
            new BlockPos(cx + 2, crystalY, cz - 4),
            new BlockPos(cx + 4, crystalY, cz - 2),
            new BlockPos(cx + 4, crystalY, cz + 2),
            new BlockPos(cx + 2, crystalY, cz + 4),
            new BlockPos(cx - 2, crystalY, cz + 4),
            new BlockPos(cx - 4, crystalY, cz + 2),
            new BlockPos(cx - 4, crystalY, cz - 2)
        };

        List<EndCrystal> allCrystals = new ArrayList<>();
        for (BlockPos slot : slots) {
            List<EndCrystal> near = findCrystalsNear(slot);
            if (near.isEmpty()) {

                return;
            }
            allCrystals.add(near.get(0));
        }

        if (allCrystals.size() == 8) {
            this.exitPortalLocation = new BlockPos(cx, baseY + 1, cz);
            this.setDirty();
            this.respawnDragon(allCrystals);
            ci.cancel();
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTickCheckRespawn(CallbackInfo ci) {

        if (this.dragonKilled && this.respawnStage == null && this.level.getGameTime() % 20L == 0L) {
            this.tryRespawn();
        }
    }

    private List<EndCrystal> findCrystalsNear(BlockPos pos) {

        AABB box = new AABB(
            pos.getX() - 0.5, pos.getY() - 1.0, pos.getZ() - 0.5,
            pos.getX() + 1.5, pos.getY() + 2.5, pos.getZ() + 1.5
        );
        return this.level.getEntitiesOfClass(EndCrystal.class, box);
    }
}

