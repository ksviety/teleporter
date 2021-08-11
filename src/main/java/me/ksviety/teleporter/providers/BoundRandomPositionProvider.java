package me.ksviety.teleporter.providers;

import me.ksviety.teleporter.PositionProvider;
import net.minecraft.util.math.Vec3i;

import java.util.Random;

public class BoundRandomPositionProvider implements PositionProvider {
    private final int centerX;
    private final int centerZ;
    private final int size;
    private final Random random;

    public BoundRandomPositionProvider(int centerX, int centerZ, int size, Random random) {
        this.centerX = centerX;
        this.centerZ = centerZ;
        this.size = size;
        this.random = random;
    }

    @Override
    public final Vec3i provide() {
        final double x = getRandomCoordinate();
        final double z = getRandomCoordinate();
        final double y = getRandomCoordinate();

        final int minX = centerX - size;
        final int minZ = centerZ - size;
        final int minY = 0;

        final int maxX = centerX + size;
        final int maxZ = centerZ + size;
        final int maxY = 255;

        return new Vec3i(
                scaleCoordinate(x, minX, maxX),
                scaleCoordinate(y, minY, maxY),
                scaleCoordinate(z, minZ, maxZ)
        );
    }

    private int scaleCoordinate(double coordinate, double min, double max) {
        return (int) (coordinate * (max - min) + min);
    }

    private double getRandomCoordinate() {
        return (double) random.nextInt(size) / (double) size;
    }
}
