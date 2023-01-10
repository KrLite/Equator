package net.krlite.equator.debug;

import net.krlite.equator.geometry.Line;
import net.krlite.equator.geometry.Node;

public class Example {
	public static void main(String[] args) {
		Node n1 = new Node(0, 0), n2 = new Node(50, 0), n3 = new Node(50, 50), n4 = new Node(0, 50);
		Line l1 = new Line(n1, n2), l2 = new Line(n1, n3), l3 = new Line(n1, n4), l4 = new Line(n4, n1);
		System.out.println(l1.clockwiseDegree());
		System.out.println(l3.intersect(l2));
	}
}
