package co.watermelonime.InputView.Chinese.Keyboard;

import android.os.Process;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Font;
import co.watermelonime.Common.TextLayoutFactory;
import co.watermelonime.Common.Timer;
import co.watermelonime.InputView.Chinese.Common;

public class Vowels {
    public static final String code[] = {
            "a", "e", "i", "m", "q", "u",
            "b", "f", "j", "n", "r", "v",
            "c", "g", "k", "o", "s", "w",
            "d", "h", "l", "p", "t"};
    public static final String[][] vowelToChange = {
            {"u", "a", "f", "a", "v", "d", "p"},
            {"1", "i", "2", "3"},
            {"c", "j", "b", "c", "g"},
            {null, null, "u", null, null, "n", "u", null, null, "o", null, "t"},
            {"u", "u", "4", "u"},
            {"e", "r", "e", "b", "5", "b"},
            {"e", "e", null, "e", null, null},
            {"a", "a", "a", "a", "e", null},
            {null, "e", null, null, "c", null},
            {null, "m", "u", null, null, "r", "w", "d", null, null, null, null},
            {"g", null, null, null, null},
            {"u", "e", "p", null, null, null, null},
            {"e", "e", "a", "a"},
            {"u"},
            {"6", "r", "b", "7", "j"},
            {null, "m", null, null, null, "o", "w", "d", "o", "o", "p", "p"},
            {"u", "u", null, "u", "u"},
            {"r", "b", null, "u", null, null, null},
            {"f", "『", "』", "\"", "「", "」", "+", "&", "（", "）", "=", "…"},
            {"a", "8"},
            {},
            {null, null, null, null, null, null, null, null, null}
    };
    final static int[] kb18punctuation = {3, 4, 5, 9, 10, 12, 13, 15, 17, 18, 22};
    final static int[] kb21punctuation = {0, 1, 4, 7, 15, 16, 17, 18, 22};
    public static ChineseKey[] enabledKeys = new ChineseKey[24];
    public static ChineseKey[] disabledKeys = new ChineseKey[24];
    public static ChineseKey[][] keyArray = new ChineseKey[23][24];
    public static ChineseKey backspace;

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
            key.input = code[i];
            key.setOnTouchListener(onTouchVowel);
            disabledKeys[i] = new ChineseKey(td.make(s), null, Colour.DISABLED);
        }

        backspace = Consonants.backspace; // Todo: should I do this?
        enabledKeys[23] = backspace;
        disabledKeys[23] = backspace;

        for (int kb = 0; kb < 22; kb++) { // for each keyboard
            keyArray[kb] = new ChineseKey[24];
            for (int i = 0; i < 24; i++)
                keyArray[kb][i] = enabledKeys[i];

            for (int i = Common.keysToChange[kb].length - 1; i >= 0; --i) {
                final int keyToChange = Common.keysToChange[kb][i];
                final String textToChange = Common.textToChange[kb][i];

                if (textToChange == null) {
                    keyArray[kb][keyToChange] = disabledKeys[keyToChange];
                } else if (kb != 18 && kb != 21) {
                    final ChineseKey k = new ChineseKey(
                            Font.big.make(textToChange), null, Colour.CHARACTER);
                    k.setOnTouchListener(onTouchVowel);
                    k.action = ChineseKey.CHARACTER;
                    k.input = textToChange;
//                    try {
//                        k.character = vowelToChange[kb][keyToChange];
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

                    keyArray[kb][keyToChange] = k;
                } else {
                    final ChineseKey k = new ChineseKey(
                            Font.big.make(textToChange), null, Colour.CHARACTER);
                    k.setOnTouchListener(new OnTouchPunctuation(Common.textToChange[kb][i]));
                    keyArray[kb][keyToChange] = k;
                }
            }
        }
    }
}
