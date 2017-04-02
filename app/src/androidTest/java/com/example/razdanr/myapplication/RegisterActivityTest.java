package com.example.razdanr.myapplication;

import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class RegisterActivityTest extends ActivityInstrumentationTestCase2<RegisterActivity> {
    public RegisterActivityTest() {
        super(RegisterActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @SmallTest
    public void testEditText()
    {
        EditText uname = (EditText)getActivity().findViewById(R.id.etUserName);
        assertNotNull(uname);
        EditText pass = (EditText)getActivity().findViewById(R.id.etPassword);
        assertNotNull(pass);
        EditText suitcasId = (EditText)getActivity().findViewById(R.id.etSuitcaseID);
        assertNotNull(pass);
    }

    @SmallTest
    public void testButton()
    {
        Button register = (Button)getActivity().findViewById(R.id.bRegister);
        assertNotNull(register);
    }
    @SmallTest
    public void testTextView()
    {
        TextView login = (TextView)getActivity().findViewById(R.id.tvLogin);
        assertNotNull(login);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
