package oneshoppoint.com.ishop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by stephineosoro on 15/06/16.
 */

public class ShippingChoose extends AppCompatActivity {
    Button carrier, noCarrier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_choose);
        carrier = (Button) findViewById(R.id.carrier);
        carrier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetCarrier();
            }
        });
        noCarrier = (Button) findViewById(R.id.noCarrier);
        noCarrier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyShortcuts.setDefaults("carrierservice", "false", getBaseContext());
                Intent intent = new Intent(getBaseContext(), CustomerInfo.class);
                startActivity(intent);
            }
        });

    }

    private void GetCarrier() {
        // Toast.makeText(getBaseContext(), "Inside function!", Toast.LENGTH_SHORT).show();
        // Tag used to cancel the request
        String retailer = MyShortcuts.getDefaults("retailerid", getBaseContext());
        JSONObject js = new JSONObject();
        try {
            String cart = MyShortcuts.getDefaults("cartArray", getBaseContext());
            JSONArray ja = new JSONArray(cart);
            js.put("cart", ja);
            js.put("locationId", MyShortcuts.getDefaults("locationId", getBaseContext()));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSONErrorin serializing", e.toString());
        }
        Log.e("JSON serializing", js.toString());
        String tag_string_req = "req_Categories";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, "https://www.oneshoppoint.com/api/checkout/carrier?retailerId=" + retailer, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response from server is", response.toString());
                        try {
                            JSONArray data = response.getJSONArray("data");
                            if (data.length() > 0) {
                                MyShortcuts.setDefaults("carrierservice", "true", getBaseContext());
                                Intent intent = new Intent(getBaseContext(), Shipping.class);
                                intent.putExtra("carriers", response.toString());
                                startActivity(intent);
                            }else{
                                MyShortcuts.showToast("Sorry, Shipping services not available at the moment!",getBaseContext());
                                MyShortcuts.setDefaults("carrierservice", "false", getBaseContext());
                                Intent intent = new Intent(getBaseContext(),CustomerInfo.class);
                                startActivity(intent);
                            }
                            /*JSONArray data = response.getJSONArray("data");

                            for (int i=0;i<data.length();i++){
                                JSONObject c = data.getJSONObject(i);
                                JSONObject d= c.getJSONObject("carrier");
                                String paybill=d.getString("payBillNo");
                            }*/


                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(getBaseContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }


                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VolleyError", "Error: " + error.getMessage());
//                hideProgressDialog()
                Log.d("error volley", error.toString());
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                setRetryPolicy(new DefaultRetryPolicy(5 * DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 0));
                setRetryPolicy(new DefaultRetryPolicy(0, 0, 0));
                headers = MyShortcuts.AunthenticationHeaders(getBaseContext());
                return headers;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
        Log.e("request is", jsonObjReq.toString());
    }
}
