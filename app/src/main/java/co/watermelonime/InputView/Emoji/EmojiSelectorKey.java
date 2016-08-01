package co.watermelonime.InputView.Emoji;

import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Common.Font;
import co.watermelonime.Common.Size;

/**
 * Created by din on 2016/8/1.
 */
public class EmojiSelectorKey extends View{

    static final View.OnTouchListener onTouchListener = (v, event) -> {
        if (event.getActionMasked() != MotionEvent.ACTION_DOWN) return false;
        EmojiSelectorKey key = (EmojiSelectorKey) v;
        C.emojiKeyboard.removeAllViews();
        C.emojiKeyboard.addView(EmojiKeyboard.emojiSelector);
        switch (key.emojitag) {
            case EmojiSelector.RECENT:
                // add key into emojiKeyboard
                break;
            case EmojiSelector.EMOTICON:
                break;
        }
        return true;
    };
    public final int emojitag;
    public float dx, dy;
    Layout textLayout;
    Drawable image;

    public EmojiSelectorKey(final int emoji, final String text) {
        super(C.mainService);
        setOnTouchListener(onTouchListener);
        this.emojitag = emoji;
//        setSize();
        textLayout = Font.sentence.make(text, Size.WSentenceView);
        dx = Size.WSentenceView / 2;
        dy = (Size.HKey - textLayout.getHeight()) / 2;
    }

}
