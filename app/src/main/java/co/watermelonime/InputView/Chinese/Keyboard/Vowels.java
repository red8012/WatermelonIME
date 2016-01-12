package co.watermelonime.InputView.Chinese.Keyboard;

import android.os.Process;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Font;
import co.watermelonime.Common.TextLayoutFactory;
import co.watermelonime.Common.Timer;
import co.watermelonime.InputView.Chinese.Common;

public class Vowels {
    public static ChineseKey[] enabledKeys = new ChineseKey[24];
    public static ChineseKey[] disabledKeys = new ChineseKey[24];
    public static ChineseKey[][] keyArray = new ChineseKey[23][24];

    public static void buildKeysAsync() {
        C.threadPool.submit(() -> {
            Timer.t(14);
            android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_DISPLAY);
            buildKeys();
            Consonants.addListeners();
            Timer.t(14, "build keys async");
        });
    }

    public static void buildKeys() {
        OnTouchVowel onTouchVowel = new OnTouchVowel();
        for (int i = 0; i < 23; ++i) {
            String s = Common.vowelStrings[i];
            TextLayoutFactory
                    te = s.length() > 1 ? Font.mid : Font.big,
                    td = s.length() > 1 ? Font.midDisabled : Font.bigDisabled;
            ChineseKey key = new ChineseKey(te.make(s), null, Colour.NORMAL);
            enabledKeys[i] = key;
            key.id = i;
            key.setOnTouchListener(onTouchVowel);
            disabledKeys[i] = new ChineseKey(td.make(s), null, Colour.DISABLED);
        }
        enabledKeys[23] = Common.backspace;
        disabledKeys[23] = Common.backspace;

        for (int i = 0; i < 22; i++) { // for each keyboard
            keyArray[i] = new ChineseKey[24];
            for (int j = 0; j < 24; j++)
                keyArray[i][j] = enabledKeys[j];

            for (int j = Common.keysToChange[i].length - 1; j >= 0; --j) {
                final int keyToChange = Common.keysToChange[i][j];
                final String textToChange = Common.textToChange[i][j];

                if (textToChange == null) {
                    keyArray[i][keyToChange] = disabledKeys[keyToChange];
                } else {
                    final ChineseKey k = new ChineseKey(
                            Font.big.make(textToChange), null, Colour.CHARACTER);
                    k.setOnTouchListener(onTouchVowel);
                    keyArray[i][keyToChange] = k;
                }
            }
        }
    }
}
