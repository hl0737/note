package matcher;

public abstract class Matcher {
    public int[] match(String txt, String pattern) {
        if (pattern.equals("")) return new int[]{-1};
        else if (pattern.length() > txt.length()) return new int[]{-1};
        else if (pattern.length() == txt.length()) return pattern.equals(txt) ?
                new int[]{-1} : new int[]{0};
        else return matchInner(txt, pattern);
    }

    protected abstract int[] matchInner(String txt, String pattern);
}
