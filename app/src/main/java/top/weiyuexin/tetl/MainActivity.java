package top.weiyuexin.tetl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    ViewPager2 tab_viewpager2;
    //定义底部菜单栏按钮
    private LinearLayout tab_home,tab_square,tab_message,tab_profile;
    //定义底部菜单栏图片
    private ImageView tab_iv_home,tab_iv_square,tab_iv_message,tab_iv_profile,ivCurrent;
    //定义底部菜单栏文字
    private TextView tab_tv_home,tab_tv_square,tab_tv_message,tab_tv_profile,tvCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPager();
        initTabView();
        //定义一些点击事件

        ImmersionBar.with(this)
                //.barColor(R.color.gray)//同时自定义状态栏和导航栏颜色，不写默认状态栏为透明色，导航栏为黑色
                .fitsSystemWindows(true)
                .statusBarColor(R.color.gray)     //状态栏颜色，不写默认透明色
                //.navigationBarDarkIcon(true) //导航栏图标是深色，不写默认为亮色
                .navigationBarEnable(true)   //是否可以修改导航栏颜色，默认为true
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
    }

    private void initTabView() {
        //获取底部菜单按钮
        tab_home=findViewById(R.id.tab_home);
        tab_square=findViewById(R.id.tab_square);
        tab_message=findViewById(R.id.tab_message);
        tab_profile=findViewById(R.id.tab_profile);

        //获取底部导航栏图片
        tab_iv_home=findViewById(R.id.tab_iv_home);
        tab_iv_square=findViewById(R.id.tab_iv_square);
        tab_iv_message=findViewById(R.id.tab_iv_message);
        tab_iv_profile=findViewById(R.id.tab_iv_profile);

        //获取底部导航栏文字
        tab_tv_home=(TextView) findViewById(R.id.tab_tv_home);
        tab_tv_square=(TextView)findViewById(R.id.tab_tv_square);
        tab_tv_message=(TextView)findViewById(R.id.tab_tv_message);
        tab_tv_profile=(TextView)findViewById(R.id.tab_tv_profile);

        //设置初始进入app时的页面
        tab_home.setSelected(true);
        ivCurrent=tab_iv_home;
        tvCurrent=tab_tv_home;
        //tvCurrent.setTextColor(R.color.tab_selected);

        //设置底部导航栏点击事件
        tab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(v.getId());
            }
        });
        tab_square.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(v.getId());
            }
        });
        tab_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(v.getId());
            }
        });
        tab_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(v.getId());
            }
        });

    }

    private void initPager() {
        //获取Viewpager2
        tab_viewpager2=findViewById(R.id.id_tab_viewpager);
        //设置viewpager禁止滑动
        tab_viewpager2.setUserInputEnabled(false);

        //定义fragment数组
        ArrayList<Fragment> tab_fragments =new ArrayList<>();
        tab_fragments.add(new HomeFragment());
        tab_fragments.add(new SquareFragment());
        tab_fragments.add(new MessageFragment());
        tab_fragments.add(new ProfileFragment());

        //初始化适配器
        TabFragmenntPagerAdapter tab_adapter=new TabFragmenntPagerAdapter(getSupportFragmentManager(),getLifecycle(),tab_fragments);
        //给viewpager2添加适配器
        tab_viewpager2.setAdapter(tab_adapter);
        //fragment选中事件
        tab_viewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    private void changeTab(int position) {
        ivCurrent.setSelected(false);
        switch (position) {
            case R.id.tab_home:
                /*点击时跳转到相应页面，并且取消过渡动画*/
                tab_viewpager2.setCurrentItem(0, false);
            case 0:
                tab_iv_home.setSelected(true);
                ivCurrent = tab_iv_home;
                break;
            case R.id.tab_square:
                tab_viewpager2.setCurrentItem(1, false);
            case 1:
                tab_iv_square.setSelected(true);
                ivCurrent = tab_iv_square;
                break;
            case R.id.tab_message:
                tab_viewpager2.setCurrentItem(2, false);
            case 2:
                tab_iv_message.setSelected(true);
                ivCurrent = tab_iv_message;
                break;
            case R.id.tab_profile:
                tab_viewpager2.setCurrentItem(3, false);
            case 3:
                tab_iv_profile.setSelected(true);
                ivCurrent = tab_iv_profile;
                break;
        }
    }
}