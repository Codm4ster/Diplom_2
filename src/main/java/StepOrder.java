import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class StepOrder extends EnvConfig {
    @Step("Создание заказа")
    public ValidatableResponse createOrder(CreateOrder order, String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDERS)
                .then().log().all();
    }

    @Step("Получение заказа")
    public ValidatableResponse getOrder(String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .when()
                .get(ORDERS)
                .then().log().all();
    }
}
