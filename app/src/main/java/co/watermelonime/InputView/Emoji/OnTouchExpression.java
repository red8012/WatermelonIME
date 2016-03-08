package co.watermelonime.InputView.Emoji;

import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Core.Controller;
import co.watermelonime.InputView.Chinese.Keyboard.ChineseKey;
import co.watermelonime.InputView.Chinese.Keyboard.Vowels;

/**
 * Created by din on 2016/3/8.
 */
public class OnTouchExpression implements View.OnTouchListener {
    public static final char[] code = {
            'A', 'E', 'I', 'M', 'Q', 'U',
            'B', 'F', 'J', 'N', 'R', 'V',
            'C', 'G', 'K', 'O', 'S', 'W',
            'D', 'H', 'L', 'T'};

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                EmojiKey key = (EmojiKey) v;
                int id = key.action;
               // C.emojiKeyboard.setKeys(keyArray[id]);
                char keyCode = code[id];
                Controller.add(keyCode, '\0');
                C.sentenceView.consonantPreview((String) key.mainText.getText());
                System.out.println("OnTouchExpression: " + keyCode);
        }
        return false;
    }
}
