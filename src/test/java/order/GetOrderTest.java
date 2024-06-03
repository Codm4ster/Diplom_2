package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import user.CreateUser;
import user.StepUser;

public class GetOrderTest {
    private final StepUser user = new StepUser();
    private final StepOrder order = new StepOrder();
    private final CheckOrder check = new CheckOrder();
    private CreateOrder burger = new CreateOrder();
    String accessToken;

    @After
    public void deleteUser() {
        if (accessToken != null) {
            user.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Получение заказов авторизованного пользователя")
    @Description("Проверка, что если под авторизованным пользователем передать запрос, можно получить список заказов")
    public void getOrderWithLoggedInUserTest() {
        var client = CreateUser.random();
        user.createUser(client);
        ValidatableResponse loginResponse = user.loginUser(client);
        accessToken = loginResponse.extract().path("accessToken");
        burger = IngredientsDto.correctIngredients();
        order.createOrder(burger, accessToken);
        ValidatableResponse getOrderResponse = order.getOrderWithAuthorization(accessToken);
        check.getOrderSuccessfully(getOrderResponse);
    }

    @Test
    @DisplayName("Получение заказов неавторизованного пользователя")
    @Description("Проверка, что если под неавторизованным пользователем передать запрос, возвращается ошибка")
    public void getOrderWithoutLoggedInUserTest() {
        var client = CreateUser.random();
        ValidatableResponse createResponse = user.createUser(client);
        accessToken = createResponse.extract().path("accessToken");
        burger = IngredientsDto.correctIngredients();
        order.createOrder(burger, accessToken);
        ValidatableResponse getOrderResponse = order.getOrderWithoutAuthorization();
        check.getOrderWithoutAuthorised(getOrderResponse);
    }
}
