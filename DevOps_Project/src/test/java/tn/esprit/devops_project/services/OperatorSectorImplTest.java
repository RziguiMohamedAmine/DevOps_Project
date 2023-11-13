package tn.esprit.devops_project.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.devops_project.entities.OperatorSector;
import tn.esprit.devops_project.repositories.OperatorSectorRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class OperatorSectorImplTest {

    @InjectMocks
    private OperatorSectorImpl OperatorSectorservice;

    @Mock
    private OperatorSectorRepository OperatorSectorRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testretrieveAllOperatorSectors() {

        List<OperatorSector> OperatorSectors = Arrays.asList(new OperatorSector(), new OperatorSector());

        // Mock the behavior of the repository
        when(OperatorSectorRepository.findAll()).thenReturn(OperatorSectors);

        // Call the service method
        List<OperatorSector> result = OperatorSectorservice.retrieveAllOperatorSectors();

        // Verify that the repository method was called and the result is as expected
        verify(OperatorSectorRepository).findAll();
        assertEquals(2, result.size());

    }

    @Test
    void addOperatorSector() {

        OperatorSector OperatorSector = new OperatorSector();

        // Mock the behavior of the repository
        when(OperatorSectorRepository.save(OperatorSector)).thenReturn(OperatorSector);

        // Call the service method
        OperatorSector result = OperatorSectorservice.addOperatorSector(OperatorSector);

        // Verify that the repository method was called and the result is as expected
        verify(OperatorSectorRepository).save(OperatorSector);
        assertEquals(OperatorSector, result);



    }

    @Test
    void deleteOperatorSector() {

        Long id = 1L;

        // Mock the behavior of the repository's deleteById method
        doNothing().when(OperatorSectorRepository).deleteById(id);

        // Call the service method
        OperatorSectorservice.deleteOperatorSector(id);

        // Verify that the repository's deleteById method was called
        verify(OperatorSectorRepository).deleteById(id);


    }

    @Test
    void updateOperatorSector() {

        OperatorSector OperatorSector = new OperatorSector();

        // Mock the behavior of the repository's save method
        when(OperatorSectorRepository.save(OperatorSector)).thenReturn(OperatorSector);

        // Call the service method
        OperatorSector result = OperatorSectorservice.updateOperatorSector(OperatorSector);

        // Verify that the repository's save method was called and the result is as expected
        verify(OperatorSectorRepository).save(OperatorSector);
        assertEquals(OperatorSector, result);



    }

    @Test
    void retrieveOperatorSector() {
        OperatorSector OperatorSector = new OperatorSector();
        Long id = 1L;

        // Mock the behavior of the repository
        when(OperatorSectorRepository.findById(id)).thenReturn(Optional.of(OperatorSector));

        // Call the service method
        OperatorSector result = OperatorSectorservice.retrieveOperatorSector(id);

        // Verify that the repository method was called and the result is as expected
        verify(OperatorSectorRepository).findById(id);
        assertEquals(OperatorSector, result);


    }

    @Test
    void testUpdateOperatorSector_NonExisting() {
        OperatorSector OperatorSector = new OperatorSector();

        when(OperatorSectorRepository.save(OperatorSector)).thenThrow(new IllegalArgumentException("OperatorSector does not exist."));

        assertThrows(IllegalArgumentException.class, () -> {
            OperatorSectorservice.updateOperatorSector(OperatorSector);
        });

        verify(OperatorSectorRepository).save(OperatorSector);
    }


    @Test
    void testDeleteOperatorSector_NonExisting() {
        Long invalidId = 999L;

        doThrow(new IllegalArgumentException("OperatorSector does not exist.")).when(OperatorSectorRepository).deleteById(invalidId);

        assertThrows(IllegalArgumentException.class, () -> {
            OperatorSectorservice.deleteOperatorSector(invalidId);
        });

        verify(OperatorSectorRepository).deleteById(invalidId);
    }
}