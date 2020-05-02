package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

public class CalendarActivity extends Activity {

    private CalendarView mCalendar;
    private TextView mEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        mCalendar = findViewById(R.id.calendar);
        mEvents = findViewById(R.id.calendar_events);

        mCalendar.setDate(System.currentTimeMillis());

        mCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int _month,
                                            int dayOfMonth) {
                int month = _month + 1;
                getDateEvent(year, month , dayOfMonth);
            }
        });
    }


    private void getDateEvent(int year, int month, int dayOfMonth) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        String monthString = (month >= 10)? String.valueOf(month): "0" + String.valueOf(month);
        String dayString = (dayOfMonth >= 10)? String.valueOf(dayOfMonth): "0" + String.valueOf(dayOfMonth);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                getResources().getString(R.string.api) + "/event/get.php?year=" + year + "&month=" + monthString + "&day=" + dayString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray data = jsonResponse.getJSONArray("data");
                            JSONObject event = data.optJSONObject(0);
                            String[] events = event.getString("details").split("///"); // several events are separated by ///

                            StringBuilder text = new StringBuilder();
                            for (String s : events) {
                                text.append(s).append("\n");
                            }
                            mEvents.setText(text.toString());

                        } catch (Exception e) {
                            mEvents.setText("No Event in " + dayOfMonth + "/" + month + "/" + year);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error", error.getMessage());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
