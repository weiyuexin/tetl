package top.weiyuexin.tetl;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

public class MessageFragment extends Fragment {

    //自动刷新布局
    private RefreshLayout refreshLayout_notice;
    private LinearLayout ll_notice_xitong;
    private LinearLayout ll_notice_comment;
    private LinearLayout ll_notice_star;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_message,null);

        ivitView(view);
        return view;
    }

    private void ivitView(View view) {
        //下拉刷新
        refreshLayout_notice=(RefreshLayout)view.findViewById(R.id.refreshLayout_notice);
        refreshLayout_notice.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout_notice.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
        ll_notice_xitong=view.findViewById(R.id.ll_notice_xitong);
        ll_notice_xitong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),NoticeSystem.class);
                startActivity(intent);
                //改变切换动画
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.anim_no);
            }
        });
        ll_notice_comment=view.findViewById(R.id.ll_notice_comment);
        ll_notice_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),NoticeComment.class);
                startActivity(intent);
                //改变切换动画
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.anim_no);
            }
        });
        ll_notice_star=view.findViewById(R.id.ll_notice_star);
        ll_notice_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),NoticeStar.class);
                startActivity(intent);
                //改变切换动画
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.anim_no);
            }
        });
    }
}