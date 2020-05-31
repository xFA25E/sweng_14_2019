package it.polimi.project14;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import it.polimi.project14.common.EventTests;
import it.polimi.project14.common.SearchFilterTests;
import it.polimi.project14.server.EventStorageImplTests;
import it.polimi.project14.user.CapsTests;
import it.polimi.project14.user.UserTests;

public class CivilProtectionTests {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(
            EventTests.class,
            SearchFilterTests.class,
            EventStorageImplTests.class,
            CapsTests.class,
            UserTests.class
        );

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println("Result == " + result.wasSuccessful());
    }
}
