package net.krlite.equator.math;

import net.krlite.equator.base.HashCodeComparable;
import net.krlite.equator.core.ShortStringable;
import net.minecraft.entity.Entity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class DimensionalVec3d extends HashCodeComparable implements ShortStringable, Cloneable {
    protected final @Nullable RegistryKey<World> dimension;

    protected final @NotNull Vec3d pos;

    public @Nullable RegistryKey<World> getDimension() {
        return dimension;
    }

    public @NotNull Vec3d getPos() {
        return pos;
    }

    public DimensionalVec3d(@Nullable RegistryKey<World> dimension, @NotNull Vec3d pos) {
        this.dimension = dimension;
        this.pos = pos;
    }

    public DimensionalVec3d(@NotNull Entity entity) {
        this(entity.world.getRegistryKey(), entity.getPos());
    }

    public DimensionalVec3d(@NotNull GlobalPos globalPos) {
        this(globalPos.getDimension(), Vec3d.of(globalPos.getPos()));
    }

    public Optional<Double> distance(DimensionalVec3d destination) {
        return getDimension() == null || getDimension().equals(destination.getDimension()) ?
                       Optional.of(getPos().distanceTo(destination.getPos())) :
                       Optional.empty();
    }

    @Override
    public String toString() {
        return getClass().getName() + "{" + formatFields() + "}";
    }

    @Override
    public DimensionalVec3d clone() {
        try {
            return (DimensionalVec3d) super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException(cloneNotSupportedException);
        }
    }
}
