package co.watermelonime.InputView.Chinese.Keyboard;

import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Core.Controller;
import co.watermelonime.InputView.Chinese.Candidate.CharacterKey;
import co.watermelonime.InputView.Chinese.Candidate.PredictionKey;

public class OnTouchConsonant implements View.OnTouchListener {
    public static final char[] code = {
            'A', 'E', 'I', 'M', 'Q', 'U',
            'B', 'F', 'J', 'N', 'R', 'V',
            'C', 'G', 'K', 'O', 'S', 'W',
            'D', 'H', 'L', 'T'};

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                ChineseKey key = (ChineseKey) v;
                int id = key.action;
                char keyCode = code[id];
                Controller.add(keyCode, '\0');
                C.chineseKeyboard.setKeys(Vowels.keyArray[id]);
                C.sentenceView.consonantPreview((String) key.mainText.getText());

                if (id != 20) { // if not IUV
                    C.candidateView.addView(PredictionKey.placeholder);
                }

                // fill in character locks
                System.out.println("Charakey" + id);
                for (CharacterKey k : CharacterKey.keys[id]) {
                    C.candidateView.addView(k);
                }

                System.out.println("OnTouchConsonant: " + keyCode);
        }
        return true;
    }
}
