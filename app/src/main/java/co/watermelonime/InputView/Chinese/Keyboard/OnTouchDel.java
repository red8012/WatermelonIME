package co.watermelonime.InputView.Chinese.Keyboard;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import co.watermelonime.C;
import co.watermelonime.Common.Size;
import co.watermelonime.Core.Engine;
import co.watermelonime.Core.Runnables;

public class OnTouchDel implements OnTouchListener {
    static int lastX;

    final static void backspace() {
        if (Engine.isEmpty()) C.mainService.sendDownUpKeyEvents(KeyEvent.KEYCODE_DEL);
        else {
            Engine.delCharacter();
            int length = Engine.getLength();
                Engine.makeCandidateLeft(length);
                Engine.makeCandidateRight(length);
            if (length > 0) {
                Runnables.makeSeparator(); // Todo: why do I need this?
                Runnables.makeSentence();
            }
            System.out.println(Engine.getSentence());
            C.sentenceView.display();
            Runnables.displayCandidate.run();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (ChineseKeyboard.currentKeys != Consonants.keys) {
            Engine.delConsonant();
            C.sentenceView.display();
            C.chineseKeyboard.setCurrentKeys(Consonants.keys);
            if (Engine.isEmpty()) {
            } // Todo: displayTexts nav
            else Runnables.displayCandidate.run();
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
