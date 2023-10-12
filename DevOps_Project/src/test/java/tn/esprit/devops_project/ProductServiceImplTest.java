package tn.esprit.devops_project;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;
import tn.esprit.devops_project.services.ProductServiceImpl;
import tn.esprit.devops_project.services.StockServiceImpl;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
class ProductServiceImplTest {
    @InjectMocks
    private ProductServiceImpl productServiceImpl;
    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockRepository stockRepository;


    @Test
    void addProduct() {
        Stock stock = new Stock();
        Product product = new Product();
        Long idStock = 1L;

        Mockito.when(stockRepository.findById(idStock)).thenReturn(Optional.of(stock));

        Mockito.when(productRepository.save(product)).thenReturn(product);

        Product result = productServiceImpl.addProduct(product, idStock);

        Mockito.verify(stockRepository).findById(idStock);
        Mockito.verify(productRepository).save(product);

        assertEquals(stock, product.getStock());
        assertEquals(product, result);

    }

    @Test
    void retrieveProduct() {
        Product mockProduct = new Product();
        mockProduct.setIdProduct(1L);
        mockProduct.setTitle("Product name");

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(mockProduct));

        Product retrievedProduct = productServiceImpl.retrieveProduct(1L);

        verify(productRepository).findById(1L);

        assertEquals(mockProduct, retrievedProduct);
    }

}