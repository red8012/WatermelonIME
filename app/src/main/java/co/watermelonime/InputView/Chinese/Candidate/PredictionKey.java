package co.watermelonime.InputView.Chinese.Candidate;

import co.watermelonime.Common.Size;

public class PredictionKey extends CandidateButton {
    public static PredictionKey placeholder;

    public PredictionKey() {
        super();
    }

    public static void init() {
        placeholder = new PredictionKey();
        placeholder.setOnTouchListener(null);
        placeholder.setMeasuredDimension(Size.WCandidateView, Size.HCandidateVisible / 2);
    }

}
