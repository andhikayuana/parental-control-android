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


public class SetTimerFragment extends Fragment {

    private RadioGroup radioTimer;
    private RadioButton radio10, radio15, radio20, radio25;
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
        radio10     = (RadioButton) root.findViewById(R.id.btn10);
        radio15     = (RadioButton) root.findViewById(R.id.btn15);
        radio20     = (RadioButton) root.findViewById(R.id.btn20);
        radio25     = (RadioButton) root.findViewById(R.id.btn25);

        switch (sPref.getInt("timer",10)){
            case 10:
                idRadio = R.id.btn10;
                break;
            case 15:
                idRadio = R.id.btn15;
                break;
            case 20:
                idRadio = R.id.btn20;
                break;
            case 25:
                idRadio = R.id.btn25;
                break;
            default:
                break;
        }

        radioTimer.check(idRadio);

        radioTimer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.btn10){
                    sPref.edit().putInt("timer", 10).commit();
                }
                else if (checkedId == R.id.btn15){
                    sPref.edit().putInt("timer", 15).commit();
                }
                else if (checkedId == R.id.btn20){
                    sPref.edit().putInt("timer", 20).commit();
                }
                else if (checkedId == R.id.btn25){
                    sPref.edit().putInt("timer", 25).commit();
                }

                Toast.makeText(getActivity().getApplicationContext(),
                        "Anda telah mengatur timer "+sPref.getInt("timer",10)+" menit",
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
