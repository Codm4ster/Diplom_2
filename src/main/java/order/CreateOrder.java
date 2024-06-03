package order;

public class CreateOrder {

    private String[] ingredients;

    public CreateOrder(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public CreateOrder() {
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}
