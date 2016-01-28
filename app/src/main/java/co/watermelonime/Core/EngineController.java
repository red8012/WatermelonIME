package co.watermelonime.Core;

public interface EngineController {
    /**
     * Initialize the engine
     */
    void init();

    /**
     * Add pinyin to the engine and trigger actions
     *
     * @param pinyin        Raw pinyin before transformation
     * @param characterLock Character on the blue key, should be a single character
     *                      if pinyin is a consonant, this should be null
     */
    void add(final char pinyin, final char characterLock);

    /**
     * Delete a single pinyin (can be either a consonant or a vowel depending on whether current pinyin length is even or odd)
     */
    void del();

    /**
     * Called when left candidate or enter is clicked
     *
     * @param c the characters to be changed and popped,
     *          if null, the left most phrase is popped
     */
    void popLeft(final CharSequence c);

    /**
     * @param c the characters to be changed,
     *          if null, the current sentence is popped
     */
    void popRight(final CharSequence c);

    /**
     * Change the characters in sentence[start:end].
     *
     * @param start
     * @param end
     * @param c     modified characters
     */
    void change(final int start, final int end, final CharSequence c);

    /**
     * Clear everything in the engine
     */
    void clear();

}
