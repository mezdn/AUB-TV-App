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

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                getResources().getString(R.string.api) + "/api/Events/" + year + "/" + month + "/" + dayOfMonth,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            mEvents.setText(response);

                        } catch (Exception e) {
                            Log.i("Error", e.getMessage());
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
