package com.example.cafemanager.ui.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.cafemanager.ui.Fragment.NhanVienFragment;
import com.example.cafemanager.ui.Fragment.MemberFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0:
                return  new MemberFragment();
            case 1 :
                return new NhanVienFragment();
            default :
                return new MemberFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch(position) {
            case 0:title = "   VIP   ";
                break;
            case 1:title = "  Staff  ";
                break;
        }
        return  title;
    }
}
