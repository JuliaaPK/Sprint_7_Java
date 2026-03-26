package ru.samokat.yandex;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.samokat.yandex.courier.CourierCreator;
import ru.samokat.yandex.requests.RequestApi;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class CourierLoginTest extends CommonTest{

    @Test
    @DisplayName("Успешная авторизация курьера")
    public void successCourierAuthorizationTest() {
        courier = CourierCreator.create();
        RequestApi.registrationCourier(courier);
        Response response = RequestApi.loginCourier(courier);

        response
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация курьера под несуществующим пользователем")
    public void courierAuthorizationWithoutRegistration() {
        courier = CourierCreator.create();
        Response response = RequestApi.loginCourier(courier);

        response
                .then()
                .assertThat()
                .statusCode(404)
                .body("message", equalTo(RequestsErrors.ERROR_LOGIN_OR_PASSWORD_NOT_FOUND));
    }

    @Test
    @DisplayName("Авторизация курьера с несуществующим логином")
    public void courierAuthorizationWithWrongLogin() {
        courier = CourierCreator.create();
        RequestApi.registrationCourier(courier);
        courier.setLogin(CourierCreator.generateLogin(10));
        Response response = RequestApi.loginCourier(courier);

        response
                .then()
                .assertThat()
                .statusCode(404)
                .body("message", equalTo(RequestsErrors.ERROR_LOGIN_OR_PASSWORD_NOT_FOUND));
    }

    @Test
    @DisplayName("Авторизация курьера с несуществующим паролем")
    public void courierAuthorizationWithWrongPassword() {
        courier = CourierCreator.create();
        RequestApi.registrationCourier(courier);
        courier.setPassword(CourierCreator.generatePassword(10));
        Response response = RequestApi.loginCourier(courier);

        response
                .then()
                .assertThat()
                .statusCode(404)
                .body("message", equalTo(RequestsErrors.ERROR_LOGIN_OR_PASSWORD_NOT_FOUND));
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    public void courierAuthorizationWithoutPassword() {
        courier = CourierCreator.create();
        RequestApi.registrationCourier(courier);
        courier.setPassword(null);
        Response response = RequestApi.loginCourier(courier);

        response
                .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo(RequestsErrors.ERROR_NOT_ENOUGH_DATA_FOR_LOGIN));
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    public void courierAuthorizationWithoutLogin() {
        courier = CourierCreator.create();
        RequestApi.registrationCourier(courier);
        courier.setLogin(null);
        Response response = RequestApi.loginCourier(courier);

        response
                .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo(RequestsErrors.ERROR_NOT_ENOUGH_DATA_FOR_LOGIN));
    }
}