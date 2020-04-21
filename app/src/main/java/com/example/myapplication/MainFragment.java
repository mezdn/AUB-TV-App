package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.leanback.app.BackgroundManager;
import androidx.leanback.app.BrowseFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;

import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainFragment extends BrowseFragment {
    private static final String TAG = "MainFragment";

    private ArrayObjectAdapter mRowsAdapter;

    private Drawable mDefaultBackground;
    private DisplayMetrics mMetrics;
    private BackgroundManager mBackgroundManager;

    private JSONObject metadata;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onActivityCreated(savedInstanceState);

        // Get the content form the API
        FetchDataFromApi();

        setupEvenListeners();

    }

    private void setupUIElements() {
        setBadgeDrawable(getActivity().getResources().getDrawable(R.drawable.logo_white));
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);
        setBrandColor(getResources().getColor(R.color.indigo_dark));
    }

    private void setupEvenListeners() {
        // Actions taken when an item is clicked
        setOnItemViewClickedListener(new ItemViewClickedListener());

        // Actions taken when an item is selected
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {

        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {
            if (item instanceof Video) {
                Video video = (Video) item;
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("video", video);
                getActivity().startActivity(intent);
            }
            if (item instanceof Powerpoint) {
                Powerpoint powerpoint = (Powerpoint) item;
                Intent intent = new Intent(getActivity(), PowerPointActivity.class);
                intent.putExtra("powerpoint", powerpoint);
                getActivity().startActivity(intent);
            }
            else if (item instanceof Calendar) {
                Intent intent = new Intent(getActivity(), CalendarActivity.class);
                startActivity(intent);
            }
            else if (item instanceof String) {
                if (((String) item).contains(getString(R.string.donate_to_aub))) {
                    Intent intent = new Intent(getActivity(), QrCodeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), ((String) item), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(
                Presenter.ViewHolder itemViewHolder,
                Object item,
                RowPresenter.ViewHolder rowViewHolder,
                Row row) {
            if (item instanceof Video) {
                // Update the app background with the video card image
                updateBackground(((Video) item).getCardUrl());
            }
            if (item instanceof Powerpoint) {
                // Update the app background with the video card image
                updateBackground(((Powerpoint) item).getCardUrl());
            }
        }
    }

    // todo: here we can add rows
    private void loadRows() {
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        // region calendar
        HeaderItem cardPresenterHeader_0 = new HeaderItem(0, "CALENDAR");
        CardPresenter cardPresenter_0 = new CardPresenter();
        ArrayObjectAdapter cardRowAdapter_0 = new ArrayObjectAdapter(cardPresenter_0);

        Calendar calendar = new Calendar();
        cardRowAdapter_0.add(calendar);
        mRowsAdapter.add(new ListRow(cardPresenterHeader_0, cardRowAdapter_0));
        // endregion

        // region add rows
        try {
            JSONArray categories = metadata.names();
            for(int i = 0; i < categories.length(); i++) {
                String category = categories.get(i).toString();
                HeaderItem cardPresenterHeader = new HeaderItem(i, category);
                CardPresenter cardPresenter = new CardPresenter();
                ArrayObjectAdapter cardRowAdapter = new ArrayObjectAdapter(cardPresenter);

                JSONArray items = metadata.getJSONArray(category);
                for (int j = 0; j < items.length(); j++) {
                    try {
                        JSONObject item = items.optJSONObject(j);
                        Log.i("JSON:", item.toString());
                        String itemType = item.getString("type");
                        Log.i("Type:", itemType);
                        DisplayObject itemParsed = setItemSpecifications(item);
                        if (itemParsed != null) {
                            cardRowAdapter.add(itemParsed);
                        }
                    } catch (Exception e) {
                        Log.i("ERROR", e.getMessage());
                    }
                }
                mRowsAdapter.add(new ListRow(cardPresenterHeader, cardRowAdapter));

            }
        } catch (Exception e) {
            Log.i("Error:", e.getMessage());
        }
        setAdapter(mRowsAdapter);
        // endregion

        // region donate
        HeaderItem donateHeader = new HeaderItem(0,getString(R.string.donate_to_aub));
        CardPresenter donateCardPresenter = new CardPresenter();
        ArrayObjectAdapter donateArrayObjectAdapter = new ArrayObjectAdapter(donateCardPresenter);
        donateArrayObjectAdapter.add(getString(R.string.donate_to_aub));
        mRowsAdapter.add(new ListRow(donateHeader , donateArrayObjectAdapter));
        // endregion
    }

    private void prepareBackgroundManager() {

        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());

        mDefaultBackground = ContextCompat.getDrawable(getActivity(), R.drawable.default_background);
        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    private void updateBackground(String uri) {
        int width = mMetrics.widthPixels;
        int height = mMetrics.heightPixels;

        Glide.with(getActivity())
                .load(uri)
                .centerCrop()
                .error(mDefaultBackground) // on error: set default background
                .into(new  SimpleTarget<GlideDrawable>(width, height) {
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable>
                                                        glideAnimation) {
                        // update the background
                        mBackgroundManager.setDrawable(resource);
                        // darken the background
                        resource.setColorFilter(Color.rgb(77, 77, 77), android.graphics.PorterDuff.Mode.MULTIPLY);
                    }
                });
    }

    private void FetchDataFromApi() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        // Add a spinner
        SpinnerFragment mSpinnerFragment = new SpinnerFragment();
        getFragmentManager().beginTransaction().add(R.id.main_browse_fragment, mSpinnerFragment).commit();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getResources().getString(R.string.api) + "/displayObject/getlist.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            Log.i("response: ", response);

                            metadata = new JSONObject(response);

                            prepareBackgroundManager();

                            setupUIElements();

                            loadRows();

                            // remove the spinner
                            getFragmentManager().beginTransaction().remove(mSpinnerFragment).commit();

                        } catch (JSONException e) {
                            // remove the spinner
                            getFragmentManager().beginTransaction().remove(mSpinnerFragment).commit();
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.i("Error", e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error", error.getMessage());
                getFragmentManager().beginTransaction().remove(mSpinnerFragment).commit();
                Toast.makeText(getActivity(), "Failed to get data", Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private DisplayObject setItemSpecifications(JSONObject item) {
        try {
            String itemType = item.getString("type");
            Log.i("Type", itemType);
            if (itemType.toUpperCase().equals("VIDEO")) {
                String videoTitle = item.getString("title");
                String videoDescription = item.getString("description");
                String videoYouTubeID = item.getString("youtubeId");
                String cardUrl = item.getString("imageUrls");
                return new Video(videoTitle, "Video | " + videoDescription, videoYouTubeID, cardUrl);
            }
            else if (itemType.toUpperCase().equals("SLIDESHOW")) {
                String showTitle = item.getString("title");
                String showDescription = item.getString("description");
                String[] showUrls = item.getString("imageUrls").split(";");
                return new Powerpoint(showTitle, "Slideshow | " + showDescription, showUrls);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
