package com.example.razdanr.myapplication;

import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class RegisterActivityUnitTest {
    @Rule
    public ActivityTestRule<RegisterActivity> registerActivityTestRule = new ActivityTestRule<RegisterActivity>(RegisterActivity.class);
    private RegisterActivity registerActivity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(RegisterActivity.class.getName(), null, false);
    @Before
    public void setUp() throws Exception {
        registerActivity = registerActivityTestRule.getActivity();
    }

    @Test
    public void testregisterChecker() throws Exception {
        String email="rr@gmail.com";
        String password="qwerty";
        String suitID="H12345";
        boolean bool= registerActivity.registerChecker(email,password,suitID);

        boolean expected= true;

        assertEquals(expected,bool);
    }

    @After
    public void tearDown() throws Exception {
        registerActivity = null;
    }


}