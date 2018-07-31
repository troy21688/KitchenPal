package kitchenpal.troychuinard.com.kitchenpal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kitchenpal.troychuinard.com.kitchenpal.Model.Recipe;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeStepsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeStepsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeStepsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String RECIPE = "RECIPE";
    private TextView mLabel;
    private Button mButton;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Recipe> mRecipeList;
    private int mPosition;

    private OnFragmentInteractionListener mListener;

    public RecipeStepsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecipeStepsFragment.
     */
    //TODO: Is it correct to pass the complete JSON response to each Fragment? I wasn't able to determine a better way
    //TODO: of passing the List<Recipes> between each Fragment.
    //TODO: See https://stackoverflow.com/questions/51583602/access-non-hosting-activity-objects-from-fragment
    public static RecipeStepsFragment newInstance(List<Recipe> recipeList, int position) {
        RecipeStepsFragment fragment = new RecipeStepsFragment();
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
        View v = inflater.inflate(R.layout.fragment_recipe_details_one, null, false);
        // Inflate the layout for this fragment
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mRecipeList = bundle.getParcelableArrayList("Recipe_List");
            mPosition = bundle.getInt("Position");
        }

        String label = mRecipeList.get(mPosition).getName();
        Log.v("NAME", mRecipeList.get(mPosition).getName());
        mButton = v.findViewById(R.id.to_recipe_2);
        mLabel = v.findViewById(R.id.label);
        mLabel.setText(label);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecipeStepsFragmentTwo recipeStepsFragmentTwo = RecipeStepsFragmentTwo.newInstance(mRecipeList, mPosition);
                FragmentManager fm = getFragmentManager();
                //TODO: Why am I being prompted for V4? Am I handling correctly?
                android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
                //TODO: Am I handling removal of previous fragment correctly?
                RecipeStepsFragment recipeStepsFragment = (RecipeStepsFragment) fm.findFragmentById(R.id.recipe_details);
                transaction.remove(recipeStepsFragment);
                transaction.replace(R.id.recipe_details_two, recipeStepsFragmentTwo);
                transaction.commit();


            }
        });
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
