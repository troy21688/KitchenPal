package kitchenpal.troychuinard.com.kitchenpal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

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
public class RecipeStepsFragmentThree extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SimpleExoPlayerView mSimpleExoPlayer;
    private SimpleExoPlayer mExoPlayer;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Recipe> mRecipeList;
    private int mPosition;
    private int mStepPosition;
    private String mVideoURL;
    private List<Steps> mSteps;
    private FrameLayout mExoPlayerPlaceholder;

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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer!=null) {
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recipe_details_three, container,false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mRecipeList = bundle.getParcelableArrayList("Recipe_List");
            mPosition = bundle.getInt("Recipe_Position");
            mStepPosition = bundle.getInt("Step_Position", 0);
        }
        mSteps = mRecipeList.get(mPosition).getSteps();
        Steps step = mSteps.get(mStepPosition);
        mVideoURL = step.getVideoURL();
        mSimpleExoPlayer = v.findViewById(R.id.exoplayer);
        mExoPlayerPlaceholder = v.findViewById(R.id.exoplayer_placeholder);
        if (mVideoURL == null || mVideoURL.isEmpty()){
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

    private void initializePlayer(){
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
        mExoPlayer.prepare(videoSource);
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
