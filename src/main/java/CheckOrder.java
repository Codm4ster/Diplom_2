import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.*;

public class CheckOrder {

    @Step("Проверка, что создание заказа прошло успешно")
    public void createdOrderSuccessfully(ValidatableResponse createOrderResponse) {
        createOrderResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", is(true))
                .body("order.number", notNullValue());
    }

    @Step("Проверка, что нельзя создать заказ с неверным хешем ингредиентов")
    public void createdOrderWithWrongIngredientsHash(ValidatableResponse createOrderResponse) {
        createOrderResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }

    @Step("Проверка, что нельзя создать заказ без ингредиентов")
    public void createdOrderWithoutIngredients(ValidatableResponse createOrderResponse) {
        createOrderResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("success", is(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Step("Проверка, что успешно получен список заказов")
    public void getOrderSuccessfully(ValidatableResponse getOrderResponse) {
        getOrderResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", is(true));
    }

    @Step("Проверка, что нельзя получить список заказов без авторизации")
    public void getOrderWithoutAuthorised(ValidatableResponse getOrderResponse) {
        getOrderResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", equalTo("You should be authorised"));
    }
}
