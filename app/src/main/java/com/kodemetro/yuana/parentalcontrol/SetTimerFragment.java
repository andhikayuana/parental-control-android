package com.kodemetro.yuana.parentalcontrol;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


/**
 * Created by yuana on 5/3/16.
 */
public class SetTimerFragment extends Fragment {

    private RadioGroup radioTimer;
    private RadioButton radio1, radio5, radio10, radio15;
    private int idRadio;
    private SharedPreferences sPref;

    public SetTimerFragment() {
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
        View root = inflater.inflate(R.layout.fragment_set_timer, container, false);

        sPref = ParentalApplication.getInstance().getSharedPreferences();

        radioTimer  = (RadioGroup) root.findViewById(R.id.radioTimer);
        radio1     = (RadioButton) root.findViewById(R.id.btn1);
        radio5     = (RadioButton) root.findViewById(R.id.btn5);
        radio10     = (RadioButton) root.findViewById(R.id.btn10);
        radio15     = (RadioButton) root.findViewById(R.id.btn15);

        switch (sPref.getInt("timer",1)){
            case 1:
                idRadio = R.id.btn1;
                break;
            case 5:
                idRadio = R.id.btn5;
                break;
            case 10:
                idRadio = R.id.btn10;
                break;
            case 15:
                idRadio = R.id.btn15;
                break;
            default:
                break;
        }

        radioTimer.check(idRadio);

        radioTimer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.btn1){
                    sPref.edit().putInt("timer", 1).commit();
                }
                else if (checkedId == R.id.btn5){
                    sPref.edit().putInt("timer", 5).commit();
                }
                else if (checkedId == R.id.btn10){
                    sPref.edit().putInt("timer", 10).commit();
                }
                else if (checkedId == R.id.btn15){
                    sPref.edit().putInt("timer", 15).commit();
                }

                Toast.makeText(getActivity().getApplicationContext(),
                        "Anda telah mengatur timer "+sPref.getInt("timer",1)+" menit",
                        Toast.LENGTH_SHORT).show();
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
