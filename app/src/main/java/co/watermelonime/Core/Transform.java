package co.watermelonime.Core;

public class Transform {
    //	private static final char[][] targets = {
//			{'c', 'u'},
//			{'c', 'f'},
//			{'u', 'c'},
//			{'c', 'b', 'g', 'v'},
//			{'j', 'c'},
//			{'i', 'c', 'b'},
//			{'e', 'm'},
//			{'c', 's'}
//	};
    private static final char[][] targets = {
            {'a', 'b', 'f'},
            {'a', 'f'},
            {'c', 'b', 'i'},
            {'c', 'f'},
            {'c', 'j'},
            {'c', 's'},
            {'c', 'u'},
            {'c', 'v', 'b', 'g'},
            {'e', 'a'},
            {'e', 'm'},
    };
    private static final char[][] alphabets = new char[52][];
    private static final char[][] data = new char[18][]; // stores possible pinyin codes at each position
    private static final int[] lengths = new int[18]; // stores number of possible pinyin expansion at that position
    private static final int[] indices = new int[18]; // stores current index that represents the combination
    private static int end;

    public static void init() {
        for (int i = 0; i < 26; i++)
            //noinspection ObjectAllocationInLoop
            alphabets[i] = new char[]{(char) (i + 65)}; // A~Z
        for (int i = 26; i < 52; i++)
            //noinspection ObjectAllocationInLoop
            alphabets[i] = new char[]{(char) (i + 71)}; // a~z
    }

    public static void expandQuery(final StringBuilder pinyin, final int start) {
        StringBuilder result = Engine.sql;
        end = pinyin.length() - 1 - start;

        // fill in data, lengths, indices
        for (int i = 0; i <= end; i++) {
            indices[i] = 0;
            final char code = pinyin.charAt(i + start);
            char[] target;
            if ('0' < code && code < '9') {
                target = targets[code - '1'];
            } else if ('A' <= code && code <= 'Z') {
                target = alphabets[code - 'A'];
            } else {
                target = alphabets[code - 'a' + 26];
            }
            data[i] = target;
            lengths[i] = target.length;
        }
        indices[end] = -1; // move pointer before head

        // compose result
        boolean isHead = true;
        while (getNextCartesianProduct()) {
            if (isHead) {
                result.append('\'');
                isHead = false;
            } else result.append("', '");
            for (int i = 0; i <= end; i++)
                result.append(data[i][indices[i]]);
        }
        result.append('\'');
    }

    /**
     * Set indices represent the next Cartesian product (e.g. 00, 01, 10, 11)
     *
     * @return true if it has the next product; false otherwise
     */
    private static boolean getNextCartesianProduct() {
        indices[end]++;
        for (int i = end; i > 0; i--) {
            if (indices[i] >= lengths[i]) {
                indices[i] = 0;
                indices[i - 1]++;
            }
        }
        return indices[0] < lengths[0];
    }
}
