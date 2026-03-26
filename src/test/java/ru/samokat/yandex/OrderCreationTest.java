package ru.samokat.yandex;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import ru.samokat.yandex.order.Order;
import ru.samokat.yandex.order.OrderParameterized;
import ru.samokat.yandex.requests.RequestApi;

import static org.hamcrest.Matchers.notNullValue;


public class OrderCreationTest extends CommonTest {

    @ParameterizedTest
    @ArgumentsSource(OrderParameterized.class)
    @DisplayName("Создание заказа с необязательным полем Color")
    public void createOrderWithTwoColorsTest(Order order) {
        Response response = RequestApi.createOrder(order);

        response
                .then()
                .assertThat()
                .statusCode(201)
                .body("track", notNullValue());
    }
}