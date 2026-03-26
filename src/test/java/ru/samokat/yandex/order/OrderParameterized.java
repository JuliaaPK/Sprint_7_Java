package ru.samokat.yandex.order;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.List;
import java.util.stream.Stream;

public class OrderParameterized implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        List<Order> orders = List.of(
                OrderCreator.create(),
                OrderCreator.create(),
                OrderCreator.create()
        );

        orders.get(0).setColor(List.of("BLACK", "GREY"));
        orders.get(1).setColor(List.of("BLACK"));
        orders.get(2).setColor(List.of());

        return Stream.of(
                Arguments.of(orders.get(0)),
                Arguments.of(orders.get(1)),
                Arguments.of(orders.get(2))
        );
    }
}