package com.bassanidevelopment.santiago.hci_movil;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bassanidevelopment.santiago.hci_movil.Fragments.MostUsedFragment;
import com.bassanidevelopment.santiago.hci_movil.Fragments.RoomsFragment;
import com.bassanidevelopment.santiago.hci_movil.Fragments.RoutinesFragment;

public class MainActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private SectionStatePageAdapter sectionStatePageAdapter;
    public static  ProgressBar spinner;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Toolbar myToolbar = findViewById(R.id.upper_toolbar);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setViewPager(0);
                    myToolbar.setTitle(R.string.home);
                    //myToolbar.setNavigationIcon(null);
                    return true;
                case R.id.navigation_rooms:
                    setViewPager(1);
                    myToolbar.setTitle(R.string.rooms);
                    //myToolbar.setNavigationIcon(R.drawable.ic_back);

                    return true;
                case R.id.navigation_routines:
                    setViewPager(2);
                    myToolbar.setTitle(R.string.routines);
                    //myToolbar.setNavigationIcon(R.drawable.ic_back);

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.upper_toolbar);
        myToolbar.inflateMenu(R.menu.toolbar_menu);
        myToolbar.setTitle(R.string.home);
        //spinner = findViewById(R.id.progressBar1);
        //spinner.setVisibility(View.INVISIBLE);
        sectionStatePageAdapter = new SectionStatePageAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.container);
        //set up the pager
        setupViewPager(viewPager);
    }


    private  void setupViewPager(ViewPager viewPager){
        SectionStatePageAdapter adapter = new SectionStatePageAdapter(getSupportFragmentManager());
        //by default it inflates the first fragment
        adapter.addFragment(new MostUsedFragment());
        adapter.addFragment(new RoomsFragment());
        adapter.addFragment(new RoutinesFragment());
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
