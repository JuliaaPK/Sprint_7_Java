package ru.samokat.yandex;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.samokat.yandex.actions.Actions;
import ru.samokat.yandex.courier.CourierCreator;
import ru.samokat.yandex.order.Order;
import ru.samokat.yandex.order.OrderCreator;
import ru.samokat.yandex.requests.RequestApi;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderListTest extends CommonTest{

    @Test
    @DisplayName("Получение списка заказов курьера")
    public void getListOfCourierOrdersTest() {
        List<Order> orders = List.of(OrderCreator.create(), OrderCreator.create(), OrderCreator.create());
        List<String> acceptedOrderIds = new ArrayList<>();
        int existedOrderId = 0;
        courier = CourierCreator.create();
        RequestApi.registrationCourier(courier);
        String courierId = Actions.getCourierId(courier);

        for (Order order : orders) {
            String orderTrack = Actions.getOrderTrack(order);
            String orderId = Actions.getOrderId(orderTrack);
            acceptedOrderIds.add(orderId);
            RequestApi.acceptOrder(orderId, courierId);
        }

        Response response = RequestApi.getListOfOrders(courierId);
        List<Integer> ids = response.jsonPath().getList("orders.id");

        for (String acceptedOrder : acceptedOrderIds) {
            for (Integer orderId : ids) {
                if (acceptedOrder.equals(String.valueOf(orderId))){
                    existedOrderId += 1;
                }
            }
        }

        response
                .then()
                .assertThat()
                .statusCode(200);

        assertEquals(acceptedOrderIds.size(), existedOrderId);
    }
}