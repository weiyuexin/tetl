package top.weiyuexin.tetl;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.gyf.immersionbar.ImmersionBar;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ProfileFragment extends Fragment {

    private ImageView h_back;
    private ImageView h_head;
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


        return view;
    }
}