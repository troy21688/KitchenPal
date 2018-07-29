package kitchenpal.troychuinard.com.kitchenpal;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import kitchenpal.troychuinard.com.kitchenpal.Model.Recipe;


public class IndividualRecipeActivity extends AppCompatActivity implements RecipeStepsFragment.OnFragmentInteractionListener, RecipeStepsFragmentTwo.OnFragmentInteractionListener {

    public Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_host);

        Intent intent = getIntent();
        mRecipe = intent.getParcelableExtra("Recipe");
        Log.v("NAME", mRecipe.getName());

        if (savedInstanceState == null){


            RecipeStepsFragment recipeStepsFragment = RecipeStepsFragment.newInstance(mRecipe);
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .add(R.id.recipe_details, recipeStepsFragment)
                    .commit();
        }
    }

    public Recipe getRecipe(){
        return mRecipe;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
