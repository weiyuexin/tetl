package top.weiyuexin.tetl;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class HomeTuijianFragment extends Fragment {

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
    //保存从数据库查询到的真实姓名
    private ArrayList<String> realNameList =new ArrayList<>();


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

    //ListView
    private ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_tuijian, null);
        initView(view);

        //新建异步线程，链接查询数据库
        new Task().execute();
        
        return view;
    }

    private void initView(View view) {
        /*初始化各个组件*/
        //头像
        iv_head_pic = view.findViewById(R.id.head_pic);
        //用户名
        tv_userName = view.findViewById(R.id.userName);
        //发布时间
        tv_release_time = view.findViewById(R.id.release_time);
        //正文
        tv_article_content = view.findViewById(R.id.article_content);
        //收藏按钮
        ll_shoucang = view.findViewById(R.id.shoucang);
        //评论
        ll_comment = view.findViewById(R.id.comment);
        //评论总数
        tv_commentSum = view.findViewById(R.id.commentSum);
        //点赞
        ll_star = view.findViewById(R.id.star);
        //点赞总数
        tv_starSum = view.findViewById(R.id.starSum);
        //ListView
        list=view.findViewById(R.id.listView_tuijian);
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
                ResultSet resultSet=statement.executeQuery("SELECT * FROM article ORDER BY time desc");

                //将查询到的数据保存的LISt中
                while (resultSet.next()){
                    idList.add(resultSet.getInt("id"));
                    typeList.add(resultSet.getString("type"));
                    contentList.add(resultSet.getString("content"));
                    imgList.add(resultSet.getString("image"));
                    authorIdList.add(resultSet.getInt("author"));
                    releaseTimeList.add(resultSet.getString("time"));
                    starList.add(resultSet.getInt("star"));
                }
                //查询作者信息
                for(int i=0;i<=authorIdList.size()+1;i++){
                    ResultSet findRealAuthor=statement.executeQuery("SELECT * FROM user WHERE id="+authorIdList.get(i));
                    while (findRealAuthor.next()){
                        userNameList.add(findRealAuthor.getString("userName"));
                        realNameList.add(findRealAuthor.getString("realName"));
                    }
                }

            }catch (Exception e){
                error = e.toString();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            HomeArticleAdapter homeArticleAdapter=new HomeArticleAdapter(idList,typeList,contentList,imgList,
                    authorIdList,releaseTimeList,starList,userNameList,realNameList);
            list.setAdapter(homeArticleAdapter);
            //System.out.println(contentList);
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

        public HomeArticleAdapter(ArrayList<Integer> id,ArrayList<String > type,
                                  ArrayList<String> content,ArrayList<String> img,
                                  ArrayList<Integer> authonid,ArrayList<String> releaseTime,
                                  ArrayList<Integer> star,ArrayList<String> userName,
                                  ArrayList<String> realName) {
            this.idList=id;
            this.typeList=type;
            this.contentList=content;
            this.imgList=img;
            this.authorIdList=authonid;
            this.releaseTimeList=releaseTime;
            this.starList=star;
            this.userNameList=userName;
            this.realNameList=realName;
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
            view=View.inflate(getActivity(),R.layout.list_items_article,null);

            TextView userName =view.findViewById(R.id.userName);
            TextView release_time = view.findViewById(R.id.release_time);
            TextView article_content =view.findViewById(R.id.article_content);
            TextView starSum = view.findViewById(R.id.starSum);

            release_time.setText(releaseTimeList.get(position).toString());
            article_content.setText(contentList.get(position).toString());
            //点赞数量大于0时，显示点赞数
            if(starList.get(position)>0){
                starSum.setText(starList.get(position).toString());
            }

            if (realNameList.get(position)!=null){
                userName.setText(realNameList.get(position).toString());
            }else {
                userName.setText(userNameList.get(position).toString());
            }
            //System.out.println(userNameList);
            //System.out.println(userNameList.size());

            return view;
        }
    }

}