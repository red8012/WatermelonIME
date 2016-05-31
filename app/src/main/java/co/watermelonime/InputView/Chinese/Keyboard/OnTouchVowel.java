package co.watermelonime.InputView.Chinese.Keyboard;

import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Common.Timer;
import co.watermelonime.Core.Controller;

public class OnTouchVowel implements View.OnTouchListener {

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getPointerCount() != 1) return true;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                ChineseKey key = (ChineseKey) v;
                C.chineseKeyboard.setKeys(Consonants.keys);
                Timer.t(375);
                Controller.add(key.pinyin, '?');
                Timer.t(375, "controller add");
                System.out.println("OnTouchVowel: " + key.pinyin);
//                }
        }
        return true;
    }
}
