package co.watermelonime.Common;

import android.graphics.Color;
import android.graphics.Typeface;

public class Font {
    public static TextLayoutFactory
            big, fr, mid, small, bigDisabled, midDisabled,
            sentence, sentenceSelected, candidate, dictTitle;
    public static Typeface sans;

    public static void init() {
        big = new TextLayoutFactory(Size.FBig, sans, Colour.textKeyboard, Size.WKey);
        fr = new TextLayoutFactory(Size.FFr, sans, Colour.textKeyboard, Size.WKey);
        mid = new TextLayoutFactory(Size.FMid, sans, Colour.textKeyboard, Size.WKey);
        small = new TextLayoutFactory(Size.FSmall, sans, Colour.textKeyboard, Size.WKey);

        bigDisabled = new TextLayoutFactory(Size.FBig, sans, Color.DKGRAY, Size.WKey);
        midDisabled = new TextLayoutFactory(Size.FMid, sans, Color.DKGRAY, Size.WKey);

        sentence = new TextLayoutFactory(Size.FSentence, sans, Colour.textSentence, Size.WSentenceView);
        sentenceSelected = new TextLayoutFactory(Size.FSentence, sans, Colour.textCandidate, Size.WSentenceView);
        candidate = new TextLayoutFactory(Size.FCandidate, sans, Colour.textCandidate, 0);
        dictTitle = new TextLayoutFactory(Size.FMid, sans, Colour.DICT_TITLE, (int) (Size.WDictTitle + Size.u));
    }
}
