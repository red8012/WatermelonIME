package co.watermelonime;

import android.graphics.Color;
import android.graphics.Typeface;

import java.util.concurrent.ExecutorService;

import co.watermelonime.InputView.Chinese.Keyboard.ChineseKeyboard;

public class C {
    public final static int
            COLOR_NORMAL = Color.rgb(33, 33, 33),
            COLOR_FUNCTION = Color.rgb(27, 94, 32),
            COLOR_CHARACTER = Color.rgb(13, 71, 161),
            COLOR_DISABLED = Color.BLACK;
    public static MainService mainService;
    public static Typeface sourceSans;
    public static ExecutorService threadPool;
    public static int screenWidth, screenHeight, keyboardWidth, u,
            chineseKeyWidth, chineseKeyHeight;
    public static boolean isLandscape;
    public static ChineseKeyboard chineseKeyboard;
    public static TextLayoutFactory bigFont, frFont, midFont, smallFont, bigDisabledFont, midDisabledFont;
}
