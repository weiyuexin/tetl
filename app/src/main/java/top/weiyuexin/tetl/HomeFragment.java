package top.weiyuexin.tetl;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.RemoteException;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    //定义首页的搜索框
    private LinearLayout search_home;
    //定义当前屏幕的密度
    private DisplayMetrics dm;

    private PagerSlidingTabStrip home_tabs;
    private ViewPager viewPager;
    //右上角写文章按钮
    private ImageView toWriteArticle;

    //定义存放要显示的Fragment的数组
    ArrayList<Fragment> home_fragments = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //获取首页的搜索框
        View view = inflater.inflate(R.layout.fragment_home, null);

        initView(view);

        search_home = view.findViewById(R.id.search_home);
        //定义点击事件，点击跳转到搜索页面
        search_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建意图对象
                Intent skip_to_search = new Intent(getActivity(), SearchActivity.class);
                //激活意图
                startActivity(skip_to_search);
                //改变切换动画
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.anim_no);
            }
        });

        return view;
    }

    private void initView(View view) {
        //获取当前屏幕的密度
        dm = getResources().getDisplayMetrics();
        //获取viewpager实例
        viewPager =(ViewPager) view.findViewById(R.id.home_viewpager);
        //获取PagerSlidingTabStrip实例
        home_tabs = (PagerSlidingTabStrip)view.findViewById(R.id.home_tabs);
        //初始化右上角写文章按钮
        toWriteArticle=view.findViewById(R.id.toWriteArticle);
        //将要显示的fragment存放到数组中
        home_fragments.add(new HomeTuijianFragment());
        home_fragments.add(new HomeShenghuoFragment());
        home_fragments.add(new HomeKaoyanFragment());
        home_fragments.add(new HomeCet46Fragment());
        home_fragments.add(new HomeQingganFragment());

        //初始化适配器
        HomePagerAdapter homePagerAdapter = new HomePagerAdapter(getChildFragmentManager(), 1, home_fragments);
        //给viewPaper设置适配器
        viewPager.setAdapter(homePagerAdapter);
        //设置标签自动扩展——当标签们的总宽度不够屏幕宽度时，自动扩展使每个标签宽度递增匹配屏幕宽度
        home_tabs.setShouldExpand(true); // 注意！这一条代码必须在setViewPager前方可生效
        home_tabs.setViewPager(viewPager);
        setTabsValue();

        //设置默认打开时的选中项
        LinearLayout tabsContainer = (LinearLayout) home_tabs.getChildAt(0);
        TextView textView = (TextView) tabsContainer.getChildAt(0);
        textView.setTextColor(getResources().getColor(R.color.red));
        //实现选中后文本大小和颜色变换
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateHomeTabStyle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //点击写文章按住跳转到写文章页面
        toWriteArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化意图
                Intent intent=new Intent(getActivity(),EditArticleActivity.class);
                Bundle bundle=new Bundle();
                //传递数据

                intent.putExtras(bundle);
                //激活意图
                startActivity(intent);
                //改变切换动画
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.anim_no);
            }
        });
    }

    private void updateHomeTabStyle(int position) {
        LinearLayout tabsContainer = (LinearLayout) home_tabs.getChildAt(0);
        for(int i=0; i< tabsContainer.getChildCount(); i++) {
            TextView textView = (TextView) tabsContainer.getChildAt(i);
            if(position == i) {
                //textView.setTextSize(18);
                textView.setTextColor(getResources().getColor(R.color.red));
            } else {
                //textView.setTextSize(16);
                textView.setTextColor(R.color.colorBlack);
            }
        }
    }

    /**
     * 对PagerSlidingTabStrip的各项属性进行赋值。
     */
    private void setTabsValue() {
        // 设置Tab是自动填充满屏幕的
        home_tabs.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        home_tabs.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
        home_tabs.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        // 设置Tab Indicator的高度
        home_tabs.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4, dm));
        // 设置Tab标题文字的大小
        home_tabs.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 13, dm));
        // 设置Tab Indicator的颜色
        home_tabs.setIndicatorColor(Color.parseColor("#45c01a"));
        // 取消点击Tab时的背景色
        home_tabs.setTabBackground(0);
    }
}