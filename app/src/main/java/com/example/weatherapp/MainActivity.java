package com.example.weatherapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private EditText mCityName;
    private TextView mDisplayData, mTempDisplay, mHumidityDisplay;

    private final String mBaseUrl1 = "https://api.openweathermap.org/data/2.5/weather?q=";
    private final String mBaseUrl2 = "&appid=22e15fdfa4ac56b868661c763df46443";
    private String mFinalUrl;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.idGoGoGo);
        mCityName = findViewById(R.id.idEnterCity);
        mDisplayData = findViewById(R.id.idClimateType);
        mTempDisplay = findViewById(R.id.idTemperature);
        mHumidityDisplay = findViewById(R.id.idHUmidity);

        requestQueue = MySingletonClass.getInstance(this).getmRequestQueue();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    if (mCityName == null) {
                        Toast.makeText(MainActivity.this, "Please enter city name.", Toast.LENGTH_SHORT).show();
                    } else {
                        mFinalUrl = mBaseUrl1 + mCityName.getText().toString() + mBaseUrl2;
                        sendApiRequest();
                    }
                }
            }
        });
    }

    public void sendApiRequest() {
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, mFinalUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String climateType = response.getString("weather");
                            JSONArray jsonArray = new JSONArray(climateType);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String res = jsonObject.getString("main");//description wala

                            String main = response.getString("main");
                            JSONObject myJsonObject = new JSONObject(main);
                            String temperature = myJsonObject.getString("temp");
                            String humidity = myJsonObject.getString("humidity");
                            double celsius = Double.parseDouble(temperature) - 273.15;
                            double roundOff = Math.round(celsius * 100) / 100;
                            mTempDisplay.setText(roundOff + "°C");
                            mHumidityDisplay.setText(humidity + "g/m3");
                            mDisplayData.setText(res);
                            //Toast.makeText(MainActivity.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something went wrong !!", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);
    }
}
