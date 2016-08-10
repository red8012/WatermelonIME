package co.watermelonime.Common;

import android.graphics.Point;

import co.watermelonime.C;

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
            WNavigationKey,
            WDictTitle,
            WSeparator,
            WKeyboard,
            WKey,
            WEnglishKey,
            WSpace,
            WEnglishCandidateUsableSpace,
            WChineseLandscapeLeft,
            WSentenceButton;
    public static int
            HScreen,
            HInputView,
            HSentenceView,
            HSentencePaddingTop,
            HSentenceButton,
            HCandidateView,
            HCandidateVisible,
            HCandidateRow,
            HSeparator,
            HKeyboard,
            HNumKey,
            HKey,
            HEnglishKey,
            HEmojiKey;

    public static int round(float x, int divider) {
        x /= divider;
        return ((int) x) * divider;
    }

    public static void calculate(Point size) {
        WScreen = size.x;
        HScreen = size.y;
        C.isLandscape = WScreen > HScreen;
        final boolean p = !C.isLandscape;
        u = p ? WScreen / 70f : WScreen / 140f;
        WInputView = WScreen;
        WEnglishKey = WInputView / 10;
        WSentenceView = (int) (WScreen * 0.12);
        WKeyboard = p ? round(WScreen - WSentenceView, 6) : round(WScreen / 2, 6);
        WSeparator = WKeyboard / 200;
        WChineseLandscapeLeft = WScreen - WKeyboard;
        WKey = WKeyboard / 6;
        HKeyboard = round(WKeyboard / phi * (p ? 1 : .9f), 4);
        HKey = HKeyboard / 4;
        WSentenceView = WScreen - WKeyboard;
        WSentenceButton = p ? WSentenceView : (WSentenceView / 9);
        WCandidateView = p ? WKeyboard : WChineseLandscapeLeft;
        WDictTitle = WCandidateView * 3 / 16;
        WDictButton = (WCandidateView - WDictTitle) / 7;
        W2ndLayerDictButton = WCandidateView / 8;
        WNavigationKey = WCandidateView / 6;
        HCandidateRow = p ? WScreen / 10 : (int) (HKey - Size.u / 2);
        HCandidateVisible = (int) (HCandidateRow * 2 + (p ? (WScreen / 60) : Size.u));
        HSeparator = p ? (HCandidateRow * 4 / 5) : (HCandidateRow * 3 / 5);
        HInputView = p ? HKeyboard + HCandidateVisible : HKeyboard;
        HEnglishKey = p ? HInputView / 6 + 1 : HInputView / 5 + 1;
        HNumKey = HInputView / 6;
        HEmojiKey = HInputView / 8;
        HSentenceView = p ? HInputView : HKey;
        HCandidateView = HInputView;
        HSentenceButton = p ? (int) ((HSentenceView - u - u) / 9) : HKey;
        HSentencePaddingTop = p ? ((HInputView - HSentenceButton * 9) / 2)
                : ((WSentenceView - WSentenceButton * 9) / 2);

        WSpace = WEnglishKey * 4;

        keyIcon = HKey * 4 / 5;
        FSentence = u * 4.5f;
        FCandidate = u * 4;
        FBig = u * 6.5f;
        FFr = u * 5;
        FMid = u * 3;
        FSmall = u * 2;

        WEnglishCandidateUsableSpace = (int) (WInputView - WEnglishKey * 3 - u * 2);
    }
}
