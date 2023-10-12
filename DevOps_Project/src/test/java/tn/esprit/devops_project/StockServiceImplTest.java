package tn.esprit.devops_project;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;
import tn.esprit.devops_project.services.ProductServiceImpl;
import tn.esprit.devops_project.services.StockServiceImpl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class StockServiceImplTest {
    @InjectMocks
    private StockServiceImpl stockService;

    @Mock
    private StockRepository stockRepository;


    private void assertStocksEqual(Stock stock1, Stock stock2) {
        assertEquals(stock1.getIdStock(), stock2.getIdStock());
        assertEquals(stock1.getTitle(), stock2.getTitle());
    }

    @Test
    public void testAddStock() {
        Stock mockStock = new Stock();
        mockStock.setIdStock(1L);
        mockStock.setTitle("Test Stock");

        when(stockRepository.save(mockStock)).thenReturn(mockStock);

        Stock addedStock = stockService.addStock(mockStock);
        //Stock addedStock = stockService.addStock(new Stock());

        verify(stockRepository).save(mockStock);

        //assertStocksEqual(addedStock, mockStock);
        assertEquals(addedStock.getIdStock(), mockStock.getIdStock());
    }


    @Test
    public void testRetrieveStockById() {
        Long stockId = 1L;
        Stock mockStock = new Stock();
        mockStock.setIdStock(stockId);
        Mockito.when(stockRepository.findById(stockId)).thenReturn(Optional.of(mockStock));

        Stock retrievedStock = stockService.retrieveStock(stockId);
        Assertions.assertThat(retrievedStock.getIdStock()).isEqualTo(stockId);
    }

    @Test
    public void testRetrieveAllStock() {
        List<Stock> mockStocks = new ArrayList<>();
        mockStocks.add(new Stock());
        mockStocks.add(new Stock());
        Mockito.when(stockRepository.findAll()).thenReturn(mockStocks);

        List<Stock> retrievedStocks = stockService.retrieveAllStock();
        Assertions.assertThat(retrievedStocks).hasSize(2);
    }


}