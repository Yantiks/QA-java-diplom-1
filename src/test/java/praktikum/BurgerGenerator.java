package praktikum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BurgerGenerator {
    public static Burger getRandomBurger() {

        //Получаем булки, соусы и наполнения и БД
        Database database = new Database();
        List<Bun> buns = database.availableBuns();
        List<Ingredient> ingredients = database.availableIngredients();
        List<Ingredient> sauces = new ArrayList<Ingredient>();
        List<Ingredient> fillings = new ArrayList<Ingredient>();
        for (Ingredient ingredient:ingredients
        ) {
            if (ingredient.getType().equals(IngredientType.SAUCE)) {
                sauces.add(ingredient);
            } else if (ingredient.getType().equals(IngredientType.FILLING)) {
                fillings.add(ingredient);
            }
        }

        //Составляем случайный бургер
        Burger burger = new Burger();
        Random random = new Random();
        burger.bun = buns.get(random.nextInt(buns.size()));
        burger.addIngredient(sauces.get(random.nextInt(sauces.size())));
        burger.addIngredient(fillings.get(random.nextInt(fillings.size())));

        return burger;
    }
}
