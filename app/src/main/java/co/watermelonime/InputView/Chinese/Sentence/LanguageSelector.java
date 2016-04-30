package co.watermelonime.InputView.Chinese.Sentence;

import co.watermelonime.C;
import co.watermelonime.InputView.Chinese.ChineseInputView;
import co.watermelonime.R;

public class LanguageSelector {
    public static final int
            CHINESE = 5,
            ENGLISH = 4,
            NUMBER = 3,
            CHINESE_NUMBER = 1,
            EMOJI = 2,
            SETTINGS = 0;
    static final LanguageSelectorKey[] keys = new LanguageSelectorKey[6];
    public static int inputLanguage = CHINESE;

    public static void init() {
        keys[CHINESE] = new LanguageSelectorKey(CHINESE, "中");
        keys[ENGLISH] = new LanguageSelectorKey(ENGLISH, "En");
        keys[NUMBER] = new LanguageSelectorKey(NUMBER, "12");
        keys[CHINESE_NUMBER] = new LanguageSelectorKey(CHINESE_NUMBER, " ");
//        keys[EMOJI] = new LanguageSelectorKey(EMOJI, "\uD83D\uDE00");
        keys[EMOJI] = new LanguageSelectorKey(EMOJI, R.drawable.emoji_happy);
        keys[SETTINGS] = new LanguageSelectorKey(SETTINGS, "S");
    }

    public static void setInputLanguage(final int inputLanguage) {
        if (inputLanguage == CHINESE_NUMBER) return;
        if (inputLanguage == SETTINGS) return;
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
                v.addView(C.emojiKeyboard);
                break;
        }
        v.invalidate(); // TODO: 2016/3/5 check this
    }
}
