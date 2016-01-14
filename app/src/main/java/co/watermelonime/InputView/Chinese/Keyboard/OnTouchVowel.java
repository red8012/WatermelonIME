package co.watermelonime.InputView.Chinese.Keyboard;

import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Core.Engine;
import co.watermelonime.Core.Runnables;

public class OnTouchVowel implements View.OnTouchListener {


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                ChineseKey key = (ChineseKey) v;
                if (key.action == ChineseKey.CONSONANT) {
                    C.chineseKeyboard.setCurrentKeys(Consonants.keys);
                    String keyCode = key.input;
                    Engine.addVowel(keyCode, "?");
                    Engine.thread1.execute(Runnables.onAdd);
                    C.candidateView.clearCandidates();
                    System.out.println("OnTouchVowel: " + keyCode);
                }
//                else if (key.action == ChineseKey.CHARACTER) {
//                    C.chineseKeyboard.setCurrentKeys(Consonants.keys);
//                    String keyCode = key.input;
//                    Engine.addVowel(keyCode, key.character);
//                    Engine.thread1.execute(Runnables.onAdd);
//                    C.candidateView.clearCandidates();
//                    System.out.println("OnTouchVowel: " + keyCode);
//                }
        }
        return true;
    }
}
