package ru.samokat.yandex.actions;

import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import ru.samokat.yandex.CustomException;
import ru.samokat.yandex.courier.Courier;
import ru.samokat.yandex.order.Order;
import ru.samokat.yandex.requests.RequestApi;

import java.util.Random;


public class Actions {
    @Step("Получение id курьера")
    public static String getCourierId(Courier courier) {
        Response response;
        try {
            response = RequestApi.loginCourier(courier);
        } catch (Exception e) {
            return null;
        }
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getString("id");
    }

    @Step("Удаление курьера по id")
    public static void deleteCourier(Courier courier) {
        String id = getCourierId(courier);
        if (id != null) {
            RequestApi.deleteCourier(id);
        }
    }

    @Step("Получение трек номера заказа")
    public static String getOrderTrack(Order order) {
        Response response = RequestApi.createOrder(order);
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getString("track");
    }

    @Step("Получение id заказа")
    public static String getOrderId(String track) {
        Response response = RequestApi.getOrderId(track);
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getString("order.id");
    }

    @Step("Получение несуществующего трек номера заказа")
    public static String getNotExistedOrderTrack() throws CustomException {
        int maxAttempts = 50;
        String randomTrack = "";

        while (maxAttempts > 0) {
            randomTrack = generateRandomTrack();
            Response response = RequestApi.getOrderId(randomTrack);

            if (response.statusCode() == 404) {
                return randomTrack;

            }
            maxAttempts--;
        }

        throw new CustomException("Несуществующий трек номер не найден");
    }

    @Step("Генерация случайного трека заказа")
    private static String generateRandomTrack() {
        String characters = "123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int indexOfNextCLoginChar = random.nextInt(characters.length());
            sb.append(characters.charAt(indexOfNextCLoginChar));
        }
        return sb.toString();
    }
}
