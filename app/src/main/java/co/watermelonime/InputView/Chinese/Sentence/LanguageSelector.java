package co.watermelonime.InputView.Chinese.Sentence;

import co.watermelonime.C;
import co.watermelonime.DBCopy;
import co.watermelonime.InputView.Chinese.ChineseInputView;
import co.watermelonime.MainService;
import co.watermelonime.R;

public class LanguageSelector {
    public static final int
            CHINESE = 5,
            ENGLISH = 4,
            NUMBER = 3,
            EMOJI = 2,
            GLOBE = 1,
            SETTINGS = 0;
    static final LanguageSelectorKey[] keys = new LanguageSelectorKey[6];
    public static int inputLanguage = CHINESE, lastInputLanguage = CHINESE;

    public static void init() {
        keys[CHINESE] = new LanguageSelectorKey(CHINESE, "中");
        keys[ENGLISH] = new LanguageSelectorKey(ENGLISH, "En");
        keys[NUMBER] = new LanguageSelectorKey(NUMBER, "12");
        keys[EMOJI] = new LanguageSelectorKey(EMOJI, R.drawable.emoji_happy);
        keys[GLOBE] = new LanguageSelectorKey(GLOBE, R.drawable.globe);
        keys[SETTINGS] = new LanguageSelectorKey(SETTINGS, R.drawable.settings);
    }

    public static void setInputLanguage(final int inputLanguage, boolean recordLastLanguage) {
        if (inputLanguage == GLOBE) {
            MainService.inputMethodManager.showInputMethodPicker();
            return;
        }
        if (inputLanguage == SETTINGS) {
            try {
//                Benchmark2.run();
//                ContactsLearner.start();
                DBCopy.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        if (recordLastLanguage) lastInputLanguage = inputLanguage;
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

    public static void setInputLanguage(final int inputLanguage) {
        setInputLanguage(inputLanguage, true);
    }
}
