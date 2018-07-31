package kitchenpal.troychuinard.com.kitchenpal;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import kitchenpal.troychuinard.com.kitchenpal.Model.Recipe;


public class IndividualRecipeActivity extends AppCompatActivity implements RecipeStepsFragment.OnFragmentInteractionListener, RecipeStepsFragmentTwo.OnFragmentInteractionListener, RecipeStepsFragmentThree.OnFragmentInteractionListener {

    private Recipe mRecipe;
    private List<Recipe> mRecipeList;


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

        if (savedInstanceState == null){


            RecipeStepsFragment recipeStepsFragment = RecipeStepsFragment.newInstance(mRecipeList, position);
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
