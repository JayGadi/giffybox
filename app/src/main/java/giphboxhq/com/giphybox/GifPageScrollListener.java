package giphboxhq.com.giphybox;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by Owner on 2017-09-10.
 */

public abstract class GifPageScrollListener extends RecyclerView.OnScrollListener {

    private StaggeredGridLayoutManager layoutManager;
    private LinearLayoutManager linearLayoutManager;

    public GifPageScrollListener(StaggeredGridLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int currentItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int currentItemPosition = layoutManager.findFirstVisibleItemPositions(new int[]);
        


    }
}
