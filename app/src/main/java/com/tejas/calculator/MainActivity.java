package com.tejas.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    LinearLayout mainLL;
    TextView dataTextView, answerTextView;
    ImageView btnClear, btnMode;
    MaterialButton btnClearAll, btnClearAll2, btnPlus, btnMinus, btnMultiply, btnDivide, btnPercent, btnEquals;
    MaterialButton btnDot, btn00, btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;

    String dataToCalculate = "";
    String finalResult = "";

    boolean darkMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing All Layout Elements
        dataTextView = findViewById(R.id.dataTextView);
        answerTextView = findViewById(R.id.answerTextView);
        btnClear = findViewById(R.id.btn_clear);
        btnMode = findViewById(R.id.btn_mode);
        btnClearAll = findViewById(R.id.btn_clearAll);
        btnClearAll2 = findViewById(R.id.btn_clearAll2);
        btnPlus = findViewById(R.id.btn_plus);
        btnMinus = findViewById(R.id.btn_minus);
        btnMultiply = findViewById(R.id.btn_multiply);
        btnDivide = findViewById(R.id.btn_divide);
        btnPercent = findViewById(R.id.btn_percent);
        btn00 = findViewById(R.id.btn_00);
        btnEquals = findViewById(R.id.btn_equals);
        btnDot = findViewById(R.id.btn_dot);
        btn0 = findViewById(R.id.btn_0);
        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        btn7 = findViewById(R.id.btn_7);
        btn8 = findViewById(R.id.btn_8);
        btn9 = findViewById(R.id.btn_9);


        btnMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainLL = findViewById(R.id.mainLL);
                MaterialButton[] colorButtons = {btnClearAll, btnClearAll2, btnPercent, btnDivide, btn7,
                        btn8, btn9, btnMultiply, btn4, btn5, btn6, btnPlus, btn1, btn2, btn3, btnMinus, btn00, btn0, btnDot};

                if (darkMode){
                    mainLL.setBackgroundColor(Color.rgb(255, 255, 255));
                    dataTextView.setTextColor(Color.rgb(65, 65, 65));
                    answerTextView.setTextColor(Color.rgb(99, 99, 99));

                    for (int i=0; i<colorButtons.length; i++){
                        changeBackgroundColor(colorButtons[i], Color.rgb(217, 231, 252));
                    }

                    btnMode.setImageResource(R.drawable.ic_baseline_dark_mode_24);
                    darkMode = false;
                }
                else{
                    mainLL.setBackgroundColor(Color.rgb(28, 33, 40));
                    dataTextView.setTextColor(Color.rgb(255, 255, 255));
                    answerTextView.setTextColor(Color.rgb(255, 255, 255));

                    for (int i=0; i<colorButtons.length; i++){
                        changeBackgroundColor(colorButtons[i], Color.rgb(45, 51, 59));
                    }

                    btnMode.setImageResource(R.drawable.ic_baseline_light_mode_24);
                    darkMode = true;
                }
            }
        });


            btnClear.setOnClickListener(new View.OnClickListener() {
                @Override
                    public void onClick (View v){
                    try {
                    replaceSignsReverse();
                    dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
                    dataTextView.setText(dataToCalculate);
                    replaceSigns();

                    Context rhino = Context.enter();
                    rhino.setOptimizationLevel(-1);
                    try {
                        Scriptable scriptable = rhino.initSafeStandardObjects();
                        finalResult = rhino.evaluateString(scriptable, dataToCalculate, "javascript", 1, null).toString();
                    } catch (Exception e) {
                        finalResult = "";
                    }
                    if (dataToCalculate.contains("+") || dataToCalculate.contains("-") || dataToCalculate.contains("*") || dataToCalculate.contains("/")) {
                        if (finalResult.endsWith(".0")) {
                            finalResult = finalResult.substring(0, finalResult.length() - 2);
                        }
                        answerTextView.setText(finalResult);
                    }
                }
                catch(Exception e){
                }
                
                }
            });



        clickEventListener(btnClearAll, "C");
        clickEventListener(btnClearAll2, "C");
        clickEventListener(btnPlus, "+");
        clickEventListener(btnMinus, "-");
        clickEventListener(btnMultiply, "*");
        clickEventListener(btnDivide, "÷");
        clickEventListener(btnPercent, "%");
        clickEventListener(btn00, "00");
        clickEventListener(btnEquals, "=");
        clickEventListener(btnDot, ".");
        clickEventListener(btn0, "0");
        clickEventListener(btn1, "1");
        clickEventListener(btn2, "2");
        clickEventListener(btn3, "3");
        clickEventListener(btn4, "4");
        clickEventListener(btn5, "5");
        clickEventListener(btn6, "6");
        clickEventListener(btn7, "7");
        clickEventListener(btn8, "8");
        clickEventListener(btn9, "9");


    }

    void changeBackgroundColor(MaterialButton btn, int rgbColor){
        btn.setBackgroundColor(rgbColor);
    }

    void replaceSigns(){
        dataToCalculate = dataToCalculate.replaceAll("%","/100");
        dataToCalculate = dataToCalculate.replaceAll("÷","/");
    }
    void replaceSignsReverse(){
        dataToCalculate = dataToCalculate.replaceAll("/100","%");
        dataToCalculate = dataToCalculate.replaceAll("/","÷");
    }

    void onBtnClick(String value){
        replaceSignsReverse();
        dataToCalculate += value;
        dataTextView.setText(dataToCalculate);
        replaceSigns();

        Context rhino = Context.enter();
        rhino.setOptimizationLevel(-1);
        try {
            Scriptable scriptable = rhino.initSafeStandardObjects();
            finalResult = rhino.evaluateString(scriptable, dataToCalculate, "javascript", 1, null).toString();
        } catch (Exception e) {
            finalResult = "";
        }
        if (finalResult.endsWith(".0")) {
        if (dataToCalculate.contains("+") || dataToCalculate.contains("-") || dataToCalculate.contains("*") || dataToCalculate.contains("/") || dataToCalculate.contains("%")) {
                finalResult = finalResult.substring(0, finalResult.length() - 2);
            }
        else{
            finalResult = "";
        }
        }
            answerTextView.setText(finalResult);
    }


    void clickEventListener(MaterialButton btn, String value){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.equals(value, "C")){
                    dataToCalculate = "";
                    finalResult = "";
                    dataTextView.setText("0");
                    answerTextView.setText(finalResult);
                }

                else if (Objects.equals(value, "=")){
                    dataToCalculate = finalResult;

                    dataTextView.setText(dataToCalculate);
                    answerTextView.setText("");
                }

                else {

                        if (Objects.equals(value, "+") || Objects.equals(value, "-") || Objects.equals(value, "*")
                                || Objects.equals(value, "÷") || Objects.equals(value, "%")){

                            char lastchar = ' ';
                            if (dataToCalculate.length()!=0){
                                lastchar = dataToCalculate.charAt(dataToCalculate.length()-1);
                            }

                            if (Objects.equals(dataToCalculate, "") || Objects.equals(dataToCalculate, "0") ||
                                    lastchar=='+' || lastchar=='-' || lastchar=='*' || lastchar=='/' || lastchar=='%') {

                            Toast.makeText(MainActivity.this, "Invalid Format", Toast.LENGTH_SHORT).show();
                        }
                            else{
                                onBtnClick(value);
                            }

                    }
                    else{

                        if (Objects.equals(value, "0") || Objects.equals(value, "00")) {
                            char lastchar = ' ';
                            char secondlastchar = ' ';
                            try {
                                lastchar = dataToCalculate.charAt(dataToCalculate.length() - 1);
                                secondlastchar = dataToCalculate.charAt(dataToCalculate.length() - 2);
                            } catch (Exception e){}

                        if (dataToCalculate.length()==0){
                        }

                        else if (secondlastchar=='+' || secondlastchar=='-' || secondlastchar=='*' || secondlastchar=='/' || secondlastchar=='%' && lastchar=='0'){
                        }
                        else{onBtnClick(value);}
                        }
                        else {
                            onBtnClick(value);
                        }
                    }
                }
                }
        });
    }

}