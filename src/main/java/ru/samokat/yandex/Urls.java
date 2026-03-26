package ru.samokat.yandex;

public class Urls {
    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    public static final String COURIER_REGISTARTION_ENDPOINT = "/api/v1/courier";
    public static final String COURIER_LOGIN_ENDPOINT = "/api/v1/courier/login";
    public static final String ORDER_CREATION_ENDPOINT = "/api/v1/orders";
    public static final String DELETE_COURIER = "/api/v1/courier/{id}";
    public static final String DELETE_COURIER_WITHOUT_PLACEHOLDER = "/api/v1/courier";
    public static final String ACCEPT_ORDER = "/api/v1/orders/accept/{id}";
    public static final String ACCEPT_ORDER_WITHOUT_ID = "/api/v1/orders/accept/";
    public static final String GET_LIST_OF_ORDERS = "/api/v1/orders";
    public static final String GET_ORDER_ID = "/api/v1/orders/track";

}
