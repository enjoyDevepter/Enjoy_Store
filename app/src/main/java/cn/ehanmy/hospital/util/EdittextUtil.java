package cn.ehanmy.hospital.util;

import android.text.TextUtils;
import android.widget.EditText;

public class EdittextUtil {
    public static boolean isEmpty(EditText editText){
        return editText == null || editText.getText() == null || TextUtils.isEmpty(editText.getText().toString());
    }
}
