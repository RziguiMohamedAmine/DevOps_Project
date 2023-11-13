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
    private OperatorServiceImpl Operatorservice;

    @Mock
    private OperatorRepository OperatorRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenValidId_thenOperatorShouldBeFound() {
        Long id = 1L;
        Operator operator = new Operator(/* parameters */);
        Mockito.when(OperatorRepository.findById(id)).thenReturn(Optional.of(operator));

        Operator found = Operatorservice.retrieveOperator(id);

        assertEquals(found.getIdOperateur(),id);
    }

}