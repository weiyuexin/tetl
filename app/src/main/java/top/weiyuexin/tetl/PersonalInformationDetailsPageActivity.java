package top.weiyuexin.tetl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PersonalInformationDetailsPageActivity extends AppCompatActivity {

    private Toolbar peopletoolbar;
    private Bundle data;
    private TextView peopleName;
    private ImageView sexPic;
    private TextView people_userName;
    private TextView peopleCollege;
    private TextView peopleMajor;
    private TextView peopleTime;
    private TextView phonenumber;

    //保存数据库查询到的注册时间
    private String registertime;
    //保存数据库查询到的学院
    private String college;
    //保存数据库查询到的专业信息
    private String major;
    //保存数据库查询到的性别信息
    private int sex;
    //姓名
    private String realName;
    private String userName;
    private String phoneNumber;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information_details_page);

        /*接收数据*/
        Intent intent=getIntent();
        data=intent.getExtras();
        id=data.getInt("id");


        initView();

        ImmersionBar.with(this)
                .barColor(R.color.white)//同时自定义状态栏和导航栏颜色，不写默认状态栏为透明色，导航栏为黑色
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();

        //新建异步线程，链接查询数据库
        new Task().execute();

    }

    private void initView() {
        peopleName=findViewById(R.id.peopleName);
        sexPic=findViewById(R.id.sexPic);
        people_userName=findViewById(R.id.people_userName);
        peopleCollege=findViewById(R.id.peopleCollege);
        peopleMajor=findViewById(R.id.peopleMajor);
        peopleTime=findViewById(R.id.peopleTime);
        phonenumber=findViewById(R.id.peoplePhone);
        peopletoolbar=findViewById(R.id.people_toolbar);

        peopletoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    class Task extends AsyncTask<Void,Void,Void> {

        String error="";
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //动态加载类
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection= DriverManager.getConnection("jdbc:mysql://1.15.60.193:3306/Android",
                        "root","Weiyuexin@123456");
                Statement statement=connection.createStatement();
                //mysql简单查询语句
                ResultSet resultSet=statement.executeQuery("SELECT * FROM user WHERE id=" + id);

                System.out.println(resultSet);
                //将查询到的数据保存的LISt中
                while (resultSet.next()){
                    realName=resultSet.getString("realName");
                    userName=resultSet.getString("userName");
                    sex=resultSet.getInt("sex");
                    college=resultSet.getString("college");
                    major=resultSet.getString("major");
                    registertime=resultSet.getString("registerTime");
                    phoneNumber=resultSet.getString("phoneNumber");
                }
            }catch (Exception e){
                error = e.toString();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            //加载完成后隐藏动画，显示ListView
            /*avi.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);*/

            if(realName!=null){
                 peopleName.setText(realName);
            }else {
                peopleName.setText(userName);
            }
            if(sex==1){
                sexPic.setImageResource(R.drawable.female);
            }
            people_userName.setText(userName);
            peopleCollege.setText(college);
            peopleMajor.setText(major);
            peopleTime.setText(registertime);
            phonenumber.setText(phoneNumber);

            System.out.println(phoneNumber);
            System.out.println(sex);
            super.onPostExecute(aVoid);
        }
    }


    @Override
    /*重写finish方法，改变返回时的动画*/
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_out_right);
    }
}