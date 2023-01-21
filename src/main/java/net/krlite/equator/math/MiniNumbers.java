package net.krlite.equator.math;

public class MiniNumbers {
	private static String toMiniNumber(String doubleNumber, boolean alwaysWithSign, String[] numbers, String plus, String minus, String dot) {
		String[] parts = doubleNumber.split("\\.");
		String integerPart = parts[0];
		String decimalPart = parts.length > 1 ? parts[1] : null;
		StringBuilder result = new StringBuilder();
		if (integerPart.startsWith("-")) {
			result.append(minus);
			integerPart = integerPart.substring(1);
		} else if (alwaysWithSign) result.append(plus);
		for (int i = 0; i < integerPart.length(); i++)
			result.append(numbers[Integer.parseInt(integerPart.substring(i, i + 1))]);
		if (decimalPart != null) {
			result.append(dot);
			for (int i = 0; i < decimalPart.length(); i++)
				result.append(numbers[Integer.parseInt(decimalPart.substring(i, i + 1))]);
		}
		return result.toString();
	}

	public enum Sup {
		ZERO(0, "⁰"),
		ONE(1, "¹"),
		TWO(2, "²"),
		THREE(3, "³"),
		FOUR(4, "⁴"),
		FIVE(5, "⁵"),
		SIX(6, "⁶"),
		SEVEN(7, "⁷"),
		EIGHT(8, "⁸"),
		NINE(9, "⁹");

		private final int number;
		private final String value;

		Sup(int number, String value) {
			this.number = number;
			this.value = value;
		}

		public static final String PLUS = "⁺";
		public static final String MINUS = "⁻";
		public static final String EQUALS = "⁼";
		public static final String OPEN_PAREN = "⁽";
		public static final String CLOSE_PAREN = "⁾";
		public static final String N = "ⁿ";
		public static final String DOT = "˙";

		public static final String[] NUMBERS = new String[]{
			ZERO.value, ONE.value, TWO.value, THREE.value, FOUR.value,
			FIVE.value, SIX.value, SEVEN.value, EIGHT.value, NINE.value
		};

		public static String toMiniNumber(double number, boolean alwaysWithSign) {
			return MiniNumbers.toMiniNumber(Double.toString(number), alwaysWithSign, NUMBERS, PLUS, MINUS, DOT);
		}

		public static String toMiniNumber(double number) {
			return toMiniNumber(number, false);
		}
	}

	public enum Sub {
		ZERO(0, "₀"),
		ONE(1, "₁"),
		TWO(2, "₂"),
		THREE(3, "₃"),
		FOUR(4, "₄"),
		FIVE(5, "₅"),
		SIX(6, "₆"),
		SEVEN(7, "₇"),
		EIGHT(8, "₈"),
		NINE(9, "₉");

		private final int number;
		private final String value;

		Sub(int number, String value) {
			this.number = number;
			this.value = value;
		}

		public static final String PLUS = "₊";
		public static final String MINUS = "₋";
		public static final String EQUALS = "₌";
		public static final String OPEN_PAREN = "₍";
		public static final String CLOSE_PAREN = "₎";
		public static final String N = "ₙ";
		public static final String DOT = ".";

		public static final String[] NUMBERS = new String[]{
			ZERO.value, ONE.value, TWO.value, THREE.value, FOUR.value,
			FIVE.value, SIX.value, SEVEN.value, EIGHT.value, NINE.value
		};

		public static String toMiniNumber(double number, boolean alwaysWithSign) {
			return MiniNumbers.toMiniNumber(Double.toString(number), alwaysWithSign, NUMBERS, PLUS, MINUS, DOT);
		}

		public static String toMiniNumber(double number) {
			return toMiniNumber(number, false);
		}
	}
}
