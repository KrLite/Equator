package net.krlite.equator.core;

import net.minecraft.util.Util;

/**
 * A simple timer class that can be used to measure the time elapsed in a period, in milliseconds.
 */
public class Timer {
    private final long origin;

    /**
     * Creates a new timer with the current time as the origin.
     */
    public Timer() {
        origin = Util.getMeasuringTimeMs();
    }

    /**
     * Creates a new timer with the given time as the origin.
     */
    @Deprecated
    public Timer(long origin) {
        this.origin = origin;
    }

    /**
     * @return  The origin time.
     */
    public long getOrigin() {
        return origin;
    }

    /**
     * @return  The time elapsed since the timer was created, in milliseconds.
     */
    public long getElapsed() {
        return Util.getMeasuringTimeMs() - origin;
    }

    /**
     * Calculates the given time with the origin time, in milliseconds.
     * @return  The time elapsed.
     */
    @Deprecated
    public long calculate(long time) {
        return time - origin;
    }
}
