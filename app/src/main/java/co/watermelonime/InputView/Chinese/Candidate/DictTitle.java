package co.watermelonime.InputView.Chinese.Candidate;

import android.graphics.Canvas;

import java.util.ArrayList;

import co.watermelonime.Common.Font;
import co.watermelonime.Common.Size;

public class DictTitle extends CandidateButton {
    final static ArrayList<DictTitle> pool = new ArrayList<>(16);
    final static OnClickDictTitle onClickDictTitle = new OnClickDictTitle();

    public DictTitle() {
        super();
        setOnClickListener(onClickDictTitle);
    }

    public static void init() {
        for (int i = 0; i < 15; i++)
            pool.add(new DictTitle());
    }

    public static DictTitle get(String s) {
        DictTitle d;
        if (pool.isEmpty()) {
            d = new DictTitle();
        } else
            d = pool.remove(pool.size() - 1);
        d.setText(s);
        return d;
    }

    public void release() {
        pool.add(this);
    }

    public void setText(String s) {
        text = s;
        width = Size.WDictTitle;
        setMeasuredDimension(Size.WDictTitle, Size.HCandidateRow);
        if (s == null) textLayout = null;
        else {
            textLayout = Font.dictTitle.make(text);
            dx = width / 2;
            dy = (Size.HCandidateRow - textLayout.getHeight()) / 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (textLayout == null) return;
        canvas.save();
        canvas.translate(dx, dy);
        textLayout.draw(canvas);
        canvas.restore();
    }
}
