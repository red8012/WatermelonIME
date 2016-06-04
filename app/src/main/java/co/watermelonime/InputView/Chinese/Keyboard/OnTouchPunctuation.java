package co.watermelonime.InputView.Chinese.Keyboard;

import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Core.Controller;
import co.watermelonime.Core.Engine;

public class OnTouchPunctuation implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getPointerCount() != 1) return true;
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            C.chineseKeyboard.setKeys(Consonants.keys);
            C.commit(Engine.sentence);
            Engine.clear();
            C.sentenceView.display();
            Controller.displayCandidates();
            C.commit(String.valueOf(((ChineseKey) v).character));
        }
        return true;
    }
}
