package org.technbolts.util;

/**
 */
public class StringMatcher {
    /**
     * Performs a wildcard matching for the text and pattern
     * provided.
     *
     * @param text the text to be tested for matches.
     *
     * @param pattern the pattern to be matched for.
     * This can contain the wildcard character '*' (asterisk).
     *
     * @return <tt>true</tt> if a match is found, <tt>false</tt>
     * otherwise.
     */
    public static boolean wildCardMatching(String text, String pattern)
    {
        if(pattern==null || text==null)
            return (text==pattern);

        int pos = 0;
        String[] parts = pattern.split("\\*");

        for (int i=0;i<parts.length;i++)
        {
            String part = parts[i];
            int idx = text.indexOf(part,pos);

            // part not detected in the text.
            if(idx == -1)
                return false;
            else
            // edge case : make sure if it does not start with * the first
            // part must fit exactly
            if (i==0 && !pattern.startsWith("*") && idx!=0)
                return false;
            else
            // edge case : make sure if it does not end with * the last
            // part must fit exactly
            if (i==parts.length && !pattern.endsWith("*") && (idx+part.length())!=text.length())
                return false;

            // search next part after this one
            pos = idx + part.length();
        }

        return true;
    }
}
