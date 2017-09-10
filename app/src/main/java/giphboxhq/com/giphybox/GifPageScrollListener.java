package giphboxhq.com.giphybox;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

/**
 * Created by Owner on 2017-09-10.
 */

public abstract class GifPageScrollListener extends RecyclerView.OnScrollListener {
    private static final String TAG = "GifPageScrollListener";
    private GridLayoutManager layoutManager;

    private int visibleThreshold = 2;
    private int currentPage = 0;
    private int prevTotalItemCount = 0;
    private boolean loading = true;
    private int startingPageIndex = 0;


    public GifPageScrollListener(GridLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int lastVisibleItemPosition = 0;
        int totalItemCount = layoutManager.getItemCount();
        lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

//        if(totalItemCount < prevTotalItemCount){
//            this.currentPage = this.startingPageIndex;
//            this.prevTotalItemCount = totalItemCount;
//            if(totalItemCount == 0){
//                this.loading = true;
//            }
//        }

        if(loading && (totalItemCount > prevTotalItemCount)){
            loading = false;
            prevTotalItemCount = totalItemCount;
        }

        if(!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount){
            currentPage += 10;
            loadMoreItems(currentPage, totalItemCount, recyclerView);
            loading = true;
        }

    }

    public void resetState(){
        this.currentPage = this.startingPageIndex;
        this.prevTotalItemCount = 0;
        this.loading = true;
    }

    protected abstract void loadMoreItems(int page, int totalItemCount, RecyclerView view);
}
