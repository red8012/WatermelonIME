package co.watermelonime.InputView.Chinese.Keyboard;

import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Core.Engine;
import co.watermelonime.Core.Runnables;

public class OnTouchVowel implements View.OnTouchListener {
    public static final String code[] = {
            "a", "e", "i", "m", "q", "u",
            "b", "f", "j", "n", "r", "v",
            "c", "g", "k", "o", "s", "w",
            "d", "h", "l", "p", "t"};

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                ChineseKey key = (ChineseKey) v;
                int id = key.id;
                if (id < 0) {
                    return true;
                }
                C.chineseKeyboard.setCurrentKeys(Consonants.keys);
                String keyCode = code[id];
                Engine.addVowel(keyCode, "?");
                Engine.thread1.execute(Runnables.onAdd);
                System.out.println("OnTouchVowel: " + keyCode);
        }
        return true;
    }
}
