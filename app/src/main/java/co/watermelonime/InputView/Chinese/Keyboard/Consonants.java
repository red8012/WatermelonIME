package co.watermelonime.InputView.Chinese.Keyboard;

import java.util.ArrayList;

import co.watermelonime.C;
import co.watermelonime.ChineseKey;
import co.watermelonime.R;

public class Consonants {
    public static ChineseKey[] keys = new ChineseKey[24];

    @SuppressWarnings("ObjectAllocationInLoop")
    public static void buildKeys() {
        ArrayList<ChineseKey> keys = new ArrayList<>(24);
        for (int i = 0; i < 19; i++)
            keys.add(new ChineseKey(C.bigFont.make(Common.consonantStrings[i]),
                    null, C.COLOR_NORMAL));

        keys.add(18, new ChineseKey(C.frFont.make("ㄈ"),
                C.smallFont.make("符號"), C.COLOR_NORMAL));
        ChineseKey key = new ChineseKey(R.drawable.enter, C.COLOR_FUNCTION);
        key.dy += C.u / 4;
        keys.add(key);
        keys.add(new ChineseKey(C.midFont.make("ㄧㄨㄩ"),
                C.smallFont.make("...其他"), C.COLOR_NORMAL));
        keys.add(new ChineseKey(C.frFont.make("ㄖ"),
                C.smallFont.make("符號"),
                C.COLOR_NORMAL));
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
