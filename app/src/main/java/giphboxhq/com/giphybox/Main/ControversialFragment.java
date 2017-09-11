package giphboxhq.com.giphybox.Main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import giphboxhq.com.giphybox.R;
import giphboxhq.com.giphybox.net.models.Gif;

/**
 * A simple {@link Fragment} subclass.
 */
public class ControversialFragment extends Fragment implements ControversialView{

    @BindView(R.id.fragment_controversial_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_controversial_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

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
        View view = inflater.inflate(R.layout.fragment_controversial, container, false);

        unbinder = ButterKnife.bind(this, view);

        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        gifs = new ArrayList<>();
        adapter = new GifViewAdapter(gifs, getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        ((MainActivity)getActivity()).setupMainComponent().inject(this);

        presenter.getDownvotedGifs();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getDownvotedGifs();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void loadRatedGifs(List<Gif> savedGifs) {
        swipeRefreshLayout.setRefreshing(false);
        gifs.clear();
        gifs.addAll(savedGifs);
        adapter.notifyDataSetChanged();
    }

}
