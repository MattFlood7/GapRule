package com.campspot.gaprule;

import org.joda.time.DateTime;

/**
 * Class used to create a reservation.
 * 
 * @author mflood
 */
public class Reservation {

    // Class variables
    private DateTime startDate;
    private DateTime endDate;

    /**
     * Constructor used to create a reservation with a start and end date.
     *
     * @param startDate
     *            start date of the reservation.
     * @param endDate
     *            end date of the reservation.
     */
    public Reservation(DateTime startDate, DateTime endDate) {

        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Get the start date.
     *
     * @return the start date.
     */
    public DateTime getStartDate() {
        return startDate;
    }

    /**
     * Get the end date.
     *
     * @return the end date.
     */
    public DateTime getEndDate() {
        return endDate;
    }
}
