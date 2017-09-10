package giphboxhq.com.giphybox.Main;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import giphboxhq.com.giphybox.GifPageScrollListener;
import giphboxhq.com.giphybox.GiphyBoxApplication;
import giphboxhq.com.giphybox.R;
import giphboxhq.com.giphybox.net.GifRepository;
import giphboxhq.com.giphybox.net.models.Data;
import giphboxhq.com.giphybox.net.models.Gif;
import rx.Subscriber;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment implements ExploreView {
    private static final String TAG = "ExploreFragment";
    @BindView(R.id.fragment_explore_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_explorer_search_bar)
    EditText searchBar;
    @BindView(R.id.fragment_explore_loader)
    ProgressBar loader;
    @BindView(R.id.fragment_explore_logout)
    ImageView logout;

    private StaggeredGridLayoutManager layoutManager;
    private Unbinder unbinder;
    private List<Gif> gifs;
    private GifViewAdapter adapter;
    private GifPageScrollListener scrollListener;
    private boolean isSearching = false;

    @Inject
    MainPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        unbinder = ButterKnife.bind(this, view);

        layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        gifs = new ArrayList<>();
        adapter = new GifViewAdapter(gifs, getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        scrollListener = new GifPageScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems(int page, int totalItemCount, RecyclerView view) {
                if(page < 250) {
                    if(!isSearching()) {
                        presenter.loadNextTrendingGifsPage(page);
                    }else{
                        presenter.loadNextSearchPage(searchBar.getText().toString(), page);
                    }
                }
            }
        };

        recyclerView.addOnScrollListener(scrollListener);

        addOverflowMenu();
        setupSearchListener();

        ((MainActivity)getActivity()).setupMainComponent().inject(this);

        presenter.loadFirstTrendingGifsPage();

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public boolean isSearching() {
        return isSearching;
    }

    public void setSearching(boolean searching) {
        isSearching = searching;
    }

    @Override
    public void showLoading() {
        loader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loader.setVisibility(View.GONE);
    }

    private void setupSearchListener(){
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(v != null){
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                        if(searchBar.getText().toString().isEmpty()){
                            setSearching(false);
                            presenter.loadFirstTrendingGifsPage();
                        }else{
                            setSearching(true);
                            presenter.loadFirstSearchPage(v.getText().toString());
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }
    @Override
    public void showGifs(List<Gif> gifs) {
        this.gifs.addAll(gifs);
        adapter.notifyItemRangeInserted(adapter.getItemCount(), gifs.size() - 1);
    }

    @Override
    public void resetScrollListener() {
        this.gifs.clear();
        adapter.notifyDataSetChanged();
        scrollListener.resetState();
    }

    @Override
    public void showSearch(List<Gif> gifs) {
        this.gifs.addAll(gifs);
        adapter.notifyItemRangeInserted(adapter.getItemCount(), gifs.size() - 1);
    }

    @Override
    public void recreateActivity() {
        ((MainActivity)getActivity()).launchLoginActivity();
    }

    private void addOverflowMenu(){
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.logout_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        switch (id){
                            case R.id.action_logout:
                                presenter.logout();
                                return true;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }
}
