package co.watermelonime.InputView.Chinese.Keyboard;

import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;

public class OnTouchConsonant implements View.OnTouchListener {
    public static final String code[] = {
            "A", "E", "I", "M", "Q", "U",
            "B", "F", "J", "N", "R", "V",
            "C", "G", "K", "O", "S", "W",
            "D", "H", "L", "T"};

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                ChineseKey key = (ChineseKey) v;
                int id = key.action;
                C.chineseKeyboard.setCurrentKeys(Vowels.keyArray[id]);
                String keyCode = code[id];
//                if (Engine.getLength() == 9) C.commit(Engine.pop());
                C.e.add(keyCode.charAt(0), '\0');
                C.sentenceView.consonantPreview((String) key.mainText.getText());
//                Engine.addConsonant(keyCode);
                System.out.println("OnTouchConsonant: " + keyCode);
        }
        return true;
    }
}
