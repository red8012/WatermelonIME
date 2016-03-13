package co.watermelonime.InputView.Chinese.Sentence;

import co.watermelonime.C;
import co.watermelonime.InputView.Chinese.ChineseInputView;

public class LanguageSelector {
    public static final int
            CHINESE = 0,
            ENGLISH = 1,
            NUMBER = 2,
            CHINESE_NUMBER = 3,
            EMOJI = 4,
            SETTINGS = 5;
    static final LanguageSelectorKey[] keys = new LanguageSelectorKey[6];
    public static int inputLanguage = 0;

    public static void init() {
        keys[CHINESE] = new LanguageSelectorKey(CHINESE, "中");
        keys[ENGLISH] = new LanguageSelectorKey(ENGLISH, "En");
        keys[NUMBER] = new LanguageSelectorKey(NUMBER, "12");
        keys[CHINESE_NUMBER] = new LanguageSelectorKey(CHINESE_NUMBER, "三");
        keys[EMOJI] = new LanguageSelectorKey(EMOJI, "\uD83D\uDE19");
        keys[SETTINGS] = new LanguageSelectorKey(SETTINGS, "S");
    }

    public static void setInputLanguage(final int inputLanguage) {
        LanguageSelector.inputLanguage = inputLanguage;
        ChineseInputView v = C.chineseInputView;
        v.removeAllViews();
        if (inputLanguage != ENGLISH)
            v.addView(C.sentenceView);
        switch (inputLanguage) {
            case CHINESE:
                v.addView(ChineseInputView.scrollView);
                v.addView(C.chineseKeyboard);
                break;
            case ENGLISH:
                v.addView(C.englishKeyboard);
                break;
            case NUMBER:
                v.addView(C.numberKeyboard);
                break;
            case EMOJI:
                v.addView(C.emoji);
                break;
        }
        v.invalidate(); // TODO: 2016/3/5 check this
    }
}
