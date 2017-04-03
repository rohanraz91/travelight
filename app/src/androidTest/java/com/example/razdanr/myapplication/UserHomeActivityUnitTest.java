package com.example.razdanr.myapplication;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class UserHomeActivityUnitTest {
    @Rule
    public ActivityTestRule<UserHomeActivity> userActivityTestRule = new ActivityTestRule<UserHomeActivity>(UserHomeActivity.class);
    private UserHomeActivity userActivity = null;
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(LoginActivity.class.getName(), null, false);
    Instrumentation.ActivityMonitor monitorb = getInstrumentation().addMonitor(ControlBLEActivity.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        userActivity = userActivityTestRule.getActivity();
    }

    @Test
    public void testLogoutIntent()
    {
        assertNotNull(userActivity.findViewById(R.id.bLogout));
        onView(withId(R.id.bLogout)).perform(click());
        Activity login = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNotNull(login);
    }

    @Test
    public void testScanIntent()
    {
        assertNotNull(userActivity.findViewById(R.id.lelist));
        onView(withId(R.id.bScan)).perform(click());
        onView(withId(R.id.lelist)).perform(click());
        Activity login = getInstrumentation().waitForMonitorWithTimeout(monitorb, 5000);
        assertNull(login);
    }


    @After
    public void tearDown() throws Exception {
        userActivity = null;
    }

}