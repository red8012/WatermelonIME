package co.watermelonime.Common;

import android.graphics.Color;

import co.watermelonime.C;

public class Font {
    public static TextLayoutFactory big, fr, mid, small, bigDisabled, midDisabled,
            sentence, candidate;

    public static void init() {
        big = new TextLayoutFactory(Size.FBig, C.sans, Colour.textKeyboard, Size.WKey);
        fr = new TextLayoutFactory(Size.FFr, C.sans, Colour.textKeyboard, Size.WKey);
        mid = new TextLayoutFactory(Size.FMid, C.sans, Colour.textKeyboard, Size.WKey);
        small = new TextLayoutFactory(Size.FSmall, C.sans, Colour.textKeyboard, Size.WKey);

        bigDisabled = new TextLayoutFactory(Size.FBig, C.sans, Color.DKGRAY, Size.WKey);
        midDisabled = new TextLayoutFactory(Size.FMid, C.sans, Color.DKGRAY, Size.WKey);

        sentence = new TextLayoutFactory(Size.FSentence, C.sans, Colour.textSentence, Size.WSentenceView);
        candidate = new TextLayoutFactory(Size.FCandidate, C.sans, Colour.textCandidate, 0);
    }
}
