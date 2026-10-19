package org.formation.catalog;

import org.formation.catalog.internal.CoffeeEntity;
import org.formation.inventory.internal.StockRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CatalogIntegrationTests {

    @Autowired
    private Catalog catalog;

    @Autowired
    private StockRepository stockRepository;

    @Test
    void creatingCoffeePublishesEventAndInitializesStock() {
        Long coffee = catalog.createCoffee("Espresso", 2);

        assertThat(coffee).isNotNull();
        assertThat(stockRepository.findByCoffeeId(coffee)).isPresent();
    }
}
