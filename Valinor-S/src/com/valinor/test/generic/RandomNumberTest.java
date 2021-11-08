package com.valinor.test.generic;

import com.valinor.StackLogger;
import com.valinor.util.Utils;
/*
 * In this class, we can test all kinds of random number generation,
 * especially from the Misc utility class.
 */
public class RandomNumberTest {
    public static void main(String[] args) {
        StackLogger.enableStackLogger();
        System.out.println("Running RandomNumberTest.");
        int[] randoms = Utils.uniqueRandoms(1, 10, 10);
        for (int random : randoms) {
            System.out.println("Random number is: " + random);
        }
    }

}
