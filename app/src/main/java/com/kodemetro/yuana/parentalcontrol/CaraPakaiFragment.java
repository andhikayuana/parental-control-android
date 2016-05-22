package com.kodemetro.yuana.parentalcontrol;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created by yuana on 5/3/16.
 */
public class CaraPakaiFragment extends Fragment {

    private ImageView imgCara1, imgCara2, imgCara3;

    public CaraPakaiFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_cara_pakai, container, false);

        imgCara1 = (ImageView) root.findViewById(R.id.imgCara1);
        imgCara2 = (ImageView) root.findViewById(R.id.imgCara2);
        imgCara3 = (ImageView) root.findViewById(R.id.imgCara3);

        try {
            AssetManager assetManager = this.getContext().getAssets();

            InputStream cara1 = assetManager.open("daftar_aplikasi.jpg");
            InputStream cara2 = assetManager.open("set_timer.jpg");
            InputStream cara3 = assetManager.open("materi.jpg");

            Drawable d1 = Drawable.createFromStream(cara1, null);
            Drawable d2 = Drawable.createFromStream(cara2, null);
            Drawable d3 = Drawable.createFromStream(cara3, null);

            imgCara1.setImageDrawable(d1);
            imgCara2.setImageDrawable(d2);
            imgCara3.setImageDrawable(d3);

        }
        catch (IOException e){
            e.printStackTrace();
        }



        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
