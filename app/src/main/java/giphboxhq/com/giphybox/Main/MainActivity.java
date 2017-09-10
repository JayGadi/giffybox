package giphboxhq.com.giphybox.Main;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import giphboxhq.com.giphybox.GifInfo.GifInfoActivity;
import giphboxhq.com.giphybox.GiphyBoxApplication;
import giphboxhq.com.giphybox.Login.LoginActivity;
import giphboxhq.com.giphybox.R;
import giphboxhq.com.giphybox.net.GifRepository;
import giphboxhq.com.giphybox.net.GiphyBoxRestAPI;
import giphboxhq.com.giphybox.net.UserRepository;
import giphboxhq.com.giphybox.net.models.Data;
import giphboxhq.com.giphybox.net.models.Gif;
import rx.Subscriber;


public class MainActivity extends AppCompatActivity implements GifViewAdapter.GifViewHolderClickListener, MainView{
    private static final String TAG = "MainActivity";

    @BindView(R.id.activity_main_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.activity_main_view_pager)
    ViewPager viewPager;

    private GiphyPagerAdapter adapter;
    private ExploreFragment exploreFragment;
    private TrendingFragment trendingFragment;
    private ControversialFragment controversialFragment;
    private SavedFragment savedFragment;

    @Inject
    MainPresenter presenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: MainActivity");

        ButterKnife.bind(this);

        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        setupPageChangeListener();
        setupMainComponent().inject(this);

        presenter.onCreate();

    }

    public MainComponent setupMainComponent(){
        return DaggerMainComponent.builder()
                .userComponent(((GiphyBoxApplication)getApplication()).getUserComponent())
                .mainPresenterModule(new MainPresenterModule(exploreFragment, this, savedFragment, trendingFragment, controversialFragment))
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void launchLoginActivity(){
        Intent loginIntent = new Intent(this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(loginIntent, GiphyBoxApplication.LOGIN_REQUEST_CODE);
        overridePendingTransition(R.anim.fade_in, 0);


    }

    private void setupViewPager(){
        adapter = new GiphyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(exploreFragment = new ExploreFragment(), "EXPLORE");
        adapter.addFragment(trendingFragment = new TrendingFragment(), "HOT");
        adapter.addFragment(controversialFragment = new ControversialFragment(), "CONT");
        adapter.addFragment(savedFragment = new SavedFragment(), "SAVED");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons(){
        TextView exploreTab = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_text_view, null);
        exploreTab.setText(getString(R.string.explore_tab_title));
        exploreTab.setTextColor(getResources().getColor(R.color.colorAccent));
        exploreTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_search_selected, 0, 0);
        exploreTab.setGravity(Gravity.CENTER);
        tabLayout.getTabAt(0).setCustomView(exploreTab);


        TextView trendingTab = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_text_view, null);
        trendingTab.setText(getString(R.string.hot_tab_title));
        trendingTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_whatshot, 0, 0);
        trendingTab.setGravity(Gravity.CENTER);
        tabLayout.getTabAt(1).setCustomView(trendingTab);

        TextView controversialTab = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_text_view, null);
        controversialTab.setText(getString(R.string.controversial_tab_title));
        controversialTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_trending_down, 0, 0);
        controversialTab.setGravity(Gravity.CENTER);
        tabLayout.getTabAt(2).setCustomView(controversialTab);

        TextView savedTab = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_text_view, null);
        savedTab.setText(R.string.saved_tab_title);
        savedTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite, 0, 0);
        savedTab.setGravity(Gravity.CENTER);
        tabLayout.getTabAt(3).setCustomView(savedTab);
    }

    private void setActiveTabIcon(int tab) {

        TextView exploreTab = (TextView) tabLayout.getTabAt(0).getCustomView();
        TextView trendingTab = (TextView) tabLayout.getTabAt(1).getCustomView();
        TextView controversialTab = (TextView) tabLayout.getTabAt(2).getCustomView();
        TextView savedTab = (TextView) tabLayout.getTabAt(3).getCustomView();

        switch (tab) {
            case 0:
                exploreTab.setTextColor(getResources().getColor(R.color.colorAccent));
                trendingTab.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                controversialTab.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                savedTab.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                exploreTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_search_selected, 0, 0);
                trendingTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_whatshot, 0, 0);
                controversialTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_trending_down, 0, 0);
                savedTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite, 0, 0);
                break;
            case 1:
                exploreTab.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                trendingTab.setTextColor(getResources().getColor(R.color.colorAccent));
                controversialTab.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                savedTab.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                exploreTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_search, 0, 0);
                trendingTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_whatshot_selected, 0, 0);
                controversialTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_trending_down, 0, 0);
                savedTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite, 0, 0);
                break;
            case 2:
                exploreTab.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                trendingTab.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                controversialTab.setTextColor(getResources().getColor(R.color.colorAccent));
                savedTab.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                exploreTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_search, 0, 0);
                trendingTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_whatshot, 0, 0);
                controversialTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_trending_down_selected, 0, 0);
                savedTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite, 0, 0);
                break;
            case 3:
                exploreTab.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                trendingTab.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                controversialTab.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                savedTab.setTextColor(getResources().getColor(R.color.colorAccent));
                exploreTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_search, 0, 0);
                trendingTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_whatshot, 0, 0);
                controversialTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_trending_down, 0, 0);
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

    @Override
    public void launchGifInfoActivity(Gif gif) {
        Intent intent = new Intent(this, GifInfoActivity.class);
        intent.putExtra(GifInfoActivity.GIF_IMAGE_URL, gif.images.get("downsized").url);
        intent.putExtra(GifInfoActivity.GIF_IMAGE_ID, gif.id);
        startActivity(intent);
    }

    @Override
    public void onGifSelected(Gif gif) {
        presenter.onGifSelected(gif);
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
