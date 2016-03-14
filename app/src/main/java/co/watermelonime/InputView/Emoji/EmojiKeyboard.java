package co.watermelonime.InputView.Emoji;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Size;
import co.watermelonime.Common.Timer;
import co.watermelonime.InputView.Emoji.EmojiKey;
import co.watermelonime.InputView.Emoji.Expressions;
import co.watermelonime.R;

/**
 * Created by din on 2016/3/7.
 */
public class EmojiKeyboard extends ViewGroup {

    public static final String normal[] = {
            "\ue415","\ue056","\ue057","\ue414","\ue405","\ue106","\ue418","\ue417",
            "\ue40d","\ue40a","\ue404","\ue105","\ue409","\ue40e","\ue402","\ue108",
            "","","","","","","","",
            "","","","","","","","",
            "","","","","","","","",
            "","","","","","","","",
            ":-)",";-)",":-(",":-o",":-D",":-P",":@",":-S",
            ":$","B-)",":'(",":-*",">:)","O:)",":-/",":|",
            ":-B",":-SS",":-))","|-O","8-)",":-&",":-?","/:-)",
            "<3","@):-","~o)",")-|","*-:-)","(b)","(u)",""
    };
    public static final String emoticon[] = {
            "\uD83D\uDE00","\uD83D\uDE2C","\uD83D\uDE01","\uD83D\uDE02","\uD83D\uDE03","\uD83D\uDE04",
            "\uD83D\uDE07","\uD83D\uDE09","\uD83D\uDE0A","\uD83D\uDE42","\uD83D\uDE43","\uD83D\uDE05",
            "\uD83D\uDE0D","\uD83D\uDE18","\uD83D\uDE17","\uD83D\uDE19","\uD83D\uDE1A","\uD83D\uDE1C",
            "\uD83E\uDD11","\uD83E\uDD13","\uD83D\uDE0E","\uD83E\uDD17","\uD83D\uDE0F","\uD83D\uDE36",
            "\uD83D\uDE0D","\uD83D\uDE18","\uD83D\uDE17","\uD83D\uDE19","\uD83D\uDE1A","\uD83D\uDE1C",
            "null","null","\uD83D\uDE0E","\uD83E\uDD17","\uD83D\uDE0F","\uD83D\uDE36"
//            "\uD83D\uDE00","\uD83D\uDE2C","\uD83D\uDE01","\uD83D\uDE02","\uD83D\uDE03","\uD83D\uDE04","\uD83D\uDE05","\uD83D\uDE06",
//            "\uD83D\uDE07","\uD83D\uDE09","\uD83D\uDE0A","\uD83D\uDE42","\uD83D\uDE43","☺️","\uD83D\uDE0B","\uD83D\uDE0C",
//            "\uD83D\uDE0D","\uD83D\uDE18","\uD83D\uDE17","\uD83D\uDE19","\uD83D\uDE1A","\uD83D\uDE1C","\uD83D\uDE1D","\uD83D\uDE1B",
//            "\uD83E\uDD11","\uD83E\uDD13","\uD83D\uDE0E","\uD83E\uDD17","\uD83D\uDE0F","\uD83D\uDE36","\uD83D\uDE10","\uD83D\uDE11",
//            "±", ":", "∞", "#", null, null,
//            "$", "¥", "€", "<", ">", "=",
//            "7", "8", "9", "(", ")", "%",
//            "4", "5", "6", "+", "×", "÷",
//            "1", "2", "3", "-", "*", "/",
//            "0", ".", ",", " "
    };
    public static final String game[] = {
            "","","","","","","","",
            "","","","","","","","",
            "","","","","","","","",
            "","","","","","","",""
    };
    public static final String flower[] = {
            "","","","","","","","",
            "","","","","","","","",
            "","","","","","","","",
            "","","","","","","",""
    };
    public static final String traffic[] = {
            "","","","","","","","",
            "","","","","","","","",
            "","","","","","","","",
            "","","","","","","",""
    };
    public static final String direction[] = {
            "","","","","","","","",
            "","","","","","","","",
            "","","","","","","","",
            "","","","","","","",""
    };
    final static int EMOTICON = 0, CHINESE = 1, CAPITAL = 2;
//    static final OnTouchListener functionKeyListener = (v, event) -> {
//        EmojiKey key = (EmojiKey) v;
//        switch (event.getActionMasked()) {
//            case MotionEvent.ACTION_DOWN:
//                C.mainService.sendDownUpKeyEvents(key.keyCode);
//                key.setBackgroundColor(Colour.CANDIDATE_SELECTED);
//                return true;
//            case MotionEvent.ACTION_UP:
//                key.setBackgroundColor(Colour.FUNCTION);
//                return true;
//        }
//        return false;
//    };
    final static int[] colors = new int[]{Colour.FUNCTION, Colour.reached, Colour.CHARACTER};
//    final static EmojiKey[][] keys = new EmojiKey[][]{new EmojiKey[32], new EmojiKey[32], new EmojiKey[32]};
    final static EmojiKey[][] keys = new EmojiKey[][]{new EmojiKey[32]};
    static int mode = EMOTICON;


//    public static EmojiKey[] currentKeys;
//    public static boolean visible = true;

    public EmojiKeyboard() {
        super(C.mainService);
        setBackgroundColor(Colour.NORMAL);
        setMeasuredDimension(Size.WKeyboard, Size.HKeyboard);
//        setKeys(Expressions.keys);

        for (int i = 0; i < 32; i++) {
            EmojiKey k;
            switch (i) {
//                case 29:
//                    k = new EmojiKey(R.drawable.space);
//                    k.text = " ";
//                    k.setOnTouchListener(NumKey.ontouchListener);
//                    break;
                case 30:
                    k = new EmojiKey(R.drawable.enter);
                    k.setOnTouchListener(((v, event) -> {
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
                    break;
                case 31:
                    k = new EmojiKey(R.drawable.backspace);
                    k.setOnTouchListener(new OnTouchListener() {
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
                    break;
                default:
//                    boolean isDigit = (i > 11 && i < 31 && (i % 6 < 3));
                    k = new EmojiKey(emoticon[i]);
//                    keys[CHINESE][i] = new NumKey(chineseNumber[i], isDigit);
//                    keys[CAPITAL][i] = new NumKey(chineseCapital[i], isDigit);
            }
            keys[EMOTICON][i] = k;
//            if (i == 30 || i == 31) {
//                keys[CHINESE][i] = k;
//                keys[CAPITAL][i] = k;
//            }
        }
        for (EmojiKey i : keys[mode])
            addView(i);
    }

/*
    public void setKeys(EmojiKey[] k) {
        removeAllViews();
        currentKeys = k;
        for (EmojiKey i : k)
            if (i != null)
                addView(i);
            else Log.e("setKeys", "null");
        layout(0, 0, 0, 0);
    }
*/
    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        Log.i("ChineseKeyboard", "onLayout");
        l = 0;
        t = 0;
        r = l + Size.WKey;
        final int end = getChildCount();
        for (int i = 0; i < end; ++i) {
            EmojiKey k = (EmojiKey) getChildAt(i);
            if (k != null)
                k.layout(r - Size.WKey, t, r, t + Size.HKey);

            r += Size.WKey;
            if (r > Size.WKeyboard) {
                r = Size.WKey;
                t += Size.HEmojiKey;
            }
        }
    }

}
