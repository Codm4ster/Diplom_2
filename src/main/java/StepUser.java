package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import EnvConfig;

public class StepUser extends EnvConfig {

    @Step("Создание пользователя")
    public ValidatableResponse createUser(CreateUser user) {
        return spec()
                .body(user)
                .when()
                .post(EnvConfig.REGISTER)
                .then().log().all();
    }

    @Step("Авторизация пользователя")
    public ValidatableResponse loginUser(CreateUser user) {
        return spec()
                .body(user)
                .when()
                .post(EnvConfig.LOGIN)
                .then().log().all();
    }

    @Step("Изменение данных пользователя")
    public ValidatableResponse changeUser(CreateUser user, String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(EnvConfig.USER)
                .then().log().all();
    }

    @Step("Изменение данных пользователя")
    public ValidatableResponse changeUserWithoutLogin(CreateUser user) {
        return spec()
                .body(user)
                .when()
                .patch(EnvConfig.USER)
                .then().log().all();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .when()
                .delete(EnvConfig.USER)
                .then().log().all();
    }
}
