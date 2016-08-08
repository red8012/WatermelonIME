package co.watermelonime.InputView.Emoji;

import android.content.Context;
import android.view.View;

import co.watermelonime.C;

/**
 * Created by din on 2016/8/1.
 */
public class EmojiSelector extends View{
    public static final int
            RECENT = 1,
            EMOTICON = 0;

    static final EmojiSelectorKey[] keys = new EmojiSelectorKey[2];
    public static int inputEmoji = EMOTICON, lastInputEmoji = EMOTICON;

    public EmojiSelector() {
        super(C.mainService);
    }

    public static void init() {
        keys[RECENT] = new EmojiSelectorKey(RECENT, "\uD83D\uDD58");
        keys[EMOTICON] = new EmojiSelectorKey(EMOTICON, "\uD83D\uDE00");
    }



}
