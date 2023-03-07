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
     * Get the list of all favorites Neighbours
     * @return {@link List}
     */
    List<Neighbour> getFavoritesNeighbours();

    /**
     * Modify the Neighbour favorite status
     * @param neighbourId
     */
    void setFavoriteById(long neighbourId);

    /**
     * get the Neighbour favorite status
     * @param neighbourId
     */
    Boolean getFavoriteById(long neighbourId);

    /**
     * Clear the Favorites Neighbour's list
     *
     */
    void clearFavoritesNeighbourList();
}
