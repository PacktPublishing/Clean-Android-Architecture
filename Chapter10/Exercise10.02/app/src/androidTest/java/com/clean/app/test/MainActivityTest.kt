package com.clean.app.test

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import com.clean.app.MainActivity
import com.clean.app.idling.ComposeCountingIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Inject
    lateinit var idlingResource: ComposeCountingIdlingResource

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        composeTestRule.registerIdlingResource(idlingResource)
    }

    @After
    fun tearDown() {
        composeTestRule.unregisterIdlingResource(idlingResource)
    }

    @Test
    fun testDisplayList() {
        composeTestRule.onNodeWithText("Total click count: 0")
            .assertIsDisplayed()
        composeTestRule.onAllNodesWithText("Author: name1")
            .assertCountEquals(2)
        composeTestRule.onAllNodesWithText("Author: name2")
            .assertCountEquals(2)
        composeTestRule.onNodeWithText("Title: title1")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Title: title2")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Title: title3")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Title: title4")
            .assertIsDisplayed()
    }

}