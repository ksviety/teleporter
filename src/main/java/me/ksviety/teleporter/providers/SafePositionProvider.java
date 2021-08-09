package me.ksviety.teleporter.providers;

import me.ksviety.teleporter.IPositionProvider;
import me.ksviety.teleporter.ShiftAxis;
import me.ksviety.teleporter.ShiftedVec3i;
import me.ksviety.teleporter.exceptions.CannotFindClosetSafePositionException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Objects;

/**
 * Can throw <a>CannotFindClosestSafePositionException</a>
 */
public class SafePositionProvider implements IPositionProvider {
    private final static ShiftAxis[] SHIFT_DIRECTIONS = new ShiftAxis[] {
            ShiftAxis.Forward,
            ShiftAxis.Backward,
            ShiftAxis.Right,
            ShiftAxis.Left
    };

    private final IPositionProvider positionProvider;
    private final World world;
    private final Collection<String> bannedBlocks;
    private final int shiftRadius;
    private final int maxSearchIterations;

    public SafePositionProvider(
            IPositionProvider positionProvider,
            World world,
            Collection<String> bannedBlocks,
            int shiftRadius,
            int maxSearchIterations
    ) {
        this.positionProvider = positionProvider;
        this.world = world;
        this.bannedBlocks = bannedBlocks;
        this.shiftRadius = shiftRadius;
        this.maxSearchIterations = maxSearchIterations;
    }

    @Override
    public Vec3i provide() {
        return getSafePosition();
    }

    private Vec3i getSafePosition(int iteration) {
        final Vec3i position = positionProvider.provide();

        for (int shift = 0; shift < shiftRadius; shift++) {
            for (ShiftAxis axis : SHIFT_DIRECTIONS) {
                final Vec3i topBlockPosition = world.getTopSolidOrLiquidBlock(
                        new BlockPos(
                                ShiftedVec3i.shift(position, shift, axis)
                        )
                );

                // Shift up by one to avoid spawning inside the block
                final Vec3i shiftedPosition = ShiftedVec3i.shift(topBlockPosition, 1, ShiftAxis.Up);

                if (isSafe(shiftedPosition))
                    return shiftedPosition;
            }
        }

        if (iteration >= maxSearchIterations)
            throw new CannotFindClosetSafePositionException();

        return getSafePosition(iteration + 1);
    }

    private Vec3i getSafePosition() {
        return getSafePosition(0);
    }

    private boolean isSafe(Vec3i position) {
        try {
            final BlockPos positionBelowPlayer = new BlockPos(
                    ShiftedVec3i.shift(position, 1, ShiftAxis.Down)
            );

            if (world.isAirBlock(positionBelowPlayer))
                return false;

            final String name = Objects.requireNonNull(
                    world.getBlockState(positionBelowPlayer).getBlock().getRegistryName()
            ).getResourcePath();

            if (bannedBlocks.contains(name))
                return false;
        } catch (NullPointerException npe) {
            return false;
        }

        return true;
    }
}
