package ru.samokat.yandex;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.samokat.yandex.actions.Actions;
import ru.samokat.yandex.courier.CourierCreator;
import ru.samokat.yandex.requests.RequestApi;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CourierDeleteTest extends CommonTest{

    @Test
    @DisplayName("Удаление курьера по id")
    public void deleteCourierByIdTest() {
        courier = CourierCreator.create();
        RequestApi.registrationCourier(courier);
        String courierId = Actions.getCourierId(courier);
        Response response = RequestApi.deleteCourier(courierId);

        response
                .then()
                .assertThat()
                .statusCode(200).body("ok", is(true));
    }

    @Test
    @DisplayName("Удаление курьера без id")
    public void deleteCourierWithoutIdTest() {
        given()
                .when()
                .delete(Urls.DELETE_COURIER_WITHOUT_PLACEHOLDER)
                .then()
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("Not Found."));
    }

    @Test
    @DisplayName("Удаление курьера с несуществующим id")
    public void deleteCourierWithNotExistedIdTest() {
        courier = CourierCreator.create();
        RequestApi.registrationCourier(courier);
        String courierId = Actions.getCourierId(courier);
        RequestApi.deleteCourier(courierId);
        Response response = RequestApi.deleteCourier(courierId);

        response
                .then()
                .assertThat().statusCode(404)
                .body("message", equalTo("Курьера с таким id нет."));
    }
}