package top.weiyuexin.tetl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

public class SceneryActivity extends AppCompatActivity {

    private Toolbar square_photo;
    private ListView mylistview;
    private MyAdapter myAdapter;
    //定义数组，用于通讯录数据
    private String[] names={"大礼堂","大礼堂远景","古老建筑","河南大学龙子湖校区南门","河南大学龙子湖校区教学楼","河南留学欧美预备学校校门","河南留学欧美预备学校校门","大礼堂","金明校区西大门","明伦校区南大门"};
    private int[] imgs={R.drawable.school1,R.drawable.school2,R.drawable.school3,R.drawable.school7,R.drawable.school8,
            R.drawable.school9,R.drawable.school10,R.drawable.school11,R.drawable.school12,R.drawable.school13};

    //定义一个通讯录列表作为数据源
    private List<Users> books=new ArrayList<Users>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenery);
        ImmersionBar.with(this)
                .statusBarColor(R.color.gray)     //状态栏颜色，不写默认透明色
                .fitsSystemWindows(true)

                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
        square_photo=findViewById(R.id.square_photo);
        square_photo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //使用ListView展示通讯录
        //1、初始化控件
        mylistview=findViewById(R.id.mylistview);
        //初始化数据
        initDataBooks();

        //2、创建适配器（参数1：上下文，参数2：列表项布局文件，参数3：数据源）
        myAdapter=new MyAdapter(books,SceneryActivity.this);

        //3、设置适配器到ListView
        mylistview.setAdapter(myAdapter);
    }
    private void initDataBooks(){
        for(int i=0;i< names.length;i++){
            //新建Person对象，存放通讯录的数据（头像、姓名）
            Users person=new Users(names[i],imgs[i]);
            //将通讯录数据加入到数据列表中
            books.add(person);
        }
    }
    public class MyAdapter extends BaseAdapter {
        private List<Users> pdata=new ArrayList<Users>();
        //上下文
        private Context context;
        //构造方法

        public MyAdapter(List<Users> pdata, Context context) {
            this.pdata = pdata;
            this.context = context;
        }

        @Override
        public int getCount() {
            return pdata.size(); //获取列表项个数
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;//返回数据项在列表中的索引（下标
        }
        //优化ListView
        //定义一个ViewHolder静态类
        class ViewHolder{
            //定义属性，对应是列表项数据
            public ImageView myimg;
            public TextView myname;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //定义一个ViewHolder对象
            ViewHolder holder;
            //判断convertView是否为空，convertView对应的列表项视图
            if(convertView==null){
                //新建
                holder=new ViewHolder();
                convertView= LayoutInflater.from(context).inflate(R.layout.list_items_photo,parent,false);

                holder.myimg=(ImageView) convertView.findViewById(R.id.item_image);
                holder.myname=(TextView) convertView.findViewById(R.id.item_name);

                convertView.setTag(holder);

            }else{
                //复用列表项
                holder=(ViewHolder) convertView.getTag();
            }

            //设置列表项数据
            holder.myimg.setImageResource(pdata.get(position).getImg());
            holder.myname.setText(pdata.get(position).getName());

            return convertView;
        }
    }

    public class Users {
        private String name;
        private int img;

        public Users(){
        }
        public Users(String name, int img) {
            this.name = name;
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getImg() {
            return img;
        }

        public void setImg(int img) {
            this.img = img;
        }
    }
    @Override
    /*重写finish方法，改变返回时的动画*/
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_out_right);
    }
}