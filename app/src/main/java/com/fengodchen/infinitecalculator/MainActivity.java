package com.fengodchen.infinitecalculator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
    //Message to another Activity
    public final static String EXTRA_MESSAGE = "no message";
    public final static String ERROR = "Return String an ERROR";

    //Using when create mainActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Make answer unchangeable
        EditText answer = findViewById(R.id.answer);
        answer.setFocusable(false);
    }

    //Create Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    //Set onClick item on menu
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch(menuItem.getItemId()){
            case R.id.about_item:
                Intent about_intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(about_intent);
                return true;
            case R.id.setting_item:
                Intent setting_intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(setting_intent);
                return true;
            case R.id.exit_item:
                finish();
                return true;
            default:
                return false;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void onClickAdd(View view){
        showAnswer(add());
        closeKeyboard();
    }
    public void onClickMinus(View view){
        showAnswer(minus());
        closeKeyboard();
    }
    public void onClickMultiply(View view){
        showAnswer(multiply());
        closeKeyboard();
    }
    public void onClickDivision(View view){
        showAnswer(division());
        closeKeyboard();
    }
    public void onClickPower(View view){
        showAnswer(power());
        closeKeyboard();
    }
    public void onClickRemainder(View view){
        showAnswer(remainder());
        closeKeyboard();
    }
    public void onClickFactorial(View view){
        showAnswer(factorial());
        closeKeyboard();
    }
    public void onClickArrangement(View view){
        showAnswer(arrangement());
        closeKeyboard();
    }
    public void onClickCombination(View view){
        showAnswer(combination());
        closeKeyboard();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Copy the answer to system clipboard
    public void onClickCopy(View view){
        EditText answer = findViewById(R.id.answer);
        ClipData mClipData = ClipData.newPlainText(answer.getText().toString(), answer.getText().toString());
        ClipboardManager cmb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(mClipData);
        Toast.makeText(MainActivity.this, getString(R.string.copyDone), Toast.LENGTH_SHORT).show();
    }

    //Set sourceOne as answer and then clear sourceTwo and answer
    public void onClickSetAsSource(View view){
        EditText sourceOne = findViewById(R.id.sourceOne);
        EditText sourceTwo = findViewById(R.id.sourceTwo);
        EditText answer = findViewById(R.id.answer);
        sourceOne.setText(answer.getText().toString());
        sourceTwo.setText("");
        answer.setText("");
    }

    //Exchange sourceOne and sourceTwo
    public void onClickExchange(View view){
        EditText sourceOne = findViewById(R.id.sourceOne);
        EditText sourceTwo = findViewById(R.id.sourceTwo);
        String tmp = sourceOne.getText().toString();

        sourceOne.setText(sourceTwo.getText());
        sourceTwo.setText(tmp);
    }

    //Close the Virtual Keyboard
    private void closeKeyboard(){
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void sendStringToActivity(Class activity, String string){
        Intent intent = new Intent(this, activity);
        intent.putExtra(EXTRA_MESSAGE, string);
        startActivity(intent);
    }

    //Show answer on editTextAnswer or new Activity
    private void showAnswer(String answer){
        EditText editTextAnswer = findViewById(R.id.answer);
        if(answer.compareTo(ERROR) == 0){
            editTextAnswer.setText(getString(R.string.errorOnAnswer));
        }else {
            if (get_db("startAnotherActivity", 0) == 0)
                editTextAnswer.setText(answer);
            else
                sendStringToActivity(ResultActivity.class, answer);
        }
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
            Toast.makeText(MainActivity.this, getString(R.string.getDataError),Toast.LENGTH_SHORT).show();
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
            //Toast.makeText(MainActivity.this,""+ ans,Toast.LENGTH_SHORT).show();;
            inputStream.close();
            return ans;
        }
        catch (Exception e){
            create_db(filename, exceptionNum);
            return exceptionNum;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Method of add
    private String add(){
        EditText sourceOne = findViewById(R.id.sourceOne);
        EditText sourceTwo = findViewById(R.id.sourceTwo);

        //Using class BigDecimal to deal with two source
        try {
            BigDecimal bigDecimalOne = new BigDecimal(sourceOne.getText().toString());
            BigDecimal bigDecimalTwo = new BigDecimal(sourceTwo.getText().toString());
            return bigDecimalOne.add(bigDecimalTwo).toString();
        }catch(Exception e){
            return ERROR;
        }
    }

    //Method of minus
    private String minus(){
        EditText sourceOne = findViewById(R.id.sourceOne);
        EditText sourceTwo = findViewById(R.id.sourceTwo);

        //Using class BigDecimal to deal with two source
        try{
            BigDecimal bigDecimalOne = new BigDecimal(sourceOne.getText().toString());
            BigDecimal bigDecimalTwo = new BigDecimal(sourceTwo.getText().toString());
            return bigDecimalOne.subtract(bigDecimalTwo).toString();
        }catch(Exception e){
            return ERROR;
        }
    }

    //Method of multiply
    private String multiply(){
        EditText sourceOne = findViewById(R.id.sourceOne);
        EditText sourceTwo = findViewById(R.id.sourceTwo);

        //Using class BigDecimal to deal with two source
        try{
            BigDecimal bigDecimalOne = new BigDecimal(sourceOne.getText().toString());
            BigDecimal bigDecimalTwo = new BigDecimal(sourceTwo.getText().toString());
            return bigDecimalOne.multiply(bigDecimalTwo).toString();
        }catch(Exception e){
            return ERROR;
        }
    }

    //Method of division
    private String division(){
        EditText sourceOne = findViewById(R.id.sourceOne);
        EditText sourceTwo = findViewById(R.id.sourceTwo);

        //Initialize double b
        double b = -1;

        //Using class BigDecimal to deal with two source
        try{
            b = Double.valueOf(sourceTwo.getText().toString());

            int num = get_db("precision_divide", 10);  //Get accuracy from "precision_divide.db"

            BigDecimal bigDecimalOne = new BigDecimal(sourceOne.getText().toString());
            BigDecimal bigDecimalTwo = new BigDecimal(sourceTwo.getText().toString());
            return bigDecimalOne.divide(bigDecimalTwo, num, BigDecimal.ROUND_HALF_UP).toString();
        }catch(Exception e){
            if((int)b == 0)
                return getString(R.string.errorOnZero);
            else
                return ERROR;
        }
    }

    //Method of power
    private String power(){
        EditText sourceOne = findViewById(R.id.sourceOne);
        EditText sourceTwo = findViewById(R.id.sourceTwo);

        String base = sourceOne.getText().toString();
        String exponent = sourceTwo.getText().toString();
        BigDecimal baseMath;
        int n;

        //Make sure the program will not error
        try{
            n = Integer.valueOf(exponent);
            baseMath = new BigDecimal(base);
        }catch (Exception e) {
            closeKeyboard();
            return ERROR;
        }

        //Judge is the exponent an integer
        if(exponent.length() == 0){
            return ERROR;
        }else if(exponent.indexOf('.') != -1){
            //Isn't an integer
            Toast.makeText(MainActivity.this, getString(R.string.hint_exponentNotInteger), Toast.LENGTH_LONG).show();
            return ERROR;
        }else if(Integer.valueOf(exponent) < 0){
            //Lower than zero

            double b = -1;

            try{
                baseMath = baseMath.pow(-n);
                b = Double.valueOf(base);
                int num = get_db("precision_divide", 10);  //Get accuracy from "precision_divide.db"
                return (new BigDecimal("1")).divide(baseMath, num, BigDecimal.ROUND_HALF_UP).toString();
            }catch(Exception e){
                if((int)b == 0)
                    return getString(R.string.errorOnZero);
                else
                    return ERROR;
            }

        }else if(Integer.valueOf(exponent) == 0){
            //Exponent is zero
            return "1";
        }else{
            //Otherwise
            baseMath = baseMath.pow(n);
            return baseMath.toString();
        }
    }

    //Method of remainder
    private String remainder(){
        EditText sourceOne = findViewById(R.id.sourceOne);
        EditText sourceTwo = findViewById(R.id.sourceTwo);

        String numberOne = sourceOne.getText().toString();
        String numberTwo = sourceTwo.getText().toString();
        try {
            return (new BigDecimal(numberOne)).remainder(new BigDecimal(numberTwo)).toString();
        }catch(Exception e){
            closeKeyboard();
            return ERROR;
        }
    }

    //Method of factorial
    private String factorial(){
        EditText sourceOne = findViewById(R.id.sourceOne);
        EditText sourceTwo = findViewById(R.id.sourceTwo);

        String numberBase = sourceOne.getText().toString();

        BigDecimal bigDecimalBase;
        sourceTwo.setText("");

        //Make sure the program not FC
        try{
            bigDecimalBase = new BigDecimal(numberBase);
        }catch(Exception e){
            closeKeyboard();
            return ERROR;
        }

        if(numberBase.compareTo("0") == 0){
            //Base number equal 1
            return "1";
        }else if(numberBase.indexOf('-') != -1){
            //Base number lower than 0
            Toast.makeText(MainActivity.this, getString(R.string.hint_notNatureNumber), Toast.LENGTH_LONG).show();
            closeKeyboard();
            return ERROR;
        }else if(numberBase.indexOf('.') != -1){
            //Base number is not an Integer
            Toast.makeText(MainActivity.this, getString(R.string.hint_notNatureNumber), Toast.LENGTH_LONG).show();
            closeKeyboard();
            return ERROR;
        }else{
            //Otherwise
            BigDecimal bigDecimalAnswer = new BigDecimal("1");
            BigDecimal bigDecimalOne = new BigDecimal("1");

            for(; bigDecimalBase.toString().compareTo("1") != 0; bigDecimalBase = bigDecimalBase.subtract(bigDecimalOne))
                bigDecimalAnswer = bigDecimalAnswer.multiply(bigDecimalBase);

            return bigDecimalAnswer.toString();
        }
    }

    //Method of arrangement
    private String arrangement(){
        EditText editTextOne = findViewById(R.id.sourceOne);
        EditText editTextTwo = findViewById(R.id.sourceTwo);

        String stringOne = editTextOne.getText().toString();
        String stringTwo = editTextTwo.getText().toString();

        BigDecimal bigDecimalOne;
        BigDecimal bigDecimalTwo;

        //Make sure Not FC
        try{
            bigDecimalOne = new BigDecimal(stringOne);
            bigDecimalTwo = new BigDecimal(stringTwo);
        }catch (Exception e){
            return ERROR;
        }

        //Make sure sourceTwo bigger than source one
        if(bigDecimalTwo.subtract(bigDecimalOne).toString().indexOf('-') != -1){
            //Bottom is bigger than Upper
            Toast.makeText(MainActivity.this, getString(R.string.errorOnArrangement), Toast.LENGTH_LONG).show();
            return ERROR;
        }else if(stringOne.indexOf('.') != -1 | stringTwo.indexOf('.') != -1){
            //Not integer
            Toast.makeText(MainActivity.this, getString(R.string.errorOnArrangement), Toast.LENGTH_LONG).show();
            return ERROR;
        }else if(stringOne.indexOf('-') != -1 | stringTwo.indexOf('.') != -1){
            //Not bigger than ZERO
            Toast.makeText(MainActivity.this, getString(R.string.errorOnArrangement), Toast.LENGTH_LONG).show();
            return ERROR;
        }else if(stringOne.compareTo("0") == 0){
            //Upper is ZERO
            return "1";
        }else{
            //Otherwise
            BigDecimal bigDecimalAnswer = new BigDecimal("1");
            BigDecimal bigDecimalBase = new BigDecimal("0");
            final BigDecimal bigDecimalNumberOne = new BigDecimal("1");

            for(; !bigDecimalBase.equals(bigDecimalOne); bigDecimalBase = bigDecimalBase.add(bigDecimalNumberOne),bigDecimalTwo = bigDecimalTwo.subtract(bigDecimalNumberOne))
                bigDecimalAnswer = bigDecimalAnswer.multiply(bigDecimalTwo);

            return bigDecimalAnswer.toString();
        }
    }

    private String combination() {
        EditText editTextOne = findViewById(R.id.sourceOne);
        EditText editTextTwo = findViewById(R.id.sourceTwo);

        String stringOne = editTextOne.getText().toString();
        String stringTwo = editTextTwo.getText().toString();

        BigDecimal bigDecimalOne;
        BigDecimal bigDecimalTwo;

        //Make sure Not FC
        try {
            bigDecimalOne = new BigDecimal(stringOne);
            bigDecimalTwo = new BigDecimal(stringTwo);
        } catch (Exception e) {
            return ERROR;
        }

        //Make calculate easier
        if(bigDecimalTwo.divide(new BigDecimal("2")).max(bigDecimalOne).equals(bigDecimalOne))
            editTextOne.setText(bigDecimalTwo.subtract(bigDecimalOne).toString());

        //Calculate factorial and arrangement
        String stringAnswerOne = arrangement();
        String stringAnswerTwo = factorial();
        ////Undo the change
        editTextOne.setText(stringOne);
        editTextTwo.setText(stringTwo);
        if(stringAnswerOne.compareTo(ERROR) == 0 | stringAnswerTwo.compareTo(ERROR) == 0) {
            Toast.makeText(MainActivity.this, getString(R.string.errorOnCombination), Toast.LENGTH_LONG).show();
            return ERROR;
        }else
            return (new BigDecimal(stringAnswerOne)).divide(new BigDecimal(stringAnswerTwo)).toString();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
