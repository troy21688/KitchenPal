package kitchenpal.troychuinard.com.kitchenpal.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kitchenpal.troychuinard.com.kitchenpal.Model.Recipe;
import kitchenpal.troychuinard.com.kitchenpal.Model.Steps;
import kitchenpal.troychuinard.com.kitchenpal.R;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private List<Steps> mStepsDataSet = new ArrayList<Steps>();
    private Context con;

    public StepAdapter(Context context) {
        this.con = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String id = mStepsDataSet.get(position).getId();
        holder.mTextView.setText(id);
    }

    @Override
    public int getItemCount() {
        return mStepsDataSet.size();
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

    public void setDataSet(List<Steps> stepsList){
        mStepsDataSet = stepsList;
    }




}
