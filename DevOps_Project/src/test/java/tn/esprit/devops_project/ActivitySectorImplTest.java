package tn.esprit.devops_project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.devops_project.entities.ActivitySector;
import tn.esprit.devops_project.repositories.ActivitySectorRepository;
import tn.esprit.devops_project.services.ActivitySectorImpl;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ActivitySectorImplTest {

    @InjectMocks
    private ActivitySectorImpl activitySectorservice;

    @Mock
    private ActivitySectorRepository activitySectorRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testretrieveAllActivitySectors() {

        List<ActivitySector> activitySectors = Arrays.asList(new ActivitySector(), new ActivitySector());

        // Mock the behavior of the repository
        when(activitySectorRepository.findAll()).thenReturn(activitySectors);

        // Call the service method
        List<ActivitySector> result = activitySectorservice.retrieveAllActivitySectors();

        // Verify that the repository method was called and the result is as expected
        verify(activitySectorRepository).findAll();
        assertEquals(2, result.size());

    }

    @Test
    void addActivitySector() {

        ActivitySector activitySector = new ActivitySector();

        // Mock the behavior of the repository
        when(activitySectorRepository.save(activitySector)).thenReturn(activitySector);

        // Call the service method
        ActivitySector result = activitySectorservice.addActivitySector(activitySector);

        // Verify that the repository method was called and the result is as expected
        verify(activitySectorRepository).save(activitySector);
        assertEquals(activitySector, result);



    }

    @Test
    void deleteActivitySector() {

        Long id = 1L;

        // Mock the behavior of the repository's deleteById method
        doNothing().when(activitySectorRepository).deleteById(id);

        // Call the service method
        activitySectorservice.deleteActivitySector(id);

        // Verify that the repository's deleteById method was called
        verify(activitySectorRepository).deleteById(id);


    }

    @Test
    void updateActivitySector() {

        ActivitySector activitySector = new ActivitySector();

        // Mock the behavior of the repository's save method
        when(activitySectorRepository.save(activitySector)).thenReturn(activitySector);

        // Call the service method
        ActivitySector result = activitySectorservice.updateActivitySector(activitySector);

        // Verify that the repository's save method was called and the result is as expected
        verify(activitySectorRepository).save(activitySector);
        assertEquals(activitySector, result);



    }

    @Test
    void retrieveActivitySector() {
        ActivitySector activitySector = new ActivitySector();
        Long id = 1L;

        // Mock the behavior of the repository
        when(activitySectorRepository.findById(id)).thenReturn(Optional.of(activitySector));

        // Call the service method
        ActivitySector result = activitySectorservice.retrieveActivitySector(id);

        // Verify that the repository method was called and the result is as expected
        verify(activitySectorRepository).findById(id);
        assertEquals(activitySector, result);


    }

    @Test
    void testUpdateActivitySector_NonExisting() {
        ActivitySector activitySector = new ActivitySector();

        when(activitySectorRepository.save(activitySector)).thenThrow(new IllegalArgumentException("ActivitySector does not exist."));

        assertThrows(IllegalArgumentException.class, () -> {
            activitySectorservice.updateActivitySector(activitySector);
        });

        verify(activitySectorRepository).save(activitySector);
    }


    @Test
    void testDeleteActivitySector_NonExisting() {
        Long invalidId = 999L;

        doThrow(new IllegalArgumentException("ActivitySector does not exist.")).when(activitySectorRepository).deleteById(invalidId);

        assertThrows(IllegalArgumentException.class, () -> {
            activitySectorservice.deleteActivitySector(invalidId);
        });

        verify(activitySectorRepository).deleteById(invalidId);
    }
}