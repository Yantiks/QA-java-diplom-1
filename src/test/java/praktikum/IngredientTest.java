package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class IngredientTest {
    private IngredientType ingredientType;
    private String ingredientName;
    private float ingredientPrice;
    private Ingredient ingredient;

    public IngredientTest(IngredientType ingredientType, String ingredientName, float ingredientPrice) {
        this.ingredientType = ingredientType;
        this.ingredientName = ingredientName;
        this.ingredientPrice = ingredientPrice;
    }

    @Parameterized.Parameters(name = "ingredientType = {0}, ingredientName = {1}, ingredientPrice = {2}")
    public static Object[][] getParameters() {
        return new Object[][]{
                {IngredientType.SAUCE, "hot sauce", 100},
                {IngredientType.SAUCE, "sour cream", 200},
                {IngredientType.SAUCE, "chili sauce", 300},
                {IngredientType.FILLING, "cutlet", 100},
                {IngredientType.FILLING, "dinosaur", 200},
                {IngredientType.FILLING, "sausage", 300}
        };
    }

    @Before
    public void setUp() {
        ingredient = new Ingredient(ingredientType, ingredientName, ingredientPrice);
    }

    @Test
    public void getPriceTest() {
        assertEquals(ingredientPrice, ingredient.getPrice(), 0);
    }

    @Test
    public void getNameTest() {
        assertEquals(ingredientName, ingredient.getName());
    }

    @Test
    public void getTypeTest() {
        assertEquals(ingredientType, ingredient.getType());
    }
}
