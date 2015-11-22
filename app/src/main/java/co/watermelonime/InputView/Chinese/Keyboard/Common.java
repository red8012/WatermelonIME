package co.watermelonime.InputView.Chinese.Keyboard;

import android.graphics.Color;

import co.watermelonime.C;
import co.watermelonime.ChineseKey;
import co.watermelonime.R;
import co.watermelonime.TextLayoutFactory;

public class Common {
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
    public static final String[][] textToChange = {
            {"比", "吧", "被", "把", "不", "別", "並"},
            {"的", "但", "得", "地"},
            {"個", "跟", "過", "各", "搞"},
            {null, null, "及", null, null, "僅", "即", null, null, "將", null, "就"},
            {"之", "只", "著", "至"},
            {"在", "最", "再", "做", "怎", "作"},
            {"拍", "排", null, "派", null, null},
            {"它", "他", "她", "牠", "太", null},
            {null, "開", null, null, "可", null},
            {null, "前", "其", null, null, "卻", "去", "且", null, null, null, null},
            {"超", null, null, null, null},
            {"此", "才", "從", null, null, null, null},
            {"買", "賣", "嗎", "嘛"},
            {"你"},
            {"和", "會", "或", "還", "很"},
            {null, "先", null, null, null, "想", "須", "些", "向", "像", "型", "性"},
            {"是", "時", null, "使", "事"},
            {"雖", "所", null, "似", null, null, null},
            {"非", "『", "』", "\"", "「", "」", "+", "&", "（", "）", "=", "…"},
            {"啦", "了"},
            {},
            {"─", "：", "、", "；", "？", "。", "！", "‧", "，"}
    };
    final static String[] consonantStrings = {
            "ㄅ", "ㄉ", "ㄍ", "ㄐ", "ㄓ", "ㄗ",
            "ㄆ", "ㄊ", "ㄎ", "ㄑ", "ㄔ", "ㄘ",
            "ㄇ", "ㄋ", "ㄏ", "ㄒ", "ㄕ", "ㄙ",
            "ㄌ"};
    final static String[] vowelStrings = {
            "　ㄧㄨ\nㄚㄚㄚ", "ㄞ", "ㄢ", "ㄧㄨㄩ\nㄢㄢㄢ", "ㄧㄨ\nㄞㄞ", "ㄧ",
            "　ㄧㄨ\nㄛㄛㄛ", "ㄟ", "ㄣ", "ㄧㄨㄩ\nㄣㄣㄣ", "ㄨㄩ\nㄟㄝ", "ㄨ",
            "ㄜㄝ", "ㄠ", "ㄤ", "ㄧㄨ\nㄤㄤ", "ㄧ\nㄠ", "ㄩ",
            "ㄧ\nㄝ", "ㄡ", "ㄥㄦ", "ㄧㄨㄩ\nㄥㄥㄥ", "ㄧ\nㄡ"};
    static ChineseKey backspace;
    public static void initialize() {
        C.chineseKeyWidth = C.u * 10;
        C.chineseKeyHeight = C.u * 9;

        C.bigFont = new TextLayoutFactory(C.u * 6.5f, C.sourceSans, Color.WHITE, C.u * 10);
        C.frFont = new TextLayoutFactory(C.u * 5, C.sourceSans, Color.WHITE, C.u * 10);
        C.midFont = new TextLayoutFactory(C.u * 3, C.sourceSans, Color.WHITE, C.u * 10);
        C.smallFont = new TextLayoutFactory(C.u * 2, C.sourceSans, Color.WHITE, C.u * 10);

        C.bigDisabledFont = new TextLayoutFactory(C.u * 6.5f, C.sourceSans, Color.DKGRAY, C.u * 10);
        C.midDisabledFont = new TextLayoutFactory(C.u * 3, C.sourceSans, Color.DKGRAY, C.u * 10);

        backspace = new ChineseKey(R.drawable.backspace, C.COLOR_FUNCTION);
    }
}
