package net.krlite.equator.math;

import net.minecraft.entity.player.PlayerEntity;

import java.util.Objects;
import java.util.Optional;

/**
 * A class that solves for the coordinates in the world.
 */
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
        return optional.map(AngleFunctions::revert);
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
        if (ignoreDimension || Objects.equals(destination.getDimension(), player.getWorld().getRegistryKey())) {
            double
                    dstAngle = AngleFunctions.clockwiseToPositive(
                            Math.atan2(
                                    (destination.pos.getZ() + 0.5) - player.getZ(),
                                    (destination.pos.getX() + 0.5) - player.getX()
                            ) * 180 / Math.PI + 180
                    ),
                    cameraAngle = AngleFunctions.clockwiseToPositive((player.getYaw() % 360 + 360 + 270) % 360);

            return Optional.of(AngleFunctions.positiveIncludePositive(cameraAngle, dstAngle));
        }
        return Optional.empty();
    }
}
