package ru.samokat.yandex;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.samokat.yandex.actions.Actions;
import ru.samokat.yandex.courier.Courier;


public class CommonTest {

    protected Courier courier;

    @BeforeAll
    public static void setUrl() {
        RestAssured.baseURI = Urls.BASE_URL;
        RestAssured.config = RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.socket.timeout",6000)
                        .setParam("http.connection.timeout", 6000));
    }

    @BeforeEach
    public void setUp() {
        courier = null;

    }

    @AfterEach
    public void deleteCourier() {
        if (courier != null) {
            Actions.deleteCourier(courier);
        }
    }
}
