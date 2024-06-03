package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class ChangeUserTest {
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
    @DisplayName("Изменение данных пользователя с авторизацией")
    @Description("Проверка, что данные пользователя можно изменить, если авторизоваться")
    public void changeUserTest() {
        var client = CreateUser.random();
        user.createUser(client);
        ValidatableResponse loginResponse = user.loginUser(client);
        accessToken = loginResponse.extract().path("accessToken");
        client.setName("Jack Sparrow");
        ValidatableResponse changeResponse = user.changeUser(client, accessToken);
        check.changedDataUserSuccessfully(changeResponse);
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    @Description("Проверка, что если изменить данные пользователя без авторизации, возвращается ошибка")
    public void changeUserWithoutLoginTest() {
        var client = CreateUser.random();
        ValidatableResponse createResponse = user.createUser(client);
        accessToken = createResponse.extract().path("accessToken");
        client.setName("Jack Sparrow");
        ValidatableResponse changeResponse = user.changeUserWithoutLogin(client);
        check.changedDataUserWithoutLogin(changeResponse);
    }
}
