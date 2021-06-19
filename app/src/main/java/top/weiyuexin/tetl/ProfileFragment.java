package top.weiyuexin.tetl;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ProfileFragment extends Fragment {

    //自动刷新布局
    private RefreshLayout refreshLayout_profile;
    private ImageView h_back;
    private ImageView h_head;
    private TextView profileUsername;
    private ImageView profilePic;
    private LinearLayout ll_profile_about;
    private LinearLayout ll_profile_message;
    private LinearLayout ll_myIssue;
    private LinearLayout ll_helpAndFeedBack;
    private LinearLayout ll_setting;
    private LinearLayout ll_account;

    private SharedPreferences sharedPreferences;
    private String userName;
    private int sex;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, null);

        sharedPreferences= getContext().getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        h_back=view.findViewById(R.id.h_back);
        h_head=view.findViewById(R.id.h_head);
        Glide.with(getActivity()).load(R.drawable.head_img)
                .bitmapTransform(new BlurTransformation(getActivity(), 25), new CenterCrop(getActivity()))
                .into(h_back);

        Glide.with(getActivity()).load(R.drawable.head_img)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(h_head);
        profileUsername=view.findViewById(R.id.profileUsername);
        profilePic=view.findViewById(R.id.profilePic);
        userName=sharedPreferences.getString("userName","");
        sex=sharedPreferences.getInt("sex",1);
        profileUsername.setText(userName);
        if(sex==0){
            profilePic.setImageResource(R.drawable.male);
        }else {
            profilePic.setImageResource(R.drawable.female);
        }

        //下拉刷新
        refreshLayout_profile=(RefreshLayout)view.findViewById(R.id.refreshLayout_profile);
        refreshLayout_profile.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                userName=sharedPreferences.getString("userName","");
                sex=sharedPreferences.getInt("sex",1);
                profileUsername.setText(userName);
                if(sex==0){
                    profilePic.setImageResource(R.drawable.male);
                }else {
                    profilePic.setImageResource(R.drawable.female);
                }
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout_profile.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
        //初始化关于按钮
        ll_profile_about=view.findViewById(R.id.ll_profile_about);
        //点击关于，跳转到详情页面
        ll_profile_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),AboutUsActivity.class);
                startActivity(intent);
                //改变切换动画
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.anim_no);
            }
        });

        //初始化个人信息布局按钮
        ll_profile_message=view.findViewById(R.id.ll_profile_message);
        //点击个人信息，跳转到详情页面
        ll_profile_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),ProfileMessage.class);
                startActivity(intent);
                //改变切换动画
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.anim_no);
            }
        });
        //初始化账号管理页面
        ll_account=view.findViewById(R.id.ll_account);
        ll_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),AccountManagement.class);
                startActivity(intent);
                //改变切换动画
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.anim_no);
            }
        });

        //初始化我的发布布局按钮
        ll_myIssue=view.findViewById(R.id.ll_myIssue);
        //设置我的发布点击事件
        ll_myIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MyIssueActivity.class);

                startActivity(intent);
                //改变切换动画
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.anim_no);
            }
        });

        //帮助与反馈
        ll_helpAndFeedBack=view.findViewById(R.id.ll_helpAndFeedBack);
        ll_helpAndFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(),HelpandFeedBack.class);
                startActivity(intent);
                //改变切换动画
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.anim_no);
            }
        });

        //设置
        ll_setting=view.findViewById(R.id.ll_setting);
        ll_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(),Setting.class);
                startActivity(intent);
                //改变切换动画
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.anim_no);
            }
        });

        return view;
    }


}