package co.watermelonime.InputView.Chinese.Keyboard;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Core.Engine;

public class OnTouchEnter implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("OnTouchEnter");
                if (Engine.isEmpty()) {
                    C.mainService.sendDownUpKeyEvents(KeyEvent.KEYCODE_ENTER);
                } else {
                    C.commit(Engine.getSentence());
                    Engine.clear();
                    C.sentenceView.display();
                    C.candidateView.clearCandidates();
                }
        }
        return true;
    }
}