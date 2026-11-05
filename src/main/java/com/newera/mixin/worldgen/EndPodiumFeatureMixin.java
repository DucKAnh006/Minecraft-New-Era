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

        Identifier targetId = null;
        if (this.active) {
            Identifier activeId = Identifier.fromNamespaceAndPath("newera", "end_podium_active");
            if (templateManager.get(activeId).isPresent()) {
                targetId = activeId;
            }
        }

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

        if (targetId != null) {
            Optional<StructureTemplate> templateOpt = templateManager.get(targetId);
            if (templateOpt.isPresent()) {
                StructureTemplate template = templateOpt.get();
                Vec3i size = template.getSize();

                int cx = 0;
                int cz = 0;

                int baseY = -1;
                boolean isInitialGeneration = false;

                for (int y = 110; y >= 40; y--) {
                    if (worldGenLevel.getBlockState(new BlockPos(cx, y, cz)).is(Blocks.BEDROCK)) {

                        baseY = y - 9;
                        break;
                    }
                }

                if (baseY == -1 && serverLevel.getDragonFight() != null) {
                    EnderDragonFight fight = serverLevel.getDragonFight();
                    BlockPos fightPortal = ((IEnderDragonFightCustom) fight).getCustomPortalLocation();
                    if (fightPortal != null && fightPortal.getY() >= 45) {
                        baseY = fightPortal.getY() - 1;
                    }
                }

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

                int offsetX = size.getX() / 2;
                int offsetZ = size.getZ() / 2;
                BlockPos placePos = new BlockPos(
                    cx - offsetX,
                    baseY,
                    cz - offsetZ
                );

                if (serverLevel.getDragonFight() != null) {
                    EnderDragonFight fight = serverLevel.getDragonFight();
                    ((IEnderDragonFightCustom) fight).setCustomPortalLocation(new BlockPos(cx, baseY + 1, cz));
                    fight.setDirty();
                }

                StructurePlaceSettings settings = new StructurePlaceSettings()
                    .setIgnoreEntities(false)
                    .setKnownShape(true);

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

                template.placeInWorld(worldGenLevel, placePos, placePos, settings, randomSource, 2);

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
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }

                List<StructureTemplate.StructureBlockInfo> plankInfos = template.filterBlocks(placePos, new StructurePlaceSettings(), Blocks.OAK_PLANKS);
                for (StructureTemplate.StructureBlockInfo info : plankInfos) {
                    if (this.active) {
                        worldGenLevel.setBlock(info.pos(), Blocks.END_PORTAL.defaultBlockState(), 2);
                    } else {
                        worldGenLevel.setBlock(info.pos(), Blocks.AIR.defaultBlockState(), 2);
                    }
                }

                if (this.active) {

                    worldGenLevel.setBlock(new BlockPos(cx, baseY + 2, cz - 5), Blocks.TORCH.defaultBlockState(), 2);
                    worldGenLevel.setBlock(new BlockPos(cx, baseY + 2, cz + 5), Blocks.TORCH.defaultBlockState(), 2);
                    worldGenLevel.setBlock(new BlockPos(cx - 5, baseY + 2, cz), Blocks.TORCH.defaultBlockState(), 2);
                    worldGenLevel.setBlock(new BlockPos(cx + 5, baseY + 2, cz), Blocks.TORCH.defaultBlockState(), 2);

                    worldGenLevel.setBlock(new BlockPos(cx - 2, baseY + 3, cz), Blocks.WALL_TORCH.defaultBlockState().setValue(net.minecraft.world.level.block.WallTorchBlock.FACING, net.minecraft.core.Direction.WEST), 2);
                    worldGenLevel.setBlock(new BlockPos(cx + 2, baseY + 3, cz), Blocks.WALL_TORCH.defaultBlockState().setValue(net.minecraft.world.level.block.WallTorchBlock.FACING, net.minecraft.core.Direction.EAST), 2);
                    worldGenLevel.setBlock(new BlockPos(cx, baseY + 3, cz - 2), Blocks.WALL_TORCH.defaultBlockState().setValue(net.minecraft.world.level.block.WallTorchBlock.FACING, net.minecraft.core.Direction.NORTH), 2);
                    worldGenLevel.setBlock(new BlockPos(cx, baseY + 3, cz + 2), Blocks.WALL_TORCH.defaultBlockState().setValue(net.minecraft.world.level.block.WallTorchBlock.FACING, net.minecraft.core.Direction.SOUTH), 2);
                } else {

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

