package utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FullNameGenerator {
    private static final List<String> FIRST_NAMES = Arrays.asList("Jim", "Fred", "Baz", "Bing", "Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Heidi", "Ivan", "Judy", "Kevin", "Lana", "Mona", "Nina", "Omar", "Pam", "Quinn", "Rita", "Sue", "Tina", "Uma", "Vera", "Wendy", "Xena", "Yara", "Zara");
    private static final List<String> LAST_NAMES = Arrays.asList("Duck", "Swan", "Cooper", "Bing", "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Robinson", "Clark", "Rodriguez", "Lewis", "Lee", "Walker", "Hall", "Allen");

    public static String generateFullName() {
        Collections.shuffle(FIRST_NAMES);
        Collections.shuffle(LAST_NAMES);
        String firstName = FIRST_NAMES.getFirst();
        String lastName = LAST_NAMES.getFirst();
        return firstName + " " + lastName;
    }
}
