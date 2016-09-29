package com.campspot.gaprule;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to contain camp site information.
 *
 * @author mflood
 */
public class CampSite {

    // Class variables
    private int id;
    private String name;
    private List<Reservation> reservations;

    /**
     * Constructor to initialize a CampSite variable without any reservations.
     *
     * @param id
     *            id of the camp site.
     * @param name
     *            name of the camp site.
     */
    public CampSite(int id, String name) {

        this.id = id;
        this.name = name;
        reservations = new ArrayList<Reservation>();
    }

    /**
     * Constructor to initialize a CampSite variable with defined reservations.
     *
     * @param id
     *            id of the camp site.
     * @param name
     *            name of the camp site.
     * @param reservations
     *            current reservations on the camp site.
     */
    public CampSite(int id, String name, List<Reservation> reservations) {

        this.id = id;
        this.name = name;
        this.reservations = reservations;
    }

    /**
     * Get the id.
     *
     * @return the id.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the reservations.
     *
     * @return the reservations.
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Add a reservation to the list of camp site reservations.
     *
     * @param addMe
     *            reservation to add to camp site.
     */
    public void addReservation(Reservation addMe) {
        reservations.add(addMe);
    }

    /**
     * Remove a reservation from the list of camp site reservations.
     *
     * @param removeMe
     *            reservation to remove from camp site.
     */
    public void removeReservation(Reservation removeMe) {
        reservations.remove(removeMe);
    }
}
