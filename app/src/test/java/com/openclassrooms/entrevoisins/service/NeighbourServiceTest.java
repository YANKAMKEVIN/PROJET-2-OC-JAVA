package com.openclassrooms.entrevoisins.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
        service.clearFavoritesNeighbourList();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void setFavoriteNeighbourWithSuccess() {
        long neighbourId = 2; // Set a valid neighbour ID
        service.setFavoriteById(neighbourId);
        Boolean isFavorite = service.getFavoriteById(neighbourId);
        assertTrue(isFavorite); // Check that the neighbour is now marked as favorite
    }

    @Test
    public void setDoublefavoriteNeighbourWithSuccess() {
        long neighbourId = 2; // Set a valid neighbour ID
        service.setFavoriteById(neighbourId);
        Boolean isFavorite = service.getFavoriteById(neighbourId);
        assertTrue(isFavorite); // Check that the neighbour is now marked as favorite
        service.setFavoriteById(neighbourId);
        isFavorite = service.getFavoriteById(neighbourId);
        assertFalse(isFavorite); // Check that the neighbour is no more marked as favorite
    }

    @Test
    public void getFavoriteNeighbourStatusWithSuccess() {
        long neighbourId = 3; // Set a valid neighbour ID
        service.setFavoriteById(neighbourId);
        Boolean isFavorite = service.getFavoriteById(neighbourId);
        assertTrue(isFavorite); // Check that the neighbour is marked as favorite
    }

    @Test
    public void getFavoriteNeighbourListWithSuccess() {
        service.setFavoriteById(4); // Set a neighbour as favorite
        List<Neighbour> favNeighbours = service.getFavoritesNeighbours();
        assertEquals(1, favNeighbours.size()); // Check that only 1 neighbour is returned
        Neighbour neighbour = favNeighbours.get(0);
        assertEquals(4, neighbour.getId()); // Check that the correct neighbour is returned
        assertEquals(true, neighbour.getIsFavorite()); // Check that the neighbour is marked as favorite
    }

}
