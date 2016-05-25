package co.watermelonime.InputView.Chinese.Candidate;

import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Core.Controller;
import co.watermelonime.InputView.Chinese.Keyboard.Consonants;

public class OnTouchCharacterKey implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getActionMasked() != MotionEvent.ACTION_DOWN) return true;
        if (event.getPointerCount() != 1) return true;
        CharacterKey k = (CharacterKey) v;
        C.chineseKeyboard.setKeys(Consonants.keys);
        Controller.add(k.vowel, k.character);
//        System.out.println("OnTouchCharacterKey: " + k.vowel + String.valueOf(k.character));
        return true;
    }
}
