package com.amsavarthan.game.flames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextInputEditText input1,input2;
    char[] name1;
    char[] name2;
    List<Character> output=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input1=findViewById(R.id.input1);
        input2=findViewById(R.id.input2);


    }

    public void onMatchClicked(View view) {

        output.clear();
        String boyName=input1.getText().toString().replace(" ","");
        String girlName=input2.getText().toString().replace(" ","");

        if(TextUtils.isEmpty(boyName) || TextUtils.isEmpty(girlName)){
            Snackbar.make(findViewById(R.id.main),"All fields are required",Snackbar.LENGTH_SHORT).show();
            return;
        }

        if(boyName.toLowerCase().equals(girlName.toLowerCase())){
            Snackbar.make(findViewById(R.id.main),"Are you sure with the names you have entered?",Snackbar.LENGTH_SHORT).show();
            return;
        }


        name1=boyName.toLowerCase().toCharArray();
        name2=girlName.toLowerCase().toCharArray();
        for(int i=0;i<name1.length;i++){
            for(int j=0;j<name2.length;j++){
                if(name1[i]==name2[j]){
                    name1[i]=' ';
                    name2[j]=' ';
                    break;
                }
            }
        }
        for(char a:name1){
            if(a==' ')
                continue;
            output.add(a);
        }
        for(char a:name2){
            if(a==' ')
                continue;
            output.add(a);
        }

        Log.i("OUTPUT", output.toString());

        char relationIs = 0;
        int resultLength = output.size();
        String baseInput = "Flames";
        String temp = "";
        if (resultLength > 0) {
            while (baseInput.length() !=1)
            {
                Log.i("OUTPUT", baseInput);
                int tmpLen = resultLength % baseInput.length(); //finding char position to strike
                if(tmpLen != 0)
                {
                    temp = baseInput.substring(tmpLen) + baseInput.substring(0, tmpLen-1); //Append part start from next char to strike and first charater to char before strike.
                }
                else
                {
                    temp = baseInput.substring(0, baseInput.length()-1);
                }
                baseInput = temp; //Assign the temp to baseinput for next iteration.
            }
            relationIs = baseInput.charAt(0);
            Log.i("OUTPUT", String.valueOf(relationIs));
        }

        Intent intent=new Intent(getApplicationContext(),ResultActivity.class);
        intent.putExtra("boyName",boyName);
        intent.putExtra("girlName",girlName);
        intent.putExtra("result",String.valueOf(relationIs));
        ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(this,findViewById(R.id.title),"title");
        startActivity(intent,optionsCompat.toBundle());

    }
}
