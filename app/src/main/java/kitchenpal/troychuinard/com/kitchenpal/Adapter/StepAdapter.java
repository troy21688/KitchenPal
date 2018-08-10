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

import kitchenpal.troychuinard.com.kitchenpal.IngredientsListFragment;
import kitchenpal.troychuinard.com.kitchenpal.Model.Ingredients;
import kitchenpal.troychuinard.com.kitchenpal.Model.Recipe;
import kitchenpal.troychuinard.com.kitchenpal.Model.Steps;
import kitchenpal.troychuinard.com.kitchenpal.R;
import kitchenpal.troychuinard.com.kitchenpal.RecipeStepsFragmentThree;
import kitchenpal.troychuinard.com.kitchenpal.RecipeStepsFragmentTwo;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private List<Steps> mStepsDataSet = new ArrayList<Steps>();
    private List<Ingredients> mIngredientsDataSet = new ArrayList<>();
    private List<Recipe> mRecipeList;
    private Context con;
    private int mRecipePosition;
    private FragmentManager mFragmanager;


    public StepAdapter(Context context, FragmentManager fm) {
        this.con = context;
        this.mFragmanager = fm;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (position == 0){
            holder.mTextView.setText(R.string.Ingredients);
        } else {
            //TODO: When I tried concatening with a String resource it was not displaying desired text
            holder.mTextView.setText("Step " + String.valueOf(position));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = mFragmanager.beginTransaction();
                if (position == 0){
                    IngredientsListFragment ingredientsListFragment = IngredientsListFragment.newInstance(mRecipeList, mRecipePosition, position, mIngredientsDataSet);
                    RecipeStepsFragmentTwo recipeStepsFragmentTwo = (RecipeStepsFragmentTwo) mFragmanager.findFragmentById(R.id.recipe_details_two);
                    transaction.remove(recipeStepsFragmentTwo);
                    transaction.replace(R.id.ingredients_list, ingredientsListFragment);
                    transaction.commit();

                } else {
                    RecipeStepsFragmentThree recipeStepsFragmentThree;
                    recipeStepsFragmentThree = RecipeStepsFragmentThree.newInstance(mRecipeList, mRecipePosition, (position -1));
                    //TODO: Why am I being prompted for V4? Am I handling correctly?

                    //TODO: Am I handling removal of previous frag correctly?
                    RecipeStepsFragmentTwo recipeStepsFragmentTwo = (RecipeStepsFragmentTwo) mFragmanager.findFragmentById(R.id.recipe_details_two);
                    transaction.remove(recipeStepsFragmentTwo);
                    transaction.replace(R.id.recipe_details_three, recipeStepsFragmentThree);
                    transaction.commit();
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        //Account for ingredients Card
        return (mStepsDataSet.size() + 1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        protected CardView mCardView;
        protected TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.step_cardview);
            mTextView = itemView.findViewById(R.id.id_of_step_item);
        }
    }

    public void setDataSet(List<Steps> stepsList, List<Recipe> recipeList, List<Ingredients> ingredientsList, int recipePosition){
        mStepsDataSet = stepsList;
        mRecipeList = recipeList;
        mRecipePosition = recipePosition;
        mIngredientsDataSet = ingredientsList;
    }




}
