package com.example.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.leanback.widget.DetailsOverviewRow;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.Presenter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class CardPresenter extends Presenter {

    private static final String TAG = CardPresenter.class.getSimpleName();

    private static Context mContext;
    private static int CARD_WIDTH = 313;
    private static int CARD_HEIGHT = 176;

    static class ViewHolder extends Presenter.ViewHolder {

        private PicassoImageCardViewTarget mImageCardViewTarget;
        private Video mVideo;
        private Powerpoint mPowerPoint;
        private Calendar mCalendar;
        private ImageCardView mCardView;
        private Drawable mDefaultCardImage;

        public ViewHolder(View view) {
            super(view);
            mCardView = (ImageCardView) view;
            mImageCardViewTarget = new PicassoImageCardViewTarget(mCardView);
            mDefaultCardImage = mContext.getResources().getDrawable(R.drawable.logo);
        }

        public void setVideo (Video video) {
            mVideo = video;
        }

        public Video getVideo() {
            return mVideo;
        }

        public void setPowerPoint (Powerpoint powerPoint) {
            mPowerPoint = powerPoint;
        }

        public Powerpoint getPowerPoint() { return mPowerPoint; }

        public void setCalendar(Calendar calendar) { mCalendar = calendar; }

        public Calendar getCalendar() { return mCalendar; }

        public ImageCardView getCardView() {
            return mCardView;
        }

        public Drawable getDefaultCardImage() {
            return mDefaultCardImage;
        }

        public Drawable getCalendarCardImage() {
            return mContext.getResources().getDrawable(R.drawable.calendar);
        }
    }

    public static class PicassoImageCardViewTarget implements Target {
        private ImageCardView mImageCardView;

        public PicassoImageCardViewTarget(ImageCardView imageCardView) {
            mImageCardView = imageCardView;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
            Drawable bitmapDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
            mImageCardView.setMainImage(bitmapDrawable);
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable drawable) {
            mImageCardView.setMainImage(drawable);
        }

        @Override
        public void onPrepareLoad(Drawable drawable) {
            // Do nothing, default_background manager has its own transitions
        }
    }

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
        if (item instanceof Video) {
            Video video = (Video) item;
            ((ViewHolder) viewHolder).setVideo(video);
            // Setting the card presenter of the video
            setCardSpecifications(viewHolder, video);
        } else if (item instanceof Powerpoint) {
            Powerpoint powerpoint = (Powerpoint) item;
            ((ViewHolder) viewHolder).setPowerPoint(powerpoint);
            setCardSpecifications(viewHolder, powerpoint);
        } else if (item instanceof Calendar) {
            Calendar calendar = (Calendar) item;
            ((ViewHolder) viewHolder).setCalendar(calendar);
            ((ViewHolder) viewHolder).mCardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
            ((ViewHolder) viewHolder).mCardView.setMainImage(((ViewHolder) viewHolder).getCalendarCardImage());
        } else if (item instanceof String) {
            ((ViewHolder) viewHolder).mCardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
            ((ViewHolder) viewHolder).mCardView.setMainImage(((ViewHolder) viewHolder).getDefaultCardImage());
        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        Log.d(TAG, "onUnbindViewHolder");
    }

    private void setCardSpecifications(Presenter.ViewHolder viewHolder, DisplayObject item) {
        ((ViewHolder) viewHolder).mCardView.setTitleText(item.getTitle());
        ((ViewHolder) viewHolder).mCardView.setContentText(item.getDescription());
        ((ViewHolder) viewHolder).mCardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
        // Setting the card presenter of the video
        String CardUrl = item.getCardUrl();
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
        else {
            // When no card url is provided: set the default card
            ((ViewHolder) viewHolder).mCardView.setMainImage(((ViewHolder) viewHolder).getDefaultCardImage());
        }
    }

}
