package top.weiyuexin.tetl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class SquareFragment extends Fragment {

    private LinearLayout ll_CET;
    private LinearLayout ll_mandarin;
    private LinearLayout ll_school_calendar;
    private LinearLayout ll_scenery;
    private LinearLayout ll_epidemic_situation;
    private LinearLayout ll_weather;
    private LinearLayout kaoyan;
    private LinearLayout ll_qimo;
    private LinearLayout ll_lostandfound;
    private LinearLayout ll_food;
    private LinearLayout ll_hot;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_square, null);
        initView(view);

        return view;

    }

    private void initView(View view) {
        ll_CET=view.findViewById(R.id.ll_CET);
        ll_CET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://cet.neea.edu.cn/cet/");
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(uri);
                startActivity(intent);
                //改变activity切换动画
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.anim_no);
            }
        });
        ll_mandarin=view.findViewById(R.id.ll_mandarin);
        ll_mandarin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.cltt.org/studentscore");
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(uri);
                startActivity(intent);
                //改变activity切换动画
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.anim_no);
            }
        });
        ll_school_calendar=view.findViewById(R.id.ll_school_calendar);
        ll_school_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CalendarActivity.class);
                startActivity(intent);
                //改变activity切换动画
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.anim_no);
            }
        });
        ll_scenery=view.findViewById(R.id.ll_scenery);
        ll_scenery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SceneryActivity.class);
                startActivity(intent);
                //改变activity切换动画
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.anim_no);
            }
        });
        ll_epidemic_situation=view.findViewById(R.id.ll_epidemic_situation);
        ll_epidemic_situation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://sa.sogou.com/new-weball/page/sgs/epidemic?type_page=VR");
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(uri);
                startActivity(intent);
                //改变activity切换动画
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.anim_no);
            }
        });
        ll_weather=view.findViewById(R.id.ll_weather);
        ll_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.weather.com.cn/weather/101180801.shtml");
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(uri);
                startActivity(intent);
                //改变activity切换动画
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.anim_no);
            }
        });
        kaoyan=view.findViewById(R.id.kaoyan);
        kaoyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),SquareKaoyan.class);
                startActivity(intent);
                //改变activity切换动画
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.anim_no);
            }
        });
        ll_qimo=view.findViewById(R.id.ll_qimo);
        ll_qimo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),SquareQimo.class);
                startActivity(intent);
                //改变activity切换动画
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.anim_no);
            }
        });
        ll_lostandfound=view.findViewById(R.id.ll_lostandfound);
        ll_lostandfound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),SquareLostAndFound.class);
                startActivity(intent);
                //改变activity切换动画
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.anim_no);
            }
        });
        ll_food=view.findViewById(R.id.ll_food);
        ll_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),SquareDeliciousFood.class);
                startActivity(intent);
                //改变activity切换动画
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.anim_no);
            }
        });
        ll_hot=view.findViewById(R.id.ll_hot);
        ll_hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),SquareHotTopics.class);
                startActivity(intent);
                //改变activity切换动画
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.anim_no);
            }
        });
    }



/*
    public void health(View view) {
        Uri uri = Uri.parse("https://sa.sogou.com/new-weball/page/sgs/epidemic?type_page=VR");
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(uri);
        startActivity(intent);
    }*/
}