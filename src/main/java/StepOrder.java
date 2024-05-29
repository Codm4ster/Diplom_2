package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class StepOrder extends EnvConfig {
    @Step("Создание заказа")
    public ValidatableResponse createOrder(CreateOrder order, String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(EnvConfig.ORDERS)
                .then().log().all();
    }

    @Step("Получение заказа с авторизацией")
    public ValidatableResponse getOrderWithAuthorization(String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .when()
                .get(EnvConfig.ORDERS)
                .then().log().all();
    }

    @Step("Получение заказа без авторизации")
    public ValidatableResponse getOrderWithoutAuthorization() {
        return spec()
                .when()
                .get(EnvConfig.ORDERS)
                .then().log().all();
    }
}
