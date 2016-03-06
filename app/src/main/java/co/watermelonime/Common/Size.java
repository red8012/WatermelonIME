package co.watermelonime.Common;

import android.graphics.Point;

public class Size {
    public static final float phi = 1.618034f;
    public static int keyIcon;
    public static float
            u,
            FSentence,
            FCandidate,
            FBig, FFr, FMid, FSmall;

    public static int
            WScreen,
            WInputView,
            WSentenceView,
            WCandidateView,
            WDictButton,
            W2ndLayerDictButton,
            WDictTitle,
            WSeparator,
            WKeyboard,
            WKey;
    public static int
            HScreen,
            HInputView,
            HSentenceView,
            HSentencePaddingTop,
            HSentenceButton,
            HLanguageSelectorKey,
            HCandidateView,
            HCandidateVisible,
            HCandidateRow,
            HSeparator,
            HKeyboard,
            HKey;

    public static int round(float x, int divider) {
        x /= divider;
        return ((int) x) * divider;
    }

    public static void calculate(Point size) {
        WScreen = size.x;
        HScreen = size.y;
        u = WScreen / 70f;
        WSeparator = WScreen / 240;
        WInputView = WScreen;
        WSentenceView = (int) (WScreen * 0.12);
        WKeyboard = round(WScreen - WSentenceView, 6);
        WKey = WKeyboard / 6;
        HKeyboard = round(WKeyboard / phi, 4);
        HKey = HKeyboard / 4;
        WSentenceView = WScreen - WKeyboard;
        WCandidateView = WKeyboard;
        WDictTitle = WCandidateView * 3 / 16;
        WDictButton = (WCandidateView - WDictTitle) / 7;
        W2ndLayerDictButton = WCandidateView / 8;
        HCandidateRow = WScreen / 10;
        HSeparator = HCandidateRow * 4 / 5;
        HCandidateVisible = HCandidateRow * 2 + WScreen / 60;
        HInputView = HKeyboard + HCandidateVisible;
        HSentenceView = HInputView;
        HCandidateView = HInputView;
        HSentenceButton = (int) ((HSentenceView - u - u) / 9);
        HLanguageSelectorKey = (int) ((HSentenceView - u - u) / 6);
        HSentencePaddingTop = (HInputView - HSentenceButton * 9) / 2;

        keyIcon = HKey * 4 / 5;
        FSentence = u * 4.5f;
        FCandidate = u * 4;
        FBig = u * 6.5f;
        FFr = u * 5;
        FMid = u * 3;
        FSmall = u * 2;
    }
}
