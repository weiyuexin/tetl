package top.weiyuexin.tetl;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.gyf.immersionbar.ImmersionBar;

public class HelpandFeedBack extends AppCompatActivity {

    private Toolbar toolbar_HelpandFeedBack;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpand_feed_back);
        initView();
        ImmersionBar.with(this)
                .statusBarColor(R.color.gray)     //状态栏颜色，不写默认透明色
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        //初始化toolbar
        toolbar_HelpandFeedBack=findViewById(R.id.toolbar_HelpandFeedBack);
        //点击返回
        toolbar_HelpandFeedBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void detail_question1(View view) {
        Intent intent=new Intent(this,HelpandFeedBackOneActivity.class);
        startActivity(intent);
        //改变切换动画
        overridePendingTransition(R.anim.slide_in_right, R.anim.anim_no);
    }

    public void detail_question2(View view) {
        Intent intent=new Intent(this,HelpandFeedBackTwoActivity.class);
        startActivity(intent);
        //改变切换动画
        overridePendingTransition(R.anim.slide_in_right, R.anim.anim_no);
    }

    public void detail_question3(View view) {
        Intent intent=new Intent(this,HelpandFeedBackThreeActivity.class);
        startActivity(intent);
        //改变切换动画
        overridePendingTransition(R.anim.slide_in_right, R.anim.anim_no);
    }

    public void detail_question4(View view) {
        Intent intent=new Intent(this,HelpandFeedBackFourActivity.class);
        startActivity(intent);
        //改变切换动画
        overridePendingTransition(R.anim.slide_in_right, R.anim.anim_no);
    }

    public void btn_tofeedback(View view) {
        Intent intent=new Intent(this,EditFeedBack.class);

        startActivity(intent);
        //改变切换动画
        overridePendingTransition(R.anim.slide_in_right, R.anim.anim_no);
    }


    @Override
    /*重写finish方法，改变返回时的动画*/
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_out_right);
    }
}