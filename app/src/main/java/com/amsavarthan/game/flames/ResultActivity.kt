package com.amsavarthan.game.flames

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class ResultActivity : AppCompatActivity() {

    lateinit var textView: TextView
    lateinit var textView1: TextView
    lateinit var image: ImageView
    lateinit var constraintLayout: ConstraintLayout
    private lateinit var interstitialAdView:InterstitialAd
    private var rand = 0

    private var girlName: String? = null
    private var boyName: String? = null
    private var result: String?=null

    var friends = arrayOf("%s has found a new friend and her name is %s", "%s & %s are good friends", "%s & %s are going to be friends")
    var love = arrayOf("%s and %s have found love of their life", "%s & %s are going to be true lovers", "%s & %s are going to be cute couples")
    var affection = arrayOf("No more Tinder, It's a perfect match! for %s & %s", "%s is affectionate towards %s", "We have got affection! for %s and %s")
    var marriage = arrayOf("Maybe it's time for %s and %s to get ready for their wedding", "%s & %s are going to get married soon", "%s & %s are going to get married soon or already married")
    var enemy = arrayOf("Oops...%s and %s are enemies", "%s and %s may be enemies in future", "%s and %s both are enemies")
    var sister = arrayOf("%s has found a new sister and her name is %s", "It's a brother-sister relationship for %s & %s", "%s has got a cute sister and her name is %s")

    override fun onDestroy() {
        super.onDestroy()
        if(interstitialAdView.isLoaded){
            interstitialAdView.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        boyName = intent!!.getStringExtra("boyName")
        girlName = intent!!.getStringExtra("girlName")
        result = intent!!.getStringExtra("result")
        textView = findViewById(R.id.title)
        textView1 = findViewById(R.id.subtitle)
        image = findViewById(R.id.image)
        constraintLayout = findViewById(R.id.main)

        MobileAds.initialize(this){}
        interstitialAdView= InterstitialAd(this)
        interstitialAdView.adUnitId=getString(R.string.interstitial_id1)
        interstitialAdView.loadAd(AdRequest.Builder().build())

        interstitialAdView.adListener = object: AdListener() {

            override fun onAdClosed() {
                interstitialAdView.loadAd(AdRequest.Builder().build())
                performShare()
            }
        }

        val r = Random()
        rand = r.nextInt(3)
        when (result) {
            "F" -> {
                textView1.text = String.format(friends[rand], boyName, girlName)
                image.setImageDrawable(resources.getDrawable(R.mipmap.friend))
                constraintLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            "l" -> {
                textView1.text = String.format(love[rand], boyName, girlName)
                image.setImageDrawable(resources.getDrawable(R.mipmap.love))
                constraintLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            "a" -> {
                try {
                    textView1.text = String.format(affection[rand], boyName, girlName)
                } catch (e: Exception) {
                    textView1.text = affection[rand]
                }
                image.setImageDrawable(resources.getDrawable(R.mipmap.affection))
                constraintLayout.setBackgroundColor(Color.parseColor("#F9D7D8"))
            }
            "m" -> {
                textView1.text = String.format(marriage[rand], boyName, girlName)
                image.setImageDrawable(resources.getDrawable(R.mipmap.marriage))
                constraintLayout.setBackgroundColor(Color.parseColor("#F4C4B6"))
            }
            "e" -> {
                try {
                    textView1.text = String.format(enemy[rand], boyName, girlName)
                } catch (e: Exception) {
                    textView1.text = enemy[rand]
                }
                image.setImageDrawable(resources.getDrawable(R.mipmap.enemy))
                constraintLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            "s" -> {
                textView1.text = String.format(sister[rand], boyName, girlName)
                image.setImageDrawable(resources.getDrawable(R.mipmap.sister))
                constraintLayout.setBackgroundColor(Color.parseColor("#FECE02"))
            }
        }
        val handler = Handler()
        handler.postDelayed({
            when (result) {
                "F" -> textView.text = Html.fromHtml("<font color=\"#FF5252\">F</font>lames")
                "l" -> textView.text = Html.fromHtml("F<font color=\"#FF5252\">l</font>ames")
                "a" -> textView.text = Html.fromHtml("Fl<font color=\"#FF5252\">a</font>mes")
                "m" -> textView.text = Html.fromHtml("Fla<font color=\"#FF5252\">m</font>es")
                "e" -> textView.text = Html.fromHtml("Flam<font color=\"#FF5252\">e</font>s")
                "s" -> textView.text = Html.fromHtml("Flame<font color=\"#FF5252\">s</font>")
            }
        }, 300)
    }

    fun onShareClicked(view: View?) {

        if (interstitialAdView.isLoaded) {
            interstitialAdView.show()
        } else {
            performShare()
            Log.d("TAG", "The interstitial wasn't loaded yet.")
        }

    }

    private fun performShare() {

        val progressDialog = ProgressDialog(this@ResultActivity)
        progressDialog.setMessage("Generating share image...")
        progressDialog.isIndeterminate = true
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
        val customview = (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.layout_share, null)
        val cardView: CardView = customview.findViewById(R.id.cardView)
        val flames = customview.findViewById<TextView>(R.id.flames)
        val output = customview.findViewById<TextView>(R.id.result)
        val imageView = customview.findViewById<ImageView>(R.id.image)
        when (result) {
            "F" -> {
                flames.text = Html.fromHtml("<font color=\"#FF5252\">F</font>lames")
                output.text = String.format(friends[rand], boyName, girlName)
                imageView.setImageDrawable(resources.getDrawable(R.mipmap.friend))
                cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            "l" -> {
                output.text = String.format(love[rand], boyName, girlName)
                flames.text = Html.fromHtml("F<font color=\"#FF5252\">l</font>ames")
                imageView.setImageDrawable(resources.getDrawable(R.mipmap.love))
                cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            "a" -> {
                flames.text = Html.fromHtml("Fl<font color=\"#FF5252\">a</font>mes")
                try {
                    output.text = String.format(affection[rand], boyName, girlName)
                } catch (e: Exception) {
                    output.text = affection[rand]
                }
                imageView.setImageDrawable(resources.getDrawable(R.mipmap.affection))
                cardView.setCardBackgroundColor(Color.parseColor("#F9D7D8"))
            }
            "m" -> {
                flames.text = Html.fromHtml("Fla<font color=\"#FF5252\">m</font>es")
                output.text = String.format(marriage[rand], boyName, girlName)
                imageView.setImageDrawable(resources.getDrawable(R.mipmap.marriage))
                cardView.setCardBackgroundColor(Color.parseColor("#F4C4B6"))
            }
            "e" -> {
                flames.text = Html.fromHtml("Flam<font color=\"#FF5252\">e</font>s")
                try {
                    output.text = String.format(enemy[rand], boyName, girlName)
                } catch (e: Exception) {
                    output.text = enemy[rand]
                }
                imageView.setImageDrawable(resources.getDrawable(R.mipmap.enemy))
                cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            "s" -> {
                output.text = String.format(sister[rand], boyName, girlName)
                flames.text = Html.fromHtml("Flame<font color=\"#FF5252\">s</font>")
                imageView.setImageDrawable(resources.getDrawable(R.mipmap.sister))
                cardView.setCardBackgroundColor(Color.parseColor("#FECE02"))
            }
        }
        progressDialog.dismiss()
        shareImage(getSharableBitmapFromView(customview))

    }

    private fun shareImage(bitmap: Bitmap) {
        try {
            val cachePath = File(cacheDir, "images")
            cachePath.mkdirs()
            val stream = FileOutputStream("$cachePath/image.png")
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val imagePath = File(cacheDir, "images")
        val newFile = File(imagePath, "image.png")
        val contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", newFile)
        if (contentUri != null) {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(contentUri, contentResolver.getType(contentUri))
            intent.putExtra(Intent.EXTRA_STREAM, contentUri)
            intent.type = "image/png"
            startActivity(Intent.createChooser(intent, "Share using"))
        }
    }

    private fun getSharableBitmapFromView(view: View): Bitmap {
        val displayMetrics = DisplayMetrics()
        val windowManager = windowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        view.measure(View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.EXACTLY)
                , View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.EXACTLY))
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        val returnedBitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        view.draw(canvas)
        return returnedBitmap
    }
}