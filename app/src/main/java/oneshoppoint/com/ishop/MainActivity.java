package oneshoppoint.com.ishop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {
    // more efficient than HashMap for mapping integers to objects
    SparseArray<Group> groups = new SparseArray<Group>();
    JSONArray res = null;
    private List<Items> itemsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
//        createData();
        GetData(this);


    }
//
//    public void createData() {
//        for (int j = 0; j < 5; j++) {
//            Group group = new Group("TestTestTestTest " + j);
//            for (int i = 0; i < 5; i++) {
//                group.children.add("Sub ItemTestTestTestTest" + i);
//            }
//            groups.append(j, group);
//        }
//    }

    private void GetData(final Activity context) {
        // Toast.makeText(getBaseContext(), "Inside function!", Toast.LENGTH_SHORT).show();
        // Tag used to cancel the request

        JSONObject js = new JSONObject();
        try {
            JSONObject jsonobject_one = new JSONObject();

            jsonobject_one.put("type", "event_and_offer");
            jsonobject_one.put("devicetype", "I");

            JSONObject jsonobject_TWO = new JSONObject();
            jsonobject_TWO.put("value", "event");
            JSONObject jsonobject = new JSONObject();

            jsonobject.put("requestinfo", jsonobject_TWO);
            jsonobject.put("request", jsonobject_one);


            js.put("data", jsonobject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSONErrorin serializing", e.toString());
        }
        Log.e("JSON serializing", js.toString());
        String tag_string_req = "req_Categories";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, "https://www.oneshoppoint.com/api/category/", js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response from server is", response.toString());


                        String success = null;
                        try {


                            //successfully gotten matatu data
//                        String regno = jObj.getString("regno");

                            res = response.getJSONArray("data");
                            JSONArray children1 = null;
                            Log.e("result: ", res.toString());

                            // looping through All res
                            for (int i = 0; i < res.length(); i++) {
                                JSONObject c = res.getJSONObject(i);

                                // Storing each json item in variable

                                String name = c.getString("name");
                                children1 = c.getJSONArray("children");
                                Log.e("CategoryFragment", name);
                                Items items = new Items();
                                JSONObject a = c.getJSONObject("image");
                                Group group = new Group(name,a.getString("path"));
                                for (int j = 0; j < children1.length(); j++) {
                                    JSONObject d = children1.getJSONObject(j);
                                    JSONObject e = d.getJSONObject("image");
                                    group.children.add(d.getString("name"));
                                    group.id.add(d.getString("id"));
                                    group.image.add(e.getString("path"));
                                    items.setTheID(d.getString("id"));
                                }
                                groups.append(i, group);

                                ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);
                                HomeAdapter adapter = new HomeAdapter(context, groups);
                                listView.setAdapter(adapter);
//
//                                String sacco = c.getString("sacco");
//                                String question = c.getString("question");
//                                items.setTitle("winner sacco: " + sacco);
//                                items.setDescription("Best in: " + question);
//                            items.setPrice("Total Rating is: " + rate);
//                                    itemsList.add(items);
//
                            }
//
//                                adapter = new ViewHighestAdapter(getBaseContext(), itemsList);
//                                recyclerView.setAdapter(adapter);


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
//                hideProgressDialog();
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
        Log.e("request is", jsonObjReq.toString());
    }

}