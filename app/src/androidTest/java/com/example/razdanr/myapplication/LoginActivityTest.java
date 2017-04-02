package com.example.razdanr.myapplication;

import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    public LoginActivityTest() {
        super(LoginActivity.class);
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
    }

    @SmallTest
    public void testButton()
    {
        Button login = (Button)getActivity().findViewById(R.id.bLogin);
        assertNotNull(login);
    }

    @SmallTest
    public void testTextView()
    {
        TextView register = (TextView)getActivity().findViewById(R.id.tvRegister);
        assertNotNull(register);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
