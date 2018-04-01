package com.example.junaid.cameral;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnCamera,btnGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find view Id
        initView();
    }


    public void onCamera(View view){
        Intent intent = new Intent(getApplicationContext(), Camera.class);
        startActivity(intent);
        finish();
    }

    public void onList(View view) {
        Intent intent = new Intent(getApplicationContext(), ListDisplay.class);
        startActivity(intent);
        finish();

    }


    private void initView() {
        btnCamera = findViewById(R.id.Camera);
        btnGallery = findViewById(R.id.Gallery);

    }

}
