package com.amsavarthan.game.flames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class ResultActivity extends AppCompatActivity {

    TextView textView,textView1;
    ImageView image;
    ConstraintLayout constraintLayout;
    private String girlName,boyName,result;

    String[] friends={"%s has found a new friend and her name is %s","%s & %s are good friends","%s & %s are going to be friends"};
    String[] love={"%s and %s have found love of their life","%s & %s are going to be true lovers","%s & %s are going to be cute couples"};
    String[] affection={"No more Tinder, It's a perfect match! for %s & %s","%s is affectionate towards %s","We have got affection! for %s and %s"};
    String[] marriage={"Maybe it's time for %s and %s to get ready for their wedding","%s & %s are going to get married soon","%s & %s are going to get married soon or already married"};
    String[] enemy={"Oops...%s and %s are enemies","%s and %s may be enemies in future","%s and %s both are enemies"};
    String[] sister={"%s has found a new sister and her name is %s","It's a brother-sister relationship for %s & %s","%s has got a cute sister and her name is %s"};
    private int rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        boyName=getIntent().getStringExtra("boyName");
        girlName=getIntent().getStringExtra("girlName");
        result=getIntent().getStringExtra("result");
        textView=findViewById(R.id.title);
        textView1=findViewById(R.id.subtitle);
        image=findViewById(R.id.image);
        constraintLayout=findViewById(R.id.main);

        Random r=new Random();
        rand=r.nextInt(3);

        switch (result){

            case "F":
                textView1.setText(String.format(friends[rand],boyName,girlName));
                image.setImageDrawable(getResources().getDrawable(R.mipmap.friend));
                constraintLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case "l":
                textView1.setText(String.format(love[rand],boyName,girlName));
                image.setImageDrawable(getResources().getDrawable(R.mipmap.love));
                constraintLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case "a":
                try {
                    textView1.setText(String.format(affection[rand], boyName, girlName));
                }catch (Exception e){
                    textView1.setText(affection[rand]);
                }
                image.setImageDrawable(getResources().getDrawable(R.mipmap.affection));
                constraintLayout.setBackgroundColor(Color.parseColor("#F9D7D8"));
                break;
            case "m":
                textView1.setText(String.format(marriage[rand],boyName,girlName));
                image.setImageDrawable(getResources().getDrawable(R.mipmap.marriage));
                constraintLayout.setBackgroundColor(Color.parseColor("#F4C4B6"));
                break;
            case "e":
                try {
                    textView1.setText(String.format(enemy[rand], boyName, girlName));
                }catch (Exception e){
                    textView1.setText(enemy[rand]);
                }
                image.setImageDrawable(getResources().getDrawable(R.mipmap.enemy));
                constraintLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case "s":
                textView1.setText(String.format(sister[rand],boyName,girlName));
                image.setImageDrawable(getResources().getDrawable(R.mipmap.sister));
                constraintLayout.setBackgroundColor(Color.parseColor("#FECE02"));
                break;

        }


        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (result){

                    case "F":
                        textView.setText(Html.fromHtml("<font color=\"#FF5252\">F</font>lames"));
                        break;
                    case "l":
                        textView.setText(Html.fromHtml("F<font color=\"#FF5252\">l</font>ames"));
                        break;
                    case "a":
                        textView.setText(Html.fromHtml("Fl<font color=\"#FF5252\">a</font>mes"));
                        break;
                    case "m":
                        textView.setText(Html.fromHtml("Fla<font color=\"#FF5252\">m</font>es"));
                        break;
                    case "e":
                        textView.setText(Html.fromHtml("Flam<font color=\"#FF5252\">e</font>s"));
                        break;
                    case "s":
                        textView.setText(Html.fromHtml("Flame<font color=\"#FF5252\">s</font>"));
                        break;

                }
            }
        },300);

    }

    public void onShareClicked(View view) {

        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Generating share image...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        View customview=((LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_share,null);
        CardView cardView=customview.findViewById(R.id.cardView);
        TextView flames=customview.findViewById(R.id.flames);
        TextView output=customview.findViewById(R.id.result);
        ImageView imageView=customview.findViewById(R.id.image);

        switch (result){

            case "F":
                flames.setText(Html.fromHtml("<font color=\"#FF5252\">F</font>lames"));
                output.setText(String.format(friends[rand],boyName,girlName));
                imageView.setImageDrawable(getResources().getDrawable(R.mipmap.friend));
                cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case "l":
                output.setText(String.format(love[rand],boyName,girlName));
                flames.setText(Html.fromHtml("F<font color=\"#FF5252\">l</font>ames"));
                imageView.setImageDrawable(getResources().getDrawable(R.mipmap.love));
                cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case "a":
                flames.setText(Html.fromHtml("Fl<font color=\"#FF5252\">a</font>mes"));
                try {
                    output.setText(String.format(affection[rand], boyName, girlName));
                }catch (Exception e){
                    output.setText(affection[rand]);
                }
                imageView.setImageDrawable(getResources().getDrawable(R.mipmap.affection));
                cardView.setCardBackgroundColor(Color.parseColor("#F9D7D8"));
                break;
            case "m":
                flames.setText(Html.fromHtml("Fla<font color=\"#FF5252\">m</font>es"));
                output.setText(String.format(marriage[rand],boyName,girlName));
                imageView.setImageDrawable(getResources().getDrawable(R.mipmap.marriage));
                cardView.setCardBackgroundColor(Color.parseColor("#F4C4B6"));
                break;
            case "e":
                flames.setText(Html.fromHtml("Flam<font color=\"#FF5252\">e</font>s"));
                try {
                    output.setText(String.format(enemy[rand], boyName, girlName));
                }catch (Exception e){
                    output.setText(enemy[rand]);
                }
                imageView.setImageDrawable(getResources().getDrawable(R.mipmap.enemy));
                cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case "s":
                output.setText(String.format(sister[rand],boyName,girlName));
                flames.setText(Html.fromHtml("Flame<font color=\"#FF5252\">s</font>"));
                imageView.setImageDrawable(getResources().getDrawable(R.mipmap.sister));
                cardView.setCardBackgroundColor(Color.parseColor("#FECE02"));
                break;

        }

        progressDialog.dismiss();

        shareImage(getSharableBitmapFromView(customview));

    }

    private void shareImage(Bitmap bitmap){

        try{
            File cachePath=new File(getCacheDir(),"images");
            cachePath.mkdirs();
            FileOutputStream stream=new FileOutputStream(cachePath+"/image.png");
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            stream.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        File imagePath=new File(getCacheDir(),"images");
        File newFile=new File(imagePath,"image.png");
        Uri contentUri= FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID+".fileprovider",newFile);

        if(contentUri!=null){

            Intent intent=new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(contentUri,getContentResolver().getType(contentUri));
            intent.putExtra(Intent.EXTRA_STREAM,contentUri);
            intent.setType("image/png");
            startActivity(Intent.createChooser(intent,"Share using"));

        }

    }

    private Bitmap getSharableBitmapFromView(View view){

        DisplayMetrics displayMetrics=new DisplayMetrics();
        WindowManager windowManager=getWindowManager();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        view.measure(View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.EXACTLY)
                ,View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.EXACTLY));
        view.layout(0,0,view.getMeasuredWidth(),view.getMeasuredHeight());

        Bitmap returnedBitmap=Bitmap.createBitmap(view.getMeasuredWidth(),view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(returnedBitmap);
        view.draw(canvas);
        return returnedBitmap;

    }


}
