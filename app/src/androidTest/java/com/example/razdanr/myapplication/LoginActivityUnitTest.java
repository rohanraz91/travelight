package com.example.razdanr.myapplication;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.widget.EditText;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.getIdlingResources;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;


public class LoginActivityUnitTest {
    @Rule
    public ActivityTestRule<LoginActivity> loginActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);
    private LoginActivity loginActivity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(UserHome.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        loginActivity = loginActivityTestRule.getActivity();
    }

    @Test
    public void testOnClick()
    {
        assertNotNull(loginActivity.findViewById(R.id.bLogin));
        onView(withId(R.id.bLogin)).perform(click());
        Activity userHome = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNull(userHome);

    }

    @Test
    public void testChecker()
    {
        String email="rr@gmail.com";
        String password="qwerty";
        boolean bool= loginActivity.checker(email,password);

        boolean expected= true;

        assertEquals(expected,bool);
    }

    @After
    public void tearDown() throws Exception {
        loginActivity = null;
    }

}