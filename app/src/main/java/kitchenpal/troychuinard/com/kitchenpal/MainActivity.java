package kitchenpal.troychuinard.com.kitchenpal;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Movie;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kitchenpal.troychuinard.com.kitchenpal.Adapter.MyAdapter;
import kitchenpal.troychuinard.com.kitchenpal.Model.Recipe;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecipeList;
    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private List<Recipe> mRecipeListResponse;
    private MyAdapter mAdapter;
    ConstraintLayout mConstraintLayoutSmall;

    //testing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: Unable to set dynamic, material RecyclerView using Constraints; had to set static DP for height of view
        //TODO: See StackOverflow post: https://stackoverflow.com/questions/51529326/constraintlayout-unable-to-wrap-content-and-stack-recyclerview-items?noredirect=1#comment90046855_51539365
        mRecipeList = findViewById(R.id.recycler_view_ingredients);
        mConstraintLayoutSmall = findViewById(R.id.small_layout);
        int spanCount = calculateNoOfColumns(getApplicationContext());

        LinearLayoutManager lm = new LinearLayoutManager(getApplicationContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);

        GridLayoutManager glm = new GridLayoutManager(this, spanCount);
        glm.setOrientation(LinearLayoutManager.VERTICAL);

        //check if landscape mode
        if (getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && mConstraintLayoutSmall != null) {
            mRecipeList.setLayoutManager(glm);
        } else {
            mRecipeList.setLayoutManager(lm);
        }

        callToRetrofit();

    }

    private void callToRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<List<Recipe>> call = apiInterface.getRecipe();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.v("TAG", String.valueOf(response.isSuccessful()));
                mRecipeListResponse = response.body();
                for (Recipe recipe : mRecipeListResponse){
                    Log.v("ID", recipe.getId().toString());
                    Log.v("SIZE", String.valueOf(mRecipeListResponse.size()));
                }

                mAdapter = new MyAdapter(MainActivity.this);
                mAdapter.setDataSet(mRecipeListResponse);
                mRecipeList.setAdapter(mAdapter);
//                mAdapter.notifyDataSetChanged();
;            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.v("TAG", t.getMessage());
            }
        });


    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }

    public interface ApiInterface{
        @GET("topher/2017/May/59121517_baking/baking.json")
        Call<List<Recipe>> getRecipe();
    }
}
