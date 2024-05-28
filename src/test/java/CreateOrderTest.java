import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class CreateOrderTest {
    private final StepUser user = new StepUser();
    private final StepOrder order = new StepOrder();
    private final CheckOrder check = new CheckOrder();
    private CreateOrder burger = new CreateOrder();
    String token;

    @After
    public void deleteUser() {
        if (token != null) {
            user.deleteUser(token);
        }
    }

    @Test
    @DisplayName("Создание заказа с авторизацией пользователя")
    @Description("Проверка, что если под авторизованным пользователем в ручку передать все обязательные поля, можно создать заказ")
    public void createOrderWithLoggedInUserTest() {
        var client = CreateUser.random();
        user.createUser(client);
        ValidatableResponse loginResponse = user.loginUser(client);
        token = loginResponse.extract().path("accessToken");
        burger = OrderGenerator.correctIngredients();
        ValidatableResponse createOrderResponse = order.createOrder(burger, token);
        check.createdOrderSuccessfully(createOrderResponse);
    }

    @Test
    @DisplayName("Создание заказа без авторизации пользователя")
    @Description("Проверка, что если без авторизации в ручку передать все обязательные поля, можно создать заказ")
    public void createOrderWithoutLoggedTest() {
        var client = CreateUser.random();
        ValidatableResponse createResponse = user.createUser(client);
        token = createResponse.extract().path("accessToken");
        burger = OrderGenerator.correctIngredients();
        ValidatableResponse createOrderResponse = order.createOrder(burger, token);
        check.createdOrderSuccessfully(createOrderResponse);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Проверка, что если в ручку не передать хеш ингредиентов, запрос возвращает ошибку")
    public void createOrderWithoutIngredientsTest() {
        var client = CreateUser.random();
        user.createUser(client);
        ValidatableResponse loginResponse = user.loginUser(client);
        token = loginResponse.extract().path("accessToken");
        burger = OrderGenerator.correctIngredients();
        burger.setIngredients(null);
        ValidatableResponse createOrderResponse = order.createOrder(burger, token);
        check.createdOrderWithoutIngredients(createOrderResponse);
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    @Description("Проверка, что если в ручку передать неверный хеш ингредиентов, запрос возвращает ошибку")
    public void createOrderWrongHashIngredientsTest() {
        var client = CreateUser.random();
        user.createUser(client);
        ValidatableResponse loginResponse = user.loginUser(client);
        token = loginResponse.extract().path("accessToken");
        burger = OrderGenerator.wrongIngredients();
        ValidatableResponse createOrderResponse = order.createOrder(burger, token);
        check.createdOrderWithWrongIngredientsHash(createOrderResponse);
    }
}
