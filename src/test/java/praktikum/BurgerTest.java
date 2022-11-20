package praktikum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static praktikum.BurgerGenerator.getRandomBurger;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {
    private Database database = new Database();

    @Mock
    private Bun bunMock;

    @Mock
    private Ingredient ingredientMock;

    @Test
    public void addIngredientTest() {
        Burger burger = new Burger();
        Random random = new Random();
        int index = random.nextInt(database.availableIngredients().size());
        Ingredient ingredient = database.availableIngredients().get(index);
        burger.addIngredient(ingredient);
        assertEquals(true, burger.ingredients.contains(ingredient));
    }

    @Test
    public void removeIngredientTest() {
        Burger burger = getRandomBurger();
        Random random = new Random();
        int index = random.nextInt(burger.ingredients.size());
        Ingredient ingredient = burger.ingredients.get(index);
        burger.removeIngredient(index);
        assertEquals(false, burger.ingredients.contains(ingredient));
    }

    @Test
    public void moveIngredientTest() {
        Burger burger = getRandomBurger();
        Random random = new Random();
        int index = random.nextInt(burger.ingredients.size());
        int newIndex = random.nextInt(burger.ingredients.size());
        Ingredient ingredient = burger.ingredients.get(index);
        burger.moveIngredient(index, newIndex);
        assertEquals(ingredient, burger.ingredients.get(newIndex));
    }

    @Test
    public void getPriceMockTest() {
        Burger burger = new Burger();
        burger.addIngredient(ingredientMock);
        burger.setBuns(bunMock);
        burger.getPrice();
        Mockito.verify(bunMock, Mockito.times(1)).getPrice();
        Mockito.verify(ingredientMock, Mockito.times(1)).getPrice();

    }

    @Test
    public void getPriceTest() {
        Burger burger = getRandomBurger();
        //рассчитываем ожидаемую стоимость бургера на основании данных из БД
        float expectedBunsPrice = burger.bun.getPrice() * 2;
        float expectedIngredientsPrice = 0;
        for (Ingredient ingredient : burger.ingredients
        ) {
            expectedIngredientsPrice += ingredient.getPrice();
        }

        assertEquals(expectedBunsPrice + expectedIngredientsPrice, burger.getPrice(), 0);
    }

    @Test
    public void getReceiptTest() {
        Burger burger = getRandomBurger();
        StringBuilder expectedReceipt = new StringBuilder(String.format("(==== %s ====)%n", burger.bun.getName()));
        for (Ingredient ingredient : burger.ingredients) {
            expectedReceipt.append(String.format("= %s %s =%n", ingredient.getType().toString().toLowerCase(),
                    ingredient.getName()));
        }
        expectedReceipt.append(String.format("(==== %s ====)%n", burger.bun.getName()));
        expectedReceipt.append(String.format("%nPrice: %f%n", burger.getPrice()));
        assertEquals(expectedReceipt.toString(), burger.getReceipt());
    }
}
