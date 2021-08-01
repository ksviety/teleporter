package me.ksviety.teleporter;

import net.minecraft.util.math.Vec3i;

public class BoundRandomPositionProvider implements IPositionProvider {
    private final int centerX;
    private final int centerZ;
    private final int size;

    public BoundRandomPositionProvider(int centerX, int centerZ, int size) {
        this.centerX = centerX;
        this.centerZ = centerZ;
        this.size = size;
    }

    @Override
    public final Vec3i provide() {
        final double x = Math.random();
        final double z = Math.random();
        final double y = Math.random();

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
}
