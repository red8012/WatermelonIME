package co.watermelonime.InputView.Chinese.Keyboard;

import android.os.Process;
import android.view.MotionEvent;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Font;
import co.watermelonime.Common.TextLayoutFactory;
import co.watermelonime.Common.Timer;
import co.watermelonime.Core.Controller;
import co.watermelonime.Core.Engine;
import co.watermelonime.R;

public class Vowels {
    final public static String[] displayTexts = {
            "　ㄧㄨ\nㄚㄚㄚ", "ㄞ", "ㄢ", "ㄧㄨㄩ\nㄢㄢㄢ", "ㄧㄨ\nㄞㄞ", "ㄧ",
            "　ㄧㄨ\nㄛㄛㄛ", "ㄟ", "ㄣ", "ㄧㄨㄩ\nㄣㄣㄣ", "ㄨ\nㄟ", "ㄨ",
            "ㄜㄝ", "ㄠ", "ㄤ", "ㄧㄨ\nㄤㄤ", "ㄧ\nㄠ", "ㄩ",
            "ㄧㄩ\nㄝㄝ", "ㄡ", "ㄥㄦ", "ㄧㄨㄩ\nㄥㄥㄥ", "ㄧ\nㄡ"};
    public static final char code[] = {
            'a', 'e', 'i', 'm', 'q', 'u',
            'b', 'f', 'j', 'n', 'r', 'v',
            'c', 'g', 'k', 'o', 's', 'w',
            'd', 'h', 'l', 'p', 't'};
    public static final int[][] keysToChange = {
            {4, 10, 12, 15, 17, 19, 22}, // 0
            {4, 8, 15, 17},
            {5, 16, 17, 18, 22},
            {1, 2, 4, 6, 7, 8, 10, 11, 12, 13, 14, 19, 20},
            {16, 17, 18, 22},
            {4, 15, 16, 17, 18, 22}, // 5
            {4, 10, 12, 15, 17, 22},
            {4, 7, 8, 15, 17, 22},
            {5, 7, 16, 17, 18, 22},
            {1, 2, 4, 6, 7, 8, 10, 11, 12, 13, 14, 19, 20},
            {7, 16, 17, 18, 22}, // 10
            {4, 7, 15, 16, 17, 18, 22},
            {4, 10, 15, 17},
            {4, 10},
            {5, 16, 17, 18, 22},
            {1, 2, 4, 6, 7, 8, 10, 11, 12, 13, 14, 19, 20}, // 15
            {16, 17, 18, 21, 22},
            {4, 7, 15, 16, 17, 18, 22},
            {1, 3, 4, 5, 9, 10, 12, 13, 15, 17, 18, 22},
            {4, 8},
            {}, // 20
            {0, 1, 4, 7, 15, 16, 17, 18, 22},
    };

    public static final char[][] textToChange = {
            {'\"', '『', '』', '\'', '「', '」', '+', '&', '（', '）', '=', '…'},
            {'─', '：', '、', '；', '？', '。', '！', '‧', '，'}
    };
    public static final int[] noVowel = {4, 5, 10, 11, 16, 17, 21};

    public static final ChineseKey[] enabledKeys = new ChineseKey[24];
    public static final ChineseKey[] disabledKeys = new ChineseKey[24];
    public static final ChineseKey[][] keyArray = new ChineseKey[23][24];
    public static ChineseKey backspace;

    public static void buildKeysAsync() {
        C.threadPool.submit(() -> {
            Timer.t(14);
            Process.setThreadPriority(Process.THREAD_PRIORITY_DISPLAY);
            try {
                buildKeys();
                Consonants.addListeners();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Timer.t(14, "build keys async");
        });
    }

    public static void buildKeys() {
        OnTouchVowel onTouchVowel = new OnTouchVowel();
        OnTouchPunctuation onTouchPunctuation = new OnTouchPunctuation();
        for (int i = 0; i < 23; ++i) {
            String s = displayTexts[i];
            TextLayoutFactory
                    te = s.length() > 1 ? Font.mid : Font.big,
                    td = s.length() > 1 ? Font.midDisabled : Font.bigDisabled;
            ChineseKey key = new ChineseKey(s.length() > 1 ? te.makeMultiLine(s) : te.make(s),
                    null, Colour.NORMAL);
            enabledKeys[i] = key;
            key.pinyin = code[i];
            key.character = '?';
            key.setOnTouchListener(onTouchVowel);
            disabledKeys[i] = new ChineseKey(s.length() > 1 ? td.makeMultiLine(s) : td.make(s),
                    null, Colour.DISABLED);
        }

        backspace = new ChineseKey(R.drawable.level_up, Colour.FUNCTION);
        backspace.setOnTouchListener((v, event) -> {
            if (event.getActionMasked() != MotionEvent.ACTION_DOWN) return false;
            Controller.displayCandidates();
            Engine.delConsonant();
            C.sentenceView.display();
            C.chineseKeyboard.setKeys(Consonants.keys);
            Controller.displayCandidates();
            return true;
        });
        enabledKeys[23] = backspace;
        disabledKeys[23] = backspace;

        for (int kb = 0; kb < 22; kb++) { // for each keyboard
            keyArray[kb] = new ChineseKey[24];
            for (int i = 0; i < 24; i++)
                keyArray[kb][i] = enabledKeys[i];

            for (int i = Vowels.keysToChange[kb].length - 1; i >= 0; --i) {
                final int keyToChange = Vowels.keysToChange[kb][i];
                if (kb != 18 && kb != 21) { // not f or r
                    keyArray[kb][keyToChange] = disabledKeys[keyToChange];
                } else {
                    final char punctuation = textToChange[kb == 18 ? 0 : 1][i];
                    final ChineseKey k = new ChineseKey(
                            Font.big.make(String.valueOf(punctuation)), null, Colour.CHARACTER);
                    k.character = punctuation;
                    k.action = ChineseKey.PUNCTUATION;
                    k.setOnTouchListener(onTouchPunctuation);
                    keyArray[kb][keyToChange] = k;
                }
            }
        }

        for (int kb : noVowel) { // z, c, s, zh, ch, sh, r
            final ChineseKey k = new ChineseKey(
                    kb == 21 ? Font.big.make("ㄖ") : Consonants.keys[kb].mainText,
                    null, Colour.NORMAL);
            k.pinyin = 'u';
            k.setOnTouchListener(onTouchVowel);
            keyArray[kb][5] = k;
        }
    }
}
