package top.weiyuexin.tetl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MyIssueActivity extends AppCompatActivity {

    private Toolbar toolbar_profile_myissue;
    private ListView ll_myIssue;
    private MyIssueAdapter myIssueAdapter;

    //保存数据库查询到的id
    private ArrayList<Integer> idList = new ArrayList<>();
    //保存数据库查询到的type
    private ArrayList<String> typeList =new ArrayList<>();
    //保存数据库查询到的正文内容
    private ArrayList<String> contentList = new ArrayList<>();
    //保存数据库查到的图片信息
    private ArrayList<String> imgList = new ArrayList<>();
    //保存数据库查询到的文章发布时间
    private ArrayList<String> releaseTimeList = new ArrayList<>();
    //保存数据库查询到的点赞数
    private ArrayList<Integer> starList = new ArrayList<>();
    private ArrayList<String> userNameList = new ArrayList<>();
    //保存从数据库查询到的真实姓名
    private ArrayList<String> realNameList =new ArrayList<>();
    //保存从数据库查询到的评论总数
    private ArrayList<Integer> commentNumList = new ArrayList<>();

    //作者id
    private Integer authorId;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_issue);

        ll_myIssue=findViewById(R.id.lv_myIssue);
        ImmersionBar.with(this)
                .statusBarColor(R.color.gray)     //状态栏颜色，不写默认透明色
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
        //初始化toolbar
        toolbar_profile_myissue=findViewById(R.id.toolbar_profile_myissue);
        toolbar_profile_myissue.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        //获取作者的id
        SharedPreferences sharedPreferences= getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        authorId =sharedPreferences.getInt("id",1);
        //新建异步线程，链接查询数据库
        new Task().execute();
        click();
    }

    private void click() {
        ll_myIssue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //初始化意图对象
                Intent intent=new Intent(MyIssueActivity.this,ArticleContentActivity.class);
                //传递数据信息
                Bundle data=new Bundle();
                data.putInt("id",idList.get(position));
                //data.putInt("authorid",authorIdList.get(position));
                data.putString("type",typeList.get(position));
                data.putString("content",contentList.get(position));
                data.putString("author",realNameList.get(position));
                data.putString("username",userNameList.get(position));
                data.putInt("starSum",starList.get(position));
                data.putInt("commentSum",commentNumList.get(position));
                data.putString("time",releaseTimeList.get(position));
                intent.putExtras(data);

                //激活意图
                startActivity(intent);
                //改变activity切换动画
                overridePendingTransition(R.anim.slide_in_right,R.anim.anim_no);
            }
        });
    }

    class Task extends AsyncTask<Void,Void,Void> {

        String error="";
        //清空原始数据，主要是刷新时
        @Override
        protected Void doInBackground(Void... voids) {
            idList.clear();
            typeList.clear();
            contentList.clear();
            imgList.clear();
            starList.clear();
            commentNumList.clear();
            releaseTimeList.clear();
            userNameList.clear();
            realNameList.clear();
            try {
                //动态加载类
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection= DriverManager.getConnection("jdbc:mysql://1.15.60.193:3306/Android",
                        "root","Weiyuexin@123456");
                Statement statement=connection.createStatement();
                //mysql简单查询语句
                ResultSet resultSet=statement.executeQuery("SELECT * FROM article WHERE author="+ authorId +" ORDER BY id desc");

                //将查询到的数据保存的LISt中
                while (resultSet.next()){
                    idList.add(resultSet.getInt("id"));
                    typeList.add(resultSet.getString("type"));
                    contentList.add(resultSet.getString("content"));
                    imgList.add(resultSet.getString("image"));
                    releaseTimeList.add(resultSet.getString("time"));
                    starList.add(resultSet.getInt("star"));
                    commentNumList.add(resultSet.getInt("comment"));
                }
                System.out.println(typeList);
                //查询作者信息
                for(int i=0;i<=idList.size()+1;i++){
                    ResultSet findRealAuthor=statement.executeQuery("SELECT * FROM user WHERE id="+authorId);
                    while (findRealAuthor.next()){
                        userNameList.add(findRealAuthor.getString("userName"));
                        realNameList.add(findRealAuthor.getString("realName"));
                    }
                }
            }catch (Exception e){
                error = e.toString();
                System.out.println(error);
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            MyIssueAdapter myIssueAdapter=new MyIssueAdapter(idList,typeList,contentList,imgList
                    ,releaseTimeList,starList,userNameList,realNameList,commentNumList);
            ll_myIssue.setAdapter(myIssueAdapter);
            //System.out.println(contentList);
            ll_myIssue.setVisibility(View.VISIBLE);
            myIssueAdapter.notifyDataSetInvalidated();
            super.onPostExecute(aVoid);
        }
    }


    //listview适配器
    class MyIssueAdapter extends BaseAdapter{
        //保存id
        private ArrayList<Integer> idList = new ArrayList<>();
        //保存type
        private ArrayList<String> typeList =new ArrayList<>();
        //保存正文内容
        private ArrayList<String> contentList = new ArrayList<>();
        //保存图片信息
        private ArrayList<String> imgList = new ArrayList<>();
        //保存作者id
        private ArrayList<Integer> authorIdList = new ArrayList<>();
        //保存文章发布时间
        private ArrayList<String> releaseTimeList = new ArrayList<>();
        //保存点赞数
        private ArrayList<Integer> starList = new ArrayList<>();
        //保存从数据库查询到的用户名
        private ArrayList<String> userNameList = new ArrayList<>();
        //保存从数据库查询到的真实姓名
        private ArrayList<String> realNameList =new ArrayList<>();
        private ArrayList<Integer> commentSumList=new ArrayList<>();

        public MyIssueAdapter(ArrayList<Integer> id,ArrayList<String > type,
                                  ArrayList<String> content,ArrayList<String> img,
                                  ArrayList<String> releaseTime,
                                  ArrayList<Integer> star,ArrayList<String> userName,
                                  ArrayList<String> realName,ArrayList<Integer> comment) {
            this.idList=id;
            this.typeList=type;
            this.contentList=content;
            this.imgList=img;
            this.releaseTimeList=releaseTime;
            this.starList=star;
            this.userNameList=userName;
            this.realNameList=realName;
            this.commentSumList=comment;
        }

        @Override
        public int getCount() {
            return idList.size();
        }

        @Override
        public Object getItem(int position) {
            return idList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=null;
            view=View.inflate(MyIssueActivity.this,R.layout.list_items_myissue,null);

            TextView userName =view.findViewById(R.id.tv_userName);
            TextView release_time = view.findViewById(R.id.tv_release_time);
            TextView article_content =view.findViewById(R.id.tv_article_content);
            TextView starSum = view.findViewById(R.id.tv_star_sum);
            TextView commentSum=view.findViewById(R.id.tv_comment_sum);
            TextView tv_type=view.findViewById(R.id.tv_type);

            release_time.setText(releaseTimeList.get(position).toString());
            article_content.setText(contentList.get(position).toString());
            starSum.setText(starList.get(position).toString());
            //若真实姓名不为空，则显示真实姓名，否则显示昵称
            if (realNameList.get(position)!=null){
                userName.setText(realNameList.get(position).toString());
            }else {
                userName.setText(userNameList.get(position).toString());
            }
            commentSum.setText(commentSumList.get(position).toString());
            tv_type.setText(typeList.get(position).toString());
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