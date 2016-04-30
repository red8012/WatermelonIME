package co.watermelonime.InputView.Number;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewGroup;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Listeners;
import co.watermelonime.Common.Size;
import co.watermelonime.R;

public class NumberKeyboard extends ViewGroup {
    public static final String number[] = {
            "±", ":", "∞", null, null, null,
            "$", "¥", "€", "<", ">", "=",
            "(", ")", "%", "7", "8", "9",
            "+", "×", "÷", "4", "5", "6",
            "-", "*", "/", "1", "2", "3",
            "#", null, " ", "0", ".", ","};
    public static final String chineseNumber[] = {
            "年", "月", "日", null, null, null,
            "廿", "卅", "卌", "時", "分", "秒",
            "度", "元", "號", "七", "八", "九",
            "萬", "億", "兆", "四", "五", "六",
            "十", "百", "千", "一", "二", "三",
            "個",  null, " ", "〇", "‧", "-"};
    public static final String chineseCapital[] = {
            "（", "）", "之", null,null, null,
            "年", "月", "日", "時", "分", "秒",
            "度", "圓", "號", "柒", "捌", "玖",
            "萬", "億", "兆", "肆", "伍", "陸",
            "拾", "佰", "仟", "壹", "貳", "參",
            "個", null, " ", "零", "點", "負"};

    final static int ARABIC = 0, CHINESE = 1, CAPITAL = 2;
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
    final static int[] colors = new int[]{Colour.FUNCTION, Colour.reached, Colour.CANDIDATE_SELECTED};
    final static NumKey[][] keys = new NumKey[][]{new NumKey[36], new NumKey[36], new NumKey[36]};
    static int mode = ARABIC;


    public NumberKeyboard() {
        super(C.mainService);
        setBackgroundColor(Colour.NORMAL);
        for (int i = 0; i < 36; i++) {
            NumKey k;
            switch (i) {
                case 3:
                    k = new NumKey(R.drawable.keyboard_tab_white);
                    k.keyCode = KeyEvent.KEYCODE_TAB;
                    k.setOnTouchListener(functionKeyListener);
                    break;
                case 4:
                    k = new NumKey(R.drawable.arrow_bold_up);
                    k.setOnTouchListener((v, event) -> {
                        NumKey key = (NumKey) v;
                        switch (event.getActionMasked()) {
                            case MotionEvent.ACTION_DOWN:
//                                key.setBackgroundColor(Colour.CANDIDATE_SELECTED);
                                C.numberKeyboard.removeAllViews();
                                mode = (mode + 1) % 3;
                                key.setBackgroundColor(colors[mode]);
                                for (NumKey j : keys[mode])
                                    addView(j);
                                layout(0, 0, 0, 0);
                                return true;
//                            case MotionEvent.ACTION_UP:
//                                return true;
                        }
                        return false;
                    });
                    break;
                case 5:
                    k = new NumKey(R.drawable.erase);
                    k.setOnTouchListener(Listeners.backspace);
                    break;
                case 32:
                    k = new NumKey(R.drawable.space_bar_white);
                    k.text = " ";
                    k.setOnTouchListener(NumKey.ontouchListener);
                    break;

                case 31:
                    k = new NumKey(R.drawable.keyboard_return_white);
                    k.setOnTouchListener(Listeners.enter);
                    break;
                default:
                    boolean isDigit = (i > 14 && i < 34 && (i % 6 > 2));
                    k = new NumKey(number[i], isDigit);
                    keys[CHINESE][i] = new NumKey(chineseNumber[i], isDigit);
                    keys[CAPITAL][i] = new NumKey(chineseCapital[i], isDigit);
            }
            keys[ARABIC][i] = k;
            if (i == 3 || i == 4 || i == 5 || i == 31 || i == 32) {
                keys[CHINESE][i] = k;
                keys[CAPITAL][i] = k;
            }
        }
        for (NumKey i : keys[mode])
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
