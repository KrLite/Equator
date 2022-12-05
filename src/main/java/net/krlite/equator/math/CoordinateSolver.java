package net.krlite.equator.math;

import net.krlite.equator.core.DimensionalVec3d;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Optional;

public class CoordinateSolver {
    /**
     * Solves the <b>reverted</b> angle between the {@link PlayerEntity}'s vision and the destination {@link DimensionalVec3d}, without ignoring dimensions.
     * @param player        The {@link PlayerEntity} to solve the angle for.
     * @param destination   The {@link DimensionalVec3d} to solve the angle to.
     * @return              The <b>reverted</b> angle between the {@link PlayerEntity}'s vision and the destination {@link DimensionalVec3d}.
     */
    public static Optional<Double> angleBehindCamera(PlayerEntity player, DimensionalVec3d destination) {
        return angleBehindCamera(player, destination, false);
    }

    /**
     * Solves the <b>reverted</b> angle between the {@link PlayerEntity}'s vision and the destination {@link DimensionalVec3d}.
     * @param player            The {@link PlayerEntity} to solve the angle for.
     * @param destination       The {@link DimensionalVec3d} to solve the angle to.
     * @param ignoreDimension   Whether to ignore the dimension of the destination {@link DimensionalVec3d} or not.
     * @return                  The <b>reverted</b> angle between the {@link PlayerEntity}'s vision and the destination {@link DimensionalVec3d}.
     */
    public static Optional<Double> angleBehindCamera(PlayerEntity player, DimensionalVec3d destination, boolean ignoreDimension) {
        Optional<Double> optional = angleInFrontOfCamera(player, destination, ignoreDimension);

        return optional.map(AngleSolver::revert);
    }

    /**
     * Solves the angle between the {@link PlayerEntity}'s vision and the destination {@link DimensionalVec3d}, without ignoring dimensions.
     * @param player        The {@link PlayerEntity} to solve the angle for.
     * @param destination   The {@link DimensionalVec3d} to solve the angle to.
     * @return              The angle between the {@link PlayerEntity}'s vision and the destination {@link DimensionalVec3d}.
     */
    public static Optional<Double> angleInFrontOfCamera(PlayerEntity player, DimensionalVec3d destination) {
        return angleInFrontOfCamera(player, destination, false);
    }

    /**
     * Solves the angle between the {@link PlayerEntity}'s vision and the destination {@link DimensionalVec3d}.
     * @param player            The {@link PlayerEntity} to solve the angle for.
     * @param destination       The {@link DimensionalVec3d} to solve the angle to.
     * @param ignoreDimension   Whether to ignore the dimension of the destination {@link DimensionalVec3d} or not.
     * @return                  The angle between the {@link PlayerEntity}'s vision and the destination {@link DimensionalVec3d}.
     */
    public static Optional<Double> angleInFrontOfCamera(PlayerEntity player, DimensionalVec3d destination, boolean ignoreDimension) {
        if ( ignoreDimension || destination.getDimension().equals(player.getWorld().getRegistryKey()) ) {
            double
                    dstAngle = AngleSolver.clockwiseToPositive(
                            Math.atan2(
                                    (destination.getVec3d().getZ() + 0.5) - player.getZ(),
                                    (destination.getVec3d().getX() + 0.5) - player.getX()
                            ) * 180 / Math.PI + 180
                    ),
                    cameraAngle = AngleSolver.clockwiseToPositive((player.getYaw() % 360 + 360 + 270) % 360);

            return Optional.of(AngleSolver.positiveIncludePositive(cameraAngle, dstAngle));
        }

        return Optional.empty();
    }

    /**
     * Solves the distance between two {@link DimensionalVec3d}s.
     * @param source        The source {@link DimensionalVec3d}.
     * @param destination   The destination {@link DimensionalVec3d}.
     * @return              The distance between the two {@link DimensionalVec3d}s.
     */
    public static Optional<Double> distance(DimensionalVec3d source, DimensionalVec3d destination) {
        return source.getDimension() != destination.getDimension()
                ? Optional.empty()
                : Optional.of(source.getVec3d().distanceTo(destination.getVec3d()));
    }
}
