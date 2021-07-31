package me.ksviety.teleporter;

import net.minecraft.util.math.Vec3i;
import net.minecraft.world.border.WorldBorder;

import java.util.Random;

public class RandomPositionProvider implements IPositionProvider {
    private static final int DOUBLED_WORLD_HEIGHT = 255;

    private final Random random;
    private final WorldBorder worldBorder;

    protected RandomPositionProvider(Random random, WorldBorder worldBorder) {
        this.random = random;
        this.worldBorder = worldBorder;
    }

    @Override
    public Vec3i provide() {
        final int randomX = Math.abs(
                random.nextInt(
                        (int) worldBorder.maxX() * 2
                ) / 2
        );

        final int randomZ = Math.abs(
                random.nextInt(
                        (int) worldBorder.maxZ() * 2
                ) / 2
        );

        return new Vec3i(
                randomX,
                (random.nextInt() % DOUBLED_WORLD_HEIGHT) / 2,
                randomZ
        );
    }
}
