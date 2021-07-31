package me.ksviety.teleporter;

import net.minecraft.util.math.Vec3i;

import java.util.Random;

public class RandomPositionProvider implements PositionProvider {
    private final Random random;

    protected RandomPositionProvider(Random random) {
        this.random = random;
    }

    @Override
    public Vec3i provide() {
        return new Vec3i(
                random.nextInt(),
                random.nextInt(),
                random.nextInt()
        );
    }
}
