package ru.samokat.yandex.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.samokat.yandex.Urls;
import ru.samokat.yandex.courier.Courier;
import ru.samokat.yandex.order.Order;

import static io.restassured.RestAssured.given;

public class RequestApi {

    @Step("Регистрация курьера /requestAPI")
    public static Response registrationCourier(Courier courier) {
        return given()
                .contentType("application/json")
                .body(courier)
                .when()
                .post(Urls.COURIER_REGISTARTION_ENDPOINT)
                .then()
                .extract()
                .response();
    }

    @Step("Логин курьера /requestAPI")
    public static Response loginCourier(Courier courier) {
        return given()
                .contentType("application/json")
                .body(courier)
                .when()
                .post(Urls.COURIER_LOGIN_ENDPOINT)
                .then()
                .extract()
                .response();
    }


    @Step("Удаление курьера /requestAPI")
    public static Response deleteCourier(String id) {
        return given()
                .pathParam("id", id)
                .when()
                .delete(Urls.DELETE_COURIER)
                .then()
                .extract()
                .response();
    }

    @Step("Создание заказа /requestAPI")
    public static Response createOrder(Order order) {
        return given()
                .contentType("application/json")
                .body(order)
                .when()
                .post(Urls.ORDER_CREATION_ENDPOINT)
                .then()
                .extract()
                .response();
    }

    @Step("Получение заказа по его трек номеру /requestAPI")
    public static Response getOrderId(String track) {
        return given()
                .queryParam("t", track)
                .when()
                .get(Urls.GET_ORDER_ID)
                .then()
                .extract()
                .response();
    }

    @Step("Принятие заказа /requestAPI")
    public static Response acceptOrder(String orderId, String courierId) {
        return given()
                .pathParam("id", orderId)
                .queryParam("courierId", courierId)
                .when()
                .put(Urls.ACCEPT_ORDER)
                .then()
                .extract()
                .response();
    }

    @Step("Получение списка заказов курьера /requestAPI")
    public static Response getListOfOrders(String courierId) {
        return given()
                .queryParam("courierId", courierId)
                .when()
                .get(Urls.GET_LIST_OF_ORDERS)
                .then()
                .extract()
                .response();
    }
}