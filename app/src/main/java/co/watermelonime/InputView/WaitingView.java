package co.watermelonime.InputView;

import android.graphics.Canvas;
import android.text.Layout;
import android.view.ViewGroup;

import com.daimajia.numberprogressbar.NumberProgressBar;

import co.watermelonime.C;
import co.watermelonime.Common.Colour;
import co.watermelonime.Common.Font;
import co.watermelonime.Common.Printer;
import co.watermelonime.Common.Size;

public class WaitingView extends ViewGroup {
    public static NumberProgressBar progressBar;
    Layout text;

    public WaitingView() {
        super(C.mainService);
        setBackgroundColor(Colour.NORMAL);

        while (Font.mid == null) try {
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        text = Font.mid.make("正在複製詞庫，請稍侯", Size.WScreen);

        progressBar = new NumberProgressBar(C.mainService);
        progressBar.setProgressTextSize(Size.FMid);
        progressBar.setReachedBarColor(Colour.reached);
        progressBar.setUnreachedBarColor(Colour.unreached);
        progressBar.setProgressTextColor(Colour.textKeyboard);

        addView(progressBar);
    }

    public void increment(int by) {
        post(() -> progressBar.incrementProgressBy(by));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(Size.WScreen, Size.HCandidateVisible);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Printer.p("onLayout", l, t, r, b);
        int padLR = r / 7, padTop = b / 2;
        progressBar.layout(padLR, b - padTop, r - padLR, b);
        progressBar.getLayoutParams().width = Size.WScreen * 5 / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(Size.WScreen / 2, Size.HCandidateVisible / 4);
        text.draw(canvas);
        canvas.restore();
    }

}
