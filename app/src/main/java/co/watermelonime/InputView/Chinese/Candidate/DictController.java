package co.watermelonime.InputView.Chinese.Candidate;

import android.view.View;

import java.util.ArrayList;

import co.watermelonime.C;
import co.watermelonime.Common.Size;
import co.watermelonime.Core.Controller;
import co.watermelonime.Core.Engine;
import co.watermelonime.InputView.Chinese.ChineseInputView;

public class DictController {
    public static ArrayList<String> currentDict;

    public static void showDictionary() {
        ArrayList<String > d = Engine.dictResult;
        currentDict = d;
        for (int i = 0; i < d.size(); i += 2) {
            C.candidateView.addView(DictTitle.get(d.get(i), i + 1));

            String characters = d.get(i + 1);
            System.out.println(characters);
            int len = characters.length();
            if (len > 7) len = 7;
            for (int j = 0; j < len; j++) {
                String text = String.valueOf(characters.charAt(j));
                DictButton dictButton = DictButton.get();
                int padding = Size.WDictButton - CandidateButton.calculateMinWidth(" ");
                dictButton.setText(text, padding, j != len - 1, 0);
                C.candidateView.addView(dictButton);
            }
        }
        CandidateView.height = d.size() / 2 * Size.HCandidateRow;
        if (CandidateView.height < Size.HCandidateView)
            CandidateView.height = Size.HCandidateView;
    }

    public static void showLayer2Dictionary(int index) {
        String characters = currentDict.get(index);
        int len = characters.length();
        int counter = 0;
        for (int i = 0; i < len; i++) {
            char text = characters.charAt(i);
            if (text=='@') continue;
            DictButton d = DictButton.get();
            int padding = Size.W2ndLayerDictButton - CandidateButton.calculateMinWidth();
            counter++;
            d.setText(String.valueOf(text), padding, counter % 8 != 0, 0);
            C.candidateView.addView(d);
        }
        CandidateView.height = (counter / 8 + 1) * Size.HCandidateRow;
        if (CandidateView.height < Size.HCandidateView)
            CandidateView.height = Size.HCandidateView;
    }

    public static void clearCandidate() {
        int len = C.candidateView.getChildCount();
        for (int i = 0; i < len; i++) {
            View child = C.candidateView.getChildAt(i);
            if (child.getClass() == DictButton.class)
                ((DictButton) child).release();
            else if (child.getClass() == DictTitle.class)
                ((DictTitle) child).release();
            else CandidateView.releaseCandidateButton((CandidateButton) child);
        }
        C.candidateView.removeAllViews();
    }

    public static void openDict() {
        CandidateView.isDictionaryMode = true;
        C.chineseInputView.invalidate();
    }

    public static void closeDict() {
        CandidateView.isDictionaryMode = false;
        Controller.displayCandidates();
        ChineseInputView.scrollView.removeAllViews(); // todo: why do I have to do this?
        ChineseInputView.scrollView.addView(C.candidateView);
        C.chineseInputView.invalidate();
    }
}
