package co.watermelonime.InputView.English;

import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Size;

public class Umlaut extends ViewGroup {
    EnglishKey[] keys;
    int startsAt, x, y, keysPerRow;
    CharSequence currentText;

    public Umlaut(String[] umlauts, int startsAt) {
        super(C.mainService);
        setBackgroundColor(Colour.UMLAUT);
        int len = umlauts.length;
        this.startsAt = startsAt;

        if (len == 1) keysPerRow = 1;
        else if (len > 6) keysPerRow = 4;
        else if (len > 4) keysPerRow = 3;
        else if (len == 4 || len == 2) keysPerRow = 2;
        else keysPerRow = 3;
        setMeasuredDimension(Size.WEnglishKey * keysPerRow, Size.HEnglishKey * (len < 4 ? 1 : 2));

        keys = new EnglishKey[len];
        for (int i = 0; i < len; i++) {
            EnglishKey key = new EnglishKey(umlauts[i]);
            key.setBackgroundColor(Colour.UMLAUT);
            keys[i] = key;
            addView(key);
        }
    }

    public void reset() {
        for (EnglishKey key : keys)
            key.setBackgroundColor(Colour.UMLAUT);
        keys[startsAt].setBackgroundColor(Colour.UMLAUT_SELECTED);
        currentText = keys[startsAt].text;
        y = startsAt / keysPerRow;
        x = startsAt % keysPerRow;
        for (EnglishKey key : keys) key.invalidate();
    }

    public boolean move(int X, int Y) {
        int xMax = keysPerRow - 1, yMax = 0;
        if (keys.length > 1) yMax = 1;
        if (y + Y == 1) xMax = keys.length - keysPerRow - 1;

        if (!(x + X <= xMax && x + X >= 0 && y + Y <= yMax && y + Y >= 0)) {
            return false;
        }

        x += X;
        y += Y;

        for (EnglishKey key : keys)
            key.setBackgroundColor(Colour.UMLAUT);
        int index = y * keysPerRow + x;
        keys[index].setBackgroundColor(Colour.UMLAUT_SELECTED);
        currentText = keys[index].text;
        for (EnglishKey key : keys) key.invalidate();
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Logger.d("umlaut onLayout");
        int len = keys.length;
        l = 0;
        t = 0;

        for (int i = 0; i < len; i++) {
            keys[i].layout(l, t, l + Size.WEnglishKey, t + Size.HEnglishKey);
            l += Size.WEnglishKey;
            if (l >= getMeasuredWidth()) {
                l = 0;
                t += Size.HEnglishKey;
            }
        }
    }
}
