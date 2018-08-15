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
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class EspressoTest {


    @Rule
    public ActivityTestRule<MainActivity> firstRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void testRecyclerViewClick() {
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_ingredients)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void testRecyclerViewText(){
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_ingredients)).check(matches(hasDescendant(withText("Nutella Pie"))));
    }

}




