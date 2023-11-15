package tn.esprit.devops_project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.entities.ActivitySector;
import tn.esprit.devops_project.repositories.ActivitySectorRepository;
import tn.esprit.devops_project.services.ActivitySectorImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivitySectorImplTest {

    @InjectMocks
    private ActivitySectorImpl activitySectorservice;

    @Mock
    private ActivitySectorRepository activitySectorRepository;

    @Test
    void testRetrieveAllActivitySectors() {
        // Given
        List<ActivitySector> activitySectors = new ArrayList<>();
        activitySectors.add(new ActivitySector());
        activitySectors.add(new ActivitySector());

        when(activitySectorRepository.findAll()).thenReturn(activitySectors);

        // When
        List<ActivitySector> result = activitySectorservice.retrieveAllActivitySectors();

        // Then
        verify(activitySectorRepository).findAll();
        assertEquals(2, result.size());
    }

    @Test
    void testAddActivitySector() {
        // Given
        ActivitySector activitySector = new ActivitySector();

        when(activitySectorRepository.save(activitySector)).thenReturn(activitySector);

        // When
        ActivitySector result = activitySectorservice.addActivitySector(activitySector);

        // Then
        verify(activitySectorRepository).save(activitySector);
        assertEquals(activitySector, result);
    }

    @Test
    void testDeleteActivitySector() {
        // Given
        Long id = 1L;

        doNothing().when(activitySectorRepository).deleteById(id);

        // When
        activitySectorservice.deleteActivitySector(id);

        // Then
        verify(activitySectorRepository).deleteById(id);
    }

    @Test
    void testUpdateActivitySector() {
        // Given
        ActivitySector activitySector = new ActivitySector();

        when(activitySectorRepository.save(activitySector)).thenReturn(activitySector);

        // When
        ActivitySector result = activitySectorservice.updateActivitySector(activitySector);

        // Then
        verify(activitySectorRepository).save(activitySector);
        assertEquals(activitySector, result);
    }

    @Test
    void testRetrieveActivitySector() {
        // Given
        ActivitySector activitySector = new ActivitySector();
        Long id = 1L;

        when(activitySectorRepository.findById(id)).thenReturn(Optional.of(activitySector));

        // When
        ActivitySector result = activitySectorservice.retrieveActivitySector(id);

        // Then
        verify(activitySectorRepository).findById(id);
        assertEquals(activitySector, result);
    }

    @Test
    void testUpdateActivitySector_NonExisting() {
        // Given
        ActivitySector activitySector = new ActivitySector();

        when(activitySectorRepository.save(activitySector)).thenThrow(new IllegalArgumentException("ActivitySector does not exist."));

        // Then
        assertThrows(IllegalArgumentException.class, () -> activitySectorservice.updateActivitySector(activitySector));

        verify(activitySectorRepository).save(activitySector);
    }

    @Test
    void testDeleteActivitySector_NonExisting() {
        // Given
        Long invalidId = 999L;

        doThrow(new IllegalArgumentException("ActivitySector does not exist.")).when(activitySectorRepository).deleteById(invalidId);

        // Then
        assertThrows(IllegalArgumentException.class, () -> activitySectorservice.deleteActivitySector(invalidId));

        verify(activitySectorRepository).deleteById(invalidId);
    }
}
