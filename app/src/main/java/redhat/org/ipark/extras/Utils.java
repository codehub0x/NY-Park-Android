package redhat.org.ipark.extras;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
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

    public static void showKeyboard(final Activity activity, View view) {
        InputMethodManager imm = ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE));
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return !TextUtils.isEmpty(phoneNumber) && android.util.Patterns.PHONE.matcher(phoneNumber).matches();
    }

}
