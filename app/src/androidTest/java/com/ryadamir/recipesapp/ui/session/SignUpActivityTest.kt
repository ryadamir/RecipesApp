package com.ryadamir.recipesapp.ui.session


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.ryadamir.recipesapp.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SignUpActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun signUpActivityTest() {
        val materialTextView = onView(
            allOf(
                withId(R.id.signup), withText("Sign up"),
                childAtPosition(
                    allOf(
                        withId(R.id.ll_bottom),
                        childAtPosition(
                            withClassName(`is`("android.widget.RelativeLayout")),
                            4
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        val textInputEditText = onView(
            allOf(
                withId(R.id.username),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.email_layout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("test"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.password),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.mdp_layout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("test"), closeSoftKeyboard())

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.repassword),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.remdp_layout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(replaceText("test"), closeSoftKeyboard())

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.repassword), withText("test"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.remdp_layout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(pressImeActionButton())

        val materialCardView = onView(
            allOf(
                withId(R.id.signup),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        materialCardView.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
