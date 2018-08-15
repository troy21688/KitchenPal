package kitchenpal.troychuinard.com.kitchenpal;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.List;

import kitchenpal.troychuinard.com.kitchenpal.Model.Recipe;


public class IndividualRecipeActivity extends AppCompatActivity implements RecipeStepsFragment.OnFragmentInteractionListener, RecipeStepsFragmentTwo.OnFragmentInteractionListener, RecipeStepsFragmentThree.OnFragmentInteractionListener, IngredientsListFragment.OnFragmentInteractionListener, BlankFragment.OnFragmentInteractionListener {

    private Recipe mRecipe;
    private List<Recipe> mRecipeList;

    private boolean mTwopane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_host);



        Bundle bundle = getIntent().getExtras();

        Intent intent = getIntent();
        mRecipe = intent.getParcelableExtra("Recipe");
        int position = intent.getIntExtra("Position", 0);
        mRecipeList = bundle.getParcelableArrayList("Recipe_List");
        Recipe recipe = mRecipeList.get(position);
        Log.v("NAME_TEST", recipe.getName());
        getSupportActionBar().setTitle(recipe.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        if (savedInstanceState == null){


            RecipeStepsFragmentTwo recipeStepsFragmentTwo = RecipeStepsFragmentTwo.newInstance(mRecipeList, position);
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .add(R.id.recipe_details_two, recipeStepsFragmentTwo)
                    .commit();

            if (findViewById(R.id.two_pane_constraint_layout) != null && getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                mTwopane = true;
                BlankFragment blankFragment = new BlankFragment();
                fm.beginTransaction()
                        .add(R.id.recipe_details_three, blankFragment)
                        .commit();
            }
        }

        //TODO: Trying to set up two-pane display and confused what else I would do here

    }

    public Recipe getRecipe(){
        return mRecipe;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
        }
        //TODO: Is it alright to have methods before the call to super?
        return super.onOptionsItemSelected(item);

    }
}
