package com.example.calculadora2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView resultTV, solutionTV;
    MaterialButton buttonC, buttonBrackOpen, buttonBrackClose;
    MaterialButton buttonDivide, buttonMultiply, buttonSubtract, buttonAdd, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonDot, buttonAC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTV = (TextView) findViewById(R.id.result_tv);
        solutionTV = (TextView) findViewById(R.id.solution_tv);

        assign_id(buttonC, R.id.button_c);
        assign_id(buttonBrackOpen, R.id.button_open_brackets);
        assign_id(buttonBrackClose, R.id.button_close_brackets);
        assign_id(buttonDivide, R.id.button_divide);
        assign_id(buttonMultiply, R.id.button_multiply);
        assign_id(buttonSubtract, R.id.button_minus);
        assign_id(buttonAdd, R.id.button_plus);
        assign_id(buttonEquals, R.id.button_equal);
        assign_id(button0, R.id.button_0);
        assign_id(button1, R.id.button_1);
        assign_id(button2, R.id.button_2);
        assign_id(button3, R.id.button_3);
        assign_id(button4, R.id.button_4);
        assign_id(button5, R.id.button_5);
        assign_id(button6, R.id.button_6);
        assign_id(button7, R.id.button_7);
        assign_id(button8, R.id.button_8);
        assign_id(button9, R.id.button_9);
        assign_id(buttonDot, R.id.button_dot);
        assign_id(buttonAC, R.id.button_ac);

    }

    void assign_id(MaterialButton button, int id) {
        button = (MaterialButton) findViewById(id);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button =(MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTV.getText().toString();

        if(buttonText.equals("AC")){
            solutionTV.setText("");
            resultTV.setText("0");
            return;
        }
        if(buttonText.equals("=")){
            solutionTV.setText(resultTV.getText());
            return;
        }
        if(buttonText.equals("C")){
            dataToCalculate = dataToCalculate.substring(0,dataToCalculate.length()-1);
        }else{
            dataToCalculate = dataToCalculate+buttonText;
        }
        solutionTV.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);

        if(!finalResult.equals("Error")){
            resultTV.setText(finalResult);
        }

    }

    String getResult(String data){
        try{
            Context context  = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult =  context.evaluateString(scriptable,data,"Javascript",1,null).toString();
            if(finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0","");
            } else if(finalResult.startsWith("0")){
                finalResult = finalResult.replace("0","");
            }else if(finalResult.length()>10){
                finalResult = String.format("%.2f",Double.parseDouble(finalResult));
            }
            return finalResult;
        } catch (Exception e) {
            return "Error";
        }
    }
}