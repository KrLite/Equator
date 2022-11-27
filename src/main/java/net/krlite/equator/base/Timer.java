package net.krlite.equator.base;

import net.minecraft.util.Util;

public class Timer {
    private final long origin;
    private Timer offset;

    public Timer() {
        origin = Util.getMeasuringTimeMs();
    }

    @Deprecated
    public Timer(long origin) {
        this.origin = origin;
    }

    public long getOrigin() {
        return origin;
    }

    public void suspend(long time) {

    }

    public long getElapsed() {
        return getDuration() - (offset == null ? 0 : offset.getElapsed());
    }

    public double getElapsed(int tenfolds) {
        return (getElapsed()) / Math.pow(10, tenfolds);
    }

    public long getDuration() {
        return Util.getMeasuringTimeMs() - origin;
    }

    public double getDuration(int tenfolds) {
        return (getDuration()) / Math.pow(10, tenfolds);
    }

    @Deprecated
    public long calculate(long time) {
        return time - origin;
    }
}
