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

public record DimensionalVec3d(RegistryKey<World> dimension, Vec3d vec3d) {
    public DimensionalVec3d(@NotNull RegistryKey<World> dimension, @NotNull Vec3d vec3d) {
        this.dimension = dimension;
        this.vec3d = vec3d;
    }

    public DimensionalVec3d(@NotNull Entity entity) {
        this(entity.world.getRegistryKey(), entity.getPos());
    }

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
