package com.example.micalculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tv_resultado, tv_operacion;
    MaterialButton btn_parentesis_izq, btn_parentesis_der, btn_porcentaje, btn_division;
    MaterialButton btn_7, btn_8, btn_9, btn_multiplicacion;
    MaterialButton btn_4, btn_5, btn_6, btn_resta;
    MaterialButton btn_1, btn_2, btn_3, btn_suma;
    MaterialButton btn_punto, btn_0, btn_c, btn_igual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_resultado = findViewById(R.id.tv_resultado);
        tv_operacion = findViewById(R.id.tv_operacion);

        asignarId(btn_parentesis_izq,R.id.btn_parentesis_izq);
        asignarId(btn_parentesis_der,R.id.btn_parentesis_der);
        asignarId(btn_porcentaje,R.id.btn_porcentaje);
        asignarId(btn_division,R.id.btn_division);

        asignarId(btn_7,R.id.btn_7);
        asignarId(btn_8,R.id.btn_8);
        asignarId(btn_9,R.id.btn_9);
        asignarId(btn_multiplicacion,R.id.btn_multiplicacion);

        asignarId(btn_4,R.id.btn_4);
        asignarId(btn_5,R.id.btn_5);
        asignarId(btn_6,R.id.btn_6);
        asignarId(btn_resta,R.id.btn_resta);

        asignarId(btn_1,R.id.btn_1);
        asignarId(btn_2,R.id.btn_2);
        asignarId(btn_3,R.id.btn_3);
        asignarId(btn_suma,R.id.btn_suma);

        asignarId(btn_punto,R.id.btn_punto);
        asignarId(btn_0,R.id.btn_0);
        asignarId(btn_c,R.id.btn_c);
        asignarId(btn_igual,R.id.btn_igual);

    }

    void asignarId(MaterialButton btn, int id){
        btn=findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        MaterialButton boton = (MaterialButton) view;
        String texto_boton = boton.getText().toString();
        String operacion = tv_operacion.getText().toString();
        if (texto_boton.equals("=")) {
            tv_operacion.setText(tv_resultado.getText());
            return;
        }
        if(texto_boton.equals("C")){
            if(operacion.isEmpty()) return;
            operacion = operacion.substring(0,operacion.length()-1);
        }else{
            operacion = operacion+texto_boton;
        }

        tv_operacion.setText(operacion);
        if (operacion.equals("")) operacion="0";
        operacion = operacion.replace("%","/100");
        operacion = operacion.replace(")(", ")*(");
        operacion = operacion.replace("0(","0*(");
        operacion = operacion.replace("1(","1*(");
        operacion = operacion.replace("2(","2*(");
        operacion = operacion.replace("3(","3*(");
        operacion = operacion.replace("4(","4*(");
        operacion = operacion.replace("5(","5*(");
        operacion = operacion.replace("6(","6*(");
        operacion = operacion.replace("7(","7*(");
        operacion = operacion.replace("8(","8*(");
        operacion = operacion.replace("9(","9*(");

        String resultado_final = calcular(operacion);

        if(!resultado_final.equals("Err")){
            tv_resultado.setText(resultado_final);
        }
    }

    String calcular(String data){
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String resultado_final = context.evaluateString(scriptable,data,"Javascript",1,null).toString();
            if(resultado_final.endsWith(".0")){
                resultado_final = resultado_final.replace(".0","");
            }
            return  resultado_final;
        }catch (Exception err){
            return "Err";
        }
    }
}