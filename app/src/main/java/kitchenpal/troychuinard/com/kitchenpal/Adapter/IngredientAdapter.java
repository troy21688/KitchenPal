package kitchenpal.troychuinard.com.kitchenpal.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import kitchenpal.troychuinard.com.kitchenpal.Model.Ingredients;
import kitchenpal.troychuinard.com.kitchenpal.Model.Recipe;
import kitchenpal.troychuinard.com.kitchenpal.Model.Steps;
import kitchenpal.troychuinard.com.kitchenpal.R;


public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private List<Steps> mStepsDataSet = new ArrayList<Steps>();
    private List<Recipe> mRecipeList;
    private List<Ingredients> mIngredients = new ArrayList<>();
    private Context con;
    private int mRecipePosition;
    private FragmentManager mFragmanager;

    public IngredientAdapter(Context context, FragmentManager fm){
        this.con = context;
        this.mFragmanager = fm;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        protected CardView mCardView;
        protected TextView mIngredient;
        protected TextView mQuantity;
        protected TextView mMeasure;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.ingredient_cardview);
            mIngredient = itemView.findViewById(R.id.ingredient_ingredient);
            mMeasure = itemView.findViewById(R.id.ingredient_measure);
            mQuantity = itemView.findViewById(R.id.ingredient_quantity);
        }
    }



//    @NonNull
//    @Override
//    public StepAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item, parent, false);
//        return new StepAdapter.ViewHolder(v);
//    }

//    @Override
//    public void onBindViewHolder(@NonNull StepAdapter.ViewHolder holder, final int position) {
//        if (position == 0){
//            holder.mTextView.setText(R.string.Ingredients);
//        } else {
//            //TODO: When I tried concatening with a String resource it was not displaying desired text
//            holder.mTextView.setText("Step " + String.valueOf(position));
//        }
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentTransaction transaction = mFragmanager.beginTransaction();
//                if (position == 0){
//                    IngredientsListFragment ingredientsListFragment = new IngredientsListFragment();
//                    RecipeStepsFragmentTwo recipeStepsFragmentTwo = (RecipeStepsFragmentTwo) mFragmanager.findFragmentById(R.id.recipe_details_two);
//                    transaction.remove(recipeStepsFragmentTwo);
//                    transaction.replace(R.id.ingredients_list, ingredientsListFragment);
//                    transaction.commit();
//
//                } else {
//                    RecipeStepsFragmentThree recipeStepsFragmentThree;
//                    recipeStepsFragmentThree = RecipeStepsFragmentThree.newInstance(mRecipeList, mRecipePosition, (position -1));
//                    //TODO: Why am I being prompted for V4? Am I handling correctly?
//
//                    //TODO: Am I handling removal of previous frag correctly?
//                    RecipeStepsFragmentTwo recipeStepsFragmentTwo = (RecipeStepsFragmentTwo) mFragmanager.findFragmentById(R.id.recipe_details_two);
//                    transaction.remove(recipeStepsFragmentTwo);
//                    transaction.replace(R.id.recipe_details_three, recipeStepsFragmentThree);
//                    transaction.commit();
//                }
//
//
//            }
//        });

    public void setDataSet(List<Steps> stepsList, List<Recipe> recipeList, int recipePosition, List<Ingredients> ingredientsList){
        mStepsDataSet = stepsList;
        mRecipeList = recipeList;
        mRecipePosition = recipePosition;
        mIngredients = ingredientsList;

    }


    @NonNull
    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item,parent,false);
        return new IngredientAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.ViewHolder holder, int position) {
        Ingredients ingredients = mIngredients.get(position);
        holder.mIngredient.setText(ingredients.getIngredient());
        holder.mQuantity.setText(ingredients.getQuantity());
        holder.mMeasure.setText(ingredients.getMeasure());
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }
}
