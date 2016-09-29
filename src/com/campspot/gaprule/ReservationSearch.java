package com.campspot.gaprule;

import org.joda.time.DateTime;

/**
 * This class is used to initialize the search parameters parsed in the JSON file.
 * 
 * @author mflood
 */
public class ReservationSearch extends Reservation {

    /**
     * Used to initialize the search parameters parsed in the JSON file.
     *
     * @param startDate
     *            date start of the reservation is to begin from.
     * @param endDate
     *            date the reservation will end on.
     */
    public ReservationSearch(DateTime startDate, DateTime endDate) {
        super(startDate, endDate);
    }
}
