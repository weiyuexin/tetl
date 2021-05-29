package top.weiyuexin.tetl;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeTuijianFragment extends Fragment {

    public HomeTuijianFragment() {
        // Required empty public constructor
    }


    public static HomeTuijianFragment newInstance(String param1, String param2) {
       return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_tuijian, container, false);
    }
}