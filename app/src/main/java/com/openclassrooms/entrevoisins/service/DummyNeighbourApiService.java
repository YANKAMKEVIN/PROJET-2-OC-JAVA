package com.openclassrooms.entrevoisins.service;

import android.util.Log;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getFavoritesNeighbours() {
        List<Neighbour> favoriteNeighbours = new ArrayList<>();
        for (Neighbour neighbour : neighbours) {
            if (neighbour.getIsFavorite()) {
                favoriteNeighbours.add(neighbour);
            }
        }
        return favoriteNeighbours;
    }

    /**
     * {@inheritDoc}
     * @param neighbourId
     */
    @Override
    public void setFavoriteById(long neighbourId) {
        for (Neighbour neighbour : neighbours) {
            if (neighbour.getId() == neighbourId) {
                neighbour.setIsFavorite(!neighbour.getIsFavorite());
            }
        }
    }

    /**
     * {@inheritDoc}
     * @param neighbourId
     */
    @Override
    public Boolean getFavoriteById(long neighbourId) {
        for (Neighbour neighbour : neighbours) {
            if (neighbour.getId() == neighbourId) {
                return neighbour.getIsFavorite();
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearFavoritesNeighbourList() {
        for (Neighbour neighbour : neighbours) {
            if (neighbour.getIsFavorite()) {
                neighbour.setIsFavorite(false);
            }
        }
    }
}
