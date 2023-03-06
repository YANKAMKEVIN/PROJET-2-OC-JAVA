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
        service.clearFavouriteListNeighbour();
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
    public void setFavouriteNeighbourWithSuccess() {
        long neighbourId = 2; // Set a valid neighbour ID
        service.setFavouriteNeighbour(neighbourId);
        Boolean isFavourite = service.getFavouriteNeighbour(neighbourId);
        assertTrue(isFavourite); // Check that the neighbour is now marked as favourite
    }

    @Test
    public void setDoubleFavouriteNeighbourWithSuccess() {
        long neighbourId = 2; // Set a valid neighbour ID
        service.setFavouriteNeighbour(neighbourId);
        Boolean isFavourite = service.getFavouriteNeighbour(neighbourId);
        assertTrue(isFavourite); // Check that the neighbour is now marked as favourite
        service.setFavouriteNeighbour(neighbourId);
        isFavourite = service.getFavouriteNeighbour(neighbourId);
        assertFalse(isFavourite); // Check that the neighbour is no more marked as favourite
    }

    @Test
    public void getFavoriteNeighbourStatusWithSuccess() {
        long neighbourId = 3; // Set a valid neighbour ID
        service.setFavouriteNeighbour(neighbourId);
        Boolean isFavourite = service.getFavouriteNeighbour(neighbourId);
        assertTrue(isFavourite); // Check that the neighbour is marked as favourite
    }

    @Test
    public void getFavouriteNeighbourListWithSuccess() {
        service.setFavouriteNeighbour(4); // Set a neighbour as favourite
        List<Neighbour> favNeighbours = service.getFavNeighbours();
        assertEquals(1, favNeighbours.size()); // Check that only 1 neighbour is returned
        Neighbour neighbour = favNeighbours.get(0);
        assertEquals(4, neighbour.getId()); // Check that the correct neighbour is returned
        assertEquals(true, neighbour.getIsFavorite()); // Check that the neighbour is marked as favourite
    }

}
