package com.example.applymapping;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Keep;
import androidx.appcompat.app.AppCompatActivity;

import com.example.applymapping.actionunits.ActionUnit;
import com.example.applymapping.actionunits.FuncInf;
import com.example.applymapping.actionunits.FunctionalInterface;
import com.example.applymapping.actionunits.Minification;
import com.example.applymapping.actionunits.TempAction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnTestMinification).setOnClickListener(new Minification(this));
        findViewById(R.id.btnTestFunctionalInterface).setOnClickListener(new FunctionalInterface(this));
    }

    @Keep
    public void performActionUnit(ActionUnit actionUnit) {
        actionUnit.run(this);
    }

    @Keep
    public void perform(FuncInf modifier, TempAction action) {
        modifier.generate(action).run(this);
    }
}