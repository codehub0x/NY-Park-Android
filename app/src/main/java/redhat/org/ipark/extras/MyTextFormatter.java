package redhat.org.ipark.extras;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class MyTextFormatter implements TextWatcher {

    private final String TAG = this.getClass().getSimpleName();

    private EditText mEditText;
    private String mPattern;

    public MyTextFormatter(EditText editText, String pattern) {
        mEditText = editText;
        mPattern = pattern;
        //set max length of string
        int maxLength = pattern.length();
        mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        StringBuilder str = new StringBuilder(s);

        Log.d(TAG, "join");

        if (count > 0 && !isValid(str.toString())) {
            for (int i = 0; i < str.length(); i++) {
                Log.d(TAG, String.format("%s", str));
                char c = mPattern.charAt(i);

                if ((c != '#') && (c != str.charAt(i))) {
                    str.insert(i, c);
                }
            }

            mEditText.setText(str);
            mEditText.setSelection(mEditText.getText().length());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private boolean isValid(String text) {
        for (int i = 0; i < text.length(); i++) {
            char c = mPattern.charAt(i);

            if (c == '#') continue;

            if (c != text.charAt(i)) {
                return false;
            }
        }

        return true;
    }
}