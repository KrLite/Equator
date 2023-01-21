package net.krlite.equator.debug;

import net.krlite.equator.color.PreciseColors;
import net.krlite.equator.geometry.Node;

public class Debugger {
	public static void main(String[] args) {
		Node.Tinted tinted = new Node(0, 0).new Tinted(PreciseColors.DARK_RED);
		tinted = tinted.operate(node -> node.shift(1, 1));
	}
}
