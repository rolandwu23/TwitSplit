package com.the_akm.akm.tee

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import java.io.File.separator
import java.util.*
import java.util.Arrays.asList




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        but.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if(hello.hasFocus()) {
                    dismissKeyboard(hello)
                }
                val temp = dynamic(hello.text.toString(),45)
                for(i in 0 until temp.size){
                    hello2.text = temp.get(i)
                }
            }
        })

    }

    fun dismissKeyboard(editText: EditText){
        editText.clearFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0)
    }

    fun toast(st : String){
        Toast.makeText(this@MainActivity,st, Toast.LENGTH_LONG).show()
    }

    fun dynamic(text: String, width: Int): ArrayList<String> {
        val words = text.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val count = words.size
        val slack = Array(count) { IntArray(count) }
        val breaks = IntArray(count)
        val temp = Math.pow(10.0, 20.0).toInt()
        val minima = IntArray(count + 1)
        minima[0] = 0
        for (i in 1 until count + 1) {
            minima[i] = temp
        }
        val lines = ArrayList<String>()
        for (i in 0 until count) {
            for (j in 0 until count) {
                slack[i][j] = 0
            }
        }
        for (i in 0 until count) {
            slack[i][i] = width - words[i].length
            for (j in i + 1 until count) {
                slack[i][j] = slack[i][j - 1] - words[j].length - 1
            }
        }
        for (i in 0 until count) {
            breaks[i] = 0
        }
        for (j in 0 until count) {
            var i = j
            var cost: Int
            while (i >= 0) {
                if (slack[i][j] < 0) {
                    cost = Math.pow(10.0, 10.0).toInt()
                } else {
                    cost = minima[i] + Math.pow(slack[i][j].toDouble(), 2.0).toInt()
                }
                if (minima[j + 1] > cost) {
                    minima[j + 1] = cost
                    breaks[j] = i
                }
                i -= 1
            }
        }

        var j = count
        while (j > 0) {
            val i = breaks[j - 1]
            val arr = Arrays.copyOfRange(words, i, j)
            val result = arr.joinToString(separator = " ")
            lines.add(result)
            j = i
        }
        Collections.reverse(lines)
        return lines
    }

}

