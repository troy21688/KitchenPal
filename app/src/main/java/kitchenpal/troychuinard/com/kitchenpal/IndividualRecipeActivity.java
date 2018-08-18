package kitchenpal.troychuinard.com.kitchenpal;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import kitchenpal.troychuinard.com.kitchenpal.Model.Recipe;


public class IndividualRecipeActivity extends AppCompatActivity implements RecipeStepsFragment.OnFragmentInteractionListener, RecipeStepsFragmentTwo.OnFragmentInteractionListener, RecipeStepsFragmentThree.OnFragmentInteractionListener, IngredientsListFragment.OnFragmentInteractionListener, BlankFragment.OnFragmentInteractionListener {

    private static final String RECIPE_LIST = "RECIPE_LIST";
    private static final String RECIPE_POSITION = "RECIPE_POSITION";
    private Recipe mRecipe;
    private List<Recipe> mRecipeList;
    private int mPosition;
    private boolean hasClickedStep;

    private boolean mTwopane;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_host);


        Bundle bundle = getIntent().getExtras();

        Intent intent = getIntent();
        mRecipe = intent.getParcelableExtra("Recipe");
        mPosition = intent.getIntExtra("Position", 0);
        mRecipeList = bundle.getParcelableArrayList("Recipe_List");
        Recipe recipe = mRecipeList.get(mPosition);
        Log.v("NAME_TEST", recipe.getName());
        getSupportActionBar().setTitle(recipe.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTwopane = false;




        if (savedInstanceState == null) {


            RecipeStepsFragmentTwo recipeStepsFragmentTwo = RecipeStepsFragmentTwo.newInstance(mRecipeList, mPosition);
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .add(R.id.recipe_details_two, recipeStepsFragmentTwo)
                    .commit();



            //two-pane
            if (findViewById(R.id.two_pane_constraint_layout) != null & getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mTwopane = true;
                BlankFragment blankFragment = new BlankFragment();
                fm.beginTransaction()
                        .add(R.id.recipe_details_three, blankFragment)
                        .commit();
            }

        }

        else {
            mRecipeList = savedInstanceState.getParcelableArrayList(RECIPE_LIST);
            mPosition = savedInstanceState.getInt(RECIPE_POSITION);
            FragmentManager z = getSupportFragmentManager();
            //check if fragment was clicked, if so no longer necessary to show blank
            Fragment x = z.findFragmentById(R.id.recipe_details_three);
            if (x != null & x instanceof RecipeStepsFragmentThree  ) {
                return;
            }
            if (x != null & getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                z.beginTransaction().remove(x).commit();
            }
            if (x == null & getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                BlankFragment newLandscapeFrag = new BlankFragment();
                z.beginTransaction().add(R.id.recipe_details_three, newLandscapeFrag).commit();
            }


        }

    }

    public Recipe getRecipe() {
        return mRecipe;
    }

    public void setHasClickedStep(boolean hasClickedStep) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        //TODO: Is it alright to have methods before the call to super?
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RECIPE_LIST, (ArrayList<? extends Parcelable>) mRecipeList);
        outState.putInt(RECIPE_POSITION, mPosition);

    }
}
