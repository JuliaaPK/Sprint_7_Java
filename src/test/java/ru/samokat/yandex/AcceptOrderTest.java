package ru.samokat.yandex;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.samokat.yandex.actions.Actions;
import ru.samokat.yandex.courier.CourierCreator;
import ru.samokat.yandex.order.Order;
import ru.samokat.yandex.order.OrderCreator;
import ru.samokat.yandex.requests.RequestApi;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class AcceptOrderTest extends CommonTest {

    @Test
    @DisplayName("Успешное принятие заказа")
    public void successAcceptOrderTest() {
        courier = CourierCreator.create();
        Order order = OrderCreator.create();
        RequestApi.registrationCourier(courier);
        String courierId = Actions.getCourierId(courier);
        String orderTrack = Actions.getOrderTrack(order);
        String orderId = Actions.getOrderId(orderTrack);
        Response response = RequestApi.acceptOrder(orderId, courierId);

        response.then().assertThat().statusCode(200).body("ok", is(true));
    }

    @Test
    @DisplayName("Принятие заказа без номера заказа")
    public void acceptOrderWithoutOrderIdTest() {
        courier = CourierCreator.create();
        RequestApi.registrationCourier(courier);
        String courierId = Actions.getCourierId(courier);

        given()
                .queryParam("courierId", courierId)
                .when()
                .put(Urls.ACCEPT_ORDER_WITHOUT_ID)
                .then()
                .statusCode(404)
                .body("message", equalTo("Not Found."));
    }

    @Test
    @DisplayName("Принятие заказа с несуществующим номером заказа")
    public void acceptOrderWithNotExistedOrderTest() {
        courier = CourierCreator.create();
        RequestApi.registrationCourier(courier);
        String courierId = Actions.getCourierId(courier);
        String randomNotExistedId = "111111";
        Response response = RequestApi.acceptOrder(randomNotExistedId, courierId);

        response
                .then()
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("Заказа с таким id не существует"));
    }

    @Test
    @DisplayName("Принятие заказа без id курьера")
    public void acceptOrderWithoutCourierIdTest() {
        Order order = OrderCreator.create();
        String orderTrack = Actions.getOrderTrack(order);
        String orderId = Actions.getOrderId(orderTrack);

        given()
                .pathParam("id", orderId)
                .when()
                .put(Urls.ACCEPT_ORDER)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для поиска"));
    }

    @Test
    @DisplayName("Принятие заказа с несуществующим id курьера")
    public void acceptOrderWithNotExistedCourierTest() {
        courier = CourierCreator.create();
        Order order = OrderCreator.create();
        RequestApi.registrationCourier(courier);
        String courierId = Actions.getCourierId(courier);
        Actions.deleteCourier(courier);
        String orderTrack = Actions.getOrderTrack(order);
        String orderId = Actions.getOrderId(orderTrack);
        Response response = RequestApi.acceptOrder(courierId, orderId);

        response
                .then()
                .statusCode(404)
                .body("message", equalTo("Курьера с таким id не существует"));
    }
}