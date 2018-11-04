package com.the_akm.akm.TwitSplit

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.the_akm.akm.TwitSplit.ConstantCommons.ACTION_BAR_HOME_BUTTON
import com.the_akm.akm.TwitSplit.ConstantCommons.INTEXT_XTRAS_TWEETERS
import kotlinx.android.synthetic.main.activity_answer.*
import java.util.*
import kotlin.collections.ArrayList

class AnswerActivity : AppCompatActivity() {

    companion object {
        private const val ON_SAVED_INSTANCE_STATE_VALUE = "ON_SAVED_INSTANCE_STATE_VALUE"
    }

    private var storeString: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        supportActionBar?.title = ACTION_BAR_HOME_BUTTON

        val text:String

        if(savedInstanceState != null)
        {
                text = savedInstanceState.getString(ON_SAVED_INSTANCE_STATE_VALUE,"")

        }else {
            text = intent.getStringExtra(INTEXT_XTRAS_TWEETERS)
        }
            storeString = text


        // Based on Android Version, setting the list view divider to be transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            answerActivity_listview.divider = resources.getDrawable(android.R.color.transparent,applicationContext.theme)
        } else {
            answerActivity_listview.divider = resources.getDrawable(android.R.color.transparent)
        }

        if(text.length <=50)
        {
            val tweets = splitString(text,50)
            val mAdapter = TwitSplitAdapter(this,tweets)
            answerActivity_listview.adapter = mAdapter
        }else {
            val width = lineWidth(text.length)
            val tweets = splitString(text, width)
            val mAdapter = TwitSplitAdapter(this, tweets)
            answerActivity_listview.adapter = mAdapter
        }
    }

    //function for splitting the string based on line width
    fun splitString(text: String, width: Int): ArrayList<String>
    {
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
        lines.reverse()
        return lines
    }

    fun lineWidth(stringLength : Int):Int
    {
        val result :Int

        if(stringLength <= 414) result = 46  // 1/1 ~ 9/9

        else if(stringLength in 415..4356) result = 44  // 10/10  ~  99/99

        else if(stringLength in 4357..41958) result = 42 // 100/100  ~ 999/999

        else if(stringLength in 41959..399960) result = 40 // 10000/1000 ~ 9999/9999

        else result = 36

        return result
    }

    override
    fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    // store the input text in onSaveInstanceState for screen orientation changes
    override
    fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(ON_SAVED_INSTANCE_STATE_VALUE, storeString)
    }


}
