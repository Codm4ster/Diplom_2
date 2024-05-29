package order;

import io.qameta.allure.Step;

public class OrderGenerator {
    @Step("Создание заказа с правильным хешем ингредиентов")
    public static CreateOrder correctIngredients() {
        String[] ingredients = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa70", "61c0c5a71d1f82001bdaaa72"};
        return new CreateOrder(ingredients);
    }
    @Step("Создание заказа с неверным хешем ингредиентов")
    public static CreateOrder wrongIngredients(){
        String[] ingredients = {"Флюоресцентная булка R2-D3", "Говяжий метеорит (отбивная)", "Соус Spicy-X"};
        return new CreateOrder(ingredients);
    }
}
