package com.haui.phamdai.mediaappmusickpt;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class PlayerFragment extends Fragment {

    public static ObjectAnimator rotateAnim;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate layout to fragment
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        ImageView imageViewDisk = view.findViewById(R.id.imageViewDisk);

        rotateAnim = ObjectAnimator.ofFloat(imageViewDisk, "rotation", 0f, 360f);
        rotateAnim.setDuration(10000);
        rotateAnim.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnim.setInterpolator(new LinearInterpolator());

        return view;
    }
}