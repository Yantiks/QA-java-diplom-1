package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BunTest {
    private String bunName;
    private float bunPrice;
    private Bun bun;

    public BunTest(String bunName, float bunPrice) {
        this.bunName = bunName;
        this.bunPrice = bunPrice;
    }

    @Parameterized.Parameters(name = "bunName = {0}, bunPrice = {1}")
    public static Object[][] getParameters() {
        return new Object[][]{
                {"black bun", 100},
                {"white bun", 200},
                {"red bun", 300}
        };
    }

    @Before
    public void setUp() {
        bun = new Bun(bunName, bunPrice);
    }

    @Test
    public void getNameTest() {
        assertEquals(bunName, bun.getName());
    }

    @Test
    public void getPriceTest() {
        assertEquals(bunPrice, bun.getPrice(), 0);
    }
}
