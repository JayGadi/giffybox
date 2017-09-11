package giphboxhq.com.giphybox.GifInfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import giphboxhq.com.giphybox.GifCardHelper;
import giphboxhq.com.giphybox.GiphyBoxApplication;
import giphboxhq.com.giphybox.R;
import giphboxhq.com.giphybox.net.models.Gif;

public class GifInfoActivity extends AppCompatActivity implements GifInfoView, GifCardHelper.SwipeListener {
    private static final String TAG = "GifInfoActivity";
    public static final String GIF_IMAGE_URL = "gif_image_url";
    public static final String GIF_IMAGE_ID = "gif_image_id";

    @BindView(R.id.activity_gif_info_toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_gif_info_loader)
    ProgressBar loadIndicator;
    @BindView(R.id.activity_gif_info_downvote)
    ImageButton downvoteButton;
    @BindView(R.id.activity_gif_info_upvote)
    ImageButton upvoteButton;
    @BindView(R.id.activity_gif_info_save)
    ImageButton saveButton;
    @BindView(R.id.activity_gif_info_swipeable_view)
    SwipePlaceHolderView swipeableView;

    @Inject
    GifInfoPresenter presenter;

    private GifCardHelper gifCardHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_info);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setTitle("Gif Information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        DaggerGifInfoComponent.builder()
                .gifInfoModule(new GifInfoModule(this))
                .userComponent(((GiphyBoxApplication)getApplication()).getUserComponent())
                .build()
                .inject(this);

        swipeableView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor());

        String id = getIntent().getStringExtra(GIF_IMAGE_ID);
        presenter.loadGifById(id);
    }

    @OnClick(R.id.activity_gif_info_downvote)
    public void downvoteGif(){
        presenter.onDownvoteGifSelected();
    }

    @OnClick(R.id.activity_gif_info_upvote)
    public void upvoteGif(){
        presenter.onUpvoteGifSelected();
    }

    @OnClick(R.id.activity_gif_info_save)
    public void saveGif(){
        presenter.onSaveGifSelected();
    }

    @Override
    public void setDownvoteButtonSelected() {
        downvoteButton.setBackground(getResources().getDrawable(R.drawable.circular_button_blue));
    }

    @Override
    public void setDownvoteButtonUnSelected() {
        downvoteButton.setBackground(getResources().getDrawable(R.drawable.circular_button_white));

    }

    @Override
    public void setUpvoteButtonSelected() {
        upvoteButton.setBackground(getResources().getDrawable(R.drawable.circular_button_blue));
    }

    @Override
    public void setUpvoteButtonUnselected() {
        upvoteButton.setBackground(getResources().getDrawable(R.drawable.circular_button_white));
    }

    @Override
    public void setSaveButtonSelected() {
        saveButton.setBackground(getResources().getDrawable(R.drawable.circular_button_blue));
    }

    @Override
    public void setSaveButtonUnselected() {
        saveButton.setBackground(getResources().getDrawable(R.drawable.circular_button_white));
    }

    @Override
    public void showLoading() {
        loadIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadIndicator.setVisibility(View.GONE);
    }

    @Override
    public void loadGif(Gif gif) {
        gifCardHelper = new GifCardHelper(gif, this, swipeableView);
        swipeableView.addView(gifCardHelper);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateRatingsLabel(int rating) {
        if(gifCardHelper != null){
            gifCardHelper.setRating(rating);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
