package co.watermelonime.InputView.Chinese.Candidate;

import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Core.Controller;
import co.watermelonime.Core.Engine;
import co.watermelonime.Core.Learner;
import co.watermelonime.InputView.Chinese.Sentence.SentenceButton;
import co.watermelonime.InputView.Chinese.Sentence.SentenceView;

public class OnClickDict implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        DictButton dictButton = (DictButton) v;
        CharSequence text = dictButton.text;
        int index = SentenceButton.selectedIndex;
        Engine.setZiLock(index, text.charAt(0));
        C.sentenceView.display();
        DictController.closeDict();
        SentenceView.setSelected(index);
        C.chineseKeyboard.show();

        Learner.learnWordAsync(text.toString(), Engine.pinyin.substring(index * 2, index * 2 + 2));

        CandidateView.getCandidateViewForDict().clearCandidates();
        Controller.displayCandidates();
    }
}
