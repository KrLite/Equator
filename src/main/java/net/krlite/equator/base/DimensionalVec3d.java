package net.krlite.equator.base;

import net.krlite.equator.math.CoordinateSolver;
import net.minecraft.entity.Entity;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

public class DimensionalVec3d {
    private final RegistryKey<World> dimension;
    private final Vec3d vec3d;

    public DimensionalVec3d(RegistryKey<World> dimension, Vec3d vec3d) {
        this.dimension = dimension;
        this.vec3d = vec3d;
    }

    public DimensionalVec3d(@NotNull Entity entity) {
        dimension = entity.world.getRegistryKey();
        vec3d = entity.getPos();
    }

    public DimensionalVec3d(@NotNull GlobalPos globalPos) {
        dimension = globalPos.getDimension();
        vec3d = Vec3d.of(globalPos.getPos());
    }

    public RegistryKey<World> getDimension() {
        return dimension;
    }

    public Vec3d getVec3d() {
        return vec3d;
    }

    public Optional<Double> distance(DimensionalVec3d dst) {
        return CoordinateSolver.distance(this, dst);
    }

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
