package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;


/**
 * Neighbour API client
 */
public interface NeighbourApiService {

    /**
     * Get all my Neighbours
     * @return {@link List}
     */
    List<Neighbour> getNeighbours();

    /**
     * Deletes a neighbour
     * @param neighbour
     */
    void deleteNeighbour(Neighbour neighbour);

    /**
     * Create a neighbour
     * @param neighbour
     */
    void createNeighbour(Neighbour neighbour);

    /**
     * Get favourite Neighbours
     * @return {@link List}
     */
    List<Neighbour> getFavNeighbours();

    /**
     * Modify the Neighbour favourite status
     * @return {@link List}
     */
    void setFavouriteNeighbour(long neighbourId);

    /**
     * get the Neighbour favourite status
     * @return {@link List}
     */
    Boolean getFavouriteNeighbour(long neighbourId);

    /**
     * Modify the Neighbour favourite status
     * @return {@link List}
     */
    void clearFavouriteListNeighbour();
}
