package net.krlite.equator.base;

import net.minecraft.util.Util;

public class Timer {
    private final long init;

    public Timer() {
        init = Util.getMeasuringTimeMs();
    }

    @Deprecated
    public Timer(long init) {
        this.init = init;
    }

    public long initializedTime() {
        return init;
    }

    public long queue() {
        return Util.getMeasuringTimeMs() - init;
    }

    public double queue(int tenfolds) {
        return (Util.getMeasuringTimeMs() - init) / Math.pow(10, tenfolds);
    }

    @Deprecated
    public long calculate(long time) {
        return time - init;
    }
}
