package top.weiyuexin.tetl;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomePagerAdapter extends FragmentPagerAdapter {
    //定义fragment列表，存放首页有显示的所有fragment
    List<Fragment> fragmentList=new ArrayList<>();
    String[] home_tabs_list = { "推荐", "生活", "考研","学习","情感"};

    public HomePagerAdapter(@NonNull FragmentManager fm, int behavior,List<Fragment> fragments) {
        super(fm, behavior);
        fragmentList=fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return 5;  //这里改成具体的数，使用fragmentList.size()的话会出现数组越界的情况
    }
    @Override
    public CharSequence getPageTitle(int position) {
        System.out.println(position);
        return home_tabs_list[position];
    }
}
