package suriyon.cs.ubru.covid19reportth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Covid19ReportTH extends AppCompatActivity {
    private TextView tvConfirmed, tvRecovered, tvHospital, tvDeath, tvNew, tvUpdate;
    private String url = "https://covid19.ddc.moph.go.th/api/Cases/today-cases-all";

    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid19_report);

        matchView();
//        readData();
    }

    private void readData() {
        JsonArrayRequest request = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject object;
                        DecimalFormat df = new DecimalFormat("###,###");

                        if(response.length() == 1) {
                            try {
                                object = (JSONObject) response.get(0);
                                int newCase = object.getInt("new_case");
                                int totalCase = object.getInt("total_case");
                                String update = object.getString("txn_date");
//                                Log.d("New Case", String.valueOf(totalCase));
                                tvConfirmed.setText(df.format(totalCase));
                                tvNew.setText(df.format(newCase));

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date newDate = sdf.parse(update);
                                sdf = new SimpleDateFormat("MMMM dd, yyyy");
                                update = sdf.format(newDate);

                                tvUpdate.setText("Last Update: " + update);
//                                Log.d("Update Date", update);

                            } catch (JSONException | ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error Message", error.getMessage());
                    }
                }
        );
        queue = Volley.newRequestQueue(Covid19ReportTH.this);
        queue.add(request);
    }

    private void matchView() {
        tvConfirmed = findViewById(R.id.tv_confirmed_number);
        tvRecovered = findViewById(R.id.tv_recovered_number);
        tvHospital = findViewById(R.id.tv_hospital_number);
        tvDeath = findViewById(R.id.tv_death_number);
        tvNew = findViewById(R.id.tv_new_number);
        tvUpdate = findViewById(R.id.tv_update);
    }

//    private void readData() {
//        JsonObjectRequest request =
//                new JsonObjectRequest(
//                        Request.Method.GET,
//                        url,
//                        null,
//                        new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                try {
//                                    int newCase = response.getInt("new_case");
//                                    int total = response.getInt("total_case");
//                                    String update = response.getString("txn_date");
//                                    tvUpdate.setText(update);
//                                    tvConfirmed.setText(String.valueOf(total));
//                                    tvNew.setText(String.valueOf(newCase));
//
//
//                                    int death = response.getInt("Deaths");
//                                    int confirmed = response.getInt("Confirmed");
//                                    int recovered = response.getInt("Recovered");
//                                    int newConfirmed = response.getInt("NewConfirmed");
//                                    int hospital = response.getInt("Hospitalized");
//                                    String update = response.getString("UpdateDate");
//
//                                    DecimalFormat df = new DecimalFormat("###,###");
//                                    tvUpdate.setText("last update: " + update);
//                                    tvHospital.setText(df.format(hospital));
//                                    tvNew.setText(df.format(newConfirmed));
//                                    tvRecovered.setText(df.format(recovered));
//                                    tvDeath.setText(df.format(death));
//                                    tvConfirmed.setText(df.format(confirmed));
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Log.d("Error Message", error.getMessage());
//                            }
//                        }
//                );
//
//        queue = Volley.newRequestQueue(Covid19ReportTH.this);
//        queue.add(request);
//    }

}