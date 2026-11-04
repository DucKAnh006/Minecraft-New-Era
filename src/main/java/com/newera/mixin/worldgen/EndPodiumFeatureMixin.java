package com.newera.mixin.worldgen;

import com.newera.docs.worldgen.IEnderDragonFightCustom;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.end.EnderDragonFight;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@Mixin(EndPodiumFeature.class)
public abstract class EndPodiumFeatureMixin {

    @Shadow @Final private boolean active;

    @Inject(method = "place", at = @At("HEAD"), cancellable = true)
    private void onPlaceCustomPodium(WorldGenLevel worldGenLevel, ChunkGenerator chunkGenerator, RandomSource randomSource, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        ServerLevel serverLevel = worldGenLevel.getLevel();
        StructureTemplateManager templateManager = serverLevel.getServer().getStructureTemplateManager();

        // 1. Thử tìm template kích hoạt khi rồng chết (nếu active == true và người dùng tạo template riêng)
        Identifier targetId = null;
        if (this.active) {
            Identifier activeId = Identifier.fromNamespaceAndPath("newera", "end_podium_active");
            if (templateManager.get(activeId).isPresent()) {
                targetId = activeId;
            }
        }

        // 2. Nếu không có active template hoặc active == false, ưu tiên nạp template bệ đá chính
        if (targetId == null) {
            Identifier podiumId = Identifier.fromNamespaceAndPath("newera", "podium");
            if (templateManager.get(podiumId).isPresent()) {
                targetId = podiumId;
            } else {
                Identifier defaultId = Identifier.fromNamespaceAndPath("newera", "end_podium");
                if (templateManager.get(defaultId).isPresent()) {
                    targetId = defaultId;
                }
            }
        }

        // 3. Nếu tìm thấy custom template trong mod: nạp và đặt vào thế giới
        if (targetId != null) {
            Optional<StructureTemplate> templateOpt = templateManager.get(targetId);
            if (templateOpt.isPresent()) {
                StructureTemplate template = templateOpt.get();
                Vec3i size = template.getSize();

                // Cột Bedrock trung tâm luôn cố định tuyệt đối tại tọa độ X=0, Z=0
                int cx = 0;
                int cz = 0;

                // -------------------------------------------------------------
                // BẢO TOÀN VỊ TRÍ BỆ ĐÁ VĨNH VIỄN (KHÔNG QUÉT LẠI ĐỊA HÌNH):
                // -------------------------------------------------------------
                int baseY = -1;
                boolean isInitialGeneration = false;

                // Cách 1: Kiểm tra cột Bedrock trung tâm đã tồn tại tại (cx, Y, cz) chưa
                for (int y = 110; y >= 40; y--) {
                    if (worldGenLevel.getBlockState(new BlockPos(cx, y, cz)).is(Blocks.BEDROCK)) {
                        // Cột Bedrock trung tâm cao 10 block: từ baseY đến baseY + 9
                        // Do đó đỉnh cột Bedrock chính là baseY + 9 -> baseY = y - 9
                        baseY = y - 9;
                        break;
                    }
                }

                // Cách 2: Nếu chưa tìm thấy qua block, kiểm tra trong EnderDragonFight
                if (baseY == -1 && serverLevel.getDragonFight() != null) {
                    EnderDragonFight fight = serverLevel.getDragonFight();
                    BlockPos fightPortal = ((IEnderDragonFightCustom) fight).getCustomPortalLocation();
                    if (fightPortal != null && fightPortal.getY() >= 45) {
                        baseY = fightPortal.getY() - 1;
                    }
                }

                // Cách 3: NẾU THẾ GIỚI MỚI TINH (CHƯA CÓ BỆ ĐÁ):
                // Chỉ quét tìm điểm cao nhất trên địa hình End tự nhiên DUY NHẤT 1 LẦN khi gen thế giới!
                if (baseY == -1) {
                    int scannedHighestY = 60;
                    for (int rx = -7; rx <= 7; rx++) {
                        for (int rz = -7; rz <= 7; rz++) {
                            if (rx * rx + rz * rz <= 51) {
                                BlockPos scanPos = new BlockPos(cx + rx, 0, cz + rz);
                                BlockPos ground = worldGenLevel.getHeightmapPos(net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, scanPos).below();
                                if (ground.getY() > scannedHighestY && ground.getY() < 85) {
                                    scannedHighestY = ground.getY();
                                }
                            }
                        }
                    }
                    baseY = scannedHighestY;
                    isInitialGeneration = true;
                }

                // Căn giữa công trình tại (0, 0)
                int offsetX = size.getX() / 2; // 7
                int offsetZ = size.getZ() / 2; // 7
                BlockPos placePos = new BlockPos(
                    cx - offsetX,
                    baseY,
                    cz - offsetZ
                );

                // Đồng bộ và lưu khóa cứng vị trí cổng vào EnderDragonFight
                if (serverLevel.getDragonFight() != null) {
                    EnderDragonFight fight = serverLevel.getDragonFight();
                    ((IEnderDragonFightCustom) fight).setCustomPortalLocation(new BlockPos(cx, baseY + 1, cz));
                    fight.setDirty();
                }

                // Cấu hình đặt template
                StructurePlaceSettings settings = new StructurePlaceSettings()
                    .setIgnoreEntities(false)
                    .setKnownShape(true);

                // StructureProcessor:
                // - Biến đổi ván gỗ sồi (minecraft:oak_planks) thành cổng End hoặc không khí
                // - KHÔNG đặt không khí (null) ở bên ngoài hình tròn bệ đá để tránh cắt khoét địa hình hình hộp chữ nhật!
                settings.addProcessor(new StructureProcessor() {
                    @Override
                    public StructureTemplate.StructureBlockInfo processBlock(
                            LevelReader level,
                            BlockPos targetPosition,
                            BlockPos referencePosition,
                            BlockPos blockPos,
                            StructureTemplate.StructureBlockInfo currentBlockInfo,
                            StructurePlaceSettings placeSettings) {
                        int relX = currentBlockInfo.pos().getX() - (placePos.getX() + 7);
                        int relZ = currentBlockInfo.pos().getZ() - (placePos.getZ() + 7);
                        double distSq = relX * relX + relZ * relZ;

                        // Bỏ qua không khí nằm bên ngoài hình tròn bệ đá (bán kính 7.15 block)
                        if (currentBlockInfo.state().isAir() && distSq > 7.15 * 7.15) {
                            return null;
                        }

                        if (currentBlockInfo.state().is(Blocks.OAK_PLANKS)) {
                            BlockState replacement = EndPodiumFeatureMixin.this.active
                                ? Blocks.END_PORTAL.defaultBlockState()
                                : Blocks.AIR.defaultBlockState();
                            return new StructureTemplate.StructureBlockInfo(currentBlockInfo.pos(), replacement, null);
                        }
                        return currentBlockInfo;
                    }

                    @Override
                    public com.mojang.serialization.MapCodec<? extends StructureProcessor> codec() {
                        return null;
                    }
                });

                // Đặt template vào thế giới
                template.placeInWorld(worldGenLevel, placePos, placePos, settings, randomSource, 2);

                // Dọn dẹp bất kỳ khối END_PORTAL tàn dư nào ở sai độ cao (nếu thế giới cũ từng bị lỗi hạ độ cao)
                for (int rx = -7; rx <= 7; rx++) {
                    for (int rz = -7; rz <= 7; rz++) {
                        if (rx * rx + rz * rz <= 51) {
                            for (int y = 45; y <= 95; y++) {
                                if (y != baseY + 1) {
                                    BlockPos strayPos = new BlockPos(cx + rx, y, cz + rz);
                                    if (worldGenLevel.getBlockState(strayPos).is(Blocks.END_PORTAL)) {
                                        worldGenLevel.setBlock(strayPos, Blocks.AIR.defaultBlockState(), 2);
                                    }
                                }
                            }
                        }
                    }
                }

                // Tự động bù gạch End (END_STONE_BRICKS) vào khoảng trống bên dưới chân bệ đá (khi gen lần đầu)
                if (isInitialGeneration) {
                    for (int x = 0; x < size.getX(); x++) {
                        for (int z = 0; z < size.getZ(); z++) {
                            double dx = x - 7.0;
                            double dz = z - 7.0;
                            if (dx * dx + dz * dz <= 7.15 * 7.15) {
                                int wx = placePos.getX() + x;
                                int wz = placePos.getZ() + z;
                                BlockPos.MutableBlockPos fillPos = new BlockPos.MutableBlockPos(wx, placePos.getY() - 1, wz);
                                for (int d = 0; d < 35; d++) {
                                    BlockState bs = worldGenLevel.getBlockState(fillPos);
                                    if (bs.isAir() || !bs.isSolidRender()) {
                                        worldGenLevel.setBlock(fillPos, Blocks.END_STONE_BRICKS.defaultBlockState(), 2);
                                        fillPos.move(net.minecraft.core.Direction.DOWN);
                                    } else {
                                        break; // Đã chạm vào nền đá đảo đặc
                                    }
                                }
                            }
                        }
                    }
                }

                // Đảm bảo tất cả 52 vị trí đánh dấu bằng ván gỗ sồi được cập nhật chính xác
                List<StructureTemplate.StructureBlockInfo> plankInfos = template.filterBlocks(placePos, new StructurePlaceSettings(), Blocks.OAK_PLANKS);
                for (StructureTemplate.StructureBlockInfo info : plankInfos) {
                    if (this.active) {
                        worldGenLevel.setBlock(info.pos(), Blocks.END_PORTAL.defaultBlockState(), 2);
                    } else {
                        worldGenLevel.setBlock(info.pos(), Blocks.AIR.defaultBlockState(), 2);
                    }
                }

                // Cập nhật đuốc và trứng rồng theo trạng thái active
                if (this.active) {
                    // Đặt lại các ngọn đuốc mới trên vành bệ đá (Bedrock Rim ở baseY + 1, đuốc ở baseY + 2)
                    worldGenLevel.setBlock(new BlockPos(cx, baseY + 2, cz - 5), Blocks.TORCH.defaultBlockState(), 2);
                    worldGenLevel.setBlock(new BlockPos(cx, baseY + 2, cz + 5), Blocks.TORCH.defaultBlockState(), 2);
                    worldGenLevel.setBlock(new BlockPos(cx - 5, baseY + 2, cz), Blocks.TORCH.defaultBlockState(), 2);
                    worldGenLevel.setBlock(new BlockPos(cx + 5, baseY + 2, cz), Blocks.TORCH.defaultBlockState(), 2);

                    // Đặt lại 4 ngọn đuốc gắn tường trên cột Bedrock trung tâm (Y = baseY + 3)
                    worldGenLevel.setBlock(new BlockPos(cx - 2, baseY + 3, cz), Blocks.WALL_TORCH.defaultBlockState().setValue(net.minecraft.world.level.block.WallTorchBlock.FACING, net.minecraft.core.Direction.WEST), 2);
                    worldGenLevel.setBlock(new BlockPos(cx + 2, baseY + 3, cz), Blocks.WALL_TORCH.defaultBlockState().setValue(net.minecraft.world.level.block.WallTorchBlock.FACING, net.minecraft.core.Direction.EAST), 2);
                    worldGenLevel.setBlock(new BlockPos(cx, baseY + 3, cz - 2), Blocks.WALL_TORCH.defaultBlockState().setValue(net.minecraft.world.level.block.WallTorchBlock.FACING, net.minecraft.core.Direction.NORTH), 2);
                    worldGenLevel.setBlock(new BlockPos(cx, baseY + 3, cz + 2), Blocks.WALL_TORCH.defaultBlockState().setValue(net.minecraft.world.level.block.WallTorchBlock.FACING, net.minecraft.core.Direction.SOUTH), 2);
                } else {
                    // Khi rồng hồi sinh (active == false), nếu trên đỉnh cột có Trứng Rồng thì dọn đi
                    BlockPos eggPos = new BlockPos(cx, baseY + 10, cz);
                    if (worldGenLevel.getBlockState(eggPos).is(Blocks.DRAGON_EGG)) {
                        worldGenLevel.setBlock(eggPos, Blocks.AIR.defaultBlockState(), 2);
                    }
                }

                cir.setReturnValue(true);
            }
        }
    }
}
