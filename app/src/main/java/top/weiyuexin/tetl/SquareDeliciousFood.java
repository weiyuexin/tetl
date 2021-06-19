package top.weiyuexin.tetl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class SquareDeliciousFood extends AppCompatActivity {

    private Toolbar square_kaoyan;
    private ImageView square_kaoyan_add;
    private RefreshLayout refreshLayout_square_kaoyan;
    private ListView listview_square_kaoyan;

    //保存数据库查询到的id
    private ArrayList<Integer> idList = new ArrayList<>();
    //保存数据库查询到的type
    private ArrayList<String> typeList =new ArrayList<>();
    //保存数据库查询到的正文内容
    private ArrayList<String> contentList = new ArrayList<>();
    //保存数据库查到的图片信息
    private ArrayList<String> imgList = new ArrayList<>();
    //保存数据库查询到的作者id
    private ArrayList<Integer> authorIdList = new ArrayList<>();
    //保存数据库查询到的文章发布时间
    private ArrayList<String> releaseTimeList = new ArrayList<>();
    //保存数据库查询到的点赞数
    private ArrayList<Integer> starList = new ArrayList<>();
    private ArrayList<String> userNameList = new ArrayList<>();
    //评论数
    //保存从数据库查询到的真实姓名
    private ArrayList<String> realNameList =new ArrayList<>();
    //保存从数据库查询到的评论总数
    private ArrayList<Integer> commentNumList = new ArrayList<>();
    //保存查询到的手机号
    private ArrayList<String> phoneNumberList = new ArrayList<>();

    /*ListView中的布局*/
    //用户头像组件
    private ImageView iv_head_pic;
    //显示用户名组件
    private TextView tv_userName;
    //发布时间
    private TextView tv_release_time;
    //文章正文
    private TextView tv_article_content;
    //收藏按钮
    private LinearLayout ll_shoucang;
    //评论按钮
    private LinearLayout ll_comment;
    //评论总数
    private TextView tv_commentSum;
    //点赞按钮
    private LinearLayout ll_star;
    //点赞总数
    private TextView tv_starSum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square_delicious_food);
        ImmersionBar.with(this)
                .statusBarColor(R.color.gray)     //状态栏颜色，不写默认透明色
                .fitsSystemWindows(true)

                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
        initView();
        //新建异步线程，链接查询数据库
        new Task().execute();
        //定义文章列表点击事件
        click();
    }
    private void initView() {
        /*初始化各个组件*/
        square_kaoyan=findViewById(R.id.square_kaoyan);
        square_kaoyan.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //头像
        iv_head_pic = findViewById(R.id.head_pic);
        //用户名
        tv_userName = findViewById(R.id.userName);
        //发布时间
        tv_release_time = findViewById(R.id.release_time);
        //正文
        tv_article_content = findViewById(R.id.article_content);
        //收藏按钮
        ll_shoucang = findViewById(R.id.shoucang);
        //评论
        ll_comment = findViewById(R.id.comment);
        //评论总数
        tv_commentSum = findViewById(R.id.commentSum);
        //点赞
        ll_star = findViewById(R.id.star);
        //点赞总数
        tv_starSum = findViewById(R.id.starSum);


        square_kaoyan_add=findViewById(R.id.square_kaoyan_add);
        square_kaoyan_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SquareDeliciousFood.this,EditSquareDeliciousFood.class);
                startActivity(intent);
                //改变activity切换动画
                overridePendingTransition(R.anim.slide_in_right,R.anim.anim_no);
            }
        });

        //下拉刷新
        refreshLayout_square_kaoyan=(RefreshLayout)findViewById(R.id.refreshLayout_square_kaoyan);
        refreshLayout_square_kaoyan.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout_square_kaoyan.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //新建异步线程，链接查询数据库
                new Task().execute();
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        listview_square_kaoyan=findViewById(R.id.listview_square_kaoyan);
    }

    private void click() {
        listview_square_kaoyan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //初始化意图对象
                Intent intent=new Intent(SquareDeliciousFood.this,ArticleContentActivity.class);
                //传递数据信息
                Bundle data=new Bundle();
                data.putInt("id",idList.get(position));
                data.putInt("authorid",authorIdList.get(position));
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

        String error = "";

        //清空原始数据，主要是刷新时
        @Override
        protected Void doInBackground(Void... voids) {
            idList.clear();
            typeList.clear();
            contentList.clear();
            imgList.clear();
            authorIdList.clear();
            starList.clear();
            commentNumList.clear();
            releaseTimeList.clear();
            userNameList.clear();
            realNameList.clear();
            try {
                //动态加载类
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://1.15.60.193:3306/Android?useUnicode=true&characterEncoding=utf8",
                        "root", "Weiyuexin@123456");
                Statement statement = connection.createStatement();
                //mysql简单查询语句
                ResultSet resultSet=statement.executeQuery("SELECT * FROM article WHERE type='美食' ORDER BY id desc");
                //将查询到的数据保存的LISt中
                while (resultSet.next()) {
                    idList.add(resultSet.getInt("id"));
                    typeList.add(resultSet.getString("type"));
                    contentList.add(resultSet.getString("content"));
                    imgList.add(resultSet.getString("image"));
                    authorIdList.add(resultSet.getInt("author"));
                    releaseTimeList.add(resultSet.getString("time"));
                    starList.add(resultSet.getInt("star"));
                    commentNumList.add(resultSet.getInt("comment"));
                }
                //查询作者信息
                for (int i = 0; i <= authorIdList.size() + 1; i++) {
                    ResultSet findRealAuthor = statement.executeQuery("SELECT * FROM user WHERE id=" + authorIdList.get(i));
                    while (findRealAuthor.next()) {
                        userNameList.add(findRealAuthor.getString("userName"));
                        realNameList.add(findRealAuthor.getString("realName"));
                        phoneNumberList.add(findRealAuthor.getString("phoneNumber"));
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
            HomeArticleAdapter homeArticleAdapter=new HomeArticleAdapter(idList,typeList,contentList,imgList,
                    authorIdList,releaseTimeList,starList,userNameList,realNameList,commentNumList,phoneNumberList);
            listview_square_kaoyan.setAdapter(homeArticleAdapter);
            //System.out.println(contentList);
            listview_square_kaoyan.setVisibility(View.VISIBLE);
            homeArticleAdapter.notifyDataSetInvalidated();
            super.onPostExecute(aVoid);
        }
    }

    //ListView适配器
    class HomeArticleAdapter extends BaseAdapter {
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
        private ArrayList<String> phoneNumberList = new ArrayList<>();
        //保存收藏的状态，默认是0，点击收藏后会变成1
        private ArrayList<Integer> shoucangList=new ArrayList<>();
        //保存点赞的状态，同上
        private ArrayList<Integer> isStaredList=new ArrayList<>();
        private ArrayList<Integer> commentSumList=new ArrayList<>();

        public HomeArticleAdapter(ArrayList<Integer> id,ArrayList<String > type,
                                  ArrayList<String> content,ArrayList<String> img,
                                  ArrayList<Integer> authonid,ArrayList<String> releaseTime,
                                  ArrayList<Integer> star,ArrayList<String> userName,
                                  ArrayList<String> realName,ArrayList<Integer> comment,ArrayList<String> phone) {
            this.idList=id;
            this.typeList=type;
            this.contentList=content;
            this.imgList=img;
            this.authorIdList=authonid;
            this.releaseTimeList=releaseTime;
            this.starList=star;
            this.userNameList=userName;
            this.realNameList=realName;
            this.commentSumList=comment;
            this.phoneNumberList=phone;
            //初始化是否收藏标志列表和是否点赞标志位列表，默认是0
            for (int i=0;i<idList.size();i++){
                shoucangList.add(0);
                isStaredList.add(0);
            }
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
            view=View.inflate(SquareDeliciousFood.this,R.layout.list_items_article,null);

            TextView userName =view.findViewById(R.id.userName);
            TextView release_time = view.findViewById(R.id.release_time);
            TextView article_content =view.findViewById(R.id.article_content);
            TextView starSum = view.findViewById(R.id.starSum);
            ImageView head_pic=view.findViewById(R.id.head_pic);

            release_time.setText(releaseTimeList.get(position).toString());
            article_content.setText(contentList.get(position).toString());

            //点击头像跳转到作者详情页
            head_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //初始化意图
                    Intent intent1=new Intent(SquareDeliciousFood.this,PersonalInformationDetailsPageActivity.class);
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
                    Intent intent1=new Intent(SquareDeliciousFood.this,PersonalInformationDetailsPageActivity.class);
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


            //点赞数量大于0时，显示点赞数
            if(starList.get(position)>0){
                starSum.setText(starList.get(position).toString());
            }
            //判断是否点赞，若为1，则已点赞，显示为已点赞状态
            if(isStaredList.get(position) == 1){
                LinearLayout star=view.findViewById(R.id.star);
                star.setSelected(true);
            }

            //若真实姓名不为空，则显示真实姓名，否则显示昵称
            if (realNameList.get(position)!=null){
                userName.setText(realNameList.get(position).toString());
            }else {
                userName.setText(userNameList.get(position).toString());
            }
            /*if(realNameList.get(position)==null&& userNameList.get(position)==null){
                userName.setText(phoneNumberList.get(position));
            }*/
            //定义收藏按钮
            LinearLayout shoucang=view.findViewById(R.id.shoucang);
            //定义点击收藏后的事件
            shoucang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //收藏状态变成1
                    shoucangList.set(position,1);
                    shoucang.setSelected(true);
                    Toast.makeText(SquareDeliciousFood.this,"收藏成功!",Toast.LENGTH_SHORT).show();
                }
            });
            //显示是否收藏状态，若为1，则是收藏
            if(shoucangList.get(position) ==1){
                shoucang.setSelected(true);   //改变图片样式
            }

            //定义评论按钮
            LinearLayout comment=view.findViewById(R.id.comment);
            //评论数量显示TextView
            TextView comment_sum=view.findViewById(R.id.commentsum);
            //点击评论后的事件
            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(SquareDeliciousFood.this,"评论功能升级中，敬请期待",Toast.LENGTH_SHORT).show();
                }
            });
            if(commentSumList.get(position)>0){
                comment_sum.setText(commentSumList.get(position).toString());
            }
            //定义点赞按钮
            LinearLayout star=view.findViewById(R.id.star);
            //判断是否已经点赞，点赞后点赞数加一，存入数据库
            if(isStaredList.get(position) == 0){
                star.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer starsum;
                        starsum=starList.get(position);
                        starsum++;
                        //更新点赞数列表中的值
                        starList.set(position,starsum);
                        star.setSelected(true);
                        //更新是否点赞的标志位
                        isStaredList.set(position,1);
                        //更新点赞数
                        starSum.setText(starList.get(position).toString());
                        int finalStarsum = (int)starsum;
                        new Thread(){
                            @Override
                            public void run() {
                                try {
                                    //动态加载类
                                    Class.forName("com.mysql.jdbc.Driver");
                                    Connection connection= DriverManager.getConnection("jdbc:mysql://1.15.60.193:3306/Android",
                                            "root","Weiyuexin@123456");
                                    Statement statement=connection.createStatement();
                                    //更新点赞数
                                    boolean resultSet=statement.execute("UPDATE article SET star="+ finalStarsum+" WHERE id=" +(int)idList.get(position));
                                }catch (Exception e){
                                    String error;
                                    error = e.toString();
                                    System.out.println(error);
                                }
                            }
                        }.start();//开启线程
                    }
                });
            }
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