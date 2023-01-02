package net.krlite.equator.math;

import net.minecraft.entity.Entity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

/**
 * A class that represents a 3D vector with a dimension.
 * @param dimension The dimension of the vector.
 * @param vec3d     The 3D vector.
 */
public record DimensionalVec3d(@NotNull RegistryKey<World> dimension, @NotNull Vec3d vec3d) {
    /**
     * Creates a {@link DimensionalVec3d} from a {@link RegistryKey RegistryKey(Dimension)}
     * and a {@link Vec3d}.
     * @param dimension The dimension of the vector.
     *                  If <code>null</code>, the dimension will be set to the overworld.
     * @param vec3d     The 3D vector.
     */
    public DimensionalVec3d(@Nullable RegistryKey<World> dimension, @NotNull Vec3d vec3d) {
        this.dimension = dimension == null ? World.OVERWORLD : dimension;
        this.vec3d = vec3d;
    }

    /**
     * Creates a {@link DimensionalVec3d} from a {@link Entity}.
     * @param entity The {@link Entity} to get the dimension and the 3D vector from.
     */
    public DimensionalVec3d(@NotNull Entity entity) {
        this(entity.world.getRegistryKey(), entity.getPos());
    }

    /**
     * Creates a {@link DimensionalVec3d} from a {@link GlobalPos}.
     * @param globalPos The {@link GlobalPos} to get the dimension and the 3D vector from.
     */
    public DimensionalVec3d(@NotNull GlobalPos globalPos) {
        this(globalPos.getDimension(), Vec3d.of(globalPos.getPos()));
    }

    public Optional<Double> distance(DimensionalVec3d destination) {
        return CoordinateSolver.distance(this, destination);
    }

    public boolean equals(@Nullable DimensionalVec3d other) {
        if (other == null) return false;
        return hashCode() == other.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (this == other) return true;
        if (other instanceof DimensionalVec3d) return equals((DimensionalVec3d) other);
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dimension, vec3d);
    }

    @Override
    public String toString() {
        return getClass().getName() + "{" + "dimension=" + dimension.toString() + ", vec3d=" + vec3d.toString() + "}";
    }
}
