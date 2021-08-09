package me.ksviety.teleporter.providers;

import me.ksviety.teleporter.IPositionProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

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

    private Vec3i getSafePosition() {
        final Vec3i position = world.getTopSolidOrLiquidBlock(
                new BlockPos(
                        positionProvider.provide()
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
