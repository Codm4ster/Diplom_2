package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class CreateUserTest {
    private final StepUser user = new StepUser();
    private final CheckUser check = new CheckUser();
    String accessToken;

    @After
    public void deleteUser() {
        if (accessToken != null) {
            user.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Пользователя можно создать")
    @Description("Проверка, что если в ручку передать все обязательные поля, то пользователя можно создать")
    public void createUserTest() {
        var client = CreateUser.random();
        ValidatableResponse createResponse = user.createUser(client);
        check.createdSuccessfully(createResponse);

        accessToken = createResponse.extract().path("accessToken");
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    @Description("Проверка, что если создать пользователя, который уже есть, возвращается ошибка")
    public void createDuplicateUserTest() {
        var client = CreateUser.random();
        ValidatableResponse createResponse = user.createUser(client);
        accessToken = createResponse.extract().path("accessToken");
        ValidatableResponse createDuplicateResponse = user.createUser(client);
        check.createdDuplicateUser(createDuplicateResponse);
    }

    @Test
    @DisplayName("Создание пользователя без указания почты")
    @Description("Проверка, что если создать пользователя без указания почты, запрос возвращает ошибку")
    public void createUserWithoutParameterTest() {
        var client = CreateUser.random();
        client.setEmail("");
        ValidatableResponse createResponse = user.createUser(client);
        check.createdUserWithoutParameter(createResponse);
    }
}
