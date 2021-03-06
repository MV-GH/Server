package be.howest.ti.alhambra.logic;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CoinsTest {

    @Test
    void coins() {
        //declarations
        Coins coins = new Coins();
        Coin coin1 = new Coin(Currency.GREEN, 3);
        Coin coin2 = new Coin(Currency.BLUE, 9);
        List<Coin> listCoins = new ArrayList<>();
        listCoins.add(coin1);

        coins.addCoins(new Coin[]{coin1});
        //test adding coin
        assertEquals(listCoins, coins.getCoinsBag());
        //test contains method
        assertTrue(coins.containsCoin(coin1));

        coins.removeCoins(new Coin[]{coin1});
        //test remove coin
        assertEquals(Collections.emptyList(), coins.getCoinsBag());
        //test adding coins
        coins.addCoins(new Coin[]{coin1,coin1,coin2});

        List<Coin> listCoins2 = new ArrayList<>();
        listCoins2.add(coin1);
        listCoins2.add(coin1);
        listCoins2.add(coin2);

        assertEquals(listCoins2, coins.getCoinsBag());

        coins.removeCoins(new Coin[]{coin1,coin2});
        //test remove coins
        assertEquals(listCoins, coins.getCoinsBag());
        //test error on removing coin that doesnt exist
        assertThrows(IllegalArgumentException.class,()->coins.removeCoins(new Coin[]{coin2}));

        assertTrue(coins.equals(coins));
        assertFalse(coins.equals(null));
        Coin[] newCoins = new Coin[2];
        newCoins[0] = new Coin(Currency.GREEN,9);
        newCoins[1] = new Coin(Currency.ORANGE,2);
      assertFalse( coins.containsCoins(newCoins));
      assertTrue(coins.containsCoins(new Coin[]{coin1}));


    }
}
