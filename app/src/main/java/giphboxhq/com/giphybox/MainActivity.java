package giphboxhq.com.giphybox;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import giphboxhq.com.giphybox.net.GifRepository;
import giphboxhq.com.giphybox.net.GiphyBoxRestAPI;
import giphboxhq.com.giphybox.net.models.Data;
import giphboxhq.com.giphybox.net.models.Gif;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.activity_main_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.activity_main_view_pager)
    ViewPager viewPager;

    private GiphyPagerAdapter adapter;

    @Inject
    GifRepository repo;
    @Inject
    GiphyBoxRestAPI restApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: MainActivity");
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        setupPageChangeListener();

        ((GiphyBoxApplication)getApplication()).getNetComponent().inject(this);

        repo.getTrendingGifs().subscribe(new Subscriber<Data>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e );
            }

            @Override
            public void onNext(Data data) {
                Log.e(TAG, "onNext: " + data.data.size() );
            }
        });



    }


    private void setupViewPager(){
        adapter = new GiphyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ExploreFragment(), "EXPLORE");
        adapter.addFragment(new TrendingFragment(), "HOT");
        adapter.addFragment(new SavedFragment(), "SAVED");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons(){
        TextView exploreTab = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_text_view, null);
        exploreTab.setText("EXPLORE");
        exploreTab.setTextColor(getResources().getColor(R.color.colorAccent));
        exploreTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_search_selected, 0, 0);
        exploreTab.setGravity(Gravity.CENTER);
        tabLayout.getTabAt(0).setCustomView(exploreTab);


        TextView trendingTab = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_text_view, null);
        trendingTab.setText("HOT");
        trendingTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_whatshot, 0, 0);
        tabLayout.getTabAt(1).setCustomView(trendingTab);
        trendingTab.setGravity(Gravity.CENTER);

        TextView savedTab = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_text_view, null);
        savedTab.setText("SAVED");
        savedTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite, 0, 0);
        tabLayout.getTabAt(2).setCustomView(savedTab);
        savedTab.setGravity(Gravity.CENTER);
    }

    private void setActiveTabIcon(int tab) {

        TextView exploreTab = (TextView) tabLayout.getTabAt(0).getCustomView();
        TextView trendingTab = (TextView) tabLayout.getTabAt(1).getCustomView();
        TextView savedTab = (TextView) tabLayout.getTabAt(2).getCustomView();

        switch (tab) {
            case 0:
                exploreTab.setTextColor(getResources().getColor(R.color.colorAccent));
                trendingTab.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                savedTab.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                exploreTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_search_selected, 0, 0);
                trendingTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_whatshot, 0, 0);
                savedTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite, 0, 0);
                break;
            case 1:
                exploreTab.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                trendingTab.setTextColor(getResources().getColor(R.color.colorAccent));
                savedTab.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                exploreTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_search, 0, 0);
                savedTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite, 0, 0);
                trendingTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_whatshot_selected, 0, 0);
                break;
            case 2:
                exploreTab.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                trendingTab.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                savedTab.setTextColor(getResources().getColor(R.color.colorAccent));
                exploreTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_search, 0, 0);
                trendingTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_whatshot, 0, 0);
                savedTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite_selected, 0, 0);
                break;
        }
    }

    private void setupPageChangeListener(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setActiveTabIcon(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private class GiphyPagerAdapter extends FragmentStatePagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();


        public GiphyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}
