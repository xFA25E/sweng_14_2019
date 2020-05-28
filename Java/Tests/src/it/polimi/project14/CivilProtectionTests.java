package it.polimi.project14;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import it.polimi.project14.common.EventTests;
import it.polimi.project14.common.SearchFilterTests;

public class CivilProtectionTests {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(EventTests.class, SearchFilterTests.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println("Result == " + result.wasSuccessful());
    }
}
