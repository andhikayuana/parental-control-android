package com.kodemetro.yuana.parentalcontrol;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

/**
 * Created by yuana on 5/3/16.
 */
public class LockScreenActivity extends AppCompatActivity {

    private View mContentView;
    private Button btnStoreAnswer;
    private TextView txtQuestion;
    private EditText txtAnswer;
    private String mQuestion, mAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        makeFullScreen();

        setContentView(R.layout.activity_lock_screen);

        mContentView = findViewById(R.id.fullscreen_content);

        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        txtQuestion     = (TextView) findViewById(R.id.txtQuestion);
        txtAnswer       = (EditText) findViewById(R.id.txtAnswer);
        btnStoreAnswer  = (Button)   findViewById(R.id.btnStoreAnswer);

        JSONObject  qa = ParentalApplication.getInstance().getQA();

        try{
            mQuestion   = qa.getString("pertanyaan");
            mAnswer     = qa.getString("jawaban");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        txtQuestion.setText(mQuestion);

        btnStoreAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String answer = txtAnswer.getText().toString();

                if (answer.equalsIgnoreCase(mAnswer)){
                    finish();
                }
                else{
                    txtAnswer.setText("");
                    Toast.makeText(getApplicationContext(),
                            "Jawaban salah, Masukkan lagi !", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        return;
    }

    public void makeFullScreen() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(Build.VERSION.SDK_INT < 19) { //View.SYSTEM_UI_FLAG_IMMERSIVE is only on API 19+
            this.getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        } else {
            this.getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }
}
