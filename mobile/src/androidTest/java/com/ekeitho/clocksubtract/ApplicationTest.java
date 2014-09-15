package com.ekeitho.clocksubtract;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
import android.test.suitebuilder.TestSuiteBuilder;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ApplicationTest extends TestSuite {
    public static Test suite() {

        return new TestSuiteBuilder(ApplicationTest.class)
                .includeAllPackagesUnderHere().build();
    }

    public ApplicationTest() {
        super();
    }
}