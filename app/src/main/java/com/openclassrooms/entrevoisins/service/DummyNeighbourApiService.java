package com.openclassrooms.entrevoisins.service;

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

    @Override
    public List<Neighbour> getFavNeighbours() {
        List<Neighbour> favouriteNeighbours = new ArrayList<>();
        for (Neighbour neighbour : neighbours) {
            if (neighbour.getIsFavorite()) {
                favouriteNeighbours.add(neighbour);
            }
        }
        return favouriteNeighbours;
    }

    @Override
    public void setFavouriteNeighbour(long neighbourId) {
        for (Neighbour neighbour : neighbours) {
            if (neighbour.getId() == neighbourId) {
                neighbour.setIsFavorite(!neighbour.getIsFavorite());
            }
        }
    }

    @Override
    public Boolean getFavouriteNeighbour(long neighbourId) {
        for (Neighbour neighbour : neighbours) {
            if (neighbour.getId() == neighbourId) {
                return neighbour.getIsFavorite();
            }
        }
        return false;
    }

    @Override
    public void clearFavouriteListNeighbour() {
        for (Neighbour neighbour : neighbours) {
            if (neighbour.getIsFavorite()) {
                neighbour.setIsFavorite(false);
            }
        }
    }
}
