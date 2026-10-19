package org.formation.shop.catalog;

import org.formation.shop.inventory.internal.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CatalogIntegrationTests {

    @Autowired
    private Catalog catalog;

    @Autowired
    private StockRepository stockRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CatalogController(catalog)).build();
    }

    @Test
    void creatingCoffeePublishesEventAndInitializesStock() {
        Long coffee = catalog.createCoffee("Espresso", 2);

        assertThat(coffee).isNotNull();
        assertThat(stockRepository.findByCoffeeId(coffee)).isPresent();
    }

    @Test
    void creatingCoffeeViaGetRequestWorks() throws Exception {
        mockMvc.perform(get("/coffees?name=Espresso&price=10"))
                .andExpect(status().isOk());
    }
}
