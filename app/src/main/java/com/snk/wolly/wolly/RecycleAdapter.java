package com.snk.wolly.wolly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private Recyclable[] mDataset;
    private Context context;
    private final PickRecyclableClickListener pickRecyclableClickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvRecycSub, tvRecycName;
        public ImageView ivRecycLogo;
        public CardView cvRecyc;
        public ViewHolder(View v) {
            super(v);
            tvRecycSub = v.findViewById(R.id.tvRecycSub);
            tvRecycName = v.findViewById(R.id.tvRecycName);
            ivRecycLogo = v.findViewById(R.id.ivRecycLogo);
            cvRecyc = v.findViewById(R.id.cvRecyc);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecycleAdapter(Context context, Recyclable[] myDataset, PickRecyclableClickListener listener) {
        this.context = context;
        mDataset = myDataset;
        pickRecyclableClickListener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.recyclecard, parent, false);


        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Recyclable recyclable = mDataset[position];
        Glide.with(context)
                .load(recyclable.getImage())
                .dontTransform()
                .into(holder.ivRecycLogo);
        holder.tvRecycName.setText(recyclable.getName());
        ViewCompat.setTransitionName(holder.ivRecycLogo, "recycLogo");
        ViewCompat.setTransitionName(holder.tvRecycName, "recycName");

        if(recyclable.isWeighable()){
            holder.tvRecycSub.setText(String.format("+%s puntos por kilogramo", recyclable.getScorePerKg()));
        }else{
            holder.tvRecycSub.setText(String.format("+%s puntos por %s", recyclable.getScorePerUnit(), recyclable.getName().toLowerCase()));

        }

        holder.cvRecyc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickRecyclableClickListener.onItemClick(position, holder.ivRecycLogo, holder.tvRecycName, mDataset[position]);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    
}
