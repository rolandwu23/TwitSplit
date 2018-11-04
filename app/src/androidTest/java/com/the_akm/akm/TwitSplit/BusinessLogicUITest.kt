package com.the_akm.akm.TwitSplit

import android.util.Log
import androidx.test.espresso.Espresso.onData
import androidx.test.rule.ActivityTestRule

import org.junit.Test
import org.junit.runner.RunWith


import org.junit.Rule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.SmallTest
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.CoreMatchers.*


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@SmallTest
class BusinessLogicUITest {


    @Rule
    @JvmField
    val mActivityRule = ActivityTestRule<TwitSplitActivity>(TwitSplitActivity::class.java)

    companion object {

        private val test1 =
            "I can't believe Tweeter now supports chunking my messages, so I don't have to do it myself."
        private val test2 = "                                                  "
        private val test3 = ""
        private val test4 = "Ican'tbelieveTweeternowsupportschunkingmymessages,soIdonot"

        private val ans1_test1 = "1/2 I can't believe Tweeter now supports chunking"
        private val ans2_test1 = "2/2 my messages, so I don't have to do it myself."

    }

    @Test
    fun Test1(){

        Log.e("@Test","Performing Test 1")

        onView(withId(R.id.activity_twitsplit_input_TextInputEditText)).perform(typeText(test1), closeSoftKeyboard())

        onView(withId(R.id.activity_twitsplit_send_button)).perform(click())

        onView(withId(R.id.answerActivity_listview)).check(matches(isDisplayed()))

        onData(anything())
            .inAdapterView(withId(R.id.answerActivity_listview))
            .atPosition(0)
            .check(matches(isDisplayed()))


        onData(anything())
            .inAdapterView(withId(R.id.answerActivity_listview))
            .atPosition(1)
            .check(matches(isDisplayed()))

        onData(anything())
            .inAdapterView(withId(R.id.answerActivity_listview))
            .atPosition(0)
            .onChildView(withId(R.id.card_view_ans_text))
            .check(matches(withText(ans1_test1)));

        onData(anything())
            .inAdapterView(withId(R.id.answerActivity_listview))
            .atPosition(1)
            .onChildView(withId(R.id.card_view_ans_text))
            .check(matches(withText(ans2_test1)));

    }

    @Test
    fun Test2(){

        Log.e("@Test","Performing Test 2")

        onView(withId(R.id.activity_twitsplit_input_TextInputEditText)).perform(typeText(test2), closeSoftKeyboard())

        onView(withId(R.id.activity_twitsplit_send_button)).perform(click())

        onView(withText(ConstantCommons.ALL_SPACE_ERROR_MSG)).check(matches(isDisplayed()))

        onView(withId(R.id.activity_twitsplit_input_TextInputLayout)).check(matches(CustomMatchers.hasTextInputLayoutHintText("Cannot be all space")))

    }

    @Test
    fun Test3(){

        Log.e("@Test","Performing Test 3")

        onView(withId(R.id.activity_twitsplit_input_TextInputEditText)).perform(typeText(test3), closeSoftKeyboard())

        onView(withId(R.id.activity_twitsplit_send_button)).perform(click())

        onView(withText(ConstantCommons.ZERO_LENGTH_ERROR_MSG)).check(matches(isDisplayed()))

        onView(withId(R.id.activity_twitsplit_input_TextInputLayout)).check(matches(CustomMatchers.hasTextInputLayoutHintText(ConstantCommons.ZERO_LENGTH_ERROR_MSG)))

    }

    @Test
    fun Test4(){

        Log.e("@Test","Performing Test 4")

        onView(withId(R.id.activity_twitsplit_input_TextInputEditText)).perform(typeText(test4), closeSoftKeyboard())

        onView(withId(R.id.activity_twitsplit_send_button)).perform(click())

        onView(withText(ConstantCommons.CASE_ERROR_MSG)).check(matches(isDisplayed()))

        onView(withId(R.id.activity_twitsplit_input_TextInputLayout)).check(matches(CustomMatchers.hasTextInputLayoutHintText(ConstantCommons.CASE_ERROR_MSG)))

    }
}


