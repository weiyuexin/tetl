package top.weiyuexin.tetl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;

public class ProfileMessage extends AppCompatActivity {

    private Toolbar toolbar_profile_message;
    private String phoneNumber;//手机号
    private Integer id;//用户的id
    private int sex;//性别
    private String userName;
    private String realName;
    private Integer studentNumber;
    private String college;
    private String major;
    private String time;

    private TextView tv_phonenumber;
    private LinearLayout ll_sex;
    private TextView tv_sex;
    private LinearLayout ll_username;
    private TextView tv_username;
    private LinearLayout ll_realname;
    private TextView tv_realname;
    private LinearLayout ll_studentnumber;
    private TextView tv_studentnumber;
    private LinearLayout ll_college;
    private TextView tv_college;
    private LinearLayout ll_major;
    private TextView tv_major;
    private TextView tv_releaseTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_message);


        ImmersionBar.with(this)
                .statusBarColor(R.color.gray)     //状态栏颜色，不写默认透明色
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
        initView();
    }

    private void initView() {
        tv_phonenumber=findViewById(R.id.tv_phonenumber);
        ll_sex=findViewById(R.id.ll_sex);
        tv_sex=findViewById(R.id.tv_sex);
        ll_username=findViewById(R.id.ll_username);
        tv_username=findViewById(R.id.tv_username);
        ll_realname=findViewById(R.id.ll_realname);
        tv_realname=findViewById(R.id.tv_realname);
        ll_studentnumber=findViewById(R.id.ll_studentnumber);
        tv_studentnumber=findViewById(R.id.tv_studentnumber);
        ll_college=findViewById(R.id.ll_college);
        tv_college = findViewById(R.id.tv_college);
        ll_major=findViewById(R.id.ll_major);
        tv_major=findViewById(R.id.tv_major);
        tv_releaseTime=findViewById(R.id.tv_releaseTime);


        toolbar_profile_message=findViewById(R.id.toolbar_profile_message);
        toolbar_profile_message.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//返回
            }
        });
        SharedPreferences sharedPreferences= getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        phoneNumber=sharedPreferences.getString("phoneNumber","");
        sex=sharedPreferences.getInt("sex",1);
        userName=sharedPreferences.getString("userName","");
        realName=sharedPreferences.getString("realName","");
        studentNumber=sharedPreferences.getInt("studentNumber",1);
        college=sharedPreferences.getString("college","");
        major=sharedPreferences.getString("major","");
        time=sharedPreferences.getString("time","");

        tv_phonenumber.setText(phoneNumber);
        if(sex==0){
            tv_sex.setText("男");
        }else {
            tv_sex.setText("女");
        }
        tv_username.setText(userName);
        if(realName.length()>1){
            tv_realname.setText(realName);
        }
        if(studentNumber.longValue()>1){
            tv_studentnumber.setText(studentNumber.toString());
        }
        if(college.length()>1){
            tv_college.setText(college);
        }
        if(major.length()>1){
            tv_major.setText(major);
        }
        tv_releaseTime.setText(time);
    }


    @Override
    /*重写finish方法，改变返回时的动画*/
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_out_right);
    }
}