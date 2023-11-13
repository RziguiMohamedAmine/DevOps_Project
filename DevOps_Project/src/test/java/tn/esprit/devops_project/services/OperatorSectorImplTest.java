package tn.esprit.devops_project.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.repositories.OperatorRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class OperatorImplTest {

    @InjectMocks
    private OperatorImpl Operatorservice;

    @Mock
    private OperatorRepository OperatorRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testretrieveAllOperators() {

        List<Operator> Operators = Arrays.asList(new Operator(), new Operator());

        // Mock the behavior of the repository
        when(OperatorRepository.findAll()).thenReturn(Operators);

        // Call the service method
        List<Operator> result = Operatorservice.retrieveAllOperators();

        // Verify that the repository method was called and the result is as expected
        verify(OperatorRepository).findAll();
        assertEquals(2, result.size());

    }

    @Test
    void addOperator() {

        Operator Operator = new Operator();

        // Mock the behavior of the repository
        when(OperatorRepository.save(Operator)).thenReturn(Operator);

        // Call the service method
        Operator result = Operatorservice.addOperator(Operator);

        // Verify that the repository method was called and the result is as expected
        verify(OperatorRepository).save(Operator);
        assertEquals(Operator, result);



    }

    @Test
    void deleteOperator() {

        Long id = 1L;

        // Mock the behavior of the repository's deleteById method
        doNothing().when(OperatorRepository).deleteById(id);

        // Call the service method
        Operatorservice.deleteOperator(id);

        // Verify that the repository's deleteById method was called
        verify(OperatorRepository).deleteById(id);


    }

    @Test
    void updateOperator() {

        Operator Operator = new Operator();

        // Mock the behavior of the repository's save method
        when(OperatorRepository.save(Operator)).thenReturn(Operator);

        // Call the service method
        Operator result = Operatorservice.updateOperator(Operator);

        // Verify that the repository's save method was called and the result is as expected
        verify(OperatorRepository).save(Operator);
        assertEquals(Operator, result);



    }

    @Test
    void retrieveOperator() {
        Operator Operator = new Operator();
        Long id = 1L;

        // Mock the behavior of the repository
        when(OperatorRepository.findById(id)).thenReturn(Optional.of(Operator));

        // Call the service method
        Operator result = Operatorservice.retrieveOperator(id);

        // Verify that the repository method was called and the result is as expected
        verify(OperatorRepository).findById(id);
        assertEquals(Operator, result);


    }

    @Test
    void testUpdateOperator_NonExisting() {
        Operator Operator = new Operator();

        when(OperatorRepository.save(Operator)).thenThrow(new IllegalArgumentException("Operator does not exist."));

        assertThrows(IllegalArgumentException.class, () -> {
            Operatorservice.updateOperator(Operator);
        });

        verify(OperatorRepository).save(Operator);
    }


    @Test
    void testDeleteOperator_NonExisting() {
        Long invalidId = 999L;

        doThrow(new IllegalArgumentException("Operator does not exist.")).when(OperatorRepository).deleteById(invalidId);

        assertThrows(IllegalArgumentException.class, () -> {
            Operatorservice.deleteOperator(invalidId);
        });

        verify(OperatorRepository).deleteById(invalidId);
    }
}