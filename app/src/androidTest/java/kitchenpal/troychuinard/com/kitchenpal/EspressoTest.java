package kitchenpal.troychuinard.com.kitchenpal;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class EspressoTest {


    @Rule
    public ActivityTestRule<MainActivity> firstRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void testRecyclerViewClick() {
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_ingredients)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    //TODO: This test is not passing on my device, but it is passing on an emulator. I am not sure why, but have read about idling resources. Can you confirm how to handle?
    @Test
    public void testRecyclerViewText(){
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_ingredients)).perform(RecyclerViewActions.scrollToPosition(0));
        Espresso.onView(withText("Nutella Pie")).check(matches(isDisplayed()));
    }

}




