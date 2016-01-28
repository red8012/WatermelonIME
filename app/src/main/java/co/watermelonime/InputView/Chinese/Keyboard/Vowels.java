package co.watermelonime.InputView.Chinese.Keyboard;

import android.os.Process;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Font;
import co.watermelonime.Common.TextLayoutFactory;
import co.watermelonime.Common.Timer;
import co.watermelonime.InputView.Chinese.Common;

public class Vowels {
    public static final char code[] = {
            'a', 'e', 'i', 'm', 'q', 'u',
            'b', 'f', 'j', 'n', 'r', 'v',
            'c', 'g', 'k', 'o', 's', 'w',
            'd', 'h', 'l', 'p', 't'};
    public static final int[][] keysToChange = {
            {4, 10, 12, 15, 17, 19, 22}, // 0
            {4, 8, 15, 17},
            {5, 16, 17, 18, 22},
            {1, 2, 4, 6, 7, 8, 11, 12, 13, 14, 19, 20},
            {16, 17, 18, 22},
            {4, 15, 16, 17, 18, 22}, // 5
            {4, 10, 12, 15, 17, 22},
            {4, 7, 8, 15, 17, 22},
            {5, 7, 16, 17, 18, 22},
            {1, 2, 4, 6, 7, 8, 11, 12, 13, 14, 19, 20},
            {7, 16, 17, 18, 22}, // 10
            {4, 7, 15, 16, 17, 18, 22},
            {4, 10, 15, 17},
            {4},
            {5, 16, 17, 18, 22},
            {1, 2, 4, 6, 7, 8, 11, 12, 13, 14, 19, 20}, // 15
            {16, 17, 18, 21, 22},
            {4, 7, 15, 16, 17, 18, 22},
            {1, 3, 4, 5, 9, 10, 12, 13, 15, 17, 18, 22},
            {4, 8},
            {}, // 20
            {0, 1, 4, 7, 15, 16, 17, 18, 22},
    };
    public static final char[][] vowelToChange = {
            {'u', 'a', 'f', 'a', 'v', 'd', 'p'},
            {'1', 'i', '2', '3'},
            {'c', 'j', 'b', 'c', 'g'},
            {'\0', '\0', 'u', '\0', '\0', 'n', 'u', '\0', '\0', 'o', '\0', 't'},
            {'u', 'u', '4', 'u'},
            {'e', 'r', 'e', 'b', '5', 'b'},
            {'e', 'e', '\0', 'e', '\0', '\0'},
            {'a', 'a', 'a', 'a', 'e', '\0'},
            {'\0', 'e', '\0', '\0', 'c', '\0'},
            {'\0', 'm', 'u', '\0', '\0', 'r', 'w', 'd', '\0', '\0', '\0', '\0'},
            {'g', '\0', '\0', '\0', '\0'},
            {'u', 'e', 'p', '\0', '\0', '\0', '\0'},
            {'e', 'e', 'a', 'a'},
            {'u'},
            {'6', 'r', 'b', '7', 'j'},
            {'\0', 'm', '\0', '\0', '\0', 'o', 'w', 'd', 'o', 'o', 'p', 'p'},
            {'u', 'u', '\0', 'u', 'u'},
            {'r', 'b', '\0', 'u', '\0', '\0', '\0'},
            {'f', '『', '』', '\'', '「', '」', '+', '&', '（', '）', '=', '…'},
            {'a', '8'},
            {},
            {'\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0'}
    };
    public static final char[][] textToChange = {
            {'比', '吧', '被', '把', '不', '別', '並'},
            {'的', '但', '得', '地'},
            {'個', '跟', '過', '各', '搞'},
            {'\0', '\0', '及', '\0', '\0', '僅', '即', '\0', '\0', '將', '\0', '就'},
            {'之', '只', '著', '至'},
            {'在', '最', '再', '做', '怎', '作'},
            {'拍', '排', '\0', '派', '\0', '\0'},
            {'它', '他', '她', '牠', '太', '\0'},
            {'\0', '開', '\0', '\0', '可', '\0'},
            {'\0', '前', '其', '\0', '\0', '卻', '去', '且', '\0', '\0', '\0', '\0'},
            {'超', '\0', '\0', '\0', '\0'},
            {'此', '才', '從', '\0', '\0', '\0', '\0'},
            {'買', '賣', '嗎', '嘛'},
            {'你'},
            {'和', '會', '或', '還', '很'},
            {'\0', '先', '\0', '\0', '\0', '想', '須', '些', '向', '像', '型', '性'},
            {'是', '時', '\0', '使', '事'},
            {'雖', '所', '\0', '似', '\0', '\0', '\0'},
            {'\0', '『', '』', '\'', '「', '」', '+', '&', '（', '）', '=', '…'},
            {'啦', '了'},
            {},
            {'─', '：', '、', '；', '？', '。', '！', '‧', '，'}
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
        OnTouchPunctuation onTouchPunctuation = new OnTouchPunctuation();
        for (int i = 0; i < 23; ++i) {
            String s = Common.vowelStrings[i];
            TextLayoutFactory
                    te = s.length() > 1 ? Font.mid : Font.big,
                    td = s.length() > 1 ? Font.midDisabled : Font.bigDisabled;
            ChineseKey key = new ChineseKey(te.make(s), null, Colour.NORMAL);
            enabledKeys[i] = key;
            key.pinyin = code[i];
            key.character = '?';
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
                final char textToChange = Vowels.textToChange[kb][i];
                final char vowelToChange = Vowels.vowelToChange[kb][i];

                if (textToChange == '\0') {
                    keyArray[kb][keyToChange] = disabledKeys[keyToChange];
                } else if (kb != 18 && kb != 21) {
                    final ChineseKey k = new ChineseKey(
                            Font.big.make(String.valueOf(textToChange)), null, Colour.CHARACTER);
                    k.setOnTouchListener(onTouchVowel);
                    k.action = ChineseKey.CHARACTER;
                    k.pinyin = vowelToChange;
                    k.character = textToChange;
                    keyArray[kb][keyToChange] = k;
                } else {
                    final ChineseKey k = new ChineseKey(
                            Font.big.make(String.valueOf(textToChange)), null, Colour.CHARACTER);
                    k.action = ChineseKey.PUNCTUATION;
                    k.character = textToChange;
                    k.setOnTouchListener(onTouchPunctuation);
                    keyArray[kb][keyToChange] = k;
                }
            }
        }
    }
}
