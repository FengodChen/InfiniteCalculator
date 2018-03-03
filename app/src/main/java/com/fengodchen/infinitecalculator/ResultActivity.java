package com.fengodchen.infinitecalculator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String string = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        EditText editText = findViewById(R.id.resultText);
        editText.setFocusable(false);
        editText.setText(string);
    }

    public void onClickCopy(View view){
        EditText answer = findViewById(R.id.resultText);
        ClipData mClipData = ClipData.newPlainText(answer.getText().toString(), answer.getText().toString());
        ClipboardManager cmb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(mClipData);
        Toast.makeText(ResultActivity.this, getString(R.string.copyDone), Toast.LENGTH_SHORT).show();
    }
}
