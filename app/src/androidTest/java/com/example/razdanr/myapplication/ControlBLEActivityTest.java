package com.example.razdanr.myapplication;

import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;



public class ControlBLEActivityTest extends ActivityInstrumentationTestCase2<ControlBLEActivity> {
    public ControlBLEActivityTest() {
        super(ControlBLEActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @SmallTest
    public void testTextView()
    {
        TextView deviceaddr = (TextView)getActivity().findViewById(R.id.device_address);
        assertNotNull(deviceaddr);
        TextView connstate = (TextView)getActivity().findViewById(R.id.connection_state);
        assertNotNull(connstate);
        TextView datavalue = (TextView)getActivity().findViewById(R.id.data_value);
        assertNotNull(datavalue);
    }
    @SmallTest
    public void testListView()
    {
        ExpandableListView gattlist = (ExpandableListView)getActivity().findViewById(R.id.gatt_services_list);
        assertNotNull(gattlist);

    }

    @SmallTest
    public void testButton()
    {
        Button fastForward = (Button)getActivity().findViewById(R.id.bFastForward);
        assertNotNull(fastForward);
        Button stop = (Button)getActivity().findViewById(R.id.bStop);
        assertNotNull(stop);
        Button play = (Button)getActivity().findViewById(R.id.bPlay);
        assertNotNull(play);
    }


    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
