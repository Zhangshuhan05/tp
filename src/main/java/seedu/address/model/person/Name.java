package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should contain at least one letter, and may only include letters (including "
                    + "non-English characters), digits, spaces, hyphens, apostrophes, slashes, and periods. "
                    + "It should not be blank.";

    /*
     * Must contain at least one Unicode letter (covers English, Chinese, accented chars, etc.)
     * First character must be a letter or digit.
     * Remaining characters may include letters, digits, spaces, hyphens, apostrophes, slashes, periods.
     */
    public static final String VALIDATION_REGEX = "(?=.*\\p{L})[\\p{L}\\p{Digit}][\\p{L}\\p{Digit} '\\-./]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        String capitalizedName = capitalizeName(name);
        checkArgument(isValidName(capitalizedName), MESSAGE_CONSTRAINTS);
        fullName = capitalizedName;
    }

    /**
     * Capitalizes the first letter of each word in the name and lowercases the rest.
     */
    private static String capitalizeName(String name) {
        String[] words = name.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return sb.toString().trim();
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }
}
