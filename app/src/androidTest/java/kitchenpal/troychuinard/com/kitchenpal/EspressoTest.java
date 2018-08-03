package kitchenpal.troychuinard.com.kitchenpal;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class EspressoTest {

    //TODO: What is the purpose of this rule as it relates to the Test below?
    @Rule
    public ActivityTestRule<MainActivity> firstRule = new ActivityTestRule<>(MainActivity.class);

    //TODO: Very confused about Espresso testing and teh dependencies required; it appears Recyclerview
    //TODO: Requires additional dependencies other than those mentioned in the Android documentation?
    //TODO: What would be best method of testing all views of RecyclerView? What is there is a dynamic number of Views that are populated in RecyclerView?
    @Test
    public void testRecyclerViewClick(){
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_ingredients)).perform(RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));

    }

    @Test
    public void testRecyclerViewText(){


    }

}




