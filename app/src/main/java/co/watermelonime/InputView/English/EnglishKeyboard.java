package co.watermelonime.InputView.English;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Size;
import co.watermelonime.InputView.Chinese.Sentence.LanguageSelector;
import co.watermelonime.InputView.Number.NumKey;

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
    final static int LOWER = 0, UPPER = 1, PUNCTUATION = 2;
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
    final static EnglishKey[][] keys = new EnglishKey[][]{new EnglishKey[36], new EnglishKey[36], new EnglishKey[36]};
    final static View[] buttomRow = new View[6];
    final static EnglishKey[] leftPunctuation = new EnglishKey[3];
    final static int COMMA = 0, SLASH = 1, AT = 2;
    static int mode = LOWER;


    public EnglishKeyboard() {
        super(C.mainService);
        setBackgroundColor(Colour.NORMAL);
        setMeasuredDimension(Size.WInputView, Size.HInputView);
        for (int i = 0; i < 36; i++) {
            keys[LOWER][i] = new EnglishKey(lowercase[i]);
            keys[UPPER][i] = new EnglishKey(lowercase[i].toUpperCase());
            keys[PUNCTUATION][i] = new EnglishKey(punctuation[i]);
        }

        EnglishKey k = new EnglishKey("中");
        k.setBackgroundColor(Colour.SENTENCE);
        k.setOnTouchListener((v, event) -> {
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                LanguageSelector.setInputLanguage(LanguageSelector.CHINESE);
                return true;
            }
            return false;
        });

        leftPunctuation[COMMA] = new EnglishKey(",");
        leftPunctuation[SLASH] = new EnglishKey("/");
        leftPunctuation[AT] = new EnglishKey("@");

        buttomRow[0] = k;
        buttomRow[1] = leftPunctuation[COMMA];
        buttomRow[2] = new SpaceBar();
        buttomRow[3] = new EnglishKey(".");

        for (EnglishKey i : keys[LOWER])
            addView(i);
        for (View v : buttomRow)
            if (v != null)
                addView(v);
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
            System.out.print(k.text);
            r += Size.WEnglishKey;
            if (i == 9 || i == 19 || i == 28 || i == 35) {
                r = Size.WEnglishKey;
                t += Size.HEnglishKey;
            }
        }

        for (View v : buttomRow) {
            if (v != null) {
                int w = v.getMeasuredWidth(), h = v.getMeasuredHeight();
                v.layout(l, t, l + w, t + h);
                l += w;
            }
        }
    }
}
