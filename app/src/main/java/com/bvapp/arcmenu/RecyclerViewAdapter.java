package com.bvapp.arcmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final Context mContext;
    private final String[] mDataset;

    public RecyclerViewAdapter(Context context, String[] dataset) {
        mContext = context;
        mDataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lst_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        String[] values = mDataset[position].split(",");
        String countryName = values[0];
        int flagResId = mContext.getResources().getIdentifier(values[1], "mipmap", mContext.getPackageName());
        viewHolder.mTextView.setText(countryName);
        viewHolder.mImageView.setImageDrawable(Utility.getRoundedBitmap(mContext, flagResId));
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ImageView mImageView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.title);
            mImageView = (ImageView) v.findViewById(R.id.imgLst);
        }
    }
}