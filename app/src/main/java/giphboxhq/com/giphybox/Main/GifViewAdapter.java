package giphboxhq.com.giphybox.Main;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.transcode.BitmapToGlideDrawableTranscoder;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.sql.Time;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import giphboxhq.com.giphybox.R;
import giphboxhq.com.giphybox.net.models.Gif;

/**
 * Created by Owner on 2017-09-06.
 */

public class GifViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "GifViewAdapter";
    private List<Gif> gifs;
    private Context context;
    private GifViewHolderClickListener listener;

    public GifViewAdapter(List<Gif> gifs, Context context) {
        this.gifs = gifs;
        this.context = context;

        try {
            listener = ((GifViewHolderClickListener) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement GifViewHolderClickedListener");
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gif_cell, null);
        return new GifViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final GifViewHolder viewHolder = (GifViewHolder)holder;
        final Gif gif = gifs.get(position);
        loadGifToGlide(viewHolder.gifImageView, gif, true);
        viewHolder.gifImageView.setOnTouchListener(new View.OnTouchListener() {

            private Timer timer = new Timer();
            private int LONG_PRESS_TIMEOUT = 300;
            private boolean wasLong = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            wasLong = true;
                            loadGifToGlide(viewHolder.gifImageView, gif, false);
                        }
                    }, LONG_PRESS_TIMEOUT);
                    return true;
                }

                if(event.getAction() == MotionEvent.ACTION_UP){
                    timer.cancel();
                    if(wasLong){
                        loadGifToGlide(viewHolder.gifImageView, gif, true);
                        wasLong = false;
                    }else{
                        listener.onGifSelected(gifs.get(position));
                    }
                    timer = new Timer();
                    return true;
                }
                return false;
            }
        });

    }

    private void loadGifToGlide(final ImageView gifView, final Gif gif, final boolean asBitmap){
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String url = gif.images.get("downsized").url;
                int height = gif.images.get("downsized").height;
                int width = gif.images.get("downsized").width;
                if(asBitmap){
                    Glide.with(context)
                            .load(url)
                            .asBitmap()
                            .override(width, height)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(gifView);
                }else{
                    Glide.with(context)
                            .load(url)
                            .asGif()
                            .override(width, height)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(gifView);
                }
            }
        });
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

    public interface GifViewHolderClickListener{
        void onGifSelected(Gif gif);
    }

}