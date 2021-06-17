package top.weiyuexin.tetl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProfileMessage extends AppCompatActivity {

    private Toolbar toolbar_profile_message;
    private String phoneNumber;//手机号
    private Integer id;//用户的id
    private int sex;//性别
    private int updateSex;
    private String userName;
    private String updateuserName;
    private String realName;
    private String updaterealName;
    private Integer studentNumber;
    private Integer updatestudentNumber;
    private String college;
    private String updatecollege;
    private String major;
    private String updatemajor;
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

    private SharedPreferences sharedPreferences;

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
        sharedPreferences= getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        id=sharedPreferences.getInt("id",1);
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

        ll_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] items=new String[]{"男","女"};
                final int[] sign = {0};
                AlertDialog.Builder dialog = new AlertDialog.Builder(ProfileMessage.this)
                        .setIcon(R.drawable.dialogicon)
                        .setTitle("请选择性别")
                        .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tv_sex.setText(items[which]);
                                sign[0] =which;
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(tv_sex.getText().equals("男")){
                                    updateSex=0;
                                }else {
                                    updateSex=1;
                                }
                                //调用异步线程，修改性别
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("sex",updateSex);
                                editor.commit();
                                new Task().execute();
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(false);
                dialog.create().show();
            }
        });
        ll_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*创建LayoutInflater对象*/
                LayoutInflater flater = LayoutInflater.from(ProfileMessage.this);
                /*将布局文件转换为View*/
                View dialogView = flater.inflate(R.layout.changeusername,null);
                /*创建Dialog*/
                AlertDialog dialog = new AlertDialog.Builder(ProfileMessage.this)
                        .setIcon(R.drawable.dialogicon)
                        .setTitle("请输入新昵称")
                        .setCancelable(false)
                        .setView(dialogView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText newUserName=dialogView.findViewById(R.id.et_changeUsername);
                                updateuserName=newUserName.getText().toString();
                                tv_username.setText(updateuserName);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("userName",updateuserName);
                                editor.commit();
                                new Task1().execute();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create();
                dialog.show();
            }
        });
        ll_realname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*创建LayoutInflater对象*/
                LayoutInflater flater = LayoutInflater.from(ProfileMessage.this);
                /*将布局文件转换为View*/
                View dialogView = flater.inflate(R.layout.changerealname,null);
                /*创建Dialog*/
                AlertDialog dialog = new AlertDialog.Builder(ProfileMessage.this)
                        .setIcon(R.drawable.dialogicon)
                        .setTitle("请输入姓名")
                        .setCancelable(false)
                        .setView(dialogView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText newrealName=dialogView.findViewById(R.id.changeRealName);
                                updaterealName=newrealName.getText().toString();
                                tv_realname.setText(updaterealName);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("realName",updaterealName);
                                editor.commit();
                                new Task2().execute();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create();
                dialog.show();
            }
        });
        ll_studentnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*创建LayoutInflater对象*/
                LayoutInflater flater = LayoutInflater.from(ProfileMessage.this);
                /*将布局文件转换为View*/
                View dialogView = flater.inflate(R.layout.changestudentnumber,null);
                /*创建Dialog*/
                AlertDialog dialog = new AlertDialog.Builder(ProfileMessage.this)
                        .setIcon(R.drawable.dialogicon)
                        .setTitle("请输入学号")
                        .setCancelable(false)
                        .setView(dialogView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText studentNumber=dialogView.findViewById(R.id.changeStudentNumber);
                                updatestudentNumber=Integer.valueOf(studentNumber.getText().toString());
                                tv_realname.setText(String.valueOf(updatestudentNumber));
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("studentNumber",updatestudentNumber);
                                editor.commit();
                                new Task3().execute();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create();
                dialog.show();
            }
        });
        ll_college.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*创建LayoutInflater对象*/
                LayoutInflater flater = LayoutInflater.from(ProfileMessage.this);
                /*将布局文件转换为View*/
                View dialogView = flater.inflate(R.layout.changerealname,null);
                /*创建Dialog*/
                AlertDialog dialog = new AlertDialog.Builder(ProfileMessage.this)
                        .setIcon(R.drawable.dialogicon)
                        .setTitle("请输入学院名称")
                        .setCancelable(false)
                        .setView(dialogView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText newrealName=dialogView.findViewById(R.id.changeRealName);
                                updatecollege=newrealName.getText().toString();
                                tv_college.setText(updatecollege);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("college",updatecollege);
                                editor.commit();
                                new Task4().execute();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create();
                dialog.show();
            }
        });
        ll_major.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*创建LayoutInflater对象*/
                LayoutInflater flater = LayoutInflater.from(ProfileMessage.this);
                /*将布局文件转换为View*/
                View dialogView = flater.inflate(R.layout.changerealname,null);
                /*创建Dialog*/
                AlertDialog dialog = new AlertDialog.Builder(ProfileMessage.this)
                        .setIcon(R.drawable.dialogicon)
                        .setTitle("请输入学院名称")
                        .setCancelable(false)
                        .setView(dialogView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText newrealName=dialogView.findViewById(R.id.changeRealName);
                                updatemajor=newrealName.getText().toString();
                                tv_major.setText(updatemajor);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("major",updatemajor);
                                editor.commit();
                                new Task5().execute();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create();
                dialog.show();
            }
        });
    }
    /*修改性别*/
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
                //更新点赞数
                boolean resultSet=statement.execute("UPDATE user SET sex="+ updateSex+" WHERE id=" +id);

            }catch (Exception e){
                error = e.toString();
                System.out.println(error);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(ProfileMessage.this,"修改成功",Toast.LENGTH_SHORT).show();
            super.onPostExecute(aVoid);
        }
    }

    /*修改昵称*/
    class Task1 extends AsyncTask<Void,Void,Void> {
        String error="";
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //动态加载类
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection= DriverManager.getConnection("jdbc:mysql://1.15.60.193:3306/Android?useUnicode=true&characterEncoding=utf8",
                        "root","Weiyuexin@123456");
                Statement statement=connection.createStatement();
                //更新点赞数
                boolean resultSet=statement.execute("UPDATE user SET userName='"+ updateuserName+"' WHERE id=" +id);

            }catch (Exception e){
                error = e.toString();
                System.out.println(error);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(ProfileMessage.this,"修改成功",Toast.LENGTH_SHORT).show();
            super.onPostExecute(aVoid);
        }
    }

    /*修改姓名*/
    class Task2 extends AsyncTask<Void,Void,Void> {
        String error="";
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //动态加载类
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection= DriverManager.getConnection("jdbc:mysql://1.15.60.193:3306/Android?useUnicode=true&characterEncoding=utf8",
                        "root","Weiyuexin@123456");
                Statement statement=connection.createStatement();
                //更新点赞数
                boolean resultSet=statement.execute("UPDATE user SET realName='"+ updaterealName+"' WHERE id=" +id);

            }catch (Exception e){
                error = e.toString();
                System.out.println(error);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(ProfileMessage.this,"修改成功",Toast.LENGTH_SHORT).show();
            super.onPostExecute(aVoid);
        }
    }

    /*修改学号*/
    class Task3 extends AsyncTask<Void,Void,Void> {
        String error="";
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //动态加载类
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection= DriverManager.getConnection("jdbc:mysql://1.15.60.193:3306/Android",
                        "root","Weiyuexin@123456");
                Statement statement=connection.createStatement();
                //更新点赞数
                boolean resultSet=statement.execute("UPDATE user SET studentNumber="+ updatestudentNumber+" WHERE id=" +id);

            }catch (Exception e){
                error = e.toString();
                System.out.println(error);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(ProfileMessage.this,"修改成功",Toast.LENGTH_SHORT).show();
            super.onPostExecute(aVoid);
        }
    }

    /*修改学院*/
    class Task4 extends AsyncTask<Void,Void,Void> {
        String error="";
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //动态加载类
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection= DriverManager.getConnection("jdbc:mysql://1.15.60.193:3306/Android?useUnicode=true&characterEncoding=utf8",
                        "root","Weiyuexin@123456");
                Statement statement=connection.createStatement();
                //更新点赞数
                boolean resultSet=statement.execute("UPDATE user SET college='"+ updatecollege+"' WHERE id=" +id);

            }catch (Exception e){
                error = e.toString();
                System.out.println(error);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(ProfileMessage.this,"修改成功",Toast.LENGTH_SHORT).show();
            super.onPostExecute(aVoid);
        }
    }

    /*修改专业*/
    class Task5 extends AsyncTask<Void,Void,Void> {
        String error="";
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //动态加载类
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection= DriverManager.getConnection("jdbc:mysql://1.15.60.193:3306/Android?useUnicode=true&characterEncoding=utf8",
                        "root","Weiyuexin@123456");
                Statement statement=connection.createStatement();
                //更新点赞数
                boolean resultSet=statement.execute("UPDATE user SET major='"+ updatemajor+"' WHERE id=" +id);

            }catch (Exception e){
                error = e.toString();
                System.out.println(error);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(ProfileMessage.this,"修改成功",Toast.LENGTH_SHORT).show();
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