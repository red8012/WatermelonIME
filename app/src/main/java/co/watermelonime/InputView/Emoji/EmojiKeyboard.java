package co.watermelonime.InputView.Emoji;

import android.graphics.Color;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Size;
import co.watermelonime.R;

/**
 * Created by din on 2016/3/7.
 */
public class EmojiKeyboard extends ViewGroup {

    public static final String emoticon[] = {
            //the 2 of front is emojitag

            "\uD83D\uDE00","\uD83D\uDE2C","\uD83D\uDE01","\uD83D\uDE02","\uD83D\uDE03","\uD83D\uDE04",
            "\uD83D\uDE05","\uD83D\uDE06","\uD83D\uDE07","\uD83D\uDE09","\uD83D\uDE0A","\uD83D\uDE42",
            "\uD83D\uDE43","☺️","\uD83D\uDE0B","\uD83D\uDE0C","\uD83D\uDE0D","\uD83D\uDE18",
            "\uD83D\uDE17","\uD83D\uDE19","\uD83D\uDE1A","\uD83D\uDE1C","\uD83D\uDE1D","\uD83D\uDE1B",
            "\uD83E\uDD11","\uD83E\uDD13","\uD83D\uDE0E","\uD83E\uDD17","\uD83D\uDE0F","\uD83D\uDE36",
            "\uD83D\uDE10","\uD83D\uDE11","\uD83D\uDE12","\uD83D\uDE44","\uD83E\uDD14","\uD83D\uDE33",

//            "\uD83D\uDE1E","\uD83D\uDE1F","\uD83D\uDE20","\uD83D\uDE21","\uD83D\uDE14","\uD83D\uDE15",
//            "\uD83D\uDE41","☹️","\uD83D\uDE23","\uD83D\uDE16","\uD83D\uDE2B","\uD83D\uDE29",
//            "\uD83D\uDE24","\uD83D\uDE2E","\uD83D\uDE31","\uD83D\uDE28","\uD83D\uDE30","\uD83D\uDE2F",
//            "\uD83D\uDE26","\uD83D\uDE27","\uD83D\uDE22","\uD83D\uDE25","\uD83D\uDE2A","\uD83D\uDE13",
//            "\uD83D\uDE2D","\uD83D\uDE35","\uD83D\uDE32","\uD83E\uDD10","\uD83D\uDE37","\uD83E\uDD12",
//            "\uD83E\uDD15","\uD83D\uDE34"
    };

    public static final String recent[] = {
    "!", "@", "#", "$", "%", "^",
            "&", "*", "(", ")", "-", "+",
            "[", "]", "{", "}", "/", "*",
            "<", ">", ",", ".", ";", "~",
            "®", "©", "℗", "™", "℃", "℉",
            "⨀", "⨁", "⨂", "$", "€", "¥"
//            ":-)",";-)",":-(",":-o",":-D",":-P",
//            ":@",":-S",":$","B-)",":'(",":-*",
//            ">:)","O:)",":-/",":|",":-B",":-SS",
//            ":-))","|-O","8-)",":-&",":-?","/:-)",
//            "<3","@):-","~o)",")-|","*-:-)","(b)",
//            "(u)",":-)",";-)",":-(",":-o",":-D"
    };

    public static final String game[] = {
            "","","","","","","","",
            "","","","","","","","",
            "","","","","","","","",
            "","","","","","","",""
    };
    final static int EMOTICON = 0, RECENT = 1;

//    final static int[] colors = new int[]{Colour.FUNCTION, Colour.reached, Colour.CANDIDATE_SELECTED};
    final static EmojiKey[][] keys = new EmojiKey[][]{new EmojiKey[36], new EmojiKey[36]};
    static int mode = EMOTICON;
    public static EmojiSelector emojiSelector;

    public EmojiKeyboard() {
        super(C.mainService);
        emojiSelector = new EmojiSelector();
        setBackgroundColor(Colour.NORMAL);
        setMeasuredDimension(Size.WKeyboard, Size.HKeyboard);

        for (int i = 0; i < 36; i++) {
            EmojiKey k;
            switch (i) {
                case 0:
                    k = new EmojiKey(R.drawable.emoji_happy);
                    k.setOnTouchListener((v, event) -> {
                        EmojiKey key = (EmojiKey) v;
                        switch (event.getActionMasked()) {
                            case MotionEvent.ACTION_DOWN:
                                C.emojiKeyboard.removeAllViews();
                                mode = EMOTICON;
                                key.setBackgroundColor(Color.rgb(70,100,160));
                                for (EmojiKey j : keys[mode])
                                    addView(j);
                                layout(0, 0, 0, 0);
                                return true;
                        }
                        return false;
                    });
                    break;
                case 1:
                    k = new EmojiKey(R.drawable.icon);
                    k.setOnTouchListener((v, event) -> {
                        EmojiKey key = (EmojiKey) v;
                        switch (event.getActionMasked()) {
                            case MotionEvent.ACTION_DOWN:
                                C.emojiKeyboard.removeAllViews();
                                mode = RECENT;
                                key.setBackgroundColor(Color.rgb(70,100,160));
                                for (EmojiKey j : keys[mode])
                                    addView(j);
                                layout(0, 0, 0, 0);
                                return true;
                        }
                        return false;
                    });
                    break;
                case 34:
                    k = new EmojiKey(R.drawable.keyboard_return_white);
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
                case 35:
                    k = new EmojiKey(R.drawable.erase);
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

                    k = new EmojiKey(emoticon[i]);
                    keys[RECENT][i] = new EmojiKey(recent[i]);
            }
            keys[EMOTICON][i] = k;
            if (i == 0 || i ==1 || i == 34 || i == 35) {
                keys[RECENT][i] = k;
            }
        }
        for (EmojiKey i : keys[mode])
            addView(i);
    }

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
                t += Size.HNumKey;
            }
        }
    }

}
