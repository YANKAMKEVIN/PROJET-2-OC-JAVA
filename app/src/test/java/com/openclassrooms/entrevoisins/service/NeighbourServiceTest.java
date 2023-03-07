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
    /**
     * We ensure that we can get the good neighbourList trough NeighbourApiService
     */
    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    /**
     * We ensure that we are able to delete a chosen neighbour from the neighborList trough NeighbourApiService
     */
    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    /**
     * We ensure that we are able to set the favorite status to true trough NeighbourApiService
     */
    @Test
    public void setFavoriteNeighbourWithSuccess() {
        // Set a valid neighbour ID
        long neighbourId = 2;
        //Set that neighbour to true
        service.setFavoriteById(neighbourId);
        //Get the favorite status of the neighbor
        Boolean isFavorite = service.getFavoriteById(neighbourId);
        // Check that the neighbour is now marked as favorite
        assertTrue(isFavorite);
    }

    /**
     * We ensure that we if setFavoriteById is called twice on the same neighbor, then this neighbor will no more be favorite
     */
    @Test
    public void setFavoriteNeighbourTwiceWithSuccess() {
        // Set a valid neighbour ID
        long neighbourId = 2;
        service.setFavoriteById(neighbourId);
        Boolean isFavorite = service.getFavoriteById(neighbourId);
        // Check that the neighbour is now marked as favorite
        assertTrue(isFavorite);
        service.setFavoriteById(neighbourId);
        isFavorite = service.getFavoriteById(neighbourId);
        // Check that the neighbour is no more marked as favorite
        assertFalse(isFavorite);
    }

    /**
     * We ensure that we are able to get the favorite status of a neighbor
     */
    @Test
    public void getFavoriteNeighbourStatusWithSuccess() {
        // Set a valid neighbour ID
        long neighbourId = 3;
        //Get the favorite status
        Boolean isFavorite = service.getFavoriteById(neighbourId);
        //We verify that this neighbor is not favorite, because by default they are not
        assertFalse(isFavorite);
        //We change the favorite status
        service.setFavoriteById(neighbourId);
        isFavorite = service.getFavoriteById(neighbourId);
        // Check that the neighbour is marked as favorite
        assertTrue(isFavorite);
    }

    /**
     * We ensure that when the favorite status of a neighbor is set to true, then that neighbor will be add to the favoriteNeighborList
     */
    @Test
    public void getFavoriteNeighbourListWithSuccess() {
        // Set a neighbour as favorite
        service.setFavoriteById(4);
        List<Neighbour> favNeighbours = service.getFavoritesNeighbours();
        // Check that only 1 neighbour is returned
        assertEquals(1, favNeighbours.size());
        Neighbour neighbour = favNeighbours.get(0);
        // Check that the correct neighbour is returned
        assertEquals(4, neighbour.getId());
        // Check that the neighbour is marked as favorite
        assertEquals(true, neighbour.getIsFavorite());
    }

}
