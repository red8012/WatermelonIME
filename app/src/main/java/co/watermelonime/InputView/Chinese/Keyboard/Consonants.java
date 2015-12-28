package co.watermelonime.InputView.Chinese.Keyboard;

import java.util.ArrayList;

import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Font;
import co.watermelonime.InputView.Chinese.Common;
import co.watermelonime.R;

public class Consonants {
    public static ChineseKey[] keys = new ChineseKey[24];

    @SuppressWarnings("ObjectAllocationInLoop")
    public static void buildKeys() {
        ArrayList<ChineseKey> keys = new ArrayList<>(24);
        for (int i = 0; i < 19; i++)
            keys.add(new ChineseKey(Font.big.make(Common.consonantStrings[i]),
                    null, Colour.NORMAL));

        keys.add(18, new ChineseKey(Font.fr.make("ㄈ"),
                Font.small.make("符號 2"), Colour.NORMAL));
        ChineseKey key = new ChineseKey(R.drawable.enter, Colour.FUNCTION);
//        key.dy += C.u / 4;
        keys.add(key);
        keys.add(new ChineseKey(Font.mid.make("ㄧㄨㄩ"),
                Font.small.make("...其他"), Colour.NORMAL));
        keys.add(new ChineseKey(Font.fr.make("ㄖ"),
                Font.small.make("符號 1"),
                Colour.NORMAL));
        keys.add(Common.backspace);

        Consonants.keys = keys.toArray(Consonants.keys);
    }

    public static void addListeners() {
        OnTouchConsonant onTouchConsonant = new OnTouchConsonant();
        for (int i = 0; i < 20; i++) {
            Consonants.keys[i].consonantId = i;
            Consonants.keys[i].setOnTouchListener(onTouchConsonant);
        }
        for (int i = 21; i < 23; i++) {
            Consonants.keys[i].consonantId = i - 1;
            Consonants.keys[i].setOnTouchListener(onTouchConsonant);
        }
    }
}
