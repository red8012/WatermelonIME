package co.watermelonime.InputView.Chinese.Keyboard;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import co.watermelonime.C;
import co.watermelonime.Common.Size;
import co.watermelonime.Core.Engine;

public class OnTouchDel implements OnTouchListener {
    static int lastX;

    final static void backspace() {
        if (Engine.isEmpty()) C.mainService.sendDownUpKeyEvents(KeyEvent.KEYCODE_DEL);
        else {
            Engine.delCharacter();
            int length = Engine.getLength();
            Engine.makeCandidateLeft();
            Engine.makeCandidateRight();
            if (length > 0) {
//                Runnables.makeSeparator(); // Todo: why do I need this?
//                Runnables.makeSentence();
                try {
                    Engine.makeSeparator();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Engine.makeSentence();
            }
//            System.out.println(Engine.getSentence());
            C.sentenceView.display();
//            Runnables.displayCandidate.run(); // TODO: 2016/3/1 display candidate
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (ChineseKeyboard.currentKeys != Consonants.keys) {
            Engine.delConsonant();
            C.sentenceView.display();
            C.chineseKeyboard.setKeys(Consonants.keys);
            if (Engine.isEmpty()) {
            } // Todo: displayTexts nav
//            else Runnables.displayCandidate.run(); // TODO: 2016/3/1 display candidate
            return true;
        }
        final int x = (int) event.getRawX();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;// Todo: Launch hint
                backspace();
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                if (x < lastX - Size.HCandidateRow || x > lastX + Size.HCandidateRow) {
                    lastX = x;
                    backspace();
                    break;
                }
        }
        return true;
    }
}
