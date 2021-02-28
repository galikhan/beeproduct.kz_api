package kz.beeproduct.utils;

import kz.beeproduct.dto.OrdersDto;
import kz.beeproduct.dto.ProductDto;
import kz.beeproduct.dto.UsersDto;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class OrderUtils {

    public static String orderAndUserInfoToString(OrdersDto order, UsersDto savedUser) {
        return "Id заказа: " + order.id + "\n"
                + "Время заказа: " + order.orderTime + " \n"
                + "Продукты: "
                + "" + productsInfoToString(order.products) + "\n"
                + "Покупатель: " + savedUser.fullname + "\n"
                + "Адрес: " + savedUser.street + ", подьезд " + savedUser.entrance + ", этаж: " + savedUser.floor + ", кв: " + savedUser.flat + "\n"
                + "Номер: " + savedUser.phone + " \n"
                + "Тип оплаты: " + savedUser.paymentType;
    }

    private static String productsInfoToString(List<ProductDto> products) {

        AtomicReference<String> result = new AtomicReference<>("");
        AtomicReference<Integer> totalSum  = new AtomicReference<>(0);
        products.stream().forEach(p -> {
            int totalPriceForProduct = totalSum.get() + p.price * p.amount;
            String value = result.get() + "\n- " +  p.title + " (" +  p.amount + ") кол-во,  цена: " + p.price + " * " + p.amount + " = " + p.price * p.amount ;
            totalSum.set(totalPriceForProduct);
            result.set(value);
        });
        return result.toString() + "\n всего: " + totalSum.get() + " (!Сумма без учета доставки)";
    }

}
