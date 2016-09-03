package co.watermelonime.InputView.Chinese.Candidate;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;

import co.watermelonime.C;
import co.watermelonime.Common.Size;
import co.watermelonime.Core.NavigationKeyListener;
import co.watermelonime.MainService;
import co.watermelonime.R;

public class NavigationKey extends View {
    public static NavigationKey[] keys;
    public Drawable image;
    public float dx, dy;

    public NavigationKey(int icon, int type) {
        super(C.mainService);
        image = ContextCompat.getDrawable(C.context, icon);
        int size = (int) (Size.u * 4);
        if (type == 1)
            size = (int) (Size.u * 6);
        if (type == 2)
            size = (int) (Size.u * 4.5);
        image.setBounds(0, 0, size, size);
        dx = (Size.WNavigationKey - size) / 2;
        dy = (Size.HCandidateVisible / 2 - size) / 2;
        setMeasuredDimension(Size.WNavigationKey, Size.HCandidateVisible / 2);
    }

    public static void init() {
        NavigationKey
                selectAll = new NavigationKey(R.drawable.nav_select_all, 0),
                copy = new NavigationKey(R.drawable.nav_copy, 0),
                delete = new NavigationKey(R.drawable.forward_delete, 0),
                paste = new NavigationKey(R.drawable.nav_paste, 0),

                up = new NavigationKey(R.drawable.nav_up, 1),
                down = new NavigationKey(R.drawable.nav_down, 1),
                left = new NavigationKey(R.drawable.nav_left, 1),
                right = new NavigationKey(R.drawable.nav_right, 1),

                home = new NavigationKey(R.drawable.nav_home, 2),
                end = new NavigationKey(R.drawable.nav_end, 2),

                half = new NavigationKey(R.drawable.nav_space_half, 3),
                full = new NavigationKey(R.drawable.nav_space_full, 4);

        selectAll.setOnTouchListener(new NavigationKeyListener(() -> {
            MainService.inputConnection.performContextMenuAction(android.R.id.startSelectingText);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }
            MainService.inputConnection.performContextMenuAction(android.R.id.selectAll);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
            MainService.inputConnection.performContextMenuAction(android.R.id.selectAll);
        }
        ));

        copy.setOnTouchListener(new NavigationKeyListener(() ->
                MainService.inputConnection.performContextMenuAction(android.R.id.copy)
        ));

        paste.setOnTouchListener(new NavigationKeyListener(() ->
                MainService.inputConnection.performContextMenuAction(android.R.id.paste)
        ));

        delete.setOnTouchListener(new NavigationKeyListener(KeyEvent.KEYCODE_FORWARD_DEL));
        up.setOnTouchListener(new NavigationKeyListener(KeyEvent.KEYCODE_DPAD_UP));
        down.setOnTouchListener(new NavigationKeyListener(KeyEvent.KEYCODE_DPAD_DOWN));
        left.setOnTouchListener(new NavigationKeyListener(KeyEvent.KEYCODE_DPAD_LEFT));
        right.setOnTouchListener(new NavigationKeyListener(KeyEvent.KEYCODE_DPAD_RIGHT));

        home.setOnTouchListener(new NavigationKeyListener(KeyEvent.KEYCODE_MOVE_HOME));
        end.setOnTouchListener(new NavigationKeyListener(KeyEvent.KEYCODE_MOVE_END));

        half.setOnTouchListener(new NavigationKeyListener(" "));
        full.setOnTouchListener(new NavigationKeyListener("　"));

        keys = new NavigationKey[]{
                selectAll, copy, home, up, end, full,
                delete, paste, left, down, right, half
        };
        display();
    }

    public static void display() {
        C.candidateView.clearCandidates();
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
