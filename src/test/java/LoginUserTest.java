import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class LoginUserTest {
    private final StepUser user = new StepUser();
    private final CheckUser check = new CheckUser();
    String token;

    @After
    public void deleteUser() {
        if (token != null) {
            user.deleteUser(token);
        }
    }

    @Test
    @DisplayName("Успешная авторизация пользователя")
    @Description("Проверка, что если в ручку передать все обязательные поля созданного пользователя, можно авторизоваться")
    public void loggedInUserTest() {
        var client = CreateUser.random();
        user.createUser(client);
        ValidatableResponse loginResponse = user.loginUser(client);
        check.loggedInSuccessfully(loginResponse);

        token = loginResponse.extract().path("accessToken");
    }

    @Test
    @DisplayName("Авторизация пользователя с неверной почтой")
    @Description("Проверка, что если в ручку не передать поле почты, запрос возвращает ошибку")
    public void loggedInUserWithoutEmailTest() {
        var client = CreateUser.random();
        ValidatableResponse createResponse = user.createUser(client);
        token = createResponse.extract().path("accessToken");
        client.setEmail("");
        ValidatableResponse loginResponse = user.loginUser(client);
        check.loggedInWithNotCorrectParameters(loginResponse);
    }

    @Test
    @DisplayName("Авторизация пользователя с неверным паролем")
    @Description("Проверка, что если в ручку не передать поле пароля, запрос возвращает ошибку")
    public void loggedInUserWithoutPasswordTest() {
        var client = CreateUser.random();
        ValidatableResponse createResponse = user.createUser(client);
        token = createResponse.extract().path("accessToken");
        client.setPassword("");
        ValidatableResponse loginResponse = user.loginUser(client);
        check.loggedInWithNotCorrectParameters(loginResponse);
    }
}
