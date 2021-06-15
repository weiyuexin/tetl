package top.weiyuexin.tetl;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;

public class Setting extends AppCompatActivity {

    private Toolbar toolbar_Setting;
    private LinearLayout ll_clear;
    private TextView tv_rabbitSum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
        ImmersionBar.with(this)
                .statusBarColor(R.color.gray)     //状态栏颜色，不写默认透明色
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
    }

    private void initView() {
        toolbar_Setting=findViewById(R.id.toolbar_Setting);
        toolbar_Setting.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ll_clear=findViewById(R.id.ll_clear);
        tv_rabbitSum=findViewById(R.id.tv_rabbitSum);
        ll_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(Setting.this)
                        .setMessage("确定清除缓存？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tv_rabbitSum.setText("0");
                                Toast.makeText(Setting.this,"清除成功!",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setCancelable(false).create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button positiveButton = ((AlertDialog) dialog)
                                .getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setBackgroundColor(getResources().getColor(R.color.red));
                        positiveButton.setTextColor(Color.WHITE);
                        Button negativeButton =  ((AlertDialog) dialog)
                                .getButton(AlertDialog.BUTTON_NEGATIVE);
                        negativeButton.setBackgroundColor(getResources().getColor(R.color.red));
                        negativeButton.setTextColor(Color.WHITE);
                    }
                });
                dialog.show();

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