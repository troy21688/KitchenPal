package kitchenpal.troychuinard.com.kitchenpal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kitchenpal.troychuinard.com.kitchenpal.Adapter.IngredientAdapter;
import kitchenpal.troychuinard.com.kitchenpal.Model.Ingredients;
import kitchenpal.troychuinard.com.kitchenpal.Model.Recipe;
import kitchenpal.troychuinard.com.kitchenpal.Model.Steps;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IngredientsListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IngredientsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientsListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<Recipe> mRecipeList;
    private List<Ingredients> mIngredientList;
    private int mPosition;
    private int mStepPosition;
    private List<Steps> mSteps;
    private IngredientAdapter mIngredientAdapter;
    private RecyclerView mRecyclerView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    public IngredientsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment IngredientsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IngredientsListFragment newInstance(List<Recipe> recipeList, int recipePosition, int stepPosition, List<Ingredients> recipeIngredients) {
        IngredientsListFragment fragment = new IngredientsListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("Recipe_List", (ArrayList<? extends Parcelable>) recipeList);
        bundle.putInt("Recipe_Position", recipePosition);
        bundle.putInt("Step_Position", stepPosition);
        bundle.putParcelableArrayList("Ingredient_List", (ArrayList<? extends Parcelable>) recipeIngredients);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentManager fm = getFragmentManager();
        mIngredientAdapter = new IngredientAdapter(getContext(), fm);

        View v = inflater.inflate(R.layout.fragment_ingredients_list, container,false);
        mRecyclerView = v.findViewById(R.id.ingredients_recyclerview);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(mIngredientAdapter);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mRecipeList = bundle.getParcelableArrayList("Recipe_List");
            mPosition = bundle.getInt("Recipe_Position");
            mStepPosition = bundle.getInt("Step_Position", 0);
            mIngredientList = bundle.getParcelableArrayList("Ingredient_List");
        }
        mSteps = mRecipeList.get(mPosition).getSteps();
        Steps step = mSteps.get(mStepPosition);
        mIngredientAdapter.setDataSet(mSteps,mRecipeList,mPosition,mIngredientList);
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
