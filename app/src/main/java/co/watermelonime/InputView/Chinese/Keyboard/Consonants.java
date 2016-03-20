package co.watermelonime.InputView.Chinese.Keyboard;

import java.util.ArrayList;

import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Font;
import co.watermelonime.R;

public class Consonants {
    final public static String[] displayTexts = {
            "ㄅ", "ㄉ", "ㄍ", "ㄐ", "ㄓ", "ㄗ",
            "ㄆ", "ㄊ", "ㄎ", "ㄑ", "ㄔ", "ㄘ",
            "ㄇ", "ㄋ", "ㄏ", "ㄒ", "ㄕ", "ㄙ",
            "ㄌ"};
    public static ChineseKey[] keys = new ChineseKey[24];
    public static ChineseKey enter, backspace;

    @SuppressWarnings("ObjectAllocationInLoop")
    public static void buildKeys() {
        ArrayList<ChineseKey> keys = new ArrayList<>(24);
        for (int i = 0; i < 19; i++)
            keys.add(new ChineseKey(Font.big.make(displayTexts[i]),
                    null, Colour.NORMAL));

        keys.add(18, new ChineseKey(Font.fr.make("ㄈ"),
                Font.small.make("「」"), Colour.NORMAL));

        enter = new ChineseKey(R.drawable.keyboard_return_white, Colour.FUNCTION);
//        enter = new ChineseKey(R.drawable.enter, Colour.FUNCTION);
        enter.setOnTouchListener(new OnTouchEnter());
        keys.add(enter);

        keys.add(new ChineseKey(Font.mid.make("ㄧㄨㄩ"),
                Font.small.make("...其他"), Colour.NORMAL));
        keys.add(new ChineseKey(Font.fr.make("ㄖ"),
                Font.small.make("！。？"),
                Colour.NORMAL));

        backspace = new ChineseKey(R.drawable.erase, Colour.FUNCTION);
        backspace.setOnTouchListener(new OnTouchDel());
        keys.add(backspace);

        Consonants.keys = keys.toArray(Consonants.keys);
    }

    public static void addListeners() {
        OnTouchConsonant onTouchConsonant = new OnTouchConsonant();
        for (int i = 0; i < 20; i++) {
            Consonants.keys[i].action = i;
            Consonants.keys[i].setOnTouchListener(onTouchConsonant);
        }
        for (int i = 21; i < 23; i++) {
            Consonants.keys[i].action = i - 1;
            Consonants.keys[i].setOnTouchListener(onTouchConsonant);
        }
    }
}
