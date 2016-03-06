package co.watermelonime.InputView.Numpad;

import android.view.ViewGroup;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;

public class NumberKeyboard extends ViewGroup {
    public NumberKeyboard() {
        super(C.mainService);
        setBackgroundColor(Colour.NORMAL);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
