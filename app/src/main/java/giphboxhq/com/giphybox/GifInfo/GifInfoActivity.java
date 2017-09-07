package giphboxhq.com.giphybox.GifInfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import giphboxhq.com.giphybox.GiphyBoxApplication;
import giphboxhq.com.giphybox.R;

public class GifInfoActivity extends AppCompatActivity implements GifInfoView {
    private static final String TAG = "GifInfoActivity";
    public static final String GIF_IMAGE_URL = "gif_image_url";

    @BindView(R.id.activity_gif_info_toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_gif_info_loader)
    ProgressBar loadIndicator;
    @BindView(R.id.activity_gif_info_gif_view)
    ImageView gifView;

    @Inject
    GifInfoPresenter presenter;

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
                .netComponent(((GiphyBoxApplication)getApplication()).getNetComponent())
                .build()
                .inject(this);

        String url = getIntent().getStringExtra(GIF_IMAGE_URL);
        presenter.loadGifFromIntent(url);
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
    public void showLoading() {
        loadIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadIndicator.setVisibility(View.GONE);
    }

    @Override
    public void loadGif(String url) {
        Glide.with(this)
                .load(url)
                .into(new GlideDrawableImageViewTarget(gifView){
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        hideLoading();
                    }
                });
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
