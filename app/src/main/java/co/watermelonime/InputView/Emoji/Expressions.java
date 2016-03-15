package co.watermelonime.InputView.Emoji;

import java.util.ArrayList;

import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Font;
import co.watermelonime.InputView.Emoji.EmojiKey;
import co.watermelonime.InputView.Chinese.Keyboard.OnTouchDel;
//import co.watermelonime.InputView.Emoji.OnTouchEnter;
//import co.watermelonime.InputView.Emoji.OnTouchExpression;
import co.watermelonime.InputView.Emoji.EmojiKey;
import co.watermelonime.R;

/**
 * Created by din on 2016/3/7.
 */
public class Expressions {
    final public static String[] displaySymbols = {
            "\uD83D\uDE00", "\uD83D\uDE2C", "\uD83D\uDE01", "\uD83D\uDE02", "\uD83D\uDE03",
            "\uD83D\uDE04", "\uD83D\uDE05", "\uD83D\uDE06", "", "", "",
            "", "", "", "", "", "", "",
            "\uD83D\uDE19"};
    public static EmojiKey[] keys = new EmojiKey[24];
    public static EmojiKey enter, backspace;

//    @SuppressWarnings("ObjectAllocationInLoop")
//    public static void buildKeys() {
//        ArrayList<EmojiKey> keys = new ArrayList<>(24);
//        for (int i = 0; i < 19; i++)
//            keys.add(new EmojiKey(Font.big.make(displaySymbols[i]),
//                    null, Colour.NORMAL));
//
//        keys.add(18, new EmojiKey(Font.fr.make("ㄈ"),
//                Font.small.make("「」"), Colour.NORMAL));
//
//        enter = new EmojiKey(R.drawable.enter, Colour.FUNCTION);
//        //enter.setOnTouchListener(new OnTouchEnter());
//        keys.add(enter);
//
//        keys.add(new EmojiKey(Font.mid.make("ㄧㄨㄩ"),
//                Font.small.make("...其他"), Colour.NORMAL));
//        keys.add(new EmojiKey(Font.fr.make("ㄖ"),
//                Font.small.make("！。？"),
//                Colour.NORMAL));
//
//        backspace = new EmojiKey(R.drawable.backspace, Colour.FUNCTION);
//        backspace.setOnTouchListener(new OnTouchDel());
//        keys.add(backspace);
//
//        Expressions.keys = keys.toArray(Expressions.keys);
//    }
/*
    public static void addListeners() {
        OnTouchExpression onTouchExpression = new OnTouchExpression();
        for (int i = 0; i < 20; i++) {
            Expressions.keys[i].action = i;
            Expressions.keys[i].setOnTouchListener(onTouchExpression);
        }
        for (int i = 21; i < 23; i++) {
            Expressions.keys[i].action = i - 1;
            Expressions.keys[i].setOnTouchListener(onTouchExpression);
        }
    }
*/
}
