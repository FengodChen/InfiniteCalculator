package com.fengodchen.infinitecalculator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class SettingActivity extends AppCompatActivity {
    final static String fileOne = "precision_divide";
    final static String fileTwo = "startAnotherActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //Initialize editDivideAccuracy
        EditText editDivideAccuracy = findViewById(R.id.editSetAccuracy);
        editDivideAccuracy.setText("" + get_db(fileOne, 10));

        //Initialize startAnotherActivityCheckbox
        CheckBox startAnotherActivityCheckbox = findViewById(R.id.checkShowResult);
        if(get_db(fileTwo, 0) == 0)
            startAnotherActivityCheckbox.setChecked(false);
        else
            startAnotherActivityCheckbox.setChecked(true);
    }

    //Create data file
    public void create_db(String filename, int num){
        try{
            FileOutputStream outputStream = this.openFileOutput(filename +
                    ".db", Context.MODE_PRIVATE);
            String tmp = "" + num;
            outputStream.write(tmp.getBytes());
            outputStream.close();
        }
        catch (Exception e){
            Toast.makeText(SettingActivity.this, getString(R.string.getDataError),Toast.LENGTH_SHORT).show();
        }
    }


    //Get data from data file
    public int get_db(String filename, int exceptionNum){
        try{
            FileInputStream inputStream = this.openFileInput(filename +
                    ".db");
            int length = inputStream.available();
            byte [] byte_tmp = new byte[length];
            inputStream.read(byte_tmp);
            String str_tmp = new String(byte_tmp);
            int ans = Integer.valueOf(str_tmp);
            //Toast.makeText(SettingActivity.this,""+ ans,Toast.LENGTH_SHORT).show();;
            inputStream.close();
            return ans;
        }
        catch (Exception e){
            create_db(filename, exceptionNum);
            return exceptionNum;
        }
    }

    public void setDivideAccuracy(View view){
        EditText editDivideAccuracy = findViewById(R.id.editSetAccuracy);
        create_db(fileOne, Integer.valueOf(editDivideAccuracy.getText().toString()));
        Toast.makeText(SettingActivity.this ,getString(R.string.hint_successful), Toast.LENGTH_SHORT).show();
    }

    public void onCheckboxClicked(View view){
        //Is the checkbox now checked
        boolean checked = ((CheckBox)view).isChecked();

        switch(view.getId()){
            case R.id.checkShowResult:
                if(checked)
                    create_db(fileTwo, 1);
                else
                    create_db(fileTwo, 0);
                break;
        }
    }

}
