package co.watermelonime.InputView.English;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Font;
import co.watermelonime.Common.Size;

public class EnglishKey extends View {
    static final OnTouchListener ontouchListener = new OnTouchEnglishKey();
    static String[] umlautCandidates = {
            "a", "æãåāäàáâ",
            "e", "ēêëėęéè",
            "i", "īįìîïí",
            "o", "œøōõòóöô",
            "u", "ùúūûü",
            "s", "śßš",
            "c", "ćçč",
            "n", "ñń",
            "A", "ÆÃÅĀÄÀÁÂ",
            "E", "ĒÊËĖĘÉÈ",
            "I", "ĪĮÌÎÏÍ",
            "O", "ŒØŌÕÒÓÖÔ",
            "U", "ÙÚŪÛÜ",
            "S", "ŚßŠ",
            "C", "ĆÇČ",
            "N", "ÑŃ"
    };
    static int[] umlautInitialIndices = {
            4, 5, 4, 6, 4, 1, 1, 0,
            4, 5, 4, 6, 4, 1, 1, 0
    };
    public String text;
    public Layout textLayout;
    public Drawable image;
    public float dx, dy;
    public boolean displayUmlaut = false;
    public Umlaut umlaut;

    public EnglishKey(final String s, int width) {
        super(C.mainService);
        setMeasuredDimension(width, Size.HEnglishKey);
        text = s;
        textLayout = Font.fr.make(s, width);

        dx = textLayout.getWidth() / 2;
        dy = (Size.HEnglishKey - textLayout.getHeight() * 1.2f) / 2;
        setBackgroundColor(Colour.SENTENCE);
    }

    public EnglishKey(final String s) {
        super(C.mainService);
        setMeasuredDimension(Size.WEnglishKey, Size.HEnglishKey);
        text = s;
        textLayout = Font.english.make(s, Size.WEnglishKey);

        dx = textLayout.getWidth() / 2;
        dy = (Size.HEnglishKey - textLayout.getHeight() * 1.2f) / 2;
        setOnTouchListener(ontouchListener);
        setBackgroundColor(Colour.NORMAL);

        for (int i = 0; i < umlautCandidates.length; i += 2) {
            if (s.equals(umlautCandidates[i])) {
                umlaut = new Umlaut(umlautCandidates[i + 1], umlautInitialIndices[i / 2]);
                break;
            }
        }
    }

    public EnglishKey(int resource, int width) {
        super(C.mainService);
        image = ContextCompat.getDrawable(C.context, resource);
        int sideLength = Size.HEnglishKey * 4 / 5;
        image.setBounds(0, 0, sideLength, sideLength);
        dx = (width - sideLength) / 2;
        dy = (Size.HEnglishKey - sideLength) / 2;
        setBackgroundColor(Colour.FUNCTION);
        setMeasuredDimension(width, Size.HEnglishKey);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (textLayout != null) {
            canvas.save();
            canvas.translate(dx, dy);
            textLayout.draw(canvas);
            canvas.restore();
        }
        if (image != null) {
            canvas.save();
            canvas.translate(dx, dy);
            image.draw(canvas);
            canvas.restore();
        }
    }
}
