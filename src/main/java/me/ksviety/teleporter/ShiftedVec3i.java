package me.ksviety.teleporter;

import net.minecraft.util.math.Vec3i;

public class ShiftedVec3i extends Vec3i {
    public static ShiftedVec3i shift(Vec3i original, int shift, ShiftAxis axis) {
        int x = original.getX();
        int y = original.getY();
        int z = original.getZ();

        if (axis == ShiftAxis.Forward)
            z += shift;
        else if (axis == ShiftAxis.Backward)
            z -= shift;
        else if (axis == ShiftAxis.Right)
            x += shift;
        else if (axis == ShiftAxis.Left)
            x -= shift;

        return new ShiftedVec3i(x, y, z);
    }

    private ShiftedVec3i(int x, int y, int z) {
        super(x, y, z);
    }
}
