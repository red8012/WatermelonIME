package co.watermelonime.InputView.Chinese.Keyboard;

import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Core.EngineCon;

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
                C.chineseKeyboard.setKeys(Vowels.keyArray[id]);
                String keyCode = code[id];
//                if (Engine.getLength() == 9) C.commit(Engine.pop());
//                C.e.add(keyCode.charAt(0), '\0');
                EngineCon.add(keyCode.charAt(0), '\0'); // TODO: 2016/3/1 should use char
                C.sentenceView.consonantPreview((String) key.mainText.getText());
//                Engine.addConsonant(keyCode);
                System.out.println("OnTouchConsonant: " + keyCode);
        }
        return true;
    }
}
