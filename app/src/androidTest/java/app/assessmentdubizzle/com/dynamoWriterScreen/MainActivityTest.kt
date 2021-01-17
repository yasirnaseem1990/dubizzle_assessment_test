package app.assessmentdubizzle.com.dynamoWriterScreen

import android.os.SystemClock
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import app.assessmentdubizzle.com.R
import app.assessmentdubizzle.com.adapter.DynamoWriterListAdapter
import app.assessmentdubizzle.com.base.BaseUITest
import app.assessmentdubizzle.com.di.generateTestAppComponent
import app.assessmentdubizzle.com.helpers.RecyclerViewMatcher
import app.assessmentdubizzle.com.modules.view.activities.MainActivity
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.test.inject
import java.net.HttpURLConnection


@RunWith(AndroidJUnit4::class)
class MainActivityTest : BaseUITest() {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java, true, false)

    //Inject Mockwebserver created with koin
    val mMockWebServer: MockWebServer by inject()

    val mNameTestOne = "Notebook"
    val mPriceTestOne = "AED 5"
    val mNameTestTwo = "studio couch, day bed"
    val mPriceTestTwo = "AED 800"

    @Before
    fun start() {
        super.setUp()
        startKoin { generateTestAppComponent(getMockWebServerUrl()) }
        loadKoinModules(generateTestAppComponent(getMockWebServerUrl()).toMutableList())
    }

    @Test
    fun test_shimmer_layout_isDisplayed() {
        mActivityTestRule.launchActivity(null)
        onView(withId(R.id.shimmerFrameLayout)).check(matches(isDisplayed())) }

    @Test
    fun test_recyclerview_elements_for_expected_response() {
        mActivityTestRule.launchActivity(null)

        mockNetworkResponseWithFileContent("success_resp_list.json", HttpURLConnection.HTTP_OK)

        //Wait for MockWebServer to get back with response
        SystemClock.sleep(2000)

        onView(withId(R.id.rvDynamoWriter)).check(matches(isDisplayed()))

        onView(withId(R.id.rvDynamoWriter)).perform(RecyclerViewActions.actionOnItemAtPosition<DynamoWriterListAdapter.DynamoListViewHolder>(0, typeTextInChildViewWithId(R.id.tvTitle, mNameTestOne)))
        onView(withId(R.id.rvDynamoWriter)).perform(RecyclerViewActions.actionOnItemAtPosition<DynamoWriterListAdapter.DynamoListViewHolder>(0, typeTextInChildViewWithId(R.id.tvCreatedAtDate, mPriceTestOne)))


        onView(withRecyclerView(R.id.rvDynamoWriter)?.atPositionOnView(0, R.id.cvDynamoLayout)).
        check(matches(hasDescendant(withId(R.id.tvTitle))))

        onView(withId(R.id.rvDynamoWriter)).perform(RecyclerViewActions.actionOnItemAtPosition<DynamoWriterListAdapter.DynamoListViewHolder>(6, typeTextInChildViewWithId(R.id.tvTitle, mNameTestTwo)))
        onView(withId(R.id.rvDynamoWriter)).perform(RecyclerViewActions.actionOnItemAtPosition<DynamoWriterListAdapter.DynamoListViewHolder>(6, typeTextInChildViewWithId(R.id.tvCreatedAtDate, mPriceTestTwo)))

        onView(withRecyclerView(R.id.rvDynamoWriter)?.atPosition(3)).perform(click())

        // DynamoWriterDetail Screen
        onView(withId(R.id.tvNameValue)).check(matches(isDisplayed()))
        onView(withId(R.id.tvNameValue)).check(matches(withText("acoustic guitar")))
        onView(withId(R.id.tvPrice)).check(matches(withText("AED 600")))

        SystemClock.sleep(3000)

        onView(withId(R.id.ivPicture)).check(matches(isDisplayed()))
    }

    private fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher? {
        return RecyclerViewMatcher(recyclerViewId)
    }

    private fun typeTextInChildViewWithId(id: Int, textToBeTyped: String): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                val v = view.findViewById<View>(id) as TextView
                v.text = textToBeTyped
            }
        }
    }

}