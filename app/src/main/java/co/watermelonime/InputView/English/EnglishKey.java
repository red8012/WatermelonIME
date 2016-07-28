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
    static String[] alphabetsNeedsUmlaut = {
            "a", "e", "i", "o", "u", "s", "c", "n", "l", "y", "z",
            "A", "E", "I", "O", "U", "S", "C", "N", "L", "Y", "Z",};
    static String[][] umlautCandidates = {
            {"æ", "ã", "å", "ā", "ä", "à", "á", "â",},
            {"ē", "ê", "ë", "ė", "ę", "é", "è",},
            {"ī", "į", "ì", "î", "ï", "í"},
            {"œ", "ø", "ō", "õ", "ò", "ó", "ö", "ô",},
            {"ù", "ú", "ū", "û", "ü"},
            {"ś", "ß", "š"},
            {"ć", "ç", "č"},
            {"ñ", "ń"},
            {"ł"},
            {"ÿ"},
            {"ž", "ź", "ż"},
            {"Æ", "Ã", "Å", "Ā", "Ä", "À", "Á", "Â",},
            {"Ē", "Ê", "Ë", "Ė", "Ę", "É", "È",},
            {"Ī", "Į", "Ì", "Î", "Ï", "Í"},
            {"Œ", "Ø", "Ō", "Õ", "Ò", "Ó", "Ö", "Ô",},
            {"Ù", "Ú", "Ū", "Û", "Ü"},
            {"Ś", "ß", "Š"},
            {"Ć", "Ç", "Č"},
            {"Ñ", "Ń"},
            {"Ł"},
            {"Ÿ"},
            {"Ž", "Ź", "Ż"},
    };
    static int[] umlautInitialIndices = {
            4, 5, 4, 6, 4, 1, 1, 0, 0, 0, 1,
            4, 5, 4, 6, 4, 1, 1, 0, 0, 0, 1,
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
        textLayout = s.length() > 1 ?
                Font.mid.make(s, Size.WEnglishKey) :
                Font.english.make(s, Size.WEnglishKey);

        dx = textLayout.getWidth() / 2;
        dy = (Size.HEnglishKey - textLayout.getHeight()) / 2;
        setOnTouchListener(ontouchListener);
        setBackgroundColor(Colour.NORMAL);

        for (int i = 0; i < umlautCandidates.length; i++) {
            if (s.equals(alphabetsNeedsUmlaut[i])) {
                umlaut = new Umlaut(umlautCandidates[i], umlautInitialIndices[i]);
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
