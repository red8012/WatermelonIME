package co.watermelonime.InputView.Chinese.Keyboard;

import android.graphics.Color;

import java.util.ArrayList;

import co.watermelonime.C;
import co.watermelonime.ChineseKey;
import co.watermelonime.R;
import co.watermelonime.TextLayoutFactory;

public class Consonants {
    public static ArrayList<ChineseKey> keys = new ArrayList<>(24);

    @SuppressWarnings("ObjectAllocationInLoop")
    public static void buildKeys() {
        C.chineseKeyWidth = C.u * 10;
        C.chineseKeyHeight = C.u * 9;

        TextLayoutFactory
                bigFont = new TextLayoutFactory(C.u * 6.5f, C.sourceSans, Color.WHITE, C.u * 10),
                midFont = new TextLayoutFactory(C.u * 5, C.sourceSans, Color.WHITE, C.u * 10),
                iuvFont = new TextLayoutFactory(C.u * 3, C.sourceSans, Color.WHITE, C.u * 10),
                smallFont = new TextLayoutFactory(C.u * 2, C.sourceSans, Color.WHITE, C.u * 10);

        final String display[] = {
                "ㄅ", "ㄉ", "ㄍ", "ㄐ", "ㄓ", "ㄗ",
                "ㄆ", "ㄊ", "ㄎ", "ㄑ", "ㄔ", "ㄘ",
                "ㄇ", "ㄋ", "ㄏ", "ㄒ", "ㄕ", "ㄙ",
                "ㄌ"};
        for (int i = 0; i < 19; i++) {
            final String text = display[i];
            keys.add(new ChineseKey(bigFont.make(text), null, C.COLOR_NORMAL));
        }
        keys.add(18, new ChineseKey(midFont.make("ㄈ"),
                smallFont.make("符號"), C.COLOR_NORMAL));
        ChineseKey key = new ChineseKey(R.drawable.enter, C.COLOR_FUNCTION);
        key.dy += C.u / 4;
        keys.add(key);
        keys.add(new ChineseKey(iuvFont.make("ㄧㄨㄩ"),
                smallFont.make("...其他"), C.COLOR_NORMAL));
        keys.add(new ChineseKey(midFont.make("ㄖ"),
                smallFont.make("符號"),
                C.COLOR_NORMAL));
        keys.add(new ChineseKey(R.drawable.backspace, C.COLOR_FUNCTION));
    }
}
