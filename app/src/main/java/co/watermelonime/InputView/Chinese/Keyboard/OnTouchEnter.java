package co.watermelonime.InputView.Chinese.Keyboard;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Core.Controller;
import co.watermelonime.Core.Engine;

public class OnTouchEnter implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getPointerCount() != 1) return true;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("OnTouchEnter");
                if (Engine.isEmpty()) {
                    C.mainService.sendDownUpKeyEvents(KeyEvent.KEYCODE_ENTER);
                } else {
                    Controller.commitAll();
//                    C.commit(Engine.sentence);
//                    Learner.learnWord(Engine.sentence.toString(), Engine.pinyin);
//
//                    if (Learner.wordBuffer.length() != 0) {
//                        Learner.wordBuffer.append(Engine.sentence);
//                        Learner.pinyinBuffer.append(Engine.pinyin);
//                        Learner.learnFromBuffer();
//                    }
//                    Engine.clear();
//                    C.sentenceView.display();
//                    Controller.displayCandidates();
                }
        }
        return true;
    }
}
