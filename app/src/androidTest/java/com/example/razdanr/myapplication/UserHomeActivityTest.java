package com.example.razdanr.myapplication;

import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ListView;


public class UserHomeActivityTest extends ActivityInstrumentationTestCase2<UserHomeActivity> {
    public UserHomeActivityTest() {
        super(UserHomeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @SmallTest
    public void testButton()
    {
        Button logout = (Button)getActivity().findViewById(R.id.bLogout);
        assertNotNull(logout);
        Button scan = (Button)getActivity().findViewById(R.id.bScan);
        assertNotNull(scan);
    }

    @SmallTest
    public void testListView()
    {
        ListView view = (ListView)getActivity().findViewById(R.id.lelist);
        assertNotNull(view);

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
