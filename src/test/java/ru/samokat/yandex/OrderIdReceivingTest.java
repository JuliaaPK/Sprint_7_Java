package ru.samokat.yandex;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.samokat.yandex.actions.Actions;
import ru.samokat.yandex.order.Order;
import ru.samokat.yandex.order.OrderCreator;
import ru.samokat.yandex.requests.RequestApi;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class OrderIdReceivingTest extends CommonTest{

    @Test
    @DisplayName("Успешное получение номера заказа")
    public void successReceivingOrderTest() {
        Order order = OrderCreator.create();
        String orderTrack = Actions.getOrderTrack(order);
        Response response = RequestApi.getOrderId(orderTrack);

        response
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Получение номера заказа без переданного трек номера")
    public void receivingOrderWithoutTrackTest() {
        given()
                .when()
                .get(Urls.GET_ORDER_ID)
                .then()
                .statusCode(400)
                .body("message",equalTo("Недостаточно данных для поиска"));
    }

    @Test
    @DisplayName("Получение номера заказа с несуществующим трек номером")
    public void receivingOrderWithNotExistedTrackTest() throws CustomException {
        String orderTrack = Actions.getNotExistedOrderTrack();
        Response response = RequestApi.getOrderId(orderTrack);

        response
                .then()
                .statusCode(404)
                .body("message",equalTo("Заказ не найден"));
    }
}