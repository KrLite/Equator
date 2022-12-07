package net.krlite.equator.core;

import net.minecraft.util.Util;

/**
 * A simple timer class that can be used to measure the time elapsed in a period, in milliseconds.
 */
public class Timer {
    private long origin;
    private final long period;

    /**
     * Creates a new timer with the current time as the origin.
     */
    public Timer() {
        this.origin = Util.getMeasuringTimeMs();
        this.period = Long.MAX_VALUE;
    }

    /**
     * Creates a new timer with the current time as the origin and a specified period as the limit.
     * @param period    The period of the timer.
     */
    public Timer(long period) {
        this.origin = Util.getMeasuringTimeMs();
        this.period = period;
    }

    /**
     * @return  The origin time.
     */
    public long queueOrigin() {
        return origin;
    }

    public long queuePeriod() {
        return period;
    }

    /**
     * @return  The time elapsed since the timer was created, in milliseconds.
     */
    public long queue() {
        return Math.min(queueElapsed(), period);
    }

    /**
     * @return  The time elapsed since the timer was created, in milliseconds, synced with the game's time.
     */
    public long queueElapsed() {
        return Util.getMeasuringTimeMs() - origin;
    }

    public double queueAsPercentage() {
        return (double) queue() / (double) period;
    }

    public boolean isPresent() {
        return queueElapsed() >= 0 && queueElapsed() <= period;
    }

    /**
     * Sets the origin time to the current time.
     */
    public Timer reset() {
        this.origin = Util.getMeasuringTimeMs();
        return this;
    }

    /**
     * Calculates the given time with the current time, in milliseconds.
     * @return  The time elapsed.
     */
    @Deprecated
    public long calculate(long time) {
        return time - queue();
    }
}
