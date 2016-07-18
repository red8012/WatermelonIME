package co.watermelonime.InputView.English;

import co.watermelonime.Common.Size;

public class NavigationKey extends co.watermelonime.InputView.Chinese.Candidate.NavigationKey {
    public NavigationKey(int icon, int type) {
        super(icon, type);
        int size = (int) (Size.u * 6);
        if (type == 0) size = (int) (Size.u * 3.5);
        dx = (Size.WEnglishKey - size) / 2;
        dy = (Size.HEnglishKey - size) / 2;
        setMeasuredDimension(Size.WEnglishKey, Size.HEnglishKey);
    }
}
