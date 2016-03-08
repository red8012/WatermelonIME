package co.watermelonime.InputView.Numpad;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Size;
import co.watermelonime.R;

public class NumberKeyboard extends ViewGroup {
    public static final String number[] = {
            "±", ":", "∞", "#", null, null,
            "$", "¥", "€", "<", ">", "=",
            "7", "8", "9", "(", ")", "%",
            "4", "5", "6", "+", "×", "÷",
            "1", "2", "3", "-", "*", "/",
            "0", ".", ",", " "};
    public static final String chineseNumber[] = {
            " ", " ", " ", " ", null, null,
            "年", "月", "日", "時", "分", "秒",
            "七", "八", "九", "十", "百", "千",
            "四", "五", "六", "萬", "億", "兆",
            "一", "二", "三", "度", "元", "號",
            "〇", "‧", "負", " "};
    public static final String chineseCapital[] = {
            " ", " ", " ", " ", null, null,
            "年", "月", "日", "時", "分", "秒",
            "柒", "捌", "玖", "拾", "佰", "仟",
            "肆", "伍", "陸", "萬", "億", "兆",
            "壹", "貳", "參", "度", "圓", "號",
            "零", "點", "負", "⬆"};
    final static int ARABIC = 0, CHINESE = 1, CAPITAL = 2;
    static NumKey[] keys = new NumKey[36];
    static NumKey[] chineseNumberKeys = new NumKey[36];
    static NumKey[] chineseNumberCapitalKeys = new NumKey[36];
    static int mode;

    public NumberKeyboard() {
        super(C.mainService);
        setBackgroundColor(Colour.NORMAL);
        for (int i = 0; i < number.length; i++) {
            NumKey k;
            switch (i) {
                case 4:
                    k = new NumKey(R.drawable.tab);
                    k.keyCode = KeyEvent.KEYCODE_TAB;
                    k.setOnTouchListener(NumKey.functionKeyListener);
                    chineseNumberKeys[i] = k;
                    chineseNumberCapitalKeys[i] = k;
                    break;
                case 5:
                    k = new NumKey(R.drawable.capslock);
                    k.setOnTouchListener(NumKey.shiftListener);
                    chineseNumberKeys[i] = k;
                    chineseNumberCapitalKeys[i] = k;
                    break;
                case 33:
                    k = new NumKey(R.drawable.space);
                    k.text = " ";
                    k.setOnTouchListener(NumKey.ontouchListener);
                    chineseNumberKeys[i] = k;
                    chineseNumberCapitalKeys[i] = k;
                    break;
                default:
                    k = new NumKey(number[i]);
                    chineseNumberKeys[i] = new NumKey(chineseNumber[i]);
                    chineseNumberCapitalKeys[i] = new NumKey(chineseCapital[i]);
            }
            keys[i] = k;
        }

        keys[34] = new NumKey(R.drawable.enter);
        keys[34].setOnTouchListener(((v, event) -> {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    C.mainService.sendDownUpKeyEvents(KeyEvent.KEYCODE_ENTER);
                    v.setBackgroundColor(Colour.CANDIDATE_SELECTED);
                    return true;
                case MotionEvent.ACTION_UP:
                    v.setBackgroundColor(Colour.FUNCTION);
            }
            return false;
        }));
        chineseNumberKeys[34] = keys[34];

        keys[35] = new NumKey(R.drawable.backspace);
        keys[35].setOnTouchListener(new OnTouchListener() {
            int lastX;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int x = (int) event.getRawX();
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = x;
                        C.mainService.sendDownUpKeyEvents(KeyEvent.KEYCODE_DEL);
                        v.setBackgroundColor(Colour.CANDIDATE_SELECTED);
                        return true;
                    case MotionEvent.ACTION_UP:
                        v.setBackgroundColor(Colour.FUNCTION);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        if (x < lastX - Size.HCandidateRow || x > lastX + Size.HCandidateRow) {
                            lastX = x;
                            C.mainService.sendDownUpKeyEvents(KeyEvent.KEYCODE_DEL);
                            return true;
                        }
                }
                return false;
            }
        });
        chineseNumberKeys[35] = keys[35];

        for (NumKey i: keys)
            addView(i);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        l = 0;
        t = 0;
        r = l + Size.WKey;
        final int end = getChildCount();
        for (int i = 0; i < end; ++i) {
            NumKey k = (NumKey) getChildAt(i);
            if (k != null)
                k.layout(r - Size.WKey, t, r, t + Size.HNumKey);

            r += Size.WKey;
            if (r > Size.WKeyboard) {
                r = Size.WKey;
                t += Size.HNumKey;
            }
        }
    }
}
