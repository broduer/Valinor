package com.valinor.test;

import com.valinor.StackLogger;
import com.valinor.test.generic.*;

public class Tests {
    public static void main(String[] args) {
        StackLogger.enableStackLogger();
        System.out.println("Running TimeZoneTest");
        TimeZoneTest.main(args);
        //System.out.println("Running ListVersusHashSetTest");
        //ArrayListVersusLinkedHashSetTest.main(args);
        //System.out.println("Generating Spawnable PVP Items Constants Array");
        //ConstantGeneratorTest.main(args);
        //System.out.println("Running HashSetVersusLinkedHashSetTest");
        //HashSetVersusLinkedHashSetTest.main(args);
        System.out.println("Running FractionDecimalTest");
        FractionDecimalTest.main(args);
        System.out.println("Running JavaNullabilityTest");
        JavaNullablityTest.main(args);
    }
}
