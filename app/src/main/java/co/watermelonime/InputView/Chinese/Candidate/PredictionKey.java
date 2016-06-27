package co.watermelonime.InputView.Chinese.Candidate;

import android.view.MotionEvent;

import co.watermelonime.C;
import co.watermelonime.Common.Timer;
import co.watermelonime.Core.Controller;
import co.watermelonime.Core.Engine;
import co.watermelonime.Core.Learner;
import co.watermelonime.InputView.Chinese.Keyboard.Consonants;

public class PredictionKey extends CandidateButton {
    public final static OnTouchListener onTouchListener = (v, event) -> {
        if (event.getActionMasked() != MotionEvent.ACTION_DOWN) return true;
        if (event.getPointerCount() != 1) return true;
        PredictionKey key = ((PredictionKey) v);
        CharSequence text = key.text;
        int position = key.startPosition;
        C.commitBuffer.setLength(0);
        C.commitBuffer.append(Engine.sentence, 0, position);
        C.commitBuffer.append(text);
        Learner.wordBuffer.append(Engine.sentence, 0, key.startPosition);
        Learner.wordBuffer.append(text);
        Learner.pinyinBuffer.append(Engine.pinyin, 0, key.startPosition * 2);
        Learner.pinyinBuffer.append(key.pinyin);
        C.commit();
        Engine.clear();
        C.chineseKeyboard.setKeys(Consonants.keys);
        C.sentenceView.display();
        Controller.displayCandidates();

        Timer.t(1029);
        Learner.learnWordAsync(text.toString(), key.pinyin.toString());

        Learner.learnFromBuffer();
        Timer.t(1029, "learnWord");
        return true;
    };
    public static PredictionKey[] keys = new PredictionKey[3];
    public int startPosition;
    public StringBuilder pinyin;

    public PredictionKey() {
        super();
    }

    public static void init() {
        for (int i = 0; i < 3; i++) {
            keys[i] = new PredictionKey();
            keys[i].setOnTouchListener(onTouchListener);
        }
    }
}
