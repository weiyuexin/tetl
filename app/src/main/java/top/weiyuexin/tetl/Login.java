package top.weiyuexin.tetl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Login extends AppCompatActivity {

    private EditText et_login_phone;
    private EditText et_login_password;
    private Button login_button;
    private Button register_button;
    private String phoneNumber;
    private String password;
    private String pwd;
    private Integer id;//用户的id
    private ArrayList<String> phoneList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        ImmersionBar.with(this)
                .statusBarColor(R.color.gray)     //状态栏颜色，不写默认透明色
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
    }

    private void initView() {
        et_login_phone=findViewById(R.id.et_login_phone);
        et_login_password=findViewById(R.id.et_login_password);
        et_login_password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        login_button=findViewById(R.id.login_button);
        register_button=findViewById(R.id.register_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入的手机号和密码
                phoneNumber=et_login_phone.getText().toString();
                password=et_login_password.getText().toString();
                //判断输入是否为空
                System.out.println(phoneNumber);
                if(TextUtils.isEmpty(phoneNumber)){
                    Toast.makeText(Login.this,"请输入手机号",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(password)){
                    Toast.makeText(Login.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }else {
                    //调用异步线程，查询数据库
                    new Task().execute();
                }
            }
        });


        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
                //改变activity切换动画
                overridePendingTransition(R.anim.slide_in_right,R.anim.anim_no);
            }
        });
    }
    class Task extends AsyncTask<Void,Void,Void> {

        String error="";
        @Override
        protected Void doInBackground(Void... voids) {
            phoneList.clear();
            try {
                //动态加载类
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection= DriverManager.getConnection("jdbc:mysql://1.15.60.193:3306/Android",
                        "root","Weiyuexin@123456");
                Statement statement=connection.createStatement();
                //mysql简单查询语句
                ResultSet resultSet=statement.executeQuery("SELECT phoneNumber FROM user ORDER BY id desc");

                //将查询到的数据保存的LISt中
                while (resultSet.next()){
                    phoneList.add(resultSet.getString("phoneNumber"));
                }
            }catch (Exception e){
                error = e.toString();
                System.out.println(error);
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            if (phoneList.contains(phoneNumber)){//判断输入的手机号是否注册
                new Task1().execute();
            }else {
                Toast.makeText(Login.this,"该手机号还未注册，请前往注册",Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(aVoid);
        }
    }

    class Task1 extends AsyncTask<Void,Void,Void> {

        String error="";
        @Override
        protected Void doInBackground(Void... voids) {
            phoneList.clear();
            try {
                //动态加载类
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection= DriverManager.getConnection("jdbc:mysql://1.15.60.193:3306/Android",
                        "root","Weiyuexin@123456");
                Statement statement=connection.createStatement();
                //mysql简单查询语句
                ResultSet resultSet=statement.executeQuery("SELECT id,passWord FROM user WHERE phoneNumber="+phoneNumber +" ORDER BY id desc");

                //将查询到的数据保存的LISt中
                while (resultSet.next()){
                    pwd=resultSet.getString("passWord");
                    id=resultSet.getInt("id");
                }
            }catch (Exception e){
                error = e.toString();
                System.out.println(error);
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            if(pwd.equals(password)){//判断密码是否相同
                //实例化SharedPreferences对象，保存用户信息
                SharedPreferences mySharedPreferences= getSharedPreferences("user",
                        Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = mySharedPreferences.edit();
                editor.putInt("id", id);
                editor.putString("phoneNumber", phoneNumber);
                editor.commit();
                Toast.makeText(Login.this,"登录成功",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Login.this,MainActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(Login.this,"密码错误",Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(aVoid);
        }
    }
    /*插入用户信息的方法*//*
    private void insertData(SQLiteDatabase sqLiteDatabase,Integer id,String phoneNumber){
        ContentValues values = new ContentValues();
        values.put("id",id);
        values.put("phoneNumber",phoneNumber);
        sqLiteDatabase.insert("user",null,values);//执行插入操作
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dbOpenHelper!=null){
            dbOpenHelper.close();//关闭数据库连接
        }
    }*/

    /**
     重写dispatchTouchEvent
     * 点击软键盘外面的区域关闭软键盘
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                //根据判断关闭软键盘
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    /**
     * 判断用户点击的区域是否是输入框
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}