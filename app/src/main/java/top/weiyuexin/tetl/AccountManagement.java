package top.weiyuexin.tetl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;

public class AccountManagement extends AppCompatActivity {

    private Toolbar toolbar_account_management;
    private LinearLayout ll_changepassword;
    private LinearLayout ll_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);
        ImmersionBar.with(this)
                .statusBarColor(R.color.gray)     //状态栏颜色，不写默认透明色
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
        initView();
    }

    private void initView() {
        toolbar_account_management=findViewById(R.id.toolbar_account_management);
        toolbar_account_management.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ll_changepassword=findViewById(R.id.ll_changepassword);
        ll_changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountManagement.this,ChangePassword.class);
                startActivity(intent);
                //改变activity切换动画
                overridePendingTransition(R.anim.slide_in_right,R.anim.anim_no);
            }
        });
        ll_logout=findViewById(R.id.ll_logout);
        ll_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AccountManagement.this,"注销成功",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AccountManagement.this,Login.class);
                startActivity(intent);
                //改变activity切换动画
                overridePendingTransition(R.anim.slide_in_right,R.anim.anim_no);
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