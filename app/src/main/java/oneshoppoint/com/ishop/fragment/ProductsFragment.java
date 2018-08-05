package oneshoppoint.com.ishop.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardGridView;
import oneshoppoint.com.ishop.AppController;
import oneshoppoint.com.ishop.CategoryAdapter;
import oneshoppoint.com.ishop.Items;
import oneshoppoint.com.ishop.R;
import oneshoppoint.com.ishop.TestRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class ProductsFragment extends Fragment {
    protected ScrollView mScrollView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Items> itemsList = new ArrayList<>();
    JSONArray res = null;
    private static final int ITEM_COUNT = 100;
    ImageLoader imageLoader;
    private List<Object> mContentItems = new ArrayList<>();

    public static ProductsFragment newInstance() {
        return new ProductsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_grid_gplay, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageLoader = AppController.getInstance().getImageLoader();
//        initCards();
        GetData();
/*        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewMaterialAdapter(new TestRecyclerViewAdapter(mContentItems));
        mRecyclerView.setAdapter(mAdapter);

        {
            for (int i = 0; i < ITEM_COUNT; ++i)
                mContentItems.add(new Object());
            mAdapter.notifyDataSetChanged();
        }
        GetData();
        Mat*//*erialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);*/
    }


    private void initCards() {

        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 200; i++) {
            GplayGridCard card = new GplayGridCard(getActivity());

            card.headerTitle = "App example " + i;
            card.secondaryTitle = "Some text here " + i;
            card.rating = (float) (Math.random() * (5.0));

            //Only for test, change some icons
            if ((i % 6 == 0)) {
                card.resourceIdThumbnail = R.drawable.ic_launcher;
            } else if ((i % 6 == 1)) {
                card.resourceIdThumbnail = R.drawable.ic_launcher;
            } else if ((i % 6 == 2)) {
                card.resourceIdThumbnail = R.drawable.ic_launcher;
            } else if ((i % 6 == 3)) {
                card.resourceIdThumbnail = R.drawable.ic_launcher;
            } else if ((i % 6 == 4)) {
                card.resourceIdThumbnail = R.drawable.ic_launcher;
            }

            card.init();
            cards.add(card);

        }

        CardGridArrayAdapter mCardArrayAdapter = new CardGridArrayAdapter(getActivity(), cards);

        CardGridView listView = (CardGridView) getActivity().findViewById(R.id.carddemo_grid_base1);
        if (listView != null) {
            listView.setAdapter(mCardArrayAdapter);
        }
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
        protected float rating;

        public GplayGridCard(Context context) {
            super(context, R.layout.inner_content);
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
            header.setPopupMenu(R.menu.popupmain, new CardHeader.OnClickCardHeaderPopupMenuListener() {
                @Override
                public void onMenuItemClick(BaseCard card, MenuItem item) {
                    Toast.makeText(getContext(), "Item " + item.getTitle(), Toast.LENGTH_SHORT).show();

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
                    //Do something
                }
            });
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

            TextView title = (TextView) view.findViewById(R.id.carddemo_gplay_main_inner_title);
            title.setText("I-shop");

            TextView subtitle = (TextView) view.findViewById(R.id.carddemo_gplay_main_inner_subtitle);
            subtitle.setText(secondaryTitle);
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

//            Log.e("CategoryId", getActivity().getIntent().getStringExtra("category_id"));
            js.put("categoryId", getActivity().getIntent().getStringExtra("category_id"));

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSONErrorin serializing", e.toString());
        }
        Log.e("JSON serializing", js.toString());
        String tag_string_req = "req_Categories";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, "https://www.oneshoppoint.com/api/product/", js,
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
                            ArrayList<Card> cards = new ArrayList<Card>();

                            // looping through All res
                            for (int i = 0; i < res.length(); i++) {
                                JSONObject c = res.getJSONObject(i);

                                // Storing each json item in variable

                                String name = c.getString("name");
                                String description = c.getString("description");
                                //                                children1 = c.getJSONArray("children");
                                Log.e("CategoryFragment", name);
                                Items items = new Items();
                                items.setTitle(name);
                                items.setTheID(c.getString("id"));
                                JSONObject a = c.getJSONObject("primaryImage");
                                String path = "https://www.oneshoppoint.com/images" + a.getString("path");
                                items.setThumbnailUrl("https://www.oneshoppoint.com/images" + a.getString("path"));
                                itemsList.add(items);


                                GplayGridCard card = new GplayGridCard(getActivity());

                                card.headerTitle = name;
                                card.secondaryTitle = c.getString("price");
                                card.url = path;
                                CardThumbnail thumb = new CardThumbnail(getActivity());

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
//                                }

                                card.init();
                                cards.add(card);


//
                            }

                            CardGridArrayAdapter mCardArrayAdapter = new CardGridArrayAdapter(getActivity(), cards);

                            CardGridView listView = (CardGridView) getActivity().findViewById(R.id.carddemo_grid_base1);
                            if (listView != null) {
                                listView.setAdapter(mCardArrayAdapter);
                            }


                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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


/*

    class UniversalCardThumbnail extends CardThumbnail {

        public UniversalCardThumbnail(Context context) {
            super(context);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View viewImage) {

            */
/**//*

             * If your cardthumbnail uses external library you have to provide how to load the image.
             * If your cardthumbnail doesn't use an external library it will use a built-in method


            //It is just an example.
            //In real case you should config better the imageLoader
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));


            //Here you have to set your image with an external library
            //Only for test, use a Resource Id and a Url
//            if (((UniversalImageLoaderCard) getParentCard()).getCount() % 2 == 0) {
                imageLoader.displayImage("https://lh5.googleusercontent.com/-squZd7FxR8Q/UyN5UrsfkqI/AAAAAAAAbAo/VoDHSYAhC_E/s96/new%2520profile%2520%25282%2529.jpg", (ImageView) viewImage,options);
//            } else {
//                imageLoader.displayImage("drawable://" + R.drawable.ic_tris, (ImageView) viewImage,options);
//            }


            viewImage.getLayoutParams().width = 96;
            viewImage.getLayoutParams().height = 96;

        }
    }
*/


}


//            Card card = new Card(getActivity());
//            CardHeader header = new CardHeader(getContext());
//            header.setTitle("Osoro");
//            card.addCardHeader(header);
////            GplayGridCard card = new GplayGridCard(getActivity());
////
////            card.headerTitle = "App example " + i;
////            card.secondaryTitle = "Some text here " + i;
////            card.rating = (float) (Math.random() * (5.0));
////
////            //Only for test, change some icons
////            if ((i % 6 == 0)) {
////                card.resourceIdThumbnail = R.drawable.ic_ic_dh_bat;
////            } else if ((i % 6 == 1)) {
////                card.resourceIdThumbnail = R.drawable.ic_ic_dh_net;
////            } else if ((i % 6 == 2)) {
////                card.resourceIdThumbnail = R.drawable.ic_tris;
////            } else if ((i % 6 == 3)) {
////                card.resourceIdThumbnail = R.drawable.ic_info;
////            } else if ((i % 6 == 4)) {
////                card.resourceIdThumbnail = R.drawable.ic_smile;
////            }
////
////            card.init();
//            cards.add(card);