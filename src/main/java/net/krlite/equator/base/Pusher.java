package net.krlite.equator.base;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

public record Pusher(@NotNull AtomicBoolean ready) {
	public Pusher(boolean ready) {
		this(new AtomicBoolean(ready));
	}

	public Pusher() {
		this(false);
	}

	public void push() {
		ready.set(true);
	}

	public void push(@NotNull Runnable runnable) {
		push();
		runnable.run();
	}

	public boolean pull() {
		return ready.get() && ready.getAndSet(false);
	}

	public void run(@NotNull Runnable runnable) {
		if (pull()) runnable.run();
	}

	public void or(boolean or, @NotNull Runnable runnable) {
		if (or || pull()) runnable.run();
	}

	public void and(boolean and, @NotNull Runnable runnable) {
		if (and && pull()) runnable.run();
	}

	public Pusher paste() {
		return new Pusher(new AtomicBoolean(ready.get()));
	}
}
