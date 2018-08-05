package oneshoppoint.com.ishop;

/**
 * Created by stephineosoro on 19/05/16.
 */

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import oneshoppoint.com.ishop.fragment.CategoryFragment;
import oneshoppoint.com.ishop.fragment.HomeFragment;
import oneshoppoint.com.ishop.fragment.ProductsFragment;
import oneshoppoint.com.ishop.fragment.RecyclerViewFragment;


public class HomePage extends AppCompatActivity  {

    private static final String TAG = HomePage.class.getSimpleName();


    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newhomerate);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
     setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_fa_home);

        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // handle the menu item
                        return true;
                    }
                }
        );

        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setTitle("Brands Plus");

        initViewPagerAndTabs();


    }

    private void initViewPagerAndTabs() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager1);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
//        pagerAdapter.addFragment(new HomeFragment(), "Test");
        pagerAdapter.addFragment(new CategoryFragment(), "Categories");
//        pagerAdapter.addFragment(new ProductsFragment(), "Products");

//        pagerAdapter.addFragment(new PostCommentFragment(), "Comments");
//        pagerAdapter.addFragment(new ViewHighestFragment(), "Winners");
        try {
            viewPager.setAdapter(pagerAdapter);
        } catch (NullPointerException e) {
            Log.e("NUllPOINTER", e.toString());
        }


        TabLayout tabLayout = (TabLayout) findViewById(R.id.movieTabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




}

