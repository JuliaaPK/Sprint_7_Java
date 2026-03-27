package ru.samokat.yandex.order;

import java.util.List;
import java.util.Random;

public class OrderCreator {

    public static Order create() {
        return new Order(
                OrderCreator.generateFirstName(5),
                OrderCreator.generateLastName(5),
                OrderCreator.generateAddress(5),
                OrderCreator.generateMetroStation(5),
                OrderCreator.generatePhone(),
                OrderCreator.generateRentTime(),
                OrderCreator.generateDeliveryDate(),
                OrderCreator.generateComment(),
                List.of("BLACK", "GREY"));
    }

    public static String generateFirstName(int length) {
        return generateText(length);
    }

    public static String generateLastName(int length) {
        return generateText(length);
    }

    public static String generateAddress(int length) {
        return generateText(length);
    }

    public static String generateMetroStation(int length) {
        return generateText(length);
    }

    public static String generatePhone() {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        StringBuilder sb4 = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            sb1.append(random.nextInt(9) + 1);
            sb2.append(random.nextInt(10));
        }

        for (int j = 0; j < 2; j++) {
            sb3.append(random.nextInt(10));
            sb4.append(random.nextInt(10));

        }

        return String.format("+7 %s %s %s %s", sb1, sb2, sb3, sb4);
    }

    public static int generateRentTime() {
        Random random = new Random();
        return random.nextInt(5) + 1;
    }

    public static String generateDeliveryDate() {
        Random random = new Random();

        int year = random.nextInt(2) + 2026;
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(27) + 1;

        return String.format("%s-%02d-%02d", year, month, day);
    }

    public static String generateComment() {
        return generateText(20);
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
}
