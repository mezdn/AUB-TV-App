package com.example.myapplication;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.util.List;
import timber.log.Timber;

public class MyPowerpointAdapter extends RecyclerView.Adapter {

    private List<String> mPowerPointList;

    static class PagesViewHolder extends RecyclerView.ViewHolder {
        private ImageView mViewImageView;

        public PagesViewHolder(@NonNull View itemView) {
            super(itemView);
            mViewImageView = itemView.findViewById(R.id.fragment_pager_item_imageView);
        }
    }

    public MyPowerpointAdapter(List<String> mPowerPointList) {
        this.mPowerPointList = mPowerPointList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_pager_item, parent, false);
        return new PagesViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PagesViewHolder mViewHolder = (PagesViewHolder) holder;
        String imageUrl = mPowerPointList.get(position);
        Log.i("imageUrl", imageUrl);
        // set image
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .override(mViewHolder.mViewImageView.getMaxWidth(),mViewHolder.mViewImageView.getMaxHeight())
                .error(R.drawable.default_background)
                .into(mViewHolder.mViewImageView);
    }

    @Override
    public int getItemCount()  {return mPowerPointList.size();}
}