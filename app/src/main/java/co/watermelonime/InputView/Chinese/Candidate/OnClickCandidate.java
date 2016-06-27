package co.watermelonime.InputView.Chinese.Candidate;

import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Common.Timer;
import co.watermelonime.Core.Controller;
import co.watermelonime.Core.Engine;
import co.watermelonime.Core.Learner;
import co.watermelonime.InputView.Chinese.Keyboard.ChineseKeyboard;
import co.watermelonime.InputView.Chinese.Keyboard.Consonants;
import co.watermelonime.InputView.Chinese.Sentence.SentenceButton;
import co.watermelonime.InputView.Chinese.Sentence.SentenceView;

public class OnClickCandidate implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        CharSequence text = ((CandidateButton) v).text;
        int len = text.length();
        int type = ((CandidateButton) v).type;
        // for words in dictionary
        if (SentenceButton.selectedIndex >= 0 && type == CandidateButton.DICT) {
            int index = SentenceButton.selectedIndex;
            for (int i = 0; i < len; i++)
                Engine.setZiLock(i + index, text.charAt(i));
            C.sentenceView.display();
            DictController.closeDict();
            SentenceView.setSelected(index);
            C.chineseKeyboard.show();
            Learner.learnWordAsync(text.toString(),
                    Engine.pinyin.substring(index * 2, (index + len) * 2));
            CandidateView.clearCandidates();
            Controller.displayCandidates();
            return;
        }

        // for characters
        if (ChineseKeyboard.currentKeys != Consonants.keys) return; // for defense only
        if (type == CandidateButton.BOTTOM) {
            C.commitBuffer.setLength(0);
            C.commitBuffer.append(Engine.sentence, 0, Engine.sentence.length() - len);
            C.commitBuffer.append(text);
            C.commit();

            Timer.t(1029);
            int pinyinLength = Engine.pinyin.length();

//            Logger.d("sentence: %s, len: %d, zi: %s, pinyin: %s", Engine.sentence, len, zi, Engine.pinyin);
            Learner.learnWordAsync(text.toString(),
                    Engine.pinyin.substring(pinyinLength - len * 2));
            Timer.t(1029, "learnWord");
            Learner.wordBuffer.append(Engine.sentence, 0, Engine.sentence.length() - len);
            Learner.wordBuffer.append(text);
            Learner.pinyinBuffer.append(Engine.pinyin);

            Engine.clear();
        } else {
            C.commit(text);
            Learner.learnWordAsync(text.toString(),
                    Engine.pinyin.substring(0, len * 2));
            Learner.wordBuffer.append(text);
            Learner.pinyinBuffer.append(Engine.pinyin, 0, len * 2);
            try {
                Engine.pop(text.length());
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
