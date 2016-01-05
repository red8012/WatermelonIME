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

public class Waiting extends ViewGroup {
//    static int textPadding;
    NumberProgressBar progressBar;
    Layout text;

    public Waiting() {
        super(C.mainService);
        setBackgroundColor(Colour.NORMAL);

        text = Font.mid.make("正在複製詞庫，請稍侯", Size.WScreen);

        progressBar = new NumberProgressBar(C.mainService);
        progressBar.setProgressTextSize(Size.FMid);
        progressBar.setReachedBarColor(Colour.reached);
        progressBar.setUnreachedBarColor(Colour.unreached);
        progressBar.setProgressTextColor(Colour.textKeyboard);

        addView(progressBar);
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
        progressBar.getLayoutParams().width = Size.WScreen*5/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(Size.WScreen / 2, Size.HCandidateVisible / 4);
        text.draw(canvas);
        canvas.restore();
    }

    public void start() {
        new Thread(()->{
            int progress = 0;
            while (progress++ <= 100) {
                final int finalProgress = progress;
                post(() -> {
                    progressBar.setProgress(finalProgress);
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (progress >=100) progress = 0;
            }
        }).start();
    }
}
