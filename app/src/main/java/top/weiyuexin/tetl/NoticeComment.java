package top.weiyuexin.tetl;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class NoticeComment extends AppCompatActivity {

    private Toolbar toolbar_CommentNotice;
    private RefreshLayout refreshLayout_message_comment;
    private ListView listview_message_comment;
    private Integer authorId;

    //保存数据库查询到的作品id
    private ArrayList<String> commentList =  new ArrayList<>();
    private ArrayList<String> commentTimeList =  new ArrayList<>();
    private ArrayList<Integer> commentAuthorIdList=new ArrayList<>();
    private ArrayList<String> commentAuthorUserName =  new ArrayList<>();
    private ArrayList<String> commentAuthorRealName =  new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_comment);
        ImmersionBar.with(this)
                .statusBarColor(R.color.gray)     //状态栏颜色，不写默认透明色
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
        initView();
        //新建异步线程，链接查询数据库
        new Task().execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        //获取作者的id
        SharedPreferences sharedPreferences= getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        authorId =sharedPreferences.getInt("id",1);
        toolbar_CommentNotice=findViewById(R.id.toolbar_CommentNotice);
        toolbar_CommentNotice.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refreshLayout_message_comment=(RefreshLayout)findViewById(R.id.refreshLayout_message_comment);
        refreshLayout_message_comment.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout_message_comment.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //新建异步线程，链接查询数据库
                new Task().execute();
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        listview_message_comment=findViewById(R.id.listview_message_comment);
    }
    class Task extends AsyncTask<Void,Void,Void> {

        String error = "";

        //清空原始数据，主要是刷新时
        @Override
        protected Void doInBackground(Void... voids) {
            commentAuthorIdList.clear();
            commentList.clear();
            commentTimeList.clear();
            commentAuthorUserName.clear();
            commentAuthorRealName.clear();
            try {
                //动态加载类
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://1.15.60.193:3306/Android",
                        "root", "Weiyuexin@123456");
                Statement statement = connection.createStatement();
                //mysql简单查询语句
                ResultSet resultSet = statement.executeQuery("SELECT * FROM comment WHERE articleId=any(SELECT id FROM article WHERE author="+ authorId+") ORDER BY id desc");

                //将查询到的数据保存的LISt中
                while (resultSet.next()) {
                    commentAuthorIdList.add(resultSet.getInt("authorId"));
                    commentList.add(resultSet.getString("commentContent"));
                    commentTimeList.add(resultSet.getString("time"));
                }
                for(int i=0;i<commentAuthorIdList.size();i++){
                    ResultSet resultSet1=statement.executeQuery("SELECT * FROM user WHERE id="+commentAuthorIdList.get(i));
                    while (resultSet1.next()){
                        commentAuthorUserName.add(resultSet1.getString("userName"));
                        commentAuthorRealName.add(resultSet1.getString("realName"));
                    }
                }
            } catch (Exception e) {
                error = e.toString();
                System.out.println(error);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {

            MyAdapter myAdapter=new MyAdapter(commentAuthorIdList,commentList,commentTimeList,commentAuthorUserName,commentAuthorRealName);
            listview_message_comment.setAdapter(myAdapter);
            myAdapter.notifyDataSetInvalidated();
            super.onPostExecute(aVoid);
        }
    }

    //ListView适配器
    class MyAdapter extends BaseAdapter {
        private ArrayList<Integer> authorIdList=new ArrayList<>();
        //保存正文内容
        private ArrayList<String> contentList = new ArrayList<>();
        //保存文章发布时间
        private ArrayList<String> releaseTimeList = new ArrayList<>();
        //保存从数据库查询到的用户名
        private ArrayList<String> userNameList = new ArrayList<>();
        //保存从数据库查询到的真实姓名
        private ArrayList<String> realNameList =new ArrayList<>();


        public MyAdapter(ArrayList<Integer> authorId,ArrayList<String> content, ArrayList<String> releaseTime, ArrayList<String> userName, ArrayList<String> realName) {
            this.authorIdList=authorId;
            this.contentList=content;
            this.releaseTimeList=releaseTime;
            this.userNameList=userName;
            this.realNameList=realName;
        }

        @Override
        public int getCount() {
            return contentList.size();
        }

        @Override
        public Object getItem(int position) {
            return contentList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=null;
            view=View.inflate(NoticeComment.this,R.layout.list_items_comments_notice,null);

            TextView userName =view.findViewById(R.id.commentAuthorName);
            TextView release_time = view.findViewById(R.id.commenttime);
            TextView comment_content =view.findViewById(R.id.commentContent);
            ImageView head_pic=view.findViewById(R.id.head_pic);

            release_time.setText(releaseTimeList.get(position).toString());
            comment_content.setText(contentList.get(position).toString());
            if(realNameList.get(position)!=null){
                userName.setText(realNameList.get(position));
            }else {
                userName.setText(userNameList.get(position));
            }

            //点击头像跳转到作者详情页
            head_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //初始化意图
                    Intent intent1=new Intent(NoticeComment.this,PersonalInformationDetailsPageActivity.class);
                    //传递数据信息
                    Bundle bundle=new Bundle();
                    bundle.putInt("id",authorIdList.get(position));

                    intent1.putExtras(bundle);
                    //激活意图
                    startActivity(intent1);
                    //改变activity切换动画
                    overridePendingTransition(R.anim.slide_in_right,R.anim.anim_no);
                }
            });
            //点击昵称跳转到作者详情页
            userName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //初始化意图
                    Intent intent1=new Intent(NoticeComment.this,PersonalInformationDetailsPageActivity.class);
                    //传递数据信息
                    Bundle bundle=new Bundle();
                    bundle.putInt("id",authorIdList.get(position));

                    intent1.putExtras(bundle);
                    //激活意图
                    startActivity(intent1);
                    //改变activity切换动画
                    overridePendingTransition(R.anim.slide_in_right,R.anim.anim_no);
                }
            });

            return view;
        }
    }

    @Override
    /*重写finish方法，改变返回时的动画*/
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_out_right);
    }
}