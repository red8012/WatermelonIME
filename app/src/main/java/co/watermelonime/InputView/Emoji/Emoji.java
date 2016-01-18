package co.watermelonime.InputView.Emoji;

import android.view.ViewGroup;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;

public class Emoji extends ViewGroup {
    public Emoji() {
        super(C.mainService);
        setBackgroundColor(Colour.FUNCTION);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
