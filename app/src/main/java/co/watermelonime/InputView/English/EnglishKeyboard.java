package co.watermelonime.InputView.English;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Listeners;
import co.watermelonime.Common.Size;
import co.watermelonime.InputView.Chinese.Sentence.LanguageSelector;
import co.watermelonime.InputView.Number.NumKey;
import co.watermelonime.R;

public class EnglishKeyboard extends ViewGroup {
    public static final String lowercase[] = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
            "q", "w", "e", "r", "t", "y", "u", "i", "o", "p",
            "a", "s", "d", "f", "g", "h", "j", "k", "l",
            "z", "x", "c", "v", "b", "n", "m"};
    public static final String punctuation[] = {
            "!", "@", "#", "$", "%", "^", "&", "*", "(", ")",
            "~", "`", "_", "-", "+", "=", "{", "}", "|", "\\",
            "£", "€", "¥", ":", ";", "\"", "'", "[", "]",
            "°", "<", ">", "?", ",", ".", "/"};
    public final static int LOWER = 0, UPPER = 1, PUNCTUATION = 2,
            CAPSLOCK = 3, PUNCTUATION_LOCK = 4;
    static final OnTouchListener functionKeyListener = (v, event) -> {
        NumKey key = (NumKey) v;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                C.mainService.sendDownUpKeyEvents(key.keyCode);
                key.setBackgroundColor(Colour.CANDIDATE_SELECTED);
                return true;
            case MotionEvent.ACTION_UP:
                key.setBackgroundColor(Colour.FUNCTION);
                return true;
        }
        return false;
    };
    final static View[] bottomRow = new View[6];
    final static EnglishKey[] leftPunctuation = new EnglishKey[3];
    final static int COMMA = 0, SLASH = 1, AT = 2;
    final static int QWERTY = 0, DVORAK = 1, COLEMAK = 2;
    static EnglishKey[][] keys = new EnglishKey[][]{
            new EnglishKey[36], new EnglishKey[36], new EnglishKey[36], new EnglishKey[36], null};
    static int mode = LOWER, type = QWERTY, modeBeforePunctuation;
    static EnglishKey shiftKey, backspace, punctuationKey;
    static boolean
            isShiftPressed = false,
            isPunctuationPressed = false,
            committed = false;


    public EnglishKeyboard() {
        super(C.mainService);
        setBackgroundColor(Colour.NORMAL);
        setMeasuredDimension(Size.WInputView, Size.HInputView);
        for (int i = 0; i < 36; i++) {
            keys[LOWER][i] = new EnglishKey(lowercase[i]);
            keys[UPPER][i] = new EnglishKey(lowercase[i].toUpperCase());
            keys[PUNCTUATION][i] = new EnglishKey(punctuation[i]);
        }
        keys[CAPSLOCK] = keys[UPPER];
        keys[PUNCTUATION_LOCK] = keys[PUNCTUATION];

        shiftKey = new EnglishKey(R.drawable.arrow_bold_up, Size.WEnglishKey * 4 / 3);
        shiftKey.setOnTouchListener((v, event) -> {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    isShiftPressed = true;
                    if (mode == LOWER) {
                        changeMode(UPPER);
                        committed = false;
                    } else if (mode == UPPER) {
                        changeMode(CAPSLOCK);
                    } else if (mode == CAPSLOCK) {
                        changeMode(LOWER);
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    if (committed && mode == UPPER) {
                        changeMode(LOWER);
                    }
                    isShiftPressed = false;
                    return true;
            }
            return false;
        });
        backspace = new EnglishKey(R.drawable.erase, Size.WEnglishKey * 5 / 3);
        backspace.setOnTouchListener(Listeners.backspace);

        EnglishKey toChinese = new EnglishKey("中", Size.WEnglishKey * 4 / 3);
        toChinese.setOnTouchListener((v, event) -> {
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                LanguageSelector.setInputLanguage(LanguageSelector.CHINESE);
                return true;
            }
            return false;
        });

        punctuationKey = new EnglishKey(";!?", Size.WEnglishKey);
        punctuationKey.setOnTouchListener((v, event) -> {
//            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
//                if (mode != PUNCTUATION) {
//                    modeBeforePunctuation = mode;
//                    changeMode(PUNCTUATION);
//                    v.setBackgroundColor(Colour.CANDIDATE_SELECTED);
//                } else {
//                    changeMode(modeBeforePunctuation);
//                    v.setBackgroundColor(Colour.FUNCTION);
//                }
//                return true;
//            }
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    isPunctuationPressed = true;
                    if (mode != PUNCTUATION && mode != PUNCTUATION_LOCK) {
                        modeBeforePunctuation = mode;
                        changeMode(PUNCTUATION);
                        committed = false;
                    } else if (mode == PUNCTUATION) {
                        changeMode(PUNCTUATION_LOCK);
                    } else if (mode == PUNCTUATION_LOCK) {
                        changeMode(modeBeforePunctuation);
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    if (committed && mode == PUNCTUATION) {
                        changeMode(modeBeforePunctuation);
                    }
                    isPunctuationPressed = false;
                    return true;
            }
            return false;
        });

        leftPunctuation[COMMA] = new EnglishKey(",");
        leftPunctuation[SLASH] = new EnglishKey("/");
        leftPunctuation[AT] = new EnglishKey("@");

        EnglishKey enter = new EnglishKey(R.drawable.keyboard_return_white, Size.WEnglishKey * 5 / 3);
        enter.setOnTouchListener(Listeners.enter);

        bottomRow[0] = toChinese;
        bottomRow[1] = punctuationKey;
        bottomRow[2] = leftPunctuation[COMMA];
        bottomRow[3] = new SpaceBar();
        bottomRow[4] = new EnglishKey(".");
        bottomRow[5] = enter;
        changeMode(LOWER);
    }

    public void changeMode(int m) {
        mode = m;
        if (mode == LOWER) shiftKey.setBackgroundColor(Colour.FUNCTION);
        else if (mode == UPPER) shiftKey.setBackgroundColor(Colour.CANDIDATE_SELECTED);
        else if (mode == CAPSLOCK) shiftKey.setBackgroundColor(Colour.CHARACTER);

        if (mode == PUNCTUATION) punctuationKey.setBackgroundColor(Colour.CANDIDATE_SELECTED);
        else if (mode == PUNCTUATION_LOCK) punctuationKey.setBackgroundColor(Colour.CHARACTER);
        else punctuationKey.setBackgroundColor(Colour.FUNCTION);

        removeAllViews();
        for (EnglishKey i : keys[mode])
            addView(i);
        for (View v : bottomRow)
            if (v != null)
                addView(v);
        if (mode != PUNCTUATION && mode != PUNCTUATION_LOCK)
            addView(shiftKey);
        addView(backspace);
        layout(0, 0, 0, 0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i("EnglishKeyboard", "onLayout");
        l = 0;
        t = Size.HEnglishKey;
        r = Size.WEnglishKey;
        for (int i = 0; i < 36; ++i) {
            EnglishKey k = keys[mode][i];
            k.layout(r - Size.WEnglishKey, t, r, t + Size.HEnglishKey);
            r += Size.WEnglishKey;
            if (i == 9 || i == 19 || i == 28) {
                r = Size.WEnglishKey;
                if (i == 19) r += Size.WEnglishKey / 3;
                if (i == 28) r += Size.WEnglishKey * 4 / 3;
                t += Size.HEnglishKey;
            }
        }

        if (mode != PUNCTUATION)
            shiftKey.layout(0, t, Size.WEnglishKey * 4 / 3, t + Size.HEnglishKey);
        backspace.layout(Size.WInputView - Size.WEnglishKey * 5 / 3, t, Size.WInputView, t + Size.HEnglishKey);


        t += Size.HEnglishKey;
        for (View v : bottomRow) {
            if (v != null) {
                int w = v.getMeasuredWidth(), h = v.getMeasuredHeight();
                v.layout(l, t, l + w, t + h);
                l += w;
            }
        }

    }
}
