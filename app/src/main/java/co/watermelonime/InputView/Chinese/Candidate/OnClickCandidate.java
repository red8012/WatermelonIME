package co.watermelonime.InputView.Chinese.Candidate;

import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Common.Timer;
import co.watermelonime.Core.Controller;
import co.watermelonime.Core.Engine;
import co.watermelonime.Core.Learner;
import co.watermelonime.InputView.Chinese.Keyboard.ChineseKeyboard;
import co.watermelonime.InputView.Chinese.Keyboard.Consonants;

public class OnClickCandidate implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        if (ChineseKeyboard.currentKeys != Consonants.keys) return;
        CharSequence zi = ((CandidateButton) v).text;
        int len = zi.length();
        if (((CandidateButton) v).type == CandidateButton.BOTTOM) {
            C.commitBuffer.setLength(0);
            C.commitBuffer.append(Engine.sentence, 0, Engine.sentence.length() - len);
            C.commitBuffer.append(zi);
            C.commit();

            Timer.t(1029);
            int pinyinLength = Engine.pinyin.length();

//            Logger.d("sentence: %s, len: %d, zi: %s, pinyin: %s", Engine.sentence, len, zi, Engine.pinyin);
            Learner.learnWord(zi.toString(),
                    Engine.pinyin.substring(pinyinLength - len * 2));
            Timer.t(1029, "learnWord");
            Learner.wordBuffer.append(Engine.sentence, 0, Engine.sentence.length() - len);
            Learner.wordBuffer.append(zi);
            Learner.pinyinBuffer.append(Engine.pinyin);

            Engine.clear();
        } else {
            C.commit(zi);
            Learner.learnWord(zi.toString(),
                    Engine.pinyin.substring(0, len * 2));
            Learner.wordBuffer.append(zi);
            Learner.pinyinBuffer.append(Engine.pinyin, 0, len * 2);
            try {
                Engine.pop(zi.length());
            } catch (Exception e) {
                e.printStackTrace();
                Engine.clear();
            }
        }

        if (Engine.isEmpty()) {
            Timer.t(1030);
            Learner.learnFromBuffer();
            Timer.t(1030, "learnFromBuffer");
        }

        C.sentenceView.display();
        Controller.displayCandidates();
    }
}
