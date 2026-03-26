package ru.samokat.yandex;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.samokat.yandex.courier.CourierCreator;
import ru.samokat.yandex.requests.RequestApi;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


public class CourierRegistrationTest extends CommonTest {

    @Test
    @DisplayName("Успешная регистрация курьера")
    public void successRegistrationTest() {
        courier =  CourierCreator.create();
        Response response = RequestApi.registrationCourier(courier);

        response
                .then()
                .assertThat()
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Повторная регистрация курьера с одинаковыми значениями полей")
    public void courierRegistrationWithSameDataTest() {
        courier = CourierCreator.create();
        RequestApi.registrationCourier(courier);
        Response response = RequestApi.registrationCourier(courier);

        response
                .then()
                .assertThat()
                .statusCode(409)
                .body("message", equalTo(RequestsErrors.ERROR_REPEATED_LOGIN));
    }

    @Test
    @DisplayName("Регистрация курьера без логина")
    public void courierRegistrationWithoutLoginTest() {
        courier = CourierCreator.create();
        courier.setLogin(null);
        Response response = RequestApi.registrationCourier(courier);

        response
                .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo(RequestsErrors.ERROR_NOT_ENOUGH_DATA_FOR_REFISTRATION));
    }

    @Test
    @DisplayName("Регистрация курьера без пароля")
    public void courierRegistrationWithoutPasswordTest() {
        courier = CourierCreator.create();
        courier.setPassword(null);
        Response response = RequestApi.registrationCourier(courier);

        response
                .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo(RequestsErrors.ERROR_NOT_ENOUGH_DATA_FOR_REFISTRATION));
    }

    @Test
    @DisplayName("Регистрация курьера с уже существующим логином")
    public void courierRegistrationWithRepeatedLoginTest() {
        courier = CourierCreator.create();
        RequestApi.registrationCourier(courier);
        courier.setPassword(CourierCreator.generatePassword(10));
        courier.setFirstName(CourierCreator.generateFirstName(10));
        Response response = RequestApi.registrationCourier(courier);
        response
                .then()
                .assertThat()
                .statusCode(409)
                .body("message", equalTo(RequestsErrors.ERROR_REPEATED_LOGIN));
    }
}
