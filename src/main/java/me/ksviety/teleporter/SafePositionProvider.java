package me.ksviety.teleporter;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;

public class SafePositionProvider implements IPositionProvider {
    private final IPositionProvider positionProvider;
    private final World world;

    public SafePositionProvider(IPositionProvider positionProvider, World world) {
        this.positionProvider = positionProvider;
        this.world = world;
    }

    @Override
    public Vec3i provide() {
        final Vec3i position = positionProvider.provide();

        return world.getTopSolidOrLiquidBlock(
                new BlockPos(
                        coercePositionInWorldBorder(position, world.getWorldBorder())
                )
        );
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
}
