//YA NO SE USA ESTA CLASE SE PUEDE ELIMINAR

package com.bassanidevelopment.santiago.hci_movil;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionStatePageAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList();
    public SectionStatePageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment f){
        fragmentList.add(f);
    }

    @Override
    public Fragment getItem(int position) {

        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
