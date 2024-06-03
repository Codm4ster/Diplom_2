package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import user.CreateUser;
import user.StepUser;

public class CreateOrderTest {
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
    @DisplayName("Создание заказа с авторизацией пользователя")
    @Description("Проверка, что если под авторизованным пользователем в ручку передать все обязательные поля, можно создать заказ")
    public void createOrderWithLoggedInUserTest() {
        var client = CreateUser.random();
        user.createUser(client);
        ValidatableResponse loginResponse = user.loginUser(client);
        accessToken = loginResponse.extract().path("accessToken");
        burger = IngredientsDto.correctIngredients();
        ValidatableResponse createOrderResponse = order.createOrder(burger, accessToken);
        check.createdOrderSuccessfully(createOrderResponse);
    }

    @Test
    @DisplayName("Создание заказа без авторизации пользователя")
    @Description("Проверка, что если без авторизации в ручку передать все обязательные поля, можно создать заказ")
    public void createOrderWithoutLoggedTest() {
        var client = CreateUser.random();
        ValidatableResponse createResponse = user.createUser(client);
        accessToken = createResponse.extract().path("accessToken");
        burger = IngredientsDto.correctIngredients();
        ValidatableResponse createOrderResponse = order.createOrder(burger, accessToken);
        check.createdOrderSuccessfully(createOrderResponse);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Проверка, что если в ручку не передать хеш ингредиентов, запрос возвращает ошибку")
    public void createOrderWithoutIngredientsTest() {
        var client = CreateUser.random();
        user.createUser(client);
        ValidatableResponse loginResponse = user.loginUser(client);
        accessToken = loginResponse.extract().path("accessToken");
        burger = IngredientsDto.correctIngredients();
        burger.setIngredients(null);
        ValidatableResponse createOrderResponse = order.createOrder(burger, accessToken);
        check.createdOrderWithoutIngredients(createOrderResponse);
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    @Description("Проверка, что если в ручку передать неверный хеш ингредиентов, запрос возвращает ошибку")
    public void createOrderWrongHashIngredientsTest() {
        var client = CreateUser.random();
        user.createUser(client);
        ValidatableResponse loginResponse = user.loginUser(client);
        accessToken = loginResponse.extract().path("accessToken");
        burger = IngredientsDto.wrongIngredients();
        ValidatableResponse createOrderResponse = order.createOrder(burger, accessToken);
        check.createdOrderWithWrongIngredientsHash(createOrderResponse);
    }
}
