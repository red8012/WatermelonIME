package co.watermelonime.InputView.Chinese.Candidate;

import java.util.ArrayList;

import co.watermelonime.C;
import co.watermelonime.Common.Size;
import co.watermelonime.Core.Controller;
import co.watermelonime.Core.Engine;
import co.watermelonime.InputView.Chinese.InputView;

public class DictController {
    public static ArrayList<String> currentDict;

    public static void showLayer2Dictionary(int index) {
        String characters = currentDict.get(index);
        int len = characters.length();
        int counter = 0;
        for (int i = 0; i < len; i++) {
            char text = characters.charAt(i);
            if (text == '@') continue;
            DictButton d = DictButton.get();
            int padding = Size.W2ndLayerDictButton - CandidateButton.calculateMinWidth();
            counter++;
            d.setText(String.valueOf(text), padding, counter % 8 != 0, 0, 0);
            C.candidateView.addView(d);
        }
        CandidateView.height = (counter / 8 + 1) * Size.HCandidateRow;
        if (CandidateView.height < Size.HCandidateView)
            CandidateView.height = Size.HCandidateView;
    }

    public static void openDict() {
        ArrayList<String> d = Engine.dictResult;
        currentDict = d;
        for (int i = 0; i < d.size(); i += 2) {
            C.candidateView.addView(DictTitle.get(d.get(i), i + 1));

            String characters = d.get(i + 1);
            int len = characters.length();
            if (len > 7) len = 7;
            for (int j = 0; j < len; j++) {
                String text = String.valueOf(characters.charAt(j));
                DictButton dictButton = DictButton.get();
                int padding = Size.WDictButton - CandidateButton.calculateMinWidth(" ");
                dictButton.setText(text, padding, j != len - 1, 0, 0);
                C.candidateView.addView(dictButton);
            }
        }
        CandidateView.height = d.size() / 2 * Size.HCandidateRow;
        if (CandidateView.height < Size.HCandidateView)
            CandidateView.height = Size.HCandidateView;

        CandidateView.isDictionaryMode = true;
        C.inputView.invalidate();
    }

    public static void closeDict() {
        CandidateView.isDictionaryMode = false;
        Controller.displayCandidates();
        InputView.scrollView.removeAllViews(); // todo: why do I have to do this?
        InputView.scrollView.addView(C.candidateView);
        C.inputView.invalidate();
    }

    public static void fillInWordsInDict(int position) {
        ArrayList<StringBuilder> list = Engine.getWordsInDictRight(position);
        System.out.print("WordInDictRight: ");
        for (CharSequence s : list)
            System.out.print(s + "|");
        System.out.println();

        int end = list.size(), start = 0;
        while (start < end)
            start = CandidateView.setCandidate(list, start, end, CandidateButton.DICT);
//        for (CharSequence s : list){
//
//        }

    }
}
