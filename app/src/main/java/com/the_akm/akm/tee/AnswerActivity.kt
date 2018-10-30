package com.the_akm.akm.tee

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import com.the_akm.akm.tee.ConstantCommons.INTEXT_XTRAS_TWEETERS
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_answer.*
import java.util.*

class AnswerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        supportActionBar?.setTitle("Back")

        val text = intent.getStringExtra(INTEXT_XTRAS_TWEETERS)

        tweeter_listview.divider = resources.getDrawable(android.R.color.transparent)

        if(text.length <=50)
        {
            val tweets = dynamic(text,50)
            val mAdapter = TweeterAdapter(this,tweets)
            tweeter_listview.adapter = mAdapter
        }else {
            val tweets = dynamic(text, 45)
            val mAdapter = TweeterAdapter(this, tweets)
            tweeter_listview.adapter = mAdapter
        }
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

    override
    fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

}
