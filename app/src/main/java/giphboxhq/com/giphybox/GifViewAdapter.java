package giphboxhq.com.giphybox;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import giphboxhq.com.giphybox.net.models.Gif;

/**
 * Created by Owner on 2017-09-06.
 */

public class GifViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "GifViewAdapter";
    private List<Gif> gifs;
    private Context context;

    public GifViewAdapter(List<Gif> gifs, Context context) {
        this.gifs = gifs;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gif_cell, null);
        return new GifViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Glide.with(context)
                .load(gifs.get(position).images.get("downsized_still").url)
                .into(((GifViewHolder)holder).gifImageView);


    }

    @Override
    public int getItemCount() {
        return gifs.size();
    }

    public class GifViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.gif_cell_image_view)
        ImageView gifImageView;
        public GifViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
