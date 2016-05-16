package com.kodemetro.yuana.parentalcontrol;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MateriFragment extends Fragment {

    private AppCompatCheckBox mCheckMatematika;
    private AppCompatCheckBox mCheckInggris;
    private SharedPreferences sPref;

    public MateriFragment() {
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
        View root = inflater.inflate(R.layout.fragment_materi, container, false);

        sPref = ParentalApplication.getInstance().getSharedPreferences();

        String a = ParentalApplication.getInstance().getQA();

        Log.i("andhika", a);

        mCheckMatematika = (AppCompatCheckBox) root.findViewById(R.id.checkMatematika);
        mCheckInggris    = (AppCompatCheckBox) root.findViewById(R.id.checkInggris);

        if (sPref.getBoolean("materi_matematika",true)==true){
            mCheckMatematika.setChecked(true);
        }

        if (sPref.getBoolean("materi_inggris",true)==true){
            mCheckInggris.setChecked(true);
        }

        mCheckMatematika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sPref.edit()
                        .putBoolean("materi_matematika", mCheckMatematika.isChecked())
                        .commit();
            }
        });

        mCheckInggris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sPref.edit()
                        .putBoolean("materi_inggris", mCheckInggris.isChecked())
                        .commit();
            }
        });

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
