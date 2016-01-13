package co.watermelonime.Core;

import net.sqlcipher.Cursor;

import java.util.ArrayList;
import java.util.Iterator;

import co.watermelonime.C;
import co.watermelonime.Constants;
import co.watermelonime.InputView.Chinese.Candidate.CandidateButton;

/**
 * Created by ray on 2016/1/9.
 * This class is ported from the old repo and should be rewrote for better performance.
 * BENCHMARK REQUIRED!!!!!!!!!!!!!
 */
public class Runnables {

    public static final Runnable setThreadPriority = new Runnable() {
        @Override
        public void run() {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_DISPLAY);
        }
    };
    public static final Runnable displaySentence = new Runnable() {
        @Override
        public void run() {
            C.sentenceView.display();
        }
    };
    public static final Runnable displayCandidateInvisible = new Runnable() {
        @Override
        public void run() {
//            while (it1.hasNext())
//                C.candidateLeft.add(it1.next());
//            while (it2.hasNext())
//                C.candidateRight.add(it2.next());
        }
    };
    static Iterator<String> it1, it2;
    public static final Runnable displayCandidate = new Runnable() {
        @Override
        public void run() {
            it1 = Engine.candidateLeft.iterator();
            ArrayList<String> arrayList = new ArrayList<>(); //Todo: remove this
            for (int i = 0; i < 8; i++)
                if (it1.hasNext()) arrayList.add(it1.next());
                else break;
            C.candidateView.clearCandidates();
            C.candidateView.setCandidate(arrayList, CandidateButton.TOP);

            arrayList.clear();
            it2 = Engine.candidateRight.iterator();
            for (int i = 0; i < 8; i++)
                if (it2.hasNext()) arrayList.add(it2.next());
                else break;
            C.candidateView.setCandidate(arrayList, CandidateButton.BOTTOM);
        }
    };
    public static final Runnable onAdd = new Runnable() {
        @Override
        public void run() {
            final int length = Engine.getLength();
            final Cursor c = Database.query();
            while (c.moveToNext()) {
                final int l = c.getInt(0), start = length - l;
                Engine.queryResultD[l][start].addLast(c.getString(1));
            }
            c.close();

            Engine.makeCandidateLeft(length);
            Engine.makeCandidateRight(length);
            C.candidateView.post(displayCandidate);
//            String timer = Timer.t(768, "total time");
            makeSeparator();
            makeSentence();
            C.sentenceView.post(displaySentence);
//            C.candidateRight.post(displayCandidateInvisible);
//            C.consonantKeyboard.debug(timer.split(" ")[1].split("\\.")[0]);
        }
    };

    public static void makeSeparator() {
        boolean found;
        int counter, length = Engine.getLength();
        System.out.println("makeSeparator length: " + length);
        try {
            for (int[] a : Constants.separator[length - 1]) {
                found = true;
                counter = 0;
                for (int b : a) {
                    if (Engine.queryResultL[b][counter].isEmpty() &&
                            Engine.queryResultD[b][counter].isEmpty()) {
                        found = false;
                        break;
                    }
                    counter += b;
                }
                if (found) {
                    Engine.separatorAnswer = a;
                    return;
                }
            }
        } catch (Exception e) {
            Engine.print("error on make answer");
            e.printStackTrace();
        }
        Engine.print("No matched separator");
    }

    public static void makeSentence() {
        StringBuffer sb = new StringBuffer();
        int counter = 0;
        for (int i : Engine.separatorAnswer) { // Todo: this may be null
            sb.append(Engine.queryResultD[i][counter].getFirst());
            counter += i;
        }
        Engine.sentence = sb;
        if (Engine.sentence.length() != counter) Engine.print("sentence length mismatch");
    }
}