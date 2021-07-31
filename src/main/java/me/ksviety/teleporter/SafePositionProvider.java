package me.ksviety.teleporter;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;

import java.util.Collection;

public class SafePositionProvider implements IPositionProvider {
    private final IPositionProvider positionProvider;
    private final World world;
    private final Collection<String> bannedBlocks;

    public SafePositionProvider(IPositionProvider positionProvider, World world, Collection<String> bannedBlocks) {
        this.positionProvider = positionProvider;
        this.world = world;
        this.bannedBlocks = bannedBlocks;
    }

    @Override
    public Vec3i provide() {
        return getSafePosition();
    }

    private Vec3i coercePositionInWorldBorder(Vec3i position, WorldBorder border) {
        final int offset = 10;

        final int maxX = (int) border.maxX() - offset;
        final int maxZ = (int) border.maxZ() - offset;

        final int minX = (int) border.minX() + offset;
        final int minZ = (int) border.minZ() + offset;

        return new Vec3i(
                Math.max(minX, Math.min(maxX, position.getX())),
                position.getY(),
                Math.max(minZ, Math.min(maxZ, position.getZ()))
        );
    }

    private Vec3i getSafePosition() {
        final Vec3i position = world.getTopSolidOrLiquidBlock(
                new BlockPos(
                        coercePositionInWorldBorder(
                                positionProvider.provide(),
                                world.getWorldBorder()
                        )
                )
        );

        if (isSafe(position))
            return position;

        return getSafePosition();
    }

    private boolean isSafe(Vec3i position) {
        final String name = world.getBlockState(new BlockPos(position))
                .getBlock()
                .getRegistryName()
                .getResourcePath();

        if (bannedBlocks.contains(name))
            return false;

        return true;
    }
}
