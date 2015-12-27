package co.watermelonime.InputView.Chinese.Keyboard;

import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;

public class OnTouchConsonant implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                ChineseKey key = (ChineseKey) v;
                int id = key.consonantId;
                C.chineseKeyboard.setCurrentKeys(Vowels.keyArray[id]);
        }
        return true;
    }
}
