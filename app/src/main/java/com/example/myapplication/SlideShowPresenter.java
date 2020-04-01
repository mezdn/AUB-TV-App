package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.Presenter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class SlideShowPresenter extends Presenter {

    private static final String TAG = CardPresenter.class.getSimpleName();

    private static Context mContext;
    private static int CARD_WIDTH = 1400;
    private static int CARD_HEIGHT = 700;

    static class ViewHolder extends Presenter.ViewHolder {

//        private PicassoImageCardViewTarget mImageCardViewTarget;
        private Powerpoint mPowerPoint;
        private ImageCardView mCardView;
        private Drawable mDefaultCardImage;

        public ViewHolder(View view) {
            super(view);
            mCardView = (ImageCardView) view;
//            mImageCardViewTarget = new PicassoImageCardViewTarget(mCardView);
            mDefaultCardImage = mContext.getResources().getDrawable(R.drawable.logo);
        }

        public void setPowerPoint (Powerpoint powerPoint) {
            mPowerPoint = powerPoint;
        }

        public Powerpoint getPowerPoint() { return mPowerPoint; }

        public ImageCardView getCardView() {
            return mCardView;
        }

        public Drawable getDefaultCardImage() {
            return mDefaultCardImage;
        }
    }

//    public static class PicassoImageCardViewTarget implements Target {
//        private ImageCardView mImageCardView;
//
//        public PicassoImageCardViewTarget(ImageCardView imageCardView) {
//            mImageCardView = imageCardView;
//        }
//
//        @Override
//        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
//            Drawable bitmapDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
//            mImageCardView.setMainImage(bitmapDrawable);
//        }
//
//        @Override
//        public void onBitmapFailed(Exception e, Drawable drawable) {
//            mImageCardView.setMainImage(drawable);
//        }
//
//        @Override
//        public void onPrepareLoad(Drawable drawable) {
//            // Do nothing, default_background manager has its own transitions
//        }
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.d(TAG, "onCreateViewHolder");
        mContext = parent.getContext();

        ImageCardView cardView = new ImageCardView(mContext);
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        cardView.setBackgroundColor(mContext.getResources().getColor(R.color.indigo));
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        if (item instanceof Powerpoint) {
            Powerpoint powerpoint = (Powerpoint) item;
            ((ViewHolder) viewHolder).setPowerPoint(powerpoint);
            Log.d(TAG, "onBindViewHolder");
            ((ViewHolder) viewHolder).mCardView.setTitleText(powerpoint.getTitle());
            ((ViewHolder) viewHolder).mCardView.setContentText(powerpoint.getDescription());
            ((ViewHolder) viewHolder).mCardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);

            String CardUrl = ((Powerpoint) item).getCardUrl();
            if (CardUrl != null && !CardUrl.equals("")) {
                Glide.with(mContext)
                        .load(CardUrl)
                        .centerCrop()
                        .error(R.drawable.default_background)
                        .into(new SimpleTarget<GlideDrawable>(CARD_WIDTH, CARD_HEIGHT) {
                            @Override
                            public void onResourceReady(GlideDrawable resource,
                                                        GlideAnimation<? super GlideDrawable>
                                                                glideAnimation) {
                                ((ViewHolder) viewHolder).mCardView.setMainImage(resource);
                            }
                        });
            }
            ((ViewHolder) viewHolder).mCardView.setMainImage(((ViewHolder) viewHolder).getDefaultCardImage());
        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        Log.d(TAG, "onUnbindViewHolder");
    }

}
