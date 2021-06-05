package top.weiyuexin.tetl;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ArticleContentActivity extends AppCompatActivity {

    //自动刷新布局
    private RefreshLayout refreshLayout_article;
    private Toolbar toolbar;
    //定义文章正文的线性布局
    private LinearLayout ll_content;
    //定义评论输入线性布局
    private LinearLayout ll_comment;
    //定义评论输入框
    private EditText comment_comment;
    //定义提交评论按钮
    private TextView send_comment;
    //定义底部线性布局，显示收藏、评论、点赞等信息
    private LinearLayout ll_bottom;
    //定义开启输入评论按钮
    private LinearLayout comment;
    //定义文章作者显示
    private TextView author;
    //作者头像
    private ImageView head_pic;
    //定义文章发布日期
    private TextView registerTime;
    //定义文章正文显示组件
    private TextView content;
    //定义评论总数显示组件
    private TextView commentSum;
    //定义点赞总数显示组件
    private TextView starSum;

    private Bundle data;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_content);
        /*接收数据*/
        Intent intent=getIntent();
        data=intent.getExtras();


        //初始话视图
        initView();
        //设置点击返回事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //给评论按钮设置点击事件
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_bottom.setVisibility(View.GONE);
                ll_comment.setVisibility(View.VISIBLE);
            }
        });
        //点击正文区域时，隐藏Edit Text框
        ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_bottom.setVisibility(View.VISIBLE);
                ll_comment.setVisibility(View.GONE);
            }
        });

        //提交评论按钮点击事件
        send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ArticleContentActivity.this,"评论成功",Toast.LENGTH_SHORT).show();
            }
        });
        //点击头像跳转到作者详情页
        head_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化意图
                Intent intent1=new Intent(ArticleContentActivity.this,PersonalInformationDetailsPageActivity.class);
                //传递数据信息
                Bundle bundle=new Bundle();
                bundle.putInt("id",data.getInt("authorid"));

                intent1.putExtras(bundle);
                //激活意图
                startActivity(intent1);
                //改变activity切换动画
                overridePendingTransition(R.anim.slide_in_right,R.anim.anim_no);
            }
        });
        //点击昵称跳转到作者详情页
        author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化意图
                Intent intent1=new Intent(ArticleContentActivity.this,PersonalInformationDetailsPageActivity.class);
                //传递数据信息
                Bundle bundle=new Bundle();
                bundle.putInt("id",data.getInt("authorid"));

                intent1.putExtras(bundle);
                //激活意图
                startActivity(intent1);
                //改变activity切换动画
                overridePendingTransition(R.anim.slide_in_right,R.anim.anim_no);
            }
        });

        /*//获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String nowTime;
        nowTime=simpleDateFormat.format(date);
        System.out.println(nowTime);*/


        ImmersionBar.with(this)
                .statusBarColor(R.color.white)     //状态栏颜色，不写默认透明色
                .fitsSystemWindows(true)

                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
    }

    //初始化各个组件
    private void initView() {
        //初始化toolbar
        toolbar=findViewById(R.id.content_toolbar);
        //初始化
        ll_comment=findViewById(R.id.ll_comment);
        comment_comment=findViewById(R.id.comment_content);
        send_comment=findViewById(R.id.send_comment);
        ll_bottom=findViewById(R.id.ll_bottom);
        comment=findViewById(R.id.comment);
        ll_content=findViewById(R.id.ll_content);
        head_pic=findViewById(R.id.article_head_pic);

        author=findViewById(R.id.contet_userName);
        registerTime=findViewById(R.id.content_release_time);
        content=findViewById(R.id.content_article_content);
        commentSum=findViewById(R.id.content_comment_sum);
        starSum=findViewById(R.id.star_sum);
        refreshLayout_article=findViewById(R.id.refreshLayout_article);

        //下拉刷新
        refreshLayout_article.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout_article.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });

        /*将接受到的数据显示在对应的组件上*/
        if(data.getString("author") != null){
            author.setText(data.getString("author"));
        }else {
            author.setText(data.getString("username"));
        }
        registerTime.setText(data.getString("time"));
        content.setText(data.getString("content"));
        if(data.getInt("commentSum") > 0){
            commentSum.setText(String.valueOf(data.getInt("commentSum")));
        }
        if (data.getInt("starSum")>0){
            starSum.setText(String.valueOf(data.getInt("starSum")));
        }
        System.out.println(data);
    }


    @Override
    /*重写finish方法，改变返回时的动画*/
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_out_right);
    }
}