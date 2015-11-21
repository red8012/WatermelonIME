package co.watermelonime;

import android.graphics.Color;
import android.graphics.Typeface;

import java.util.concurrent.ExecutorService;

import co.watermelonime.InputView.Chinese.Keyboard.ChineseKeyboard;

public class C {
    public final static int COLOR_NORMAL = Color.rgb(33, 33, 33);
    public final static int COLOR_FUNCTION = Color.rgb(0, 77, 32);
    public static MainService mainService;
    public static Typeface sourceSans;
    public static ExecutorService threadPool;

    public static int screenWidth, screenHeight, keyboardWidth, u,
            chineseKeyWidth, chineseKeyHeight;

    public static boolean isLandscape;

    public static ChineseKeyboard chineseKeyboard;
}
