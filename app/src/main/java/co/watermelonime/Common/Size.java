package co.watermelonime.Common;

import android.graphics.Point;

public class Size {
    public static final float phi = 1.618034f;
    public static float u;
    public static int
            WScreen,
            WInputView,
            WSentenceView,
            WCandidateView,
            WKeyboard,
            WKey;
    public static int
            HScreen,
            HInputView,
            HSentenceView,
            HSentencePaddingTop,
            HSentenceButton,
            HCandidateView,
            HCandidateVisible,
            HCandidateRow,
            HKeyboard,
            HKey;

    public static int round(float x, int divider) {
        x /= divider;
        return ((int) x) * divider;
    }

    public static void calculate(Point size) {
        WScreen = size.x;
        HScreen = size.y;
        u = WScreen / 60f;
        WInputView = WScreen;
        WSentenceView = (int) (WScreen * 0.12);
        WKeyboard = round(WScreen - WSentenceView, 6);
        WKey = WKeyboard / 6;
        HKeyboard = round(WKeyboard / phi, 4);
        HKey = HKeyboard / 4;
        WSentenceView = WScreen - WKeyboard;
        WCandidateView = WKeyboard;
        HCandidateRow = WScreen / 10; // 6u
        HCandidateVisible = HCandidateRow * 2 + WScreen / 60;
        HInputView = HKeyboard + HCandidateVisible;
        HSentenceView = HInputView;
        HCandidateView = HInputView;
        HSentenceButton = (int) ((HSentenceView - u - u) / 9);
        HSentencePaddingTop = (HInputView - HSentenceButton * 9) / 2;
    }
}
