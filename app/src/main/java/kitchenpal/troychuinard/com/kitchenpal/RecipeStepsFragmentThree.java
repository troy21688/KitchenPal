package kitchenpal.troychuinard.com.kitchenpal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import kitchenpal.troychuinard.com.kitchenpal.Model.Recipe;
import kitchenpal.troychuinard.com.kitchenpal.Model.Steps;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeStepsFragmentThree.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeStepsFragmentThree#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeStepsFragmentThree extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String SELECTED_POSITION = "SELECTED_POSITION";
    private static final String PLAYER_STATE = "PLAYER_STATE";
    private SimpleExoPlayerView mSimpleExoPlayer;
    private SimpleExoPlayer mExoPlayer;
    private ImageView mNextArrow;
    private ImageView mBackArrow;
    private TextView mStepDescription;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Recipe> mRecipeList;
    private int mPosition;
    private int mStepPosition;
    private String mVideoURL;
    private List<Steps> mSteps;
    private FrameLayout mExoPlayerPlaceholder;
    private FragmentManager mFragManager;
    private FragmentTransaction mTransaction;
    private Long mPlayerPosition;
    private boolean mIsPlayWhenReady;


    private OnFragmentInteractionListener mListener;

    public RecipeStepsFragmentThree() {
        // Required empty public constructor
    }


    public static RecipeStepsFragmentThree newInstance(List<Recipe> recipeList, int recipePosition, int stepPosition) {
        RecipeStepsFragmentThree fragment = new RecipeStepsFragmentThree();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("Recipe_List", (ArrayList<? extends Parcelable>) recipeList);
        bundle.putInt("Recipe_Position", recipePosition);
        bundle.putInt("Step_Position", stepPosition);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            mPlayerPosition = savedInstanceState.getLong(SELECTED_POSITION);
            mIsPlayWhenReady = savedInstanceState.getBoolean(PLAYER_STATE);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mVideoURL != null) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            mExoPlayer.release();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
            mPlayerPosition = mExoPlayer.getCurrentPosition();
            mIsPlayWhenReady = mExoPlayer.getPlayWhenReady();
            outState.putLong(SELECTED_POSITION, mPlayerPosition);
            outState.putBoolean(PLAYER_STATE, mIsPlayWhenReady);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recipe_details_three, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mRecipeList = bundle.getParcelableArrayList("Recipe_List");
            mPosition = bundle.getInt("Recipe_Position");
            mStepPosition = bundle.getInt("Step_Position", 0);
        }

        mFragManager = getFragmentManager();
        mTransaction = mFragManager.beginTransaction();


        mStepDescription = v.findViewById(R.id.ingredient_description);

        mNextArrow = v.findViewById(R.id.next_arrow);
        mNextArrow.setOnClickListener(this);
        mBackArrow = v.findViewById(R.id.back_arrow);
        mBackArrow.setOnClickListener(this);


        mSteps = mRecipeList.get(mPosition).getSteps();
        Steps step = mSteps.get(mStepPosition);

        if (mStepPosition == (mSteps.size() - 1)) {
            mNextArrow.setVisibility(View.INVISIBLE);
        }

        if (mStepPosition == 0) {
            mBackArrow.setVisibility(View.INVISIBLE);
        }

        mStepDescription.setText(step.getDescription());
        mVideoURL = step.getVideoURL();
        mSimpleExoPlayer = v.findViewById(R.id.exoplayer);
        mExoPlayerPlaceholder = v.findViewById(R.id.exoplayer_placeholder);
        if (mVideoURL == null || mVideoURL.isEmpty()) {
            mSimpleExoPlayer.setVisibility(View.GONE);
            mExoPlayerPlaceholder.setVisibility(View.VISIBLE);
        }


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

    private void initializePlayer() {
        // Create a default TrackSelector

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        //Initialize the player
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
        mSimpleExoPlayer.setPlayer(mExoPlayer);


        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "CloudinaryExoplayer"));

        // Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();


        // This is the MediaSource representing the media to be played.
        Uri videoUri = Uri.parse(mVideoURL);
        MediaSource videoSource = new ExtractorMediaSource(videoUri,
                dataSourceFactory, extractorsFactory, null, null);

        // Prepare the player with the source.
        Bundle bundle = new Bundle();
        mExoPlayer.prepare(videoSource);
        if (mPlayerPosition != null){
            mExoPlayer.seekTo(mPlayerPosition);
            mExoPlayer.setPlayWhenReady(mIsPlayWhenReady);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.next_arrow):
                if (mStepPosition < mSteps.size()) {
                    RecipeStepsFragmentThree recipeStepsFragmentThree;
                    recipeStepsFragmentThree = RecipeStepsFragmentThree.newInstance(mRecipeList, mPosition, mStepPosition + 1);
                    //TODO: Why am I being prompted for V4? Am I handling correctly?

                    //TODO: Am I handling removal of previous frag correctly?
                    RecipeStepsFragmentThree recipeStepsFragmentThreeOld = (RecipeStepsFragmentThree) mFragManager.findFragmentById(R.id.recipe_details_three);
                    mTransaction.remove(recipeStepsFragmentThreeOld);
                    mTransaction.replace(R.id.recipe_details_three, recipeStepsFragmentThree);
                    mTransaction.commit();
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.final_step), Toast.LENGTH_LONG).show();
                }
                break;
            case (R.id.back_arrow):
                RecipeStepsFragmentThree recipeStepsFragmentThree;
                recipeStepsFragmentThree = RecipeStepsFragmentThree.newInstance(mRecipeList, mPosition, mStepPosition - 1);
                //TODO: Why am I being prompted for V4? Am I handling correctly?

                //TODO: Am I handling removal of previous frag correctly?
                RecipeStepsFragmentThree recipeStepsFragmentThreeOld = (RecipeStepsFragmentThree) mFragManager.findFragmentById(R.id.recipe_details_three);
                mTransaction.remove(recipeStepsFragmentThreeOld);
                mTransaction.replace(R.id.recipe_details_three, recipeStepsFragmentThree);
                mTransaction.commit();
                break;
        }
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
