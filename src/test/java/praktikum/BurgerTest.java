package praktikum;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class BurgerTest {
    @RunWith(Parameterized.class)
    public static class BurgerParameterizedTest {
        @Rule
        public MockitoRule rule = MockitoJUnit.rule();
        @Mock
        private Bun bunMock;
        @Mock
        private Ingredient ingredientMock;

        private String bunName;
        private float bunPrice;
        private IngredientType ingredientType;
        private String ingredientName;
        private float ingredientPrice;
        private Burger burger;

        public BurgerParameterizedTest(String bunName, float bunPrice, IngredientType ingredientType, String ingredientName, float ingredientPrice) {
            this.bunName = bunName;
            this.bunPrice = bunPrice;
            this.ingredientType = ingredientType;
            this.ingredientName = ingredientName;
            this.ingredientPrice = ingredientPrice;
        }

        @Parameterized.Parameters(name = "bunName = {0}, bunPrice = {1}, ingredientType = {2}, ingredientName = {3}, ingredientPrice = {4}")
        public static Object[][] getParameters() {
            return new Object[][]{
                    {"black bun", 100, IngredientType.SAUCE, "hot sauce", 100},
                    {"white bun", 200, IngredientType.SAUCE, "sour cream", 200},
                    {"red bun", 300, IngredientType.SAUCE, "chili sauce", 300},
                    {"red bun", 300, IngredientType.FILLING, "cutlet", 100},
                    {"black bun", 100, IngredientType.FILLING, "dinosaur", 200},
                    {"white bun", 200, IngredientType.FILLING, "sausage", 300}
            };
        }

        @Before
        public void setUp() {
            burger = new Burger();
        }

        @Test
        public void getPriceStubTest() {
            Mockito.when(ingredientMock.getPrice()).thenReturn(ingredientPrice);
            Mockito.when(bunMock.getPrice()).thenReturn(bunPrice);
            burger.addIngredient(ingredientMock);
            burger.setBuns(bunMock);

            assertEquals(bunPrice * 2 + ingredientPrice, burger.getPrice(), 0);
        }

        @Test
        public void getReceiptStubTest() {
            Mockito.when(bunMock.getName()).thenReturn(bunName);
            Mockito.when(ingredientMock.getType()).thenReturn(ingredientType);
            Mockito.when(ingredientMock.getName()).thenReturn(ingredientName);
            Mockito.when(bunMock.getPrice()).thenReturn(bunPrice);
            Mockito.when(ingredientMock.getPrice()).thenReturn(ingredientPrice);

            burger.addIngredient(ingredientMock);
            burger.setBuns(bunMock);

            float expectedBurgerPrice = bunPrice * 2 + ingredientPrice;
            StringBuilder expectedReceipt = new StringBuilder(String.format("(==== %s ====)%n", bunName));
            expectedReceipt.append(String.format("= %s %s =%n", ingredientType.toString().toLowerCase(), ingredientName));
            expectedReceipt.append(String.format("(==== %s ====)%n", bunName));
            expectedReceipt.append(String.format("%nPrice: %f%n", expectedBurgerPrice));
            assertEquals(expectedReceipt.toString(), burger.getReceipt());
        }
    }

    @RunWith(MockitoJUnitRunner.class)
    public static class BurgerNotParameterizedTest {
        @Mock
        private Bun bunMock;

        @Mock
        private Ingredient ingredientMock;

        @Mock
        private Ingredient ingredientMock2;

        private Burger burger;

        @Before
        public void setUp() {
            burger = new Burger();
        }

        @Test
        public void addIngredientTest() {
            burger.addIngredient(ingredientMock);
            assertEquals(true, burger.ingredients.contains(ingredientMock));
        }

        @Test
        public void removeIngredientTest() {
            burger.addIngredient(ingredientMock);
            burger.removeIngredient(burger.ingredients.indexOf(ingredientMock));
            assertEquals(false, burger.ingredients.contains(ingredientMock));
        }

        @Test
        public void moveIngredientTest() {
            burger.addIngredient(ingredientMock);
            burger.addIngredient(ingredientMock2);
            int index = burger.ingredients.indexOf(ingredientMock);
            int newIndex = burger.ingredients.indexOf(ingredientMock2);
            burger.moveIngredient(index, newIndex);
            assertEquals(ingredientMock, burger.ingredients.get(newIndex));
        }

        @Test
        public void getPriceMockTest() {
            burger.addIngredient(ingredientMock);
            burger.setBuns(bunMock);
            burger.getPrice();
            Mockito.verify(bunMock, Mockito.times(1)).getPrice();
            Mockito.verify(ingredientMock, Mockito.times(1)).getPrice();
        }

        @Test
        public void getReceiptMockTest() {
            burger.addIngredient(ingredientMock);
            burger.setBuns(bunMock);
            Mockito.when(ingredientMock.getType()).thenReturn(IngredientType.SAUCE);

            burger.getReceipt();

            Mockito.verify(bunMock, Mockito.times(2)).getName();
            Mockito.verify(ingredientMock, Mockito.times(1)).getType();
            Mockito.verify(ingredientMock, Mockito.times(1)).getName();
            Mockito.verify(bunMock, Mockito.times(1)).getPrice();
            Mockito.verify(ingredientMock, Mockito.times(1)).getPrice();
        }
    }
}
