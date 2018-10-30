package com.the_akm.akm.TwitSplit

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.the_akm.akm.TwitSplit.ConstantCommons.CASE_ERROR_MSG
import com.the_akm.akm.TwitSplit.ConstantCommons.INTEXT_XTRAS_TWEETERS
import com.the_akm.akm.TwitSplit.ConstantCommons.ZERO_LENGTH_ERROR_MSG
import android.view.View.OnFocusChangeListener




class TwitSplitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity_main_constraintLayout.setOnFocusChangeListener{ v, hasFocus ->
            if (hasFocus) {
                dismissKeyboard(activity_main_input_TextInputEditText)
            }
        }


        activity_main_send_button.setOnClickListener {
            if(activity_main_input_TextInputEditText.hasFocus()) {
                dismissKeyboard(activity_main_input_TextInputEditText)
            }
            val st = activity_main_input_TextInputEditText.text.toString()
            val words = st.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if(st.length == 0) {
                activity_main_input_TextInputLayout.error = ZERO_LENGTH_ERROR_MSG
            } else if(st.length > 50 && words.size == 1) {
                activity_main_input_TextInputLayout.error = CASE_ERROR_MSG
            } else {
                val intent = Intent(this@TwitSplitActivity, AnswerActivity::class.java)
                intent.putExtra(INTEXT_XTRAS_TWEETERS, activity_main_input_TextInputEditText.text.toString())
                startActivity(intent)
            }
        }

    }

    fun dismissKeyboard(editText: EditText){
        editText.clearFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0)
    }


}


