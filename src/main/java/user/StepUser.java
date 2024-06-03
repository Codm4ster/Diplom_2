package user;

import configure.EnvConfig;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class StepUser extends EnvConfig {

    @Step("Создание пользователя")
    public ValidatableResponse createUser(CreateUser user) {
        return spec()
                .body(user)
                .when()
                .post(REGISTER)
                .then().log().all();
    }

    @Step("Авторизация пользователя")
    public ValidatableResponse loginUser(CreateUser user) {
        return spec()
                .body(user)
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    @Step("Изменение данных пользователя с авторизацией")
    public ValidatableResponse changeUser(CreateUser user, String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(USER)
                .then().log().all();
    }

    @Step("Изменение данных пользователя без авторизации")
    public ValidatableResponse changeUserWithoutLogin(CreateUser user) {
        return spec()
                .body(user)
                .when()
                .patch(USER)
                .then().log().all();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .when()
                .delete(USER)
                .then().log().all();
    }
}
