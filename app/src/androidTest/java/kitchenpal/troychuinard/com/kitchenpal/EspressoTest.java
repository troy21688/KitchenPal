package kitchenpal.troychuinard.com.kitchenpal;


import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class EspressoTest {

    private Activity mMainActivity;
    private RecyclerView mRecyclerView;
    private int res_ID = R.id.recycler_view_ingredients;
    private int itemCount = 0;

    //TODO: What is the purpose of this rule as it relates to the Test below?
    @Rule
    public ActivityTestRule<MainActivity> firstRule = new ActivityTestRule<>(MainActivity.class);


    //TODO: Very confused about Espresso testing and the dependencies required; it appears Recyclerview
    //TODO: Requires additional dependencies other than those mentioned in the Android documentation?
    //TODO: What would be best method of testing all views of RecyclerView? What is there is a dynamic number of Views that are populated in RecyclerView?


    //TODO: Instruction from StackOverflow Post: https://stackoverflow.com/questions/51678563/how-to-test-recyclerview-viewholder-text-with-espresso/51698252?noredirect=1#comment90433415_51698252
    //TODO: Is this necessary?
    @Before
    public void setupTest() {
        this.mMainActivity = this.firstRule.getActivity();
        this.mRecyclerView = this.mMainActivity.findViewById(this.res_ID);
        this.itemCount = this.mRecyclerView.getAdapter().getItemCount();

    }

    @Test
    public void testRecyclerViewClick() {
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_ingredients)).perform(RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));

    }

    @Test
    public void testRecyclerViewText() {
        if (this.itemCount > 0) {
            for (int i = 0; i < this.itemCount; i++) {

                /* clicking the item */
                onView(withId(this.res_ID))
                        .perform(RecyclerViewActions.actionOnItemAtPosition(i, click()));

                /* check if the ViewHolder is being displayed */
                //TODO: Why can I not access the RecyclerViewMatcher?
//                onView(new RecyclerViewMatcher(this.res_ID)
//                        .atPositionOnView(i, R.id.recipe_cardview))
//                        .check(matches(isDisplayed()));

                /* checking for the text of the first one item */
                //TODO: Why can I not access the RecyclerViewMatcher?
                Espresso.onView(ViewMatchers.withId(res_ID));
//                if(i == 0) {
//                    Espresso.onView(new ViewMatchers.withId(this.res_ID)
//                            .atPositionOnView(i, R.id.ingredientName))
//                            .check(matches(withText("Farbstoffe")));

            }

        }
    }
}




