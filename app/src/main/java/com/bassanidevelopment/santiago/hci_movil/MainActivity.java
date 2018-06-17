package com.bassanidevelopment.santiago.hci_movil;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bassanidevelopment.santiago.hci_movil.Fragments.DeviceTypeFragment;
import com.bassanidevelopment.santiago.hci_movil.Fragments.MostUsedFragment;
import com.bassanidevelopment.santiago.hci_movil.Fragments.RoomsFragment;
import com.bassanidevelopment.santiago.hci_movil.Fragments.RoutinesFragment;

public class MainActivity extends AppCompatActivity {


    private  static ViewPager viewPager;
    private SectionStatePageAdapter sectionStatePageAdapter;
    public static  ProgressBar spinner;
    private FragmentManager manager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction;

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
        fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, f);
        fragmentTransaction.commit();

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
        setSupportActionBar(myToolbar);
        //manager.addOnBackStackChangedListener(); necestamos esto para que cambie el titulo del
        //toolbar cada vez que apretas para atras

        fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, new MostUsedFragment());
        fragmentTransaction.commit();

        // ATTENTION: in the future this can't be harcoded, it must be a settings option
        Context context = getApplicationContext();

        Intent intent = new Intent(MainActivity.this, NotificationHandler.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        long interval = 10*1000;
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + interval,
                interval,alarmIntent);
        Log.d("Alarm","Inexact alarm setted");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, AppPreferences.class));
                //Toast.makeText(this, "Settings Selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                break;
        }
        return  super.onOptionsItemSelected(item);
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
