package giphboxhq.com.giphybox.Main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
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

    private StaggeredGridLayoutManager layoutManager;
    private Unbinder unbinder;
    private List<Gif> gifs;
    private GifViewAdapter adapter;

    @Inject
    MainPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        unbinder = ButterKnife.bind(this, view);

        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        gifs = new ArrayList<>();
        adapter = new GifViewAdapter(gifs, getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        setupSearchListener();

        ((MainActivity)getActivity()).setupMainComponent().inject(this);

        presenter.loadTrendingGifs();

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
                    if(searchBar.getText().toString().isEmpty()){
                        presenter.loadTrendingGifs();
                    }else{
                        presenter.loadSearch(searchBar.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });
    }
    @Override
    public void showGifs(List<Gif> gifs) {
        this.gifs.clear();
        this.gifs.addAll(gifs);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void showSearch(List<Gif> gifs) {
        this.gifs.clear();
        this.gifs.addAll(gifs);
        adapter.notifyDataSetChanged();
    }
}
