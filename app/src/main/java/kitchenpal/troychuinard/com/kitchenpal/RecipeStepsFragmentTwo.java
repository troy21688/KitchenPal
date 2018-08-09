package kitchenpal.troychuinard.com.kitchenpal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

import kitchenpal.troychuinard.com.kitchenpal.Adapter.StepAdapter;
import kitchenpal.troychuinard.com.kitchenpal.Model.Ingredients;
import kitchenpal.troychuinard.com.kitchenpal.Model.Recipe;
import kitchenpal.troychuinard.com.kitchenpal.Model.Steps;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeStepsFragmentTwo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeStepsFragmentTwo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeStepsFragmentTwo extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String RECIPE = "RECIPE";
    private Recipe mRecipe;
    private TextView mTextView;
    private List<Recipe> mRecipeList;
    private int mPosition;
    private List<Ingredients> mIngredients;
    private List<Steps> mSteps;
    private RecyclerView mStepsRecyclerView;
    private StepAdapter mAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RecipeStepsFragmentTwo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment RecipeStepsFragmentTwo.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeStepsFragmentTwo newInstance(List<Recipe> recipeList, int position) {
        RecipeStepsFragmentTwo fragment = new RecipeStepsFragmentTwo();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("Recipe_List", (ArrayList<? extends Parcelable>) recipeList);
        bundle.putInt("Position", position);
        fragment.setArguments(bundle);
        //TODO: Is this the correct way of passing data between the activity and the fragment?
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recipe_details_two, null, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mRecipeList = bundle.getParcelableArrayList("Recipe_List");
            mPosition = bundle.getInt("Position");
        }

        mIngredients = mRecipeList.get(mPosition).getIngredients();
        for (Ingredients ingredients : mIngredients){
            Log.v("INGREDIENTS", ingredients.getIngredient());
        }
        mSteps = mRecipeList.get(mPosition).getSteps();
        mStepsRecyclerView = v.findViewById(R.id.recycler_view_recipe_steps);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        mStepsRecyclerView.setLayoutManager(lm);
        FragmentManager fm = getFragmentManager();
        mAdapter = new StepAdapter(getActivity().getApplicationContext(),fm);
        mAdapter.setDataSet(mSteps, mRecipeList, mPosition);
        mStepsRecyclerView.setAdapter(mAdapter);




        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
