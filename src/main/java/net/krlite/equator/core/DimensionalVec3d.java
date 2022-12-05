package net.krlite.equator.core;

import net.krlite.equator.math.CoordinateSolver;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

/**
 * A dimensional 3D vector that can be used to represent a position in a dimension.
 * @see RegistryKey
 * @see Vec3d
 */
public class DimensionalVec3d {
    /**
     * The dimension of the 3D vector.
     */
    private final RegistryKey<World> dimension;

    /**
     * The 3D vector.
     */
    private final Vec3d vec3d;

    /**
     * Creates a new dimensional 3D vector.
     * @param dimension The dimension of the 3D vector.
     * @param vec3d     The 3D vector.
     */
    public DimensionalVec3d(RegistryKey<World> dimension, Vec3d vec3d) {
        this.dimension = dimension;
        this.vec3d = vec3d;
    }

    /**
     * Creates a new dimensional 3D vector by the presence of an {@link Entity}.
     * @param entity    The dedicated {@link Entity}.
     */
    public DimensionalVec3d(@NotNull Entity entity) {
        dimension = entity.world.getRegistryKey();
        vec3d = entity.getPos();
    }

    /**
     * Creates a new dimensional 3D vector by a {@link GlobalPos}.
     * @param globalPos The dedicated {@link GlobalPos}.
     */
    public DimensionalVec3d(@NotNull GlobalPos globalPos) {
        dimension = globalPos.getDimension();
        vec3d = Vec3d.of(globalPos.getPos());
    }

    /**
     * Gets the dimension of the 3D vector.
     */
    public RegistryKey<World> getDimension() {
        return dimension;
    }

    /**
     * Gets the 3D vector.
     */
    public Vec3d getVec3d() {
        return vec3d;
    }

    /**
     * Solves the distance to another {@link DimensionalVec3d}.
     * @param destination   The destination {@link DimensionalVec3d}.
     * @return              The distance to the destination {@link DimensionalVec3d}.
     */
    public Optional<Double> distance(DimensionalVec3d destination) {
        return CoordinateSolver.distance(this, destination);
    }

    /**
     * Checks if two {@link DimensionalVec3d}s are equal.
     * @return  <code>true</code> only if the two objects are equal, otherwise <code>false</code>.
     */
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }

        if ( o == null || this.getClass() != o.getClass() ) {
            return false;
        }

        DimensionalVec3d d = (DimensionalVec3d) o;
        return Objects.equals(this.dimension, d.dimension) && Objects.equals(this.vec3d, d.vec3d);
    }

    public int hashCode() {
        return Objects.hash(this.dimension, this.vec3d);
    }

    public String toString() {
        return this.dimension + " " + this.vec3d;
    }
}
