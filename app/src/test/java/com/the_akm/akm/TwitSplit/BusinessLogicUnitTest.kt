package com.the_akm.akm.TwitSplit


import org.junit.Test

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat


class BusinessLogicUnitTest {

    companion object {

        private val test1 = "I can't believe Tweeter now supports chunking my messages, so I don't have to do it myself."
        private val test2 = ""
        private val test3 = "                                                  "
        private val test4 = "Ican'tbelieveTweeternowsupportschunkingmymessages,soIdonot"

        private val ans1 = 1
        private val ans2 = -1
        private val ans3 = -2
        private val ans4 = -3

        private val ans1_test1 = "I can't believe Tweeter now supports chunking"
        private val ans2_test1 = "my messages, so I don't have to do it myself."
    }

    @Test
    fun Test1() {
        val twitSplitActivity = TwitSplitActivity()
        val result = twitSplitActivity.validateString(test1)
        assertThat(result, `is`(ans1))

        val answerActivity = AnswerActivity()
        val arrayList = answerActivity.dynamic(test1, 46)
        assertThat(arrayList[0], `is`(ans1_test1))
        assertThat(arrayList[1], `is`(ans2_test1))

    }

    @Test
    fun Test2() {
        val twitSplitActivity = TwitSplitActivity()
        val result = twitSplitActivity.validateString(test2)
        assertThat(result, `is`(ans2))
    }

    @Test
    fun Test3() {
        val twitSplitActivity = TwitSplitActivity()
        val result = twitSplitActivity.validateString(test3)
        assertThat(result, `is`(ans3))
    }

    @Test
    fun Test4() {
        val twitSplitActivity = TwitSplitActivity()
        val result = twitSplitActivity.validateString(test4)
        assertThat(result, `is`(ans4))
    }

}
