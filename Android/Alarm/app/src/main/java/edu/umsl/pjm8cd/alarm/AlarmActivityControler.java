package edu.umsl.pjm8cd.alarm;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class AlarmActivityControler extends AppCompatActivity {
    ViewPager pager;
//    ListViewFragment listViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);

        FragmentManager manager = getSupportFragmentManager();
        pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(new AlarmAdapter(manager));

        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between pages, select the
                // corresponding tab.
                try {
                    getSupportActionBar().setSelectedNavigationItem(position);
                }catch(NullPointerException e) {
                    Log.d("CONTROLLER", "NPE tryint to get support action bar.");
                }
            }
        });


        ActionBar actionBar = getSupportActionBar();

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                pager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };

        actionBar.addTab(actionBar.newTab().setText("Alarm").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Timer").setTabListener(tabListener));




    }

    private class AlarmAdapter extends FragmentPagerAdapter {
        public AlarmAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ListViewFragment.newInstance();
//            ListViewFragment listViewFragment;
//            FragmentManager manager = getSupportFragmentManager();
////            if(position == 0) {
//                listViewFragment = (ListViewFragment) manager.findFragmentById(R.id.alarm_listing_layout);
//                if (listViewFragment == null) {
//                    listViewFragment = new ListViewFragment();
//                    manager.beginTransaction()
//                            .add(R.id.alarm_listing_layout, listViewFragment)
//                            .commit();
//                }
////            }
//            return listViewFragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
//    private ListViewFragment listViewFragment;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.blank_layout);
//        FragmentManager manager = getSupportFragmentManager();
//
//        listViewFragment = (ListViewFragment) manager.findFragmentById(R.id.alarm_listing_layout);
//        if (listViewFragment == null) {
//            listViewFragment = new ListViewFragment();
//            manager.beginTransaction()
//                    .add(R.id.alarm_listing_layout, listViewFragment)
//                    .commit();
//        }
//    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        listViewFragment.updateView();
//        checkDisplayEmpty();
//    }
//
//    /* Called from the add contact button. */
//    public void newContact(View v) {
////        Intent i = AlarmDetailActivity.newIntent(this);
////        startActivity(i);
////        checkDisplayEmpty();
//    }
//
//    /* If there are no contacts, this will display no contacts */
//    void checkDisplayEmpty() {
//        TextView notify = (TextView) findViewById(R.id.no_alarms);
//        if (listViewFragment.isEmpty()) {
//            notify.setVisibility(View.VISIBLE);
//        } else {
//            notify.setVisibility(View.GONE);
//        }
//    }
}
