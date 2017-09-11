package giphboxhq.com.giphybox;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

import giphboxhq.com.giphybox.Main.GifViewAdapter;
import giphboxhq.com.giphybox.net.models.Gif;

/**
 * Created by Owner on 2017-09-10.
 */

@Layout(R.layout.gifs_card_view)
public class GifCardHelper {
    private static final String TAG = "GifCardHelper";

    @View(R.id.gifs_card_view_gif_holder)
    private ImageView gifView;

    @View(R.id.gifs_card_view_rating)
    private TextView rating;

    private Gif gif;
    private Context context;
    private SwipePlaceHolderView swipeableView;
    private SwipeListener listener;

    public GifCardHelper(Gif gif, Context context, SwipePlaceHolderView swipeableView) {
        this.gif = gif;
        this.context = context;
        this.swipeableView = swipeableView;

        try {
            listener = ((SwipeListener) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement SwipeListener");
        }
    }

    @Resolve
    private void onResolved(){
        Glide.with(context)
                .load(gif.images.get("downsized").url)
                .into(new GlideDrawableImageViewTarget(gifView){
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        listener.hideLoading();
                    }
                });

        if(gif.ratingCount != 0){
            rating.setVisibility(android.view.View.VISIBLE);
            rating.setText("Rating: " + gif.ratingCount);
        }


    }

    @SwipeOut
    private void onSwipedOut(){
        Log.d("EVENT", "onSwipedOut");
        listener.downvoteGif();
        swipeableView.addView(this);
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn(){
        Log.d("EVENT", "onSwipedIn");
        listener.upvoteGif();
        swipeableView.addView(this);
    }

    @SwipeInState
    private void onSwipeInState(){
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d("EVENT", "onSwipeOutState");
    }

    public void setRating(int rating){
        this.rating.setText("Rating: " + rating);
    }

    public interface SwipeListener{
        void downvoteGif();
        void upvoteGif();
        void hideLoading();
    }
}
