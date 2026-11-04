package com.newera.mixin.worldgen;

import com.newera.docs.entity.animation.INEEndCrystalAnimation;
import com.newera.docs.worldgen.EndPillarHeightManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.EndSpikeFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

@Mixin(EndSpikeFeature.class)
public abstract class EndSpikeFeatureMixin {

    @Shadow @Final private boolean crystalInvulnerable;
    @Shadow @Final private Optional<BlockPos> crystalBeamTarget;

    @Inject(method = "placeSpike", at = @At("HEAD"), cancellable = true)
    private void onPlaceCustomSpike(ServerLevelAccessor level, RandomSource random, EndSpikeFeature.EndSpike spike, CallbackInfo ci) {
        ServerLevel serverLevel = level.getLevel();
        StructureTemplateManager templateManager = serverLevel.getServer().getStructureTemplateManager();

        int cx = spike.getCenterX();
        int cz = spike.getCenterZ();
        int originX = cx - 11;
        int originZ = cz - 11;

        // 1. Kiểm tra template cột đơn tùy chỉnh: end_pilar.nbt (kích thước 23x48x23)
        Identifier singlePilarId = Identifier.fromNamespaceAndPath("newera", "end_pilar");
        Optional<StructureTemplate> optSingle = templateManager.get(singlePilarId);
        if (optSingle.isEmpty()) {
            singlePilarId = Identifier.fromNamespaceAndPath("new_era", "end_pilar");
            optSingle = templateManager.get(singlePilarId);
        }

        if (optSingle.isPresent()) {
            StructureTemplate template = optSingle.get();

            // Khớp địa hình: lấy điểm cao nhất của mặt đất trong phạm vi chân cột làm chuẩn baseY
            // Khóa cố định độ cao qua EndPillarHeightManager để không bao giờ bị hạ độ cao khi hồi sinh rồng
            int baseY = EndPillarHeightManager.getOrComputeBaseY(serverLevel, cx, cz, () -> {
                int highestY = -1;
                BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos();
                for (int x = -11; x <= 11; x++) {
                    for (int z = -11; z <= 11; z++) {
                        if (x * x + z * z <= 11.45 * 11.45) {
                            int wx = cx + x;
                            int wz = cz + z;
                            for (int y = 95; y >= 45; y--) {
                                mpos.set(wx, y, wz);
                                BlockState bs = level.getBlockState(mpos);
                                if (bs.isSolidRender()) {
                                    if (y > highestY) {
                                        highestY = y;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
                return highestY > 0 ? highestY : 58;
            });

            BlockPos pos = new BlockPos(originX, baseY, originZ);

            StructurePlaceSettings settings = new StructurePlaceSettings()
                .setIgnoreEntities(false)
                .setKnownShape(true);

            // Bỏ qua không khí ngoài bán kính cột (11.45 block) để không khoét lẹm vào sườn đảo End
            settings.addProcessor(new StructureProcessor() {
                @Override
                public StructureTemplate.StructureBlockInfo processBlock(
                        LevelReader levelReader,
                        BlockPos targetPosition,
                        BlockPos referencePosition,
                        BlockPos blockPos,
                        StructureTemplate.StructureBlockInfo currentBlockInfo,
                        StructurePlaceSettings placeSettings) {
                    int relX = currentBlockInfo.pos().getX() - cx;
                    int relZ = currentBlockInfo.pos().getZ() - cz;
                    double distSq = relX * relX + relZ * relZ;
                    if (currentBlockInfo.state().isAir() && distSq > 11.45 * 11.45) {
                        return null;
                    }
                    return currentBlockInfo;
                }

                @Override
                public com.mojang.serialization.MapCodec<? extends StructureProcessor> codec() {
                    return null;
                }
            });

            // Đặt cấu trúc cột End tùy chỉnh
            template.placeInWorld(level, pos, pos, settings, random, 2);

            // Kéo móng chạm đất: chỗ nào còn thiếu thì lấy block dưới cùng kéo thẳng xuống chạm đất mới thôi
            for (int x = 0; x < 23; x++) {
                for (int z = 0; z < 23; z++) {
                    double dx = x - 11.0;
                    double dz = z - 11.0;
                    if (dx * dx + dz * dz <= 11.45 * 11.45) {
                        int wx = originX + x;
                        int wz = originZ + z;
                        BlockPos bottomPos = new BlockPos(wx, baseY, wz);
                        BlockState bottomState = level.getBlockState(bottomPos);

                        if (!bottomState.isAir()) {
                            BlockState fillState;
                            if (bottomState.is(Blocks.OBSIDIAN)) {
                                fillState = Blocks.OBSIDIAN.defaultBlockState();
                            } else {
                                fillState = Blocks.END_STONE_BRICKS.defaultBlockState();
                            }

                            BlockPos.MutableBlockPos fillPos = new BlockPos.MutableBlockPos(wx, baseY - 1, wz);
                            for (int d = 0; d < 45; d++) {
                                BlockState curState = level.getBlockState(fillPos);
                                if (curState.isAir() || !curState.isSolidRender()) {
                                    level.setBlock(fillPos, fillState, 2);
                                    fillPos.move(Direction.DOWN);
                                } else {
                                    break; // Đã chạm mặt đất tự nhiên vững chắc!
                                }
                            }
                        }
                    }
                }
            }

            // Đảm bảo khối Bedrock đỉnh cột luôn tồn tại tại tọa độ (cx, baseY + 41, cz)
            BlockPos bedrockPos = new BlockPos(cx, baseY + 41, cz);
            level.setBlock(bedrockPos, Blocks.BEDROCK.defaultBlockState(), 2);

            // Đặt pha lê End trên khối Bedrock tại (cx + 0.5, baseY + 42, cz + 0.5)
            double crystalX = cx + 0.5D;
            double crystalY = baseY + 42.0D;
            double crystalZ = cz + 0.5D;

            AABB box = new AABB(cx - 2.0, crystalY - 2.0, cz - 2.0, cx + 3.0, crystalY + 4.0, cz + 3.0);
            List<EndCrystal> near = level.getEntitiesOfClass(EndCrystal.class, box);
            if (near.isEmpty()) {
                EndCrystal crystal = EntityTypes.END_CRYSTAL.create(serverLevel, EntitySpawnReason.STRUCTURE);
                if (crystal != null) {
                    crystal.setShowBottom(true);
                    crystal.setBeamTarget(this.crystalBeamTarget.orElse(null));
                    crystal.setPermanentlyInvulnerable(this.crystalInvulnerable);
                    crystal.snapTo(crystalX, crystalY, crystalZ, random.nextFloat() * 360.0F, 0.0F);
                    // Đặt trạng thái ban đầu là kén đóng Phase 1 (STATE_SLEEP)
                    ((INEEndCrystalAnimation) crystal).setCrystalState(INEEndCrystalAnimation.STATE_SLEEP);
                    level.addFreshEntity(crystal);
                }
            } else {
                for (EndCrystal crystal : near) {
                    crystal.setBeamTarget(this.crystalBeamTarget.orElse(null));
                    crystal.setPermanentlyInvulnerable(this.crystalInvulnerable);
                }
            }

            ci.cancel();
            return;
        }

        // 2. Dự phòng: Template 2 phần end_pilar_0 và end_pilar_1
        Identifier pilar0Id = Identifier.fromNamespaceAndPath("newera", "end_pilar_0");
        Identifier pilar1Id = Identifier.fromNamespaceAndPath("newera", "end_pilar_1");

        Optional<StructureTemplate> opt0 = templateManager.get(pilar0Id);
        Optional<StructureTemplate> opt1 = templateManager.get(pilar1Id);

        if (opt0.isEmpty()) {
            pilar0Id = Identifier.fromNamespaceAndPath("new_era", "end_pilar_0");
            opt0 = templateManager.get(pilar0Id);
        }
        if (opt1.isEmpty()) {
            pilar1Id = Identifier.fromNamespaceAndPath("new_era", "end_pilar_1");
            opt1 = templateManager.get(pilar1Id);
        }

        if (opt0.isPresent() && opt1.isPresent()) {
            StructureTemplate template0 = opt0.get();
            StructureTemplate template1 = opt1.get();

            int baseY = EndPillarHeightManager.getOrComputeBaseY(serverLevel, cx, cz, () -> 58);

            BlockPos pos0 = new BlockPos(originX, baseY, originZ);
            BlockPos pos1 = new BlockPos(originX, baseY + 48, originZ);

            StructurePlaceSettings settings = new StructurePlaceSettings()
                .setIgnoreEntities(false)
                .setKnownShape(true);

            settings.addProcessor(new StructureProcessor() {
                @Override
                public StructureTemplate.StructureBlockInfo processBlock(
                        LevelReader levelReader,
                        BlockPos targetPosition,
                        BlockPos referencePosition,
                        BlockPos blockPos,
                        StructureTemplate.StructureBlockInfo currentBlockInfo,
                        StructurePlaceSettings placeSettings) {
                    int relX = currentBlockInfo.pos().getX() - cx;
                    int relZ = currentBlockInfo.pos().getZ() - cz;
                    double distSq = relX * relX + relZ * relZ;
                    if (currentBlockInfo.state().isAir() && distSq > 11.45 * 11.45) {
                        return null;
                    }
                    return currentBlockInfo;
                }

                @Override
                public com.mojang.serialization.MapCodec<? extends StructureProcessor> codec() {
                    return null;
                }
            });

            template0.placeInWorld(level, pos0, pos0, settings, random, 2);
            template1.placeInWorld(level, pos1, pos1, settings, random, 2);

            for (int x = 0; x < 23; x++) {
                for (int z = 0; z < 23; z++) {
                    double dx = x - 11.0;
                    double dz = z - 11.0;
                    if (dx * dx + dz * dz <= 11.45 * 11.45) {
                        int wx = originX + x;
                        int wz = originZ + z;
                        BlockPos bottomPos = new BlockPos(wx, baseY, wz);
                        BlockState bottomState = level.getBlockState(bottomPos);

                        if (!bottomState.isAir()) {
                            BlockState fillState = bottomState.is(Blocks.OBSIDIAN) ? Blocks.OBSIDIAN.defaultBlockState() : Blocks.END_STONE_BRICKS.defaultBlockState();
                            BlockPos.MutableBlockPos fillPos = new BlockPos.MutableBlockPos(wx, baseY - 1, wz);
                            for (int d = 0; d < 45; d++) {
                                BlockState curState = level.getBlockState(fillPos);
                                if (curState.isAir() || !curState.isSolidRender()) {
                                    level.setBlock(fillPos, fillState, 2);
                                    fillPos.move(Direction.DOWN);
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            BlockPos bedrockPos = new BlockPos(cx, baseY + 76, cz);
            level.setBlock(bedrockPos, Blocks.BEDROCK.defaultBlockState(), 2);

            double crystalX = cx + 0.5D;
            double crystalY = baseY + 77.0D;
            double crystalZ = cz + 0.5D;

            AABB box = new AABB(cx - 2.0, crystalY - 2.0, cz - 2.0, cx + 3.0, crystalY + 4.0, cz + 3.0);
            List<EndCrystal> near = level.getEntitiesOfClass(EndCrystal.class, box);
            if (near.isEmpty()) {
                EndCrystal crystal = EntityTypes.END_CRYSTAL.create(serverLevel, EntitySpawnReason.STRUCTURE);
                if (crystal != null) {
                    crystal.setShowBottom(true);
                    crystal.setBeamTarget(this.crystalBeamTarget.orElse(null));
                    crystal.setPermanentlyInvulnerable(this.crystalInvulnerable);
                    crystal.snapTo(crystalX, crystalY, crystalZ, random.nextFloat() * 360.0F, 0.0F);
                    ((INEEndCrystalAnimation) crystal).setCrystalState(INEEndCrystalAnimation.STATE_SLEEP);
                    level.addFreshEntity(crystal);
                }
            } else {
                for (EndCrystal crystal : near) {
                    crystal.setBeamTarget(this.crystalBeamTarget.orElse(null));
                    crystal.setPermanentlyInvulnerable(this.crystalInvulnerable);
                }
            }

            ci.cancel();
        }
    }
}
