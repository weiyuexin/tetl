package top.weiyuexin.tetl;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.ant.liao.GifView;
import com.gyf.immersionbar.ImmersionBar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AboutUsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        ImmersionBar.with(this)
                .statusBarColor(R.color.gray)     //状态栏颜色，不写默认透明色
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();

        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        toolbar=findViewById(R.id.about_us_toolbar);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//返回
            }
        });
    }


    @Override
    /*重写finish方法，改变返回时的动画*/
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_out_right);
    }
}