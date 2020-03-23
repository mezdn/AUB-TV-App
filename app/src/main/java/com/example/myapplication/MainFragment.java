/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.leanback.app.BrowseFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;

import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import timber.log.Timber;

public class MainFragment extends BrowseFragment {
    private static final String TAG = "MainFragment";

    private ArrayObjectAdapter mRowsAdapter;
    private static final int GRID_ITEM_WIDTH = 300;
    private static final int GRID_ITEM_HEIGHT = 200;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Timber.i("onCreate Called");
        super.onActivityCreated(savedInstanceState);
        setupUIElements();
        setupEvenListeners();
        loadRows();
    }

    private void setupUIElements() {
        setBadgeDrawable(getActivity().getResources().getDrawable(R.drawable.logo_white));
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);
        setBrandColor(getResources().getColor(R.color.indigo_dark));
    }

    private void setupEvenListeners() {
        setOnItemViewClickedListener(new ItemViewClickedListener());
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {

        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {
            // todo: when clicking on a video
            if (item instanceof Video) {
                Video video = (Video) item;
                Log.d(TAG, "Item: " + item.toString());
                Toast.makeText(getActivity(), video.getTitle() + " clicked", Toast.LENGTH_LONG)
                        .show();
                Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
//                intent.putExtra(DetailsActivity.MOVIE, movie);
//                intent.putExtra(PlaybackOverlayActivity.VIDEO, video.toString());
                getActivity().startActivity(intent);
            }
            if (item instanceof Powerpoint) {
                Powerpoint powerpoint = (Powerpoint) item;
                Log.d(TAG, "Item: " + item.toString());
                Toast.makeText(getActivity(), powerpoint.getTitle() + " clicked", Toast.LENGTH_LONG)
                        .show();
                Intent intent = new Intent(getActivity(), PowerPointActivity.class);
//                intent.putExtra(DetailsActivity.MOVIE, movie);
//                intent.putExtra(PlaybackOverlayActivity.VIDEO, video.toString());
                getActivity().startActivity(intent);
            } else if (item instanceof String) {
                if (((String) item).contains(getString(R.string.calendar))) {
                    Intent intent = new Intent(getActivity(), CalendarActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

    // todo: here we can add rows
    private void loadRows() {
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        // region first row
        HeaderItem cardPresenterHeader_1 = new HeaderItem(1, "CAMPUS");
        CardPresenter cardPresenter_1 = new CardPresenter();
        ArrayObjectAdapter cardRowAdapter_1 = new ArrayObjectAdapter(cardPresenter_1);

        for (int i = 0; i < 10; i++) {
            Video video = new Video("Video " + i, "Description " + i, "");
            cardRowAdapter_1.add(video);
        }
        mRowsAdapter.add(new ListRow(cardPresenterHeader_1, cardRowAdapter_1));
        // endregion
        // region second row
        HeaderItem cardPresenterHeader_2 = new HeaderItem(1, "POWER POINTS TRY HERE");
        CardPresenter cardPresenter_2 = new CardPresenter();
        ArrayObjectAdapter cardRowAdapter_2 = new ArrayObjectAdapter(cardPresenter_2);

        for (int i = 0; i < 7; i++) {
            Powerpoint powerpoint = new Powerpoint("Powerpoint " + i, "Description" + i, "");
            cardRowAdapter_2.add(powerpoint);
        }
        mRowsAdapter.add(new ListRow(cardPresenterHeader_2, cardRowAdapter_2));
        // endregion
        // region third row
        HeaderItem cardPresenterHeader_3 = new HeaderItem(1, "SPORTS");
        CardPresenter cardPresenter_3 = new CardPresenter();
        ArrayObjectAdapter cardRowAdapter_3 = new ArrayObjectAdapter(cardPresenter_3);

        for (int i = 0; i < 15; i++) {
            Video video = new Video("Video " + (i + 17), "Description " + (i + 17), "");
            cardRowAdapter_3.add(video);
        }
        mRowsAdapter.add(new ListRow(cardPresenterHeader_3, cardRowAdapter_3));
        // endregion
        // region forth row
        HeaderItem cardPresenterHeader_4 = new HeaderItem(1, "MAJORS");
        CardPresenter cardPresenter_4 = new CardPresenter();
        ArrayObjectAdapter cardRowAdapter_4 = new ArrayObjectAdapter(cardPresenter_4);

        for (int i = 0; i < 5; i++) {
            Video video = new Video("Video " + (i + 32), "Description " + (i + 32), "");
            cardRowAdapter_4.add(video);
        }
        mRowsAdapter.add(new ListRow(cardPresenterHeader_4, cardRowAdapter_4));
        // endregion
        // region fifth row
        HeaderItem cardPresenterHeader_5 = new HeaderItem(1, "STUDENT CENTER");
        CardPresenter cardPresenter_5 = new CardPresenter();
        ArrayObjectAdapter cardRowAdapter_5 = new ArrayObjectAdapter(cardPresenter_5);

        for (int i = 0; i < 10; i++) {
            Video video = new Video("Video " + (i + 37), "Description " + (i + 37), "");
            cardRowAdapter_5.add(video);
        }
        mRowsAdapter.add(new ListRow(cardPresenterHeader_5, cardRowAdapter_5));
        // endregion
        // region sixth row
        HeaderItem cardPresenterHeader_6 = new HeaderItem(1, "TUITION FEES");
        CardPresenter cardPresenter_6 = new CardPresenter();
        ArrayObjectAdapter cardRowAdapter_6 = new ArrayObjectAdapter(cardPresenter_6);

        for (int i = 0; i < 3; i++) {
            Video video = new Video("Video " + (i + 47), "Description " + (i + 47), "");
            cardRowAdapter_6.add(video);
        }
        mRowsAdapter.add(new ListRow(cardPresenterHeader_6, cardRowAdapter_6));
        // endregion
        // region seventh row
        HeaderItem cardPresenterHeader_7 = new HeaderItem(1, "FINANCIAL AID");
        CardPresenter cardPresenter_7 = new CardPresenter();
        ArrayObjectAdapter cardRowAdapter_7 = new ArrayObjectAdapter(cardPresenter_7);

        for (int i = 0; i < 13; i++) {
            Video video = new Video("Video " + (i + 50), "Description " + (i + 50), "");
            cardRowAdapter_7.add(video);
        }
        mRowsAdapter.add(new ListRow(cardPresenterHeader_7, cardRowAdapter_7));
        // endregion
        // region eighth row
        HeaderItem cardPresenterHeader_8 = new HeaderItem(1, "DORMS");
        CardPresenter cardPresenter_8 = new CardPresenter();
        ArrayObjectAdapter cardRowAdapter_8 = new ArrayObjectAdapter(cardPresenter_8);

        for (int i = 0; i < 13; i++) {
            Video video = new Video("Video " + (i + 63), "Description " + (i + 63), "");
            cardRowAdapter_8.add(video);
        }
        mRowsAdapter.add(new ListRow(cardPresenterHeader_8, cardRowAdapter_8));
        // endregion

        /* set */
        setAdapter(mRowsAdapter);

        HeaderItem gridHeader = new HeaderItem(1, "CALENDAR");
        GridItemPresenter mGridPresenter = new GridItemPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
        gridRowAdapter.add(getResources().getString(R.string.calendar));
        mRowsAdapter.add(new ListRow(gridHeader, gridRowAdapter));
    }

    private class GridItemPresenter extends Presenter {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent) {
            TextView view = new TextView(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.setBackgroundColor(getResources().getColor(R.color.default_background));
            view.setTextColor(Color.WHITE);
            view.setGravity(Gravity.CENTER);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Object item) {
            ((TextView) viewHolder.view).setText((String) item);
        }

        @Override
        public void onUnbindViewHolder(ViewHolder viewHolder) {

        }
    }

}
