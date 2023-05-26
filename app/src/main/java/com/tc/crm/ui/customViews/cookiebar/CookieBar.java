package com.tc.crm.ui.customViews.cookiebar;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;

import com.tc.crm.R;


public class CookieBar {
    private Cookie cookieView;
    private final Activity context;

    public static Builder build(Activity activity) {
        return new Builder(activity);
    }

    public static void dismiss(Activity activity) {
        new CookieBar(activity, null);
    }

    private CookieBar(Activity context, Params params) {
        this.context = context;
        if(params == null) {
            // since params is null, this CookieBar object can only be used to dismiss
            // existing cookies
            dismiss();
            return;
        }

        cookieView = new Cookie(context);
        cookieView.setParams(params);
    }

    private void show() {
        if (cookieView != null) {
            final ViewGroup decorView = (ViewGroup) context.getWindow().getDecorView();
            final ViewGroup content = decorView.findViewById(android.R.id.content);
            if (cookieView.getParent() == null) {
                ViewGroup parent = cookieView.getLayoutGravity() == Gravity.BOTTOM ?
                        content : decorView;
                addCookie(parent, cookieView);
            }
        }
    }

    private void dismiss() {
        final ViewGroup decorView = (ViewGroup) context.getWindow().getDecorView();
        final ViewGroup content = decorView.findViewById(android.R.id.content);

        removeFromParent(decorView);
        removeFromParent(content);
    }

    private void removeFromParent(ViewGroup parent) {
        int childCount = parent .getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if(child instanceof Cookie) {
                ((Cookie) child).dismiss();
                return;
            }
        }

    }

    private void addCookie(final ViewGroup parent, final Cookie cookie) {
        if(cookie.getParent() != null) {
            return;
        }

        // if exists, remove existing cookie
        int childCount = parent .getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if(child instanceof Cookie) {
                ((Cookie) child).dismiss(new Cookie.CookieBarDismissListener() {
                    @Override
                    public void onDismiss() {
                        parent.addView(cookie);
                    }
                });
                return;
            }
        }

        parent.addView(cookie);
    }
    
    public View getView() {
        return cookieView;
    }

    public static class Builder {

        private final Params params = new Params();

        public final Activity context;

        /**
         * Create a builder for an cookie.
         */
        public Builder(Activity activity) {
            this.context = activity;
        }

        public Builder setIcon(@DrawableRes int iconResId) {
            params.iconResId = iconResId;
            return this;
        }

        public Builder setTitle(String title) {
            params.title = title;
            return this;
        }

        public Builder setTitle(@StringRes int resId) {
            params.title = context.getString(resId);
            return this;
        }

        public Builder setMessage(String message) {
            params.message = message;
            return this;
        }

        public Builder setMessage(@StringRes int resId) {
            params.message = context.getString(resId);
            return this;
        }

        public Builder setDuration(long duration) {
            params.duration = duration;
            return this;
        }

        public Builder setTitleColor(@ColorRes int titleColor) {
            params.titleColor = titleColor;
            return this;
        }

        public Builder setMessageColor(@ColorRes int messageColor) {
            params.messageColor = messageColor;
            return this;
        }

        public Builder setBackgroundColor(@ColorRes int backgroundColor) {
            params.backgroundColor = backgroundColor;
            return this;
        }

        public Builder setActionColor(@ColorRes int actionColor) {
            params.actionColor = actionColor;
            return this;
        }

        public Builder setAction(String action, OnActionClickListener onActionClickListener) {
            params.action = action;
            params.onActionClickListener = onActionClickListener;
            return this;
        }

        public Builder setIconAnimation(@AnimatorRes int iconAnimation) {
            params.iconAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(context, iconAnimation);
            return this;
        }

        public Builder setAction(@StringRes int resId, OnActionClickListener onActionClickListener) {
            params.action = context.getString(resId);
            params.onActionClickListener = onActionClickListener;
            return this;
        }

        public Builder setLayoutGravity(int layoutGravity) {
            params.layoutGravity = layoutGravity;
            return this;
        }

        public Builder setCustomView(@LayoutRes int customView) {
            params.customView = customView;
            return this;
        }
    
        public Builder setAnimationDuration(long duration) {
            params.animationDuration = duration;
            return this;
        }
        
        public Builder setAnimationIn(@AnimRes int[] animationIn) {
            params.animationIn = animationIn;
            return this;
        }
    
        public Builder setAnimationOut(@AnimRes int[] animationOut) {
            params.animationOut = animationOut;
            return this;
        }

        public CookieBar create() {
            return new CookieBar(context, params);
        }

        public CookieBar show() {
            final CookieBar cookie = create();
            cookie.show();
            return cookie;
        }


    }

    final static class Params {
        public String title;
        public String message;
        public String action;
        public OnActionClickListener onActionClickListener;
        public int iconResId;
        public int backgroundColor;
        public int titleColor;
        public int messageColor;
        public int actionColor;
        public long duration = 2000;
        public long animationDuration = 300;
        public int layoutGravity = Gravity.TOP;
        public AnimatorSet iconAnimator;
        public int customView;

        public int[] animationIn = new int[]{R.anim.slide_in_from_top, R.anim.slide_in_from_bottom};
        public int[] animationOut = new int[]{R.anim.slide_out_to_top, R.anim.slide_out_to_bottom};
    }
}