package com.github.percivalgebashe.assignment_5.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class IdGenerator {

    public static String generateBookId(List<String> authorNames, String title, LocalDate publishedDate) {
        String authorInitials = getAuthorInitials(authorNames);
        String titleCode = getTitleCode(title);
        String year = String.valueOf(publishedDate.getYear());
        String shortHash = generateShortHash(String.join(",", authorNames) + title + year);

        return String.format("%s-%s-%s-%s", authorInitials, titleCode, year, shortHash);
    }

    private static String getAuthorInitials(List<String> authorNames) {
        return authorNames.stream()
                .map(name -> name.split(" "))
                .flatMap(parts -> List.of(parts).stream())
                .map(part -> part.isEmpty() ? "" : Character.toUpperCase(part.charAt(0)))
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    private static String getTitleCode(String title) {
        String[] words = title.split("\\s+");
        StringBuilder code = new StringBuilder();
        for (String word : words) {
            if (!word.equalsIgnoreCase("the") && !word.equalsIgnoreCase("and") && !word.equalsIgnoreCase("of")) {
                code.append(Character.toUpperCase(word.charAt(0)));
            }
        }
        return code.toString();
    }

    private static String generateShortHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            String encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
            return encoded.substring(0, 7);
        } catch (Exception e) {
            throw new RuntimeException("Error generating hash", e);
        }
    }
}
