package co.watermelonime.InputView.Chinese.Keyboard;

import android.content.Context;
import android.graphics.Color;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import co.watermelonime.C;
import co.watermelonime.Constants;
import co.watermelonime.Key;
import co.watermelonime.TextLayoutFactory;

public class Consonant extends GridLayout{

    public Consonant() {
        super(C.mainService);
        setRowCount(4);
        setColumnCount(6);
        TextLayoutFactory textLayoutFactory =
                new TextLayoutFactory(C.u*7, C.sourceSans, Color.WHITE, C.u*10);

        final String disp[] = Constants.kb1[0];
        for (int i=0; i<22; i++){
            addView(new Key(textLayoutFactory.make(disp[i])));
        }
    }
}
