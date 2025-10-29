package com.cooksys.ftd.assignments.objects;

public class Main {
	
	public static void main(String[] args) {
		// --- Basic construction & toString ---
        IRational r1 = new Rational(1, 2);
        IRational r2 = new Rational(-3, 4);
        IRational r3 = new Rational(0, -1); // edge: zero with negative denominator

        System.out.println("Rational r1 (1/2): " + r1);   // 1/2
        System.out.println("Rational r2 (-3/4): " + r2);  // -3/4
        System.out.println("Rational r3 (0/-1): " + r3);  // 0/1

        // --- Arithmetic with Rational (no auto-simplify) ---
        System.out.println("\nRational arithmetic (no auto-simplify):");
        System.out.println("r1 + r2 = " + r1.add(r2)); // (1/2) + (-3/4) = -1/4
        System.out.println("r1 - r2 = " + r1.sub(r2)); // (1/2) - (-3/4) = 5/4
        System.out.println("r1 * r2 = " + r1.mul(r2)); // (1/2) * (-3/4) = -3/8
        System.out.println("r1 / r2 = " + r1.div(r2)); // (1/2) / (-3/4) = -2/3

        // --- Inversion & Negation ---
        System.out.println("\nInvert & Negate:");
        System.out.println("negate(r2) = " + r2.negate()); // 3/4
        try {
            System.out.println("invert(r1) = " + r1.invert()); // 2/1
            System.out.println("invert(r3) = " + r3.invert()); // should throw
        } catch (IllegalStateException ise) {
            System.out.println("invert(r3) threw IllegalStateException (as expected): " + ise.getClass().getSimpleName());
        }

        // --- Division by a zero-numerator fraction -> denominator becomes 0 -> should throw ---
        System.out.println("\nDivision edge case:");
        IRational zeroHalf = new Rational(0, 2); // 0/2
        try {
            System.out.println("r1 / zeroHalf = " + r1.div(zeroHalf));
        } catch (IllegalArgumentException iae) {
            System.out.println("r1 / zeroHalf threw IllegalArgumentException (as expected): " + iae.getClass().getSimpleName());
        }

        // --- SimplifiedRational auto-reduces ---
        System.out.println("\nSimplifiedRational (auto-simplify):");
        IRational s1 = new SimplifiedRational(8, 12);    // 2/3
        IRational s2 = new SimplifiedRational(-10, -20); // 1/2
        IRational s3 = new SimplifiedRational(6, -9);    // -2/3
        IRational sZero = new SimplifiedRational(0, -5); // 0/-5 → "0/5" by toString

        System.out.println("s1 (8/12)  = " + s1);
        System.out.println("s2 (-10/-20) = " + s2);
        System.out.println("s3 (6/-9) = " + s3);
        System.out.println("sZero (0/-5) = " + sZero);

        System.out.println("\nSimplified arithmetic (results also simplified):");
        System.out.println("s1 + s3 = " + s1.add(s3)); // 2/3 + (-2/3) = 0/x → prints 0/…
        System.out.println("s2 * s3 = " + s2.mul(s3)); // 1/2 * (-2/3) = -1/3
        System.out.println("s2 / s1 = " + s2.div(s1)); // (1/2) / (2/3) = 3/4

        // --- Equality checks ---
        System.out.println("\nEquality:");
        System.out.println("new Rational(1,2).equals(new Rational(1,2)) -> " +
                new Rational(1,2).equals(new Rational(1,2))); // true
        System.out.println("new SimplifiedRational(2,4).equals(new SimplifiedRational(1,2)) -> " +
                new SimplifiedRational(2,4).equals(new SimplifiedRational(1,2))); // true
        System.out.println("new Rational(2,4).equals(new Rational(1,2)) -> " +
                new Rational(2,4).equals(new Rational(1,2))); // false (no auto-simplify)
	}

}
