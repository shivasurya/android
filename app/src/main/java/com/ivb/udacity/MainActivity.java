package com.ivb.udacity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button spotify,scores,library,build,xyz,capstone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spotify = (Button)findViewById(R.id.button);
        scores = (Button)findViewById(R.id.button2);
        library = (Button)findViewById(R.id.button3);
        build = (Button)findViewById(R.id.button4);
        xyz = (Button)findViewById(R.id.button5);
        capstone = (Button)findViewById(R.id.button6);

        spotify.setOnClickListener(this);
        scores.setOnClickListener(this);
        library.setOnClickListener(this);
        build.setOnClickListener(this);
        xyz.setOnClickListener(this);
        capstone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button :  generateToast("Spotify Streamer App");
                break;
            case R.id.button2 : generateToast("Scores App");
                break;
            case R.id.button3 : generateToast("Library App");
                break;
            case R.id.button4 : generateToast("Build It Bigger App");
                break;
            case R.id.button5 : generateToast("XYZ reader App");
                break;
            case R.id.button6 : generateToast("Capstone My App");
                break;

        }
    }
    private void generateToast(String Message){
        String msg = "This Button will Launch my "+Message+" !";
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
}
