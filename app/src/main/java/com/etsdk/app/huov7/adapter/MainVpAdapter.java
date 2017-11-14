package com.etsdk.app.huov7.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.etsdk.app.huov7.ui.fragment.H5GameFragment;
import com.etsdk.app.huov7.ui.fragment.MainMineFragmentNew;
import com.etsdk.app.huov7.ui.fragment.MainPageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu hong liang on 2016/9/23.
 */

public class MainVpAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList=new ArrayList<>();
    public MainVpAdapter(FragmentManager fm) {
        super(fm);
        fragmentList.add(new MainPageFragment());
//        fragmentList.add(new MainGameFragment());
//        fragmentList.add(new MainNewsFragment());
        fragmentList.add(new H5GameFragment());
        fragmentList.add(new MainMineFragmentNew());
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void reloadFragment(int position){
//        WebViewFragment fragment = (WebViewFragment) fragmentList.get(position);
//        fragment.reloadFragment();
    }
}
