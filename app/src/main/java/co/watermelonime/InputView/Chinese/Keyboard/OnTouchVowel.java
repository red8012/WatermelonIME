package co.watermelonime.InputView.Chinese.Keyboard;

import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Core.EngineCon;

public class OnTouchVowel implements View.OnTouchListener {

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                ChineseKey key = (ChineseKey) v;
                if (key.action == ChineseKey.CONSONANT) {
                    C.chineseKeyboard.setKeys(Consonants.keys);
//                    C.e.add(key.pinyin, '?');
                    EngineCon.add(key.pinyin, '?');
                    C.candidateView.clearCandidates();
                    System.out.println("OnTouchVowel: " + key.pinyin);
                } else if (key.action == ChineseKey.CHARACTER) {
                    C.chineseKeyboard.setKeys(Consonants.keys);
//                    C.e.add(key.pinyin, key.character);
                    EngineCon.add(key.pinyin, key.character);
                    C.candidateView.clearCandidates();
                    System.out.println("OnTouchVowel: " + key.pinyin + String.valueOf(key.character));
                }
        }
        return true;
    }
}
