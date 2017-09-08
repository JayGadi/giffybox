package giphboxhq.com.giphybox.GifInfo;

import android.util.Log;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import giphboxhq.com.giphybox.BasePresenter;
import giphboxhq.com.giphybox.net.GifRepository;
import giphboxhq.com.giphybox.net.UserRepository;
import giphboxhq.com.giphybox.net.models.Gif;
import giphboxhq.com.giphybox.net.models.SingleGifResponse;
import giphboxhq.com.giphybox.net.models.User;
import rx.Subscriber;

import static android.content.ContentValues.TAG;

/**
 * Created by Owner on 2017-09-07.
 */

public class GifInfoPresenter implements BasePresenter {

    private GifInfoView view;
    private UserRepository userRepository;
    private GifRepository gifRepository;
    private Gif gif;

    @Inject
    public GifInfoPresenter(GifInfoView view, UserRepository userRepository, GifRepository gifRepository) {
        this.view = view;
        this.userRepository = userRepository;
        this.gifRepository = gifRepository;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }


    public void loadGifById(String id){
        view.showLoading();
        gifRepository.getGifById(id).subscribe(new Subscriber<SingleGifResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(SingleGifResponse singleGifResponse) {
                gif = singleGifResponse.data;
                view.loadGif(singleGifResponse.data);
                if(userRepository.getAuthenticatedUser().downvotedGifs.contains(singleGifResponse.data)){
                    view.setDownvoteButtonSelected();
                }
                if(userRepository.getAuthenticatedUser().upvotedGifs.contains(singleGifResponse.data)){
                    view.setUpvoteButtonSelected();
                }
                if(userRepository.getAuthenticatedUser().savedGifs.contains(singleGifResponse.data)){
                    view.setSaveButtonSelected();
                }
            }
        });
    }

    public void onDownvoteGifSelected(){
        User user = userRepository.getAuthenticatedUser();
        if(!user.downvotedGifs.contains(gif)){
            user.downvotedGifs.add(gif);
            view.setDownvoteButtonSelected();
            if(user.upvotedGifs.contains(gif)){
                view.setUpvoteButtonUnselected();
                user.upvotedGifs.remove(gif);
            }
        }else{
            view.setDownvoteButtonUnSelected();
            user.downvotedGifs.remove(gif);
        }
        userRepository.setAuthenticatedUser(user);
        userRepository.saveUser(user);
    }

    public void onUpvoteGifSelected(){
        User user = userRepository.getAuthenticatedUser();
        if(!user.upvotedGifs.contains(gif)){
            user.upvotedGifs.add(gif);
            view.setUpvoteButtonSelected();
            if(user.downvotedGifs.contains(gif)){
                view.setDownvoteButtonUnSelected();
                user.downvotedGifs.remove(gif);
            }
        }else{
            view.setUpvoteButtonUnselected();
            user.upvotedGifs.remove(gif);
        }
        userRepository.setAuthenticatedUser(user);
        userRepository.saveUser(user);
    }

    public void onSaveGifSelected(){
        User user = userRepository.getAuthenticatedUser();
        if(!user.savedGifs.contains(gif)){
            user.savedGifs.add(gif);
            view.setSaveButtonSelected();
        }else{
            user.savedGifs.remove(gif);
            view.setSaveButtonUnselected();
        }

        userRepository.setAuthenticatedUser(user);
        userRepository.saveUser(user);
    }
}
