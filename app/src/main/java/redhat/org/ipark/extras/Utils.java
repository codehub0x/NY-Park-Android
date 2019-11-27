package redhat.org.ipark.extras;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

public class Utils {
    static int mAppHeight;
    static int currentOrientation = -1;

    public static void setKeyboardVisibilityListener(final Activity activity, final KeyboardVisibilityListener keyboardVisibilityListener) {
        final View contentView = activity.findViewById(android.R.id.content);
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private int mPreviousHeight;
            @Override
            public void onGlobalLayout() {
                int newHeight = contentView.getHeight();
                if (newHeight == mPreviousHeight)
                    return;

                mPreviousHeight = newHeight;
                if (activity.getResources().getConfiguration().orientation != currentOrientation) {
                    currentOrientation = activity.getResources().getConfiguration().orientation;
                    mAppHeight = 0;
                }

                if (newHeight >= mAppHeight) {
                    mAppHeight = newHeight;
                }

                if (newHeight != 0) {
                    if (mAppHeight > newHeight) {
                        // Height decreased: keyboard was shown
                        keyboardVisibilityListener.onKeyboardVisibilityChanged(true);
                    } else {
                        // Height increased: keyboard was hidden
                        keyboardVisibilityListener.onKeyboardVisibilityChanged(false);
                    }
                }
            }
        });
    }

    public static void hideKeyboard(final Activity activity, View view) {
        InputMethodManager imm = ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE));
        boolean isKeyboardUp = imm.isAcceptingText();

        if (isKeyboardUp) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
