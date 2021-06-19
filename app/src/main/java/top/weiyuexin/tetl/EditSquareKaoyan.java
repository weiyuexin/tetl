package top.weiyuexin.tetl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditSquareKaoyan extends AppCompatActivity {

    private Toolbar edit_article_toolbar;
    //文本编辑框
    private EditText edit_article_content;
    //发表按钮
    private Button publish;
    //文章内容
    private String content;
    //类型
    private String type="考研";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_square_kaoyan);
        ImmersionBar.with(this)
                .statusBarColor(R.color.white)     //状态栏颜色，不写默认透明色
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
        initView();
        editArticle();
    }
    private void editArticle() {
        //点击提交事件
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取正文
                content=edit_article_content.getText().toString();
                //获取当前时间
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
                Date date = new Date(System.currentTimeMillis());
                String nowTime;
                nowTime=simpleDateFormat.format(date);
                //获取作者的id
                SharedPreferences sharedPreferences= getSharedPreferences("user",
                        Activity.MODE_PRIVATE);
                Integer authorId =sharedPreferences.getInt("id",1);

                if(content!=null && type!=null){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(EditSquareKaoyan.this)
                            .setIcon(R.drawable.publish)
                            .setTitle("发布文章")
                            .setMessage("确认要发布当前内容?")
                            .setPositiveButton("发布", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    /*定义进度条对话框*/
                                    ProgressDialog ProDialog = new ProgressDialog(EditSquareKaoyan.this);
                                    ProDialog.setIcon(R.drawable.publish);
                                    ProDialog.setTitle("发布文章");
                                    ProDialog.setMessage("文章正在发布中。。。");
                                    final int[] count = {0};
                                    Thread myThred = new Thread(){
                                        @Override
                                        public void run() {
                                            try {
                                                //动态加载类
                                                Class.forName("com.mysql.jdbc.Driver");
                                                Connection connection= DriverManager.getConnection("jdbc:mysql://1.15.60.193:3306/Android?useUnicode=true&characterEncoding=utf8",
                                                        "root","Weiyuexin@123456");//加入后面的一串是为了解决插入数据库时的中文乱码
                                                Statement statement=connection.createStatement();
                                                //发布文章
                                                boolean resultSet=statement.execute("INSERT INTO article(type,content,author,time) VALUES('"+type+"','"+content+"','"+authorId+"','"+nowTime+"');");
                                            }catch (Exception e){
                                                String error;
                                                error = e.toString();
                                                System.out.println(error);
                                            }
                                            while (count[0] <=10){
                                                ProDialog.setProgress(count[0]++);
                                                try{
                                                    Thread.sleep(100);
                                                }catch (InterruptedException e){
                                                    e.printStackTrace();
                                                }
                                            }
                                            /*进程结束后关闭对话框*/
                                            if (count[0]>=100){
                                                ProDialog.cancel();
                                            }
                                            finish();
                                        }
                                    };
                                    myThred.start();
                                    ProDialog.setCancelable(false);
                                    ProDialog.show();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(EditSquareKaoyan.this,"取消发布，重新编辑",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setCancelable(false);
                    dialog.create().show();
                }
            }
        });
    }

    private void initView() {
        //初始化toolbar
        edit_article_toolbar=findViewById(R.id.edit_article_toolbar);
        //初始化文本编辑框
        edit_article_content=findViewById(R.id.edit_article_content);
        //初始化提交按钮
        publish=findViewById(R.id.publish);

        //点击返回按钮时，返回上一个Activity
        edit_article_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }











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





    @Override
    /*重写finish方法，改变返回时的动画*/
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_out_right);
    }
}