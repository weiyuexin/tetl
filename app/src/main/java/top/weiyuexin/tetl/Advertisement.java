package top.weiyuexin.tetl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

public class Advertisement extends AppCompatActivity {

    private TextView ad_1;
    private MycountDownTimer my;
    private DBOpenHelper dbOpenHelper;//声明数据库操作对象
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        ad_1=findViewById(R.id.ad_1);
        my = new MycountDownTimer(5000, 500);
        my.start();
        ad_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //防止再次跳过
                my.cancel();
                //读取用户信息，判断是否登录
                SharedPreferences sharedPreferences= getSharedPreferences("user",
                        Activity.MODE_PRIVATE);
                String phoneNumber =sharedPreferences.getString("phoneNumber", "");
                System.out.println(phoneNumber);
                if(phoneNumber.length()<11){
                    Intent intent = new Intent(Advertisement.this, Login.class);
                    startActivity(intent);
                    //改变activity切换动画
                    overridePendingTransition(R.anim.slide_in_right,R.anim.anim_no);
                    Advertisement.this.finish();
                }else {
                    Intent intent=new Intent(Advertisement.this,MainActivity.class);
                    startActivity(intent);
                    //改变activity切换动画
                    overridePendingTransition(R.anim.slide_in_right,R.anim.anim_no);
                    Advertisement.this.finish();
                }
            }
        });

        //实例化DBOpenHelper对象用来创建数据库
        dbOpenHelper=new DBOpenHelper(Advertisement.this,"user",null,1);

    }

    class MycountDownTimer extends CountDownTimer {
        public MycountDownTimer(long millisInFuture, long countDownInterval) {

            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            SharedPreferences sharedPreferences= getSharedPreferences("user",
                    Activity.MODE_PRIVATE);
            String phoneNumber =sharedPreferences.getString("phoneNumber", "");
            if(phoneNumber.length()<11){
                Intent intent = new Intent(Advertisement.this, Login.class);
                startActivity(intent);
                //改变activity切换动画
                overridePendingTransition(R.anim.slide_in_right,R.anim.anim_no);
                Advertisement.this.finish();
            }else {
                Intent intent=new Intent(Advertisement.this,MainActivity.class);
                startActivity(intent);
                //改变activity切换动画
                overridePendingTransition(R.anim.slide_in_right,R.anim.anim_no);
                Advertisement.this.finish();
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            ad_1.setText("跳过" + millisUntilFinished / 1000);
        }
    }

    /*public boolean isTableExists(SQLiteDatabase db) {
        String sql = "select count(*) as c from sqlite_master where type ='table' and name ='user';";
        Cursor cursor = db.rawQuery(sql, null);
        boolean result=false;
        if (cursor.moveToNext()) {
            int count = cursor.getInt(0);
            if (count > 0) {
            result = true;
            }
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dbOpenHelper!=null){
            dbOpenHelper.close();//关闭数据库连接
        }
    }*/
}