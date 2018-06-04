package com.bassanidevelopment.santiago.hci_movil;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.bassanidevelopment.santiago.hci_movil.Fragments.MostUsedFragment;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private ViewPager viewPager;
    private SectionStatePageAdapter sectionStatePageAdapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.home);
                    setViewPager(0);
                    return true;
                case R.id.navigation_rooms:
                    //mTextMessage.setText(R.string.rooms);
                    setViewPager(0);
                    return true;
                case R.id.navigation_routines:
                    //mTextMessage.setText(R.string.routines);
                    setViewPager(0);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.upper_toolbar);
        myToolbar.inflateMenu(R.menu.toolbar_menu);
        myToolbar.setNavigationIcon(R.drawable.ic_back);
        myToolbar.setTitle(R.string.home);

        sectionStatePageAdapter = new SectionStatePageAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.conatiner);
        //set up the pager
        setupViewPager(viewPager);
    }


    private  void setupViewPager(ViewPager viewPager){
        SectionStatePageAdapter adapter = new SectionStatePageAdapter(getSupportFragmentManager());
        //by default it inflates the first fragment
        adapter.addFragment(new MostUsedFragment());
        viewPager.setAdapter(adapter);
    }


    /**
    * Should be used from within the fragments as a way swap from one to other
    * @param fragmentIndex the index of the fragment you want to show
    */
    public void setViewPager(int fragmentIndex){
        viewPager.setCurrentItem(fragmentIndex);
    }



}
