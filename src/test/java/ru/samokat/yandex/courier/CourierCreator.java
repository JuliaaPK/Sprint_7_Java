package ru.samokat.yandex.courier;

import java.util.Random;

public class CourierCreator {

    public static Courier create() {
        return new Courier(generateLogin(10), generatePassword(10), generateFirstName(10));
    }

    private static String generateText(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int indexOfNextCLoginChar = random.nextInt(characters.length());
            sb.append(characters.charAt(indexOfNextCLoginChar));
        }
        return sb.toString();
    }

    public static String generateLogin(int length) {
        return generateText(length);
    }

    public static String generatePassword(int length) {
        return generateText(length);
    }

    public static String generateFirstName(int length) {
        return generateText(length);
    }

}