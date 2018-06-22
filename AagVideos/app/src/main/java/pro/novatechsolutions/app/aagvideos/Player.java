package pro.novatechsolutions.app.aagvideos;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import pro.novatechsolutions.app.aagvideos.Client.ClientException;
import pro.novatechsolutions.app.aagvideos.Client.OnServiceResponseListener;
import pro.novatechsolutions.app.aagvideos.Client.VideoClient;
import pro.novatechsolutions.app.aagvideos.Entities.Video;
import pro.novatechsolutions.app.aagvideos.Utils.GifImageView;


public class Player extends Activity implements OnServiceResponseListener {
    private GifImageView gifImageView;
    private VideoClient videoClient;
    private VideoView videoView;
    private ArrayList<Video> videos = new ArrayList<>();
    private TextView textView;
    private  int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        videoView =
                (VideoView) findViewById(R.id.videoView);

        gifImageView =  findViewById(R.id.GifImageView);
        gifImageView.setGifImageResource(R.drawable.loader_animation);
        gifImageView.setVisibility(View.VISIBLE);
        videoView.setVisibility(View.GONE);
        textView = findViewById(R.id.textView);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playVideoAtPostion();
            }
        });

        getVideos();

    }

    private  void getVideos(){

        new VideoClient(this).fetchVideos();
    }


    private void playVideoAtPostion() {
        if(position < videos.size() - 1) {
            ++position;
        } else {
            position = 0;
        }

        final Video video = videos.get(position);

        String path = video.getAsset_url();
        videoView.setVideoPath(path);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                final Animation animationFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
                final Animation animationFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout);

                animationFadeIn.setDuration(6000);
                animationFadeIn.setRepeatCount(0);
                animationFadeOut.setDuration(1000);
                animationFadeOut.setStartOffset(16000);
                animationFadeOut.setRepeatCount(0);
                animationFadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        textView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });


                textView.setText(video.getTitle()+" ("+video.getDescription()+")");
                textView.setVisibility(View.VISIBLE);
                textView.startAnimation(animationFadeIn);
                textView.startAnimation(animationFadeOut);

            }
        });


    }



    @Override
    public void onSuccess(Object object) {
        videos.clear();
        List<Video> internal = new ArrayList<>();
        internal.addAll((ArrayList<Video>) object);
        Collections.shuffle(internal);
        videos.addAll(internal);

        gifImageView.setVisibility(View.GONE);
        videoView.setVisibility(View.VISIBLE);
        playVideoAtPostion();


    }

    @Override
    public void onFailure(ClientException e) {

    }
}
