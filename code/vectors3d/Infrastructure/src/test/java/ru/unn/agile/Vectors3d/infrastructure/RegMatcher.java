package ru.unn.agile.Vectors3d.infrastructure;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class RegMatcher extends BaseMatcher {
    private final String regex;

    public RegMatcher(final String regex) {
        this.regex = regex;
    }

    public boolean matches(final Object o) {
        return ((String) o).matches(regex);
    }

    public void describeTo(final Description description) {
        description.appendText("matches regex = ");
        description.appendText(regex);
    }

    public static Matcher<? super String> matchesPattern(final String regex) {
        RegMatcher matcher = new RegMatcher(regex);
        @SuppressWarnings (value = "unchecked")
        Matcher<? super String> castedMatcher = (Matcher<? super String>)   matcher;
        return castedMatcher;
    }
}
