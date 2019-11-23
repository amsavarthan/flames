package com.amsavarthan.game.flames

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class MainActivity : AppCompatActivity() {
    var input1: TextInputEditText? = null
    var input2: TextInputEditText? = null
    lateinit var name1: CharArray
    lateinit var name2: CharArray
    var output: MutableList<Char> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        input1 = findViewById(R.id.input1)
        input2 = findViewById(R.id.input2)
    }

    fun onMatchClicked(view: View?) {
        output.clear()
        val boyName = input1!!.text.toString()
        val girlName = input2!!.text.toString()
        if (TextUtils.isEmpty(boyName.replace(" ", "")) || TextUtils.isEmpty(girlName.replace(" ", ""))) {
            Snackbar.make(findViewById(R.id.main), "All fields are required", Snackbar.LENGTH_SHORT).show()
            return
        }
        if (boyName.toLowerCase() == girlName.toLowerCase()) {
            Snackbar.make(findViewById(R.id.main), "Are you sure with the names you have entered?", Snackbar.LENGTH_SHORT).show()
            return
        }
        name1 = boyName.toLowerCase().toCharArray()
        name2 = girlName.toLowerCase().toCharArray()
        for (i in name1.indices) {
            for (j in name2.indices) {
                if (name1[i] == name2[j]) {
                    name1[i] = ' '
                    name2[j] = ' '
                    break
                }
            }
        }
        for (a in name1) {
            if (a == ' ') continue
            output.add(a)
        }
        for (a in name2) {
            if (a == ' ') continue
            output.add(a)
        }
        Log.i("OUTPUT", output.toString())
        var relationIs = 0.toChar()
        val resultLength = output.size
        var baseInput = "Flames"
        var temp: String
        if (resultLength > 0) {
            while (baseInput.length != 1) {
                Log.i("OUTPUT", baseInput)
                val tmpLen = resultLength % baseInput.length //finding char position to strike
                temp = if (tmpLen != 0) {
                    baseInput.substring(tmpLen) + baseInput.substring(0, tmpLen - 1) //Append part start from next char to strike and first charater to char before strike.
                } else {
                    baseInput.substring(0, baseInput.length - 1)
                }
                baseInput = temp //Assign the temp to baseinput for next iteration.
            }
            relationIs = baseInput[0]
            Log.i("OUTPUT", relationIs.toString())
        }
        val intent = Intent(applicationContext, ResultActivity::class.java)
        intent.putExtra("boyName", boyName)
        intent.putExtra("girlName",girlName)
        intent.putExtra("result", relationIs.toString())
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, findViewById(R.id.title), "title")
        startActivity(intent, optionsCompat.toBundle())
    }
}