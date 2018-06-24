package com.yxys365.smartglasses.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yxys365.smartglasses.fragment.HomeFragment;
import com.yxys365.smartglasses.fragment.LyFragment;
import com.yxys365.smartglasses.fragment.MineFragment;
import com.yxys365.smartglasses.fragment.PdFragment;


/**
 * Created by Administrator on 2016/8/3.
 */
public class MainVpAdapter extends FragmentPagerAdapter {
    private HomeFragment homeFragment = null;
    private LyFragment lyFragment = null;
    private PdFragment pdFragment = null;
    private MineFragment mineFragment=null;

    public MainVpAdapter(FragmentManager fm) {
        super(fm);
        homeFragment = new HomeFragment();
        lyFragment = new LyFragment();
        pdFragment = new PdFragment();
        mineFragment = new MineFragment();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = homeFragment;
                break;
            case 1:
                fragment = lyFragment;
                break;
            case 2:
                fragment = pdFragment;
                break;
            case 3:
                fragment=mineFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

}
