import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.*;

public class CheckUser {

    @Step("Проверка, что создание пользователя прошло успешно")
    public void createdSuccessfully(ValidatableResponse createResponse) {
        createResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", is(true))
                .body("accessToken", notNullValue());
    }

    @Step("Проверка, что нельзя создать пользователя, который уже зарегистрирован")
    public void createdDuplicateUser(ValidatableResponse createResponse) {
        createResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .body("success", is(false))
                .body("message", equalTo("User already exists"));
    }

    @Step("Проверка, что нельзя создать пользователя и не заполнить одно из обязательных полей")
    public void createdUserWithoutParameter(ValidatableResponse createResponse) {
        createResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .body("success", is(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Step("Проверка успешной авторизации пользователя")
    public void loggedInSuccessfully(ValidatableResponse loginResponse) {
        loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", is(true))
                .body("accessToken", notNullValue());
    }

    @Step("Проверка, что нельзя авторизоваться с неверным логином и паролем")
    public void loggedInWithNotCorrectParameters(ValidatableResponse loginResponse) {
        loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", equalTo("email or password are incorrect"));
    }

    @Step("Проверка, что изменение данных пользователя с авторизацией прошло успешно")
    public void changedDataUserSuccessfully(ValidatableResponse changeResponse) {
        changeResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", is(true));
    }

    @Step("Проверка, что нельзя изменить данные пользователя без авторизации")
    public void changedDataUserWithoutLogin(ValidatableResponse changeResponse) {
        changeResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", equalTo("You should be authorised"));
    }
}
