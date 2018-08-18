package kitchenpal.troychuinard.com.kitchenpal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kitchenpal.troychuinard.com.kitchenpal.IndividualRecipeActivity;
import kitchenpal.troychuinard.com.kitchenpal.Model.Ingredients;
import kitchenpal.troychuinard.com.kitchenpal.Model.Recipe;
import kitchenpal.troychuinard.com.kitchenpal.R;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private static final String RECIPE_NAME = "RECIPE_NAME";
    private static final String PREFS = "PREFS";
    private static final String INGREDIENTS = "INGREDIENTS";
    private List<Recipe> mRecipeDataSet = new ArrayList<Recipe>();
    private Context con;
    //TODO: I am trying to save to preferences below and I believe I need a context. I am getting an error that this is null so I have commented out.
    private SharedPreferences mPrefs;


    public MyAdapter(Context context) {
        this.con = context;
        mPrefs = con.getSharedPreferences(PREFS, Context.MODE_PRIVATE);

    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        protected CardView mCardView;
        protected TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            mCardView = itemView.findViewById(R.id.recipe_cardview);
            mTextView = itemView.findViewById(R.id.id_of_recipe_item);
        }
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, final int position) {
        String id = mRecipeDataSet.get(position).getId();
        final String recipeName = mRecipeDataSet.get(position).getName();
        final List<Ingredients> ingredients = mRecipeDataSet.get(position).getIngredients();
        holder.mTextView.setText(recipeName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = mPrefs.edit();
                editor.putString(INGREDIENTS, String.valueOf(ingredients));
                editor.putString(RECIPE_NAME, recipeName);
                editor.apply();




                //TODO: Starting Next Activity - should I incorporate passing recipe name in this section?
                Intent i = new Intent(con, IndividualRecipeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("Recipe_List", (ArrayList<? extends Parcelable>) mRecipeDataSet);
                i.putExtras(bundle);
                i.putExtra("Position", position);
                con.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mRecipeDataSet.size();
    }

    public void setDataSet(List<Recipe> recipeList) {
        mRecipeDataSet = recipeList;
    }

    public List<Recipe> getmRecipeDataSet() {
        return mRecipeDataSet;
    }
}
