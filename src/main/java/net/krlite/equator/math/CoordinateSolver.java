package net.krlite.equator.math;

import net.krlite.equator.base.DimensionalVec3d;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Optional;

public class CoordinateSolver {
    public static Optional<Double> angleBehindCamera(DimensionalVec3d dst, PlayerEntity player) {
        return angleBehindCamera(dst, player, false);
    }

    public static Optional<Double> angleBehindCamera(DimensionalVec3d dst, PlayerEntity player, boolean ignoreDimension) {
        Optional<Double> optional = angleInFrontOfCamera(dst, player, ignoreDimension);

        return optional.map(AngleSolver::revert);
    }

    public static Optional<Double> angleInFrontOfCamera(DimensionalVec3d dst, PlayerEntity player) {
        return angleInFrontOfCamera(dst, player, false);
    }

    public static Optional<Double> angleInFrontOfCamera(DimensionalVec3d dst, PlayerEntity player, boolean ignoreDimension) {
        if ( ignoreDimension || dst.getDimension().equals(player.getWorld().getRegistryKey()) ) {
            double
                    dstAngle = AngleSolver.clockwiseToPositive(
                            Math.atan2(
                                    (dst.getVec3d().getZ() + 0.5) - player.getZ(),
                                    (dst.getVec3d().getX() + 0.5) - player.getX()
                            ) * 180 / Math.PI + 180
                    ),
                    cameraAngle = AngleSolver.clockwiseToPositive((player.getYaw() % 360 + 360 + 270) % 360);

            return Optional.of(AngleSolver.positiveIncludePositive(cameraAngle, dstAngle));
        }

        return Optional.empty();
    }

    public static Optional<Double> distance(DimensionalVec3d src, DimensionalVec3d dst) {
        return src.getDimension() != dst.getDimension()
                ? Optional.empty()
                : Optional.of(src.getVec3d().distanceTo(dst.getVec3d()));
    }
}
