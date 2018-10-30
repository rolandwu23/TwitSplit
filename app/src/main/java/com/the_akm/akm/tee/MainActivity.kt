package com.the_akm.akm.tee

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.the_akm.akm.tee.ConstantCommons.INTEXT_XTRAS_TWEETERS
import kotlinx.android.synthetic.*


class MainActivity : AppCompatActivity() {

//    public val  INTEXT_XTRAS_TWEETERS = "INTENT_XTRAS_TWEETERS";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity_main_send_button.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if(activity_main_input_TextInputEditText.hasFocus()) {
                    dismissKeyboard(activity_main_input_TextInputEditText)
                }
                val st = activity_main_input_TextInputEditText.text.toString()
                val words = st.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if(st.length == 0){
                    activity_main_input_TextInputLayout.error = "Type something"
                }
                else if(st.length > 50 && words.size == 1){
                    activity_main_input_TextInputLayout.error = "Need more than one space and 50 characters"
                }

                else {
                    val intent = Intent(this@MainActivity, AnswerActivity::class.java)
                    intent.putExtra(INTEXT_XTRAS_TWEETERS, activity_main_input_TextInputEditText.text.toString())
                    startActivity(intent)
                }
            }
        })

    }

    fun dismissKeyboard(editText: EditText){
        editText.clearFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }


}

