package utils;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.example.accountbook.R;

public class KeyBoardUtils {
    private final Keyboard k1;
    private KeyboardView keyboardView;
    private EditText editText;


    public KeyBoardUtils(KeyboardView keyboardView, EditText editText) {
        this.keyboardView = keyboardView;
        this.editText = editText;
        // 取消弹出系统键盘
        this.editText.setInputType(InputType.TYPE_NULL);
        // 获取自定义键盘对象
        k1 = new Keyboard(this.editText.getContext(), R.xml.key);
        this.keyboardView.setKeyboard(k1);
        this.keyboardView.setEnabled(true);
        this.keyboardView.setPreviewEnabled(false);
        this.keyboardView.setOnKeyboardActionListener(listener);
    }

    // 接口回调。 在点击 “确定” 按钮，去执行onEnsure确定方法
    public interface OnEnsureListener{
        public void onEnsure();
    }

    // 接口初始化
    OnEnsureListener onEnsureListener;

    // 传入接口对象
    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }


    KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = editText.getText();
            int start = editText.getSelectionStart();

            if(primaryCode == Keyboard.KEYCODE_DELETE) {//删除
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start-1, start);
                    }
                }
            } else if (primaryCode == Keyboard.KEYCODE_CANCEL) {//清零
                editable.clear();
            } else if (primaryCode == Keyboard.KEYCODE_DONE) {//确定
                onEnsureListener.onEnsure();//通过接口回调的方法，当点击确定时，可以调用这个方法
            } else {//其他数值直接插入
                editable.insert(start, Character.toString((char)primaryCode));
            }
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void swipeUp() {
        }
    };

    // 显示键盘
    public void showKeyboard() {
        int visibility = keyboardView.getVisibility();
        if(visibility == View.INVISIBLE || visibility == View.GONE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

}
