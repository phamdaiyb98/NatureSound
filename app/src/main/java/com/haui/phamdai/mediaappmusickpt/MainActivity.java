package com.haui.phamdai.mediaappmusickpt;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //    widget
    TextView txtTitle, txtTiming, txtTimeTotal;
    SeekBar sbSong;
    ImageButton ibtnPrev, ibtnPlay, ibtnNext, ibtnRepeat, ibtnShuffle;
    MediaPlayer mediaPlayer;
    static ViewPager2 viewPager;

    //    class
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss", Locale.getDefault());
    Random random = new Random();
    SharedPreferences sharedPreferences;
    static List<Song> songs;

    //    primitive
    static int position = 0;
    boolean shuffle = false;
    boolean repeat = false;

    //    class lấy get instance của MainActivity
    public static WeakReference<MainActivity> weakActivity;

    public static MainActivity getInstance() {
        return weakActivity.get();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weakActivity = new WeakReference<>(MainActivity.this);

        sharedPreferences = getSharedPreferences("lastPosition", MODE_PRIVATE);

        position = sharedPreferences.getInt("position", 0);

        mapWidget();
        add();
    }

    @Override
    protected void onStart() {
        super.onStart();

//        tạo các view tab
        ScreenSlidePagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        initMediaPlayer();
        txtTitle.setText(MainActivity.songs.get(MainActivity.position).getTitle());

        ibtnRepeat.setOnClickListener(v -> {
            repeat = !repeat;
            if (repeat) {
                // chọn
                ibtnRepeat.setImageResource(R.drawable.repeat_selected);
            } else {
                ibtnRepeat.setImageResource(R.drawable.repeat);
            }
        });

        ibtnShuffle.setOnClickListener(v -> {
            shuffle = !shuffle;
            if (shuffle) {
                //chọn
                ibtnShuffle.setImageResource(R.drawable.shuffle_selected);
            } else {
                ibtnShuffle.setImageResource(R.drawable.shuffle);
            }
        });

        ibtnPlay.setOnClickListener(v -> {
            try {
                if (mediaPlayer.isPlaying()) {
                    // pause
                    mediaPlayer.pause();
                    ibtnPlay.setImageResource(R.drawable.play);
                    PlayerFragment.rotateAnim.pause();
                } else {
                    // start
                    mediaPlayer.start();
                    ibtnPlay.setImageResource(R.drawable.pause);
                    startOrResumeRotateDisk();
                }
            } catch (Exception ignored) {

            }
            updateTimeSong();
            setTimeTotal();
        });

        ibtnNext.setOnClickListener(v -> {
            // current position
            int p = MainActivity.position;
            // next
            MainActivity.position = p == (MainActivity.songs.size() - 1) ? 0 : p + 1;
            changeSong();
        });

        ibtnPrev.setOnClickListener(v -> {
            // current position
            int p = MainActivity.position;
            // previous
            MainActivity.position = p == 0 ? (MainActivity.songs.size() - 1) : p - 1;
            changeSong();
        });

        sbSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                try {
                    mediaPlayer.seekTo(seekBar.getProgress());
                } catch (NullPointerException ignored) {
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // lưu lại vị trí bài hát cũ đã phát
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("position", position);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    private void add() {
        songs = new ArrayList<>();
        songs.add(new Song("Bird singing", R.raw.bird_singing));
        songs.add(new Song("Birds singing in a forest", R.raw.birds_singing_in_a_forest));
        songs.add(new Song("Heavy rain", R.raw.heavy_rain));
        songs.add(new Song("Ocean waves", R.raw.ocean_waves));
        songs.add(new Song("Peaceful water stream in forest", R.raw.peaceful_water_stream_in_forest));
        songs.add(new Song("Pure sound of the nightingale song in the forest", R.raw.pure_sound_of_the_nightingale_song_in_the_forest));
        songs.add(new Song("Rain and thunder", R.raw.rain_and_thunder));
        songs.add(new Song("Raindrops on window sill", R.raw.raindrops_on_window_sill));
        songs.add(new Song("Soft water drops gurgling bubbling water sounds", R.raw.soft_water_drops_gurgling_bubbling_water_sounds));
        songs.add(new Song("Thunder and beginning of rain fall", R.raw.thunder_and_beginning_of_rain_fall));
        songs.add(new Song("Big rain drops",R.raw.big_rain_drops));
        songs.add(new Song("Birds and Frogs in Spring",R.raw.birds_and_frogs_in_spring));
        songs.add(new Song("Calm ocean waves with little bird song",R.raw.calm_ocean_waves_with_little_bird_song));
        songs.add(new Song("Calm River",R.raw.calm_river));
        songs.add(new Song("Calm waves on beach",R.raw.calm_waves_on_beach));
        songs.add(new Song("Cute music loop with sea waves",R.raw.cute_music_loop_with_sea_waves));
        songs.add(new Song("Gentle water stream in forest – Slow gurgle water",R.raw.gentle_water_stream_in_forest_slow_gurgle_water));
        songs.add(new Song("Happy Bird Singing 1",R.raw.happy_birds_singing_1));
        songs.add(new Song("Happy Bird Singing 2",R.raw.happy_birds_singing_2));
        songs.add(new Song("Jungle night heavy crickets",R.raw.jungle_night_heavy_crickets));
        songs.add(new Song("Rain Drips",R.raw.rain));
        songs.add(new Song("Rain Sound and Rainforest",R.raw.rain_drips));
        songs.add(new Song("Rain",R.raw.rain_sound_and_rainforest));
        songs.add(new Song("River in forest – Relaxing water stream",R.raw.river_in_forest_relaxing_water_stream));
    }

    private void mapWidget() {
        viewPager = findViewById(R.id.pager);
        txtTitle = findViewById(R.id.textviewTitle);
        txtTiming = findViewById(R.id.textviewTiming);
        txtTimeTotal = findViewById(R.id.textviewTimeTotal);
        sbSong = findViewById(R.id.seekbarTime);
        ibtnPrev = findViewById(R.id.imageButtonPrev);
        ibtnPlay = findViewById(R.id.imageButtonPlay);
        ibtnNext = findViewById(R.id.imageButtonNext);
        ibtnShuffle = findViewById(R.id.imageButtonShuffle);
        ibtnRepeat = findViewById(R.id.imageButtonRepeat);
    }

    private void initMediaPlayer() {
        int idSong = MainActivity.songs.get(MainActivity.position).getLink();
        mediaPlayer = MediaPlayer.create(this, idSong);
    }

    public void changeSong() {
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
        } catch (Exception ignored) {
        }
        initMediaPlayer();
        mediaPlayer.start();
        txtTitle.setText(MainActivity.songs.get(MainActivity.position).getTitle());
        ibtnPlay.setImageResource(R.drawable.pause);
        startOrResumeRotateDisk();
        updateTimeSong();
        setTimeTotal();
    }

    private void updateTimeSong() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                txtTiming.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                sbSong.setProgress(mediaPlayer.getCurrentPosition());
                // next song when current song finish
                mediaPlayer.setOnCompletionListener(mp -> {
                    // current position
                    int p = MainActivity.position;
                    // next
                    if (repeat) {
                        // repeat
                        changeSong();
                    } else if (shuffle) {
                        // shuffle
                        MainActivity.position = randomPosition(MainActivity.position, MainActivity.songs.size());
                        changeSong();
                    } else {
                        // next
                        MainActivity.position = p == (MainActivity.songs.size() - 1) ? 0 : p + 1;
                        changeSong();
                    }
                });
                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    private void setTimeTotal() {
        txtTimeTotal.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        // gắn max của skSong  = mediaPlayer.getDuration()
        sbSong.setMax(mediaPlayer.getDuration());
    }

    private int randomPosition(int currentPosition, int size) {
        int newPosition = random.nextInt(size);
        if (newPosition == currentPosition) {
            newPosition = randomPosition(currentPosition, size);
        }
        return newPosition;
    }

    private void startOrResumeRotateDisk() {
        if (!PlayerFragment.rotateAnim.isStarted()) {
            // bắt đầu quay
            PlayerFragment.rotateAnim.start();
        } else if (PlayerFragment.rotateAnim.isPaused()) {
            // tiếp tục quay ở vị trí vừa pause
            PlayerFragment.rotateAnim.resume();
        }
    }
}