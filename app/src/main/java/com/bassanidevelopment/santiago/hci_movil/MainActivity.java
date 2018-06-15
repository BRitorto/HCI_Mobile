package com.bassanidevelopment.santiago.hci_movil;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.bassanidevelopment.santiago.hci_movil.Fragments.DeviceTypeFragment;
import com.bassanidevelopment.santiago.hci_movil.Fragments.MostUsedFragment;
import com.bassanidevelopment.santiago.hci_movil.Fragments.RoomFragment;
import com.bassanidevelopment.santiago.hci_movil.Fragments.RoomsFragment;
import com.bassanidevelopment.santiago.hci_movil.Fragments.RoutinesFragment;

public class MainActivity extends AppCompatActivity {


    private  static ViewPager viewPager;
    private SectionStatePageAdapter sectionStatePageAdapter;
    public static  ProgressBar spinner;
    private FragmentManager manager = getSupportFragmentManager();
    private FragmentTransaction ft;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Toolbar myToolbar = findViewById(R.id.upper_toolbar);
            setSupportActionBar(myToolbar);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    myToolbar.setTitle(R.string.home);
                    setFragment(new MostUsedFragment());

                    return true;
                case R.id.navigation_rooms:
                    setFragment(new RoomsFragment());


                    return true;
                case R.id.navigation_routines:
                    myToolbar.setTitle(R.string.routines);
                    setFragment(new RoutinesFragment());

                    return true;
            }
            return false;
        }
    };

    public void setFragment(Fragment f){
        ft = manager.beginTransaction();
        ft.replace(R.id.fragment_place, f);
        ft.commit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.upper_toolbar);
        myToolbar.inflateMenu(R.menu.toolbar_menu);
        myToolbar.setTitle(R.string.home);

        //manager.addOnBackStackChangedListener(); necestamos esto para que cambie el titulo del
        //toolbar cada vez que apretas para atras

        ft = manager.beginTransaction();
        ft.replace(R.id.fragment_place, new MostUsedFragment());
        ft.commit();


        //spinner = findViewById(R.id.progressBar1);
        //spinner.setVisibility(View.INVISIBLE);
        //sectionStatePageAdapter = new SectionStatePageAdapter(getSupportFragmentManager());
        //viewPager = findViewById(R.id.container);
        //set up the pager
        //setupViewPager(viewPager);
    }


    private  void setupViewPager(ViewPager viewPager){
        //by default it inflates the first fragment
        sectionStatePageAdapter.addFragment(new MostUsedFragment());
        sectionStatePageAdapter.addFragment(new RoomsFragment());
        sectionStatePageAdapter.addFragment(new RoutinesFragment());
        //sectionStatePageAdapter.addFragment(new RoomFragment());
        sectionStatePageAdapter.addFragment(new DeviceTypeFragment());
        viewPager.setAdapter(sectionStatePageAdapter);
    }


    /**
    * Should be used from within the fragments as a way swap from one to other
    * @param fragmentIndex the index of the fragment you want to show
    */
    public static  void  setViewPager(int fragmentIndex){
        viewPager.setCurrentItem(fragmentIndex);
    }

}
