package top.weiyuexin.tetl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ChangePassword extends AppCompatActivity {

    private Toolbar toolbar_changePassword;
    private EditText old_password;
    private EditText new_password;
    private EditText confirm_new_password;
    private Button ok;
    private String old_pwd;
    private String new_pwd;
    private String confirm_new_pwd;
    private Integer id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        toolbar_changePassword=findViewById(R.id.toolbar_changePassword);
        toolbar_changePassword.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        old_password=findViewById(R.id.old_password);
        new_password=findViewById(R.id.new_password);
        confirm_new_password=findViewById(R.id.confirm_new_password);
        ok=findViewById(R.id.ok);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                old_pwd=old_password.getText().toString();
                new_pwd=new_password.getText().toString();
                confirm_new_pwd=confirm_new_password.getText().toString();

                SharedPreferences sharedPreferences= getSharedPreferences("user",
                        Activity.MODE_PRIVATE);
                String tmp =sharedPreferences.getString("passWord","");
                id=sharedPreferences.getInt("id",0);
                if(!tmp.equals(old_pwd)){
                    Toast.makeText(ChangePassword.this,"原密码错误,请重新输入",Toast.LENGTH_SHORT).show();
                }else if(!new_pwd.equals(confirm_new_pwd)){
                    Toast.makeText(ChangePassword.this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
                }else if(old_pwd.equals(new_pwd)){
                    Toast.makeText(ChangePassword.this,"新密码不能与原密码相同",Toast.LENGTH_SHORT).show();
                }else {
                    //实例化SharedPreferences对象，修改用户信息
                    SharedPreferences mySharedPreferences= getSharedPreferences("user",
                            Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mySharedPreferences.edit();
                    editor.putString("passWord",new_pwd);
                    editor.commit();
                    new Task().execute();
                }
            }
        });
    }


    /*更新密码*/
    class Task extends AsyncTask<Void,Void,Void> {

        String error = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //动态加载类
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://1.15.60.193:3306/Android",
                        "root", "Weiyuexin@123456");
                Statement statement = connection.createStatement();
                //mysql简单查询语句
                boolean resultSet=statement.execute("UPDATE user SET passWord="+ new_pwd+" WHERE id=" +id);
            } catch (Exception e) {
                error = e.toString();
                System.out.println(error);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(ChangePassword.this,"密码修改成功",Toast.LENGTH_SHORT).show();
            finish();
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