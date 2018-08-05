package oneshoppoint.com.ishop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardGridView;

public class ProductDetail extends AppCompatActivity {
    protected ScrollView mScrollView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Items> itemsList = new ArrayList<>();
    JSONObject res = null;
    private static final int ITEM_COUNT = 100;
    ImageLoader imageLoader;
    private List<Object> mContentItems = new ArrayList<>();
    protected String id;
    public static int i;

    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getIntent().getStringExtra("product_name") + "");
        id = getIntent().getStringExtra("id");
        bt = (Button) findViewById(R.id.checkout);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), GetLocation.class);
                startActivity(intent);
            }
        });
        GetData();
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public class GplayGridCard extends Card {

        protected TextView mTitle;
        protected TextView mSecondaryTitle;
        protected RatingBar mRatingBar;
        protected int resourceIdThumbnail = -1;
        protected int count;
        protected String url;

        protected String headerTitle;
        protected String secondaryTitle;
        protected String id;
        protected float rating;

        public GplayGridCard(Context context) {
            super(context, R.layout.inner_content_detail);
        }


        public GplayGridCard(Context context, int innerLayout) {
            super(context, innerLayout);
        }

        private void init() {
//
//            UniversalCardThumbnail cardThumbnail = new UniversalCardThumbnail(mContext);
//            cardThumbnail.setExternalUsage(true);
//            addCardThumbnail(cardThumbnail);

            CardHeader header = new CardHeader(getContext());
            header.setButtonOverflowVisible(true);
            header.setTitle(headerTitle);
            header.setPopupMenu(R.menu.cart, new CardHeader.OnClickCardHeaderPopupMenuListener() {
                @Override
                public void onMenuItemClick(BaseCard card, MenuItem item) {
                    String value = getDefaults("start", getBaseContext());
                    MyShortcuts.showToast("Added to cart!", getBaseContext());
                    if (MyShortcuts.checkDefaults("start", getBaseContext())) {

                        try {
                            String res = getDefaults("cartArray", getBaseContext());
                            JSONArray myCart = new JSONArray(res);
                            JSONObject items = new JSONObject();
                            items.put("uuid", card.getId());
                            items.put("quantity", 1);
                            items.put("medical", false);
                            myCart.put(items);
                            setDefaults("cartArray", myCart.toString(), getContext());
                            Log.d("cartArray", myCart.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        setDefaults("start", "true", getContext());
                        JSONArray js = new JSONArray();
                        try {
                            JSONObject items = new JSONObject();
                            items.put("uuid", card.getId());
                            items.put("quantity", 1);
                            js.put(items);
                            setDefaults("cartArray", js.toString(), getContext());
                            Log.d("cartArrayfirst", js.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                }
            });

            addCardHeader(header);
            Log.e("URL", url);


//            NetworkImageView thumbnail1 =(NetworkImageView) getActivity().findViewById(R.id.card_thumbnail_image);
//            thumbnail1.setImageUrl(url,imageLoader);
//
//            GplayGridThumb thumbnail = new GplayGridThumb(getContext());
//            thumbnail.setUrlResource(url);
//            if (resourceIdThumbnail > -1)
//                thumbnail.setDrawableResource(resourceIdThumbnail);
//            else
//                thumbnail.setDrawableResource(R.drawable.ic_launcher);
//            addCardThumbnail(thumbnail);

            setOnClickListener(new OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {

                    boolean duplicates = false;
                    int quantity = 1;
                    MyShortcuts.showToast("Added to cart!", getBaseContext());
                    int j, num;
                    int m = 0;
                    String q;
                    String value = getDefaults("start", getBaseContext());
                    if (MyShortcuts.checkDefaults("start", getBaseContext())) {
                        try {
                            String res = getDefaults("cartArray", getBaseContext());
                            JSONArray myCart = new JSONArray(res);
                            JSONObject item = new JSONObject();
                            item.put("uuid", card.getId());
                            item.put("quantity", 1);
                            item.put("medical", false);
                            myCart.put(item);
                            setDefaults("cartArray", myCart.toString(), getContext());
                            Log.d("cartArray", myCart.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        setDefaults("start", "true", getContext());
                        JSONArray js = new JSONArray();
                        try {
                            JSONObject item = new JSONObject();
                            item.put("uuid", card.getId());
                            item.put("quantity", 1);
                            item.put("medical", false);
                            js.put(item);
                            setDefaults("cartArray", js.toString(), getContext());
                            Log.d("cartArrayfirst", js.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }



                     /* for (int i=0;i<myCart.length();i++){
                                JSONObject a = myCart.getJSONObject(i);
                                if (a.getString("uuid").equals(card.getId())){
                                    alreadyAdded=true;
                                    q=a.getString("quantity");
                                    num = Integer.parseInt(q);
                                }
                            }*/

                    /*for (int k = 0; k <= i; k++) {
                        if (getDefaults("ID" + k, getContext()) == selected) {
                            int num = 0;
                            String qu = getDefaults("quantity" + k, getBaseContext());
//                    qu=qu.substring(5);
                            Log.e("Quantity is", qu);
                            Log.e("ID NUMBER", selected);
                            num = Integer.parseInt(qu);
                            num = num + 1;
                            duplicates = true;
                            String q;
                            q = num + "";
                            setDefaults("quantity" + k, q, getBaseContext());
                            Log.e("quantity added is", q);
                            Log.e("card Title duplicate is", card.getTitle());
                            Toast.makeText(getBaseContext(), "You have added another " + getDefaults("name" + k, getContext()), Toast.LENGTH_SHORT).show();

                        }
                    }
                    if (duplicates == false) {
                        int o = 1;
                        String g = o + "";
                        setDefaults("name" + i, card.getTitle(), getBaseContext());
                        setDefaults("quantity" + i, g, getBaseContext());
                        setDefaults("ID" + i, card.getId(), getBaseContext());
//                setDefaults("image" + i, selectedURL, getBaseContext());
                        Toast.makeText(getBaseContext(), card.getTitle() + " " + "has been added to the basket!", Toast.LENGTH_SHORT).show();

                        Log.e("Quantity unique", g);
                        Log.e("card Title is", card.getTitle());

                        i = i + 1;
                    }*/
                }
            });
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

            TextView title = (TextView) view.findViewById(R.id.carddemo_gplay_main_inner_title);
            title.setText("I-shop");

//            TextView subtitle = (TextView) view.findViewById(R.id.description);
//            subtitle.setText(secondaryTitle);
            TextView id1 = (TextView) view.findViewById(R.id.carddemo_gplay_main_inner_subtitle);
            id1.setText(secondaryTitle);
//            NetworkImageView thumbnail =(NetworkImageView)view.findViewById(R.id.card_thumbnail_image);
//            thumbnail.setImageUrl(url,imageLoader);


        }

        class GplayGridThumb extends CardThumbnail {

            public GplayGridThumb(Context context) {
                super(context);
            }

            @Override
            public void setupInnerViewElements(ViewGroup parent, View viewImage) {
                //viewImage.getLayoutParams().width = 196;
                //viewImage.getLayoutParams().height = 196;

            }
        }

    }


    private void GetData() {
        // Toast.makeText(getBaseContext(), "Inside function!", Toast.LENGTH_SHORT).show();
        // Tag used to cancel the request

        final JSONObject js = new JSONObject();
        try {
            JSONObject jsonobject_one = new JSONObject();

            jsonobject_one.put("", "");

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSONErrorin serializing", e.toString());
        }
        Log.e("JSON serializing", js.toString());
        String tag_string_req = "req_Categories";
        StringRequest strReq = new StringRequest(Request.Method.GET, "https://www.oneshoppoint.com/api/product?id=" + getIntent().getStringExtra("id"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response from server is", response.toString());
                bt.setVisibility(View.VISIBLE);
                String success = null;
                try {
                    JSONObject jObj = new JSONObject(response);
//                            res = jObj.getJSONArray("All");
                    //successfully gotten matatu data
//                        String regno = jObj.getString("regno");

                    JSONObject c = jObj.getJSONObject("data");

                    Log.e("result: ", response.toString());
                    ArrayList<Card> cards = new ArrayList<Card>();

                    // looping through All res
//                    for (int i = 0; i < res.length(); i++) {
//                        JSONObject c = res.getJSONObject(i);

                    // Storing each json item in variable

                    String name = c.getString("name");
                    String description = c.getString("description");
                    //                                children1 = c.getJSONArray("children");
                    Log.e("CategoryFragment", name);
                    Items items = new Items();
//                    items.setTitle(name);
//                    items.setTheID(c.getString("uuid"));
                    JSONObject a = c.getJSONObject("primaryImage");
                    String path = "https://www.oneshoppoint.com/images" + a.getString("path");
                    items.setThumbnailUrl("https://www.oneshoppoint.com/images" + a.getString("path"));
                    itemsList.add(items);


                    GplayGridCard card = new GplayGridCard(getBaseContext());

                    card.headerTitle = name + "                          Ksh. " + c.getString("price") + "";
                    card.secondaryTitle = c.getString("description");
                    card.id = c.getString("price");
                    card.setId(c.getString("uuid"));
                    card.url = path;
                    card.setTitle(c.getString("name"));
                    CardThumbnail thumb = new CardThumbnail(getBaseContext());

                    //Set URL resource
                    thumb.setUrlResource(path);

                    //Error Resource ID
                    thumb.setErrorResource(R.drawable.ic_launcher);

                    //Add thumbnail to a card
                    card.addCardThumbnail(thumb);
                    //Only for test, change some icons
//                                if ((i % 6 == 0)) {
//
//                                } else if ((i % 6 == 1)) {
//                                    card.resourceIdThumbnail = R.drawable.ic_launcher;
//                                } else if ((i % 6 == 2)) {
//                                    card.resourceIdThumbnail = R.drawable.ic_launcher;
//                                } else if ((i % 6 == 3)) {
//                                    card.resourceIdThumbnail = R.drawable.ic_launcher;
//                                } else if ((i % 6 == 4)) {
//                                    card.resourceIdThumbnail = R.drawable.ic_launcher;
//
                    card.init();
                    cards.add(card);


//
//                    }
//                    if (res.length() == 0) {
//                        Toast.makeText(getBaseContext(), "No products currently in this category,\n Explore other categories", Toast.LENGTH_LONG).show();
//                    }
                    CardGridArrayAdapter mCardArrayAdapter = new CardGridArrayAdapter(getBaseContext(), cards);

                    CardGridView listView = (CardGridView) findViewById(R.id.carddemo_grid_base1);
                    if (listView != null) {
                        listView.setAdapter(mCardArrayAdapter);
                    }


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

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
//                Log.e("category id", getIntent().getStringExtra("category_id"));
//                params.put("categoryId", 2 + "");


                return params;
            }

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
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        Log.e("request is", strReq.toString());
    }

    public void setDefaults(String key, String value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    private void storesignup() {
        // Toast.makeText(getBaseContext(), "Inside function!", Toast.LENGTH_SHORT).show();
        // Tag used to cancel the request

        JSONObject js = new JSONObject();
        try {
            JSONObject jsonobject_one = new JSONObject();
            jsonobject_one.put("quantity", "null");
            jsonobject_one.put("medical", true);
//            jsonobject_one.put("uuid", _uuid.getText().toString());
            JSONArray ja = new JSONArray();
            ja.put(jsonobject_one);
            js.put("cart", ja);
//            js.put("locationId", lID);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSONErrorin serializing", e.toString());
        }
        Log.e("JSON serializing", js.toString());
        String tag_string_req = "req_Categories";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, "http://www.oneshoppoint.com/api/checkout/", js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response from server is", response.toString());
                        try {
                            JSONArray data = response.getJSONArray("data");
                            MyShortcuts.setDefaults("locationId", "", getBaseContext());
                            MyShortcuts.setDefaults("uuid", "", getBaseContext());
                            Intent intent = new Intent(getBaseContext(), Retailers.class);
                            intent.putExtra("retailers", response.toString());
                            startActivity(intent);


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
