package com.tc.crm.utils;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.tc.crm.R;


public class AnimationUtil {

    public static void circularRevealActivity(View mainView, View view) {

        int cx = view.getWidth() / 2;
        int cy = view.getHeight() / 2;

        float finalRadius = Math.max(view.getWidth(), view.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            circularReveal = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
            circularReveal.setDuration(10000);
        }
        // make the view visible and start the animation
        mainView.setVisibility(View.VISIBLE);
        if (circularReveal != null)
            circularReveal.start();
    }
    public static void animateExpand(final View v) {
        v.measure(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? WindowManager.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration(/*(int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density)*/300);
        v.startAnimation(a);
    }

    public static void animateCollapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration(/*(int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density)*/300);
        v.startAnimation(a);
    }
    public static void fadeIn(Context context, final View v){
        Animation fadeIn = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        v.setVisibility(View.VISIBLE);
        v.setAnimation(fadeIn);
    }
    public static void fadeOut(Context context, final View v){
        Animation fadeOut= AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
        v.setVisibility(View.GONE);
        v.setAnimation(fadeOut);
    }
    public static void slideinFadeIn(Context context, final View collapse, final View expand) {
        Animation slideIn = AnimationUtils.loadAnimation(context, R.anim.slide_up);

        slideIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //collapse.setVisibility(View.GONE);
                animateExpand(expand);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        collapse.setVisibility(View.VISIBLE);
        collapse.setAnimation(slideIn);

    }


    public static void TranslateView(Context context, View tranView) {
        Animation translate = AnimationUtils.loadAnimation(context, R.anim.translte);
        tranView.setAnimation(translate);
    }

    @SuppressLint("WrongConstant")
    public static void ShowBlinkEffect(Context context, View View) {
        ObjectAnimator anim = ObjectAnimator.ofInt(View, "backgroundColor",
                Color.TRANSPARENT,
                context.getResources().getColor(R.color.colorPrimary),
                Color.TRANSPARENT);
        anim.setDuration(500);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(Animation.ABSOLUTE);
        anim.setRepeatCount(Animation.REVERSE);
        anim.start();
    }

    public static void animateTextView(int initialValue, int finalValue, final TextView textview) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(initialValue, finalValue);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(valueAnimator1 -> textview.setText(Integer.valueOf(valueAnimator1.getAnimatedValue().toString())));
        valueAnimator.start();
    }
    // To animate view slide out from left to right
    public static void slideToRight(View view){
        TranslateAnimation animate = new TranslateAnimation(0,view.getWidth(),0,0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }
    // To animate view slide out from right to left
    public static void slideToLeft(View view){
        TranslateAnimation animate = new TranslateAnimation(0,-view.getWidth(),0,0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

    // To animate view slide out from top to bottom
    public static void slideToBottom(View view){
        TranslateAnimation animate = new TranslateAnimation(0,0,0,view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

    // To animate view slide out from bottom to top
    public static void slideToTop(View view){
        TranslateAnimation animate = new TranslateAnimation(0,0,0,-view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

    public static void RotateView(Context context, View myView){
        Animation rotation = AnimationUtils.loadAnimation(context, R.anim.rotate_animation);

        rotation.setDuration(300);
        myView.startAnimation(rotation);
    }
}
