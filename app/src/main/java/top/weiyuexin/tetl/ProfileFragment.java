package top.weiyuexin.tetl;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.gyf.immersionbar.ImmersionBar;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ProfileFragment extends Fragment {

    private ImageView h_back;
    private ImageView h_head;
    private LinearLayout ll_profile_about;
    private LinearLayout ll_profile_message;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, null);

        h_back=view.findViewById(R.id.h_back);
        h_head=view.findViewById(R.id.h_head);
        Glide.with(getActivity()).load(R.drawable.head_img)
                .bitmapTransform(new BlurTransformation(getActivity(), 25), new CenterCrop(getActivity()))
                .into(h_back);

        Glide.with(getActivity()).load(R.drawable.head_img)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(h_head);
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

        return view;
    }
}