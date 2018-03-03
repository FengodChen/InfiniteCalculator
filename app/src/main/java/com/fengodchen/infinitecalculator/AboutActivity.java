package com.fengodchen.infinitecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toast.makeText(AboutActivity.this, getString(R.string.showHint), Toast.LENGTH_LONG).show();
    }

    public void showIntroduction(View view){
        Toast.makeText(AboutActivity.this, getString(R.string.app_introduction), Toast.LENGTH_LONG).show();
    }

    public void showDeveloper(View view){
        Toast.makeText(AboutActivity.this, getString(R.string.app_developer), Toast.LENGTH_LONG).show();
    }

    public void showHint(View view){
        Toast.makeText(AboutActivity.this, getString(R.string.showHint), Toast.LENGTH_LONG).show();
    }
}
