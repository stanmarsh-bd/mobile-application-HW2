package com.example.homework2.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.homework2.Model.Announcements;
import com.example.homework2.Model.FoodList;
import com.example.homework2.Model.News;

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            FoodList foodList = new FoodList();
            return foodList;
        }
        else if(position == 1){
            Announcements announcement = new Announcements();
            return announcement;
        }
        else if(position == 2){
            News news = new News();
            return news;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "FoodList";
        }else if (position == 1) {
            return "Announcements";
        }else if(position == 2){
            return "News";
        }
        return null;
    }
}
