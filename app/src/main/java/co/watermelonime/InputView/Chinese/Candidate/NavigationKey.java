package co.watermelonime.InputView.Chinese.Candidate;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Common.Size;
import co.watermelonime.R;

public class NavigationKey extends View {
    public static NavigationKey[] keys;
    public Drawable image;
    float dx, dy;

    public NavigationKey(int icon, int type) {
        super(C.mainService);
        image = ContextCompat.getDrawable(C.context, icon);
        int size = (int) (Size.u * 4);
        if (type==1)
            size = (int) (Size.u * 6);
        image.setBounds(0, 0, size, size);
        dx = (Size.WNavigationKey - size) / 2;
        dy = (Size.HCandidateVisible / 2 - size) / 2;
        setMeasuredDimension(Size.WNavigationKey, Size.HCandidateVisible / 2);
    }

    public static void init() {
        NavigationKey
                selectAll = new NavigationKey(R.drawable.nav_select_all, 0),
                copy = new NavigationKey(R.drawable.nav_copy, 0),
                cut = new NavigationKey(R.drawable.nav_cut, 0),
                paste = new NavigationKey(R.drawable.nav_paste, 0),

                up = new NavigationKey(R.drawable.nav_up, 1),
                down = new NavigationKey(R.drawable.nav_down, 1),
                left = new NavigationKey(R.drawable.nav_left, 1),
                right = new NavigationKey(R.drawable.nav_right, 1),

                home = new NavigationKey(R.drawable.nav_home, 2),
                end = new NavigationKey(R.drawable.nav_end, 2),

                half = new NavigationKey(R.drawable.nav_space_half, 3),
                full = new NavigationKey(R.drawable.nav_space_full, 4);
        keys = new NavigationKey[]{
                selectAll, copy, home, up, end, full,
                paste, cut, left, down, right, half
        };
        display();
    }

    public static void display() {
        Log.w("NavKey", "display");
        CandidateView.clearCandidates();
        for (NavigationKey k : keys)
            if (k != null)
                C.candidateView.addView(k);
        C.candidateView.layout(0, 0, 0, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (image != null) {
            canvas.save();
            canvas.translate(dx, dy);
            image.draw(canvas);
            canvas.restore();
        }
    }
}
