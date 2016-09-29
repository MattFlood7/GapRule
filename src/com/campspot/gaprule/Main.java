package com.campspot.gaprule;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {

    private static List<CampSite> campsitesFound;
    private static ReservationSearch search;
    private static int gapRule = 0;

    /**
     * Method called when jar file built is ran.
     *
     * @param args
     *            arguments passed when running jar file
     */
    public static void main(String[] args) {

        System.out.println("");
        setGapRuleFromArguments(args);
        String path = System.getProperty("user.dir").toString() + "/assets/test-case.json";
        parseJSONFile(path);
        campsitesFound = findReservableCampSites(search, campsitesFound, gapRule);

        for (CampSite campSite : campsitesFound) {
            System.out.println(campSite.getName());
        }

        System.out.println("");
    }

    /**
     * Parse the arguments to see if user if using a gap rule other than 0.
     *
     * @param args
     *            arguments passed in when running the jar.
     */
    private static void setGapRuleFromArguments(String[] args) {

        String[] keypair;

        for (String arg : args) {

            if (arg.matches(".*=.*")) {

                keypair = arg.split("=");

                if (keypair.length == 2) {
                    switch (keypair[0]) {
                    case "gapRule":
                        try {
                            gapRule = Integer.parseInt(keypair[1]);
                        } catch (Exception e) {
                            System.out.println("Looks like the gap rule provided was not a valid int value.");
                        }
                        break;
                    default:
                        break;
                    }
                }
            }
        }
    }

    /**
     * Parse the json file found from the path passed.
     *
     * @param pathToJSONFile
     *            path to the json file.
     */
    private static void parseJSONFile(String pathToJSONFile) {

        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader(pathToJSONFile));
            JSONObject json = (JSONObject) obj;

            search = CampSpotJSONParser.getSearchFromJSON(json);
            campsitesFound = CampSpotJSONParser.getCampSitesFromJSON(json);

        } catch (Exception e) {
            System.out.println("Whoops something went wrong!");
            e.printStackTrace();
        }
    }

    /**
     * Find a list of camp sites that are available based on the search and list of camp sites passed.
     *
     * @param search
     *            reservation interval that is desired to find available.
     * @param campSites
     *            list of camp sites that we will look for an opening in.
     * @param gapRule
     *            int value to apply to the search date interval to allow for gaps when looking at overlapping time
     *            intervals.
     * @return the list of camp sites that are available to be reserved based on the search.
     */
    private static List<CampSite> findReservableCampSites(ReservationSearch search, List<CampSite> campSites,
            int gapRule) {

        List<CampSite> reservableCampsites = new ArrayList<CampSite>();

        if (search == null) {

            System.out.println("\nSorry but your search values are incorrect. "
                    + "Please format them similar to 2016-06-04.\n");

        } else if (campSites == null || campSites.isEmpty()) {

            System.out.println("\nSorry but no camp sites were found in your JSON file.\n");

        } else {

            for (CampSite campSite : campSites) {

                boolean campSiteAvailable = true;

                if (!doDateRangesCollide(search, campSite.getReservations())) {

                    for (Reservation reservation : campSite.getReservations()) {

                        campSiteAvailable = doDateWindowsAbideByGapRule(search, reservation, gapRule);

                        if (campSiteAvailable)
                            break;
                    }
                }

                if (campSiteAvailable) {
                    reservableCampsites.add(campSite);
                }
            }
        }

        return reservableCampsites;
    }

    /**
     * Check to see if date intervals collide with each other.
     *
     * @param search
     *            reservation search date interval.
     * @param reservations
     *            reservations that the search is being compared to.
     * @return true if the any date intervals collide : false if the none of the date intervals collide.
     */
    private static boolean doDateRangesCollide(ReservationSearch search, List<Reservation> reservations) {

        boolean collision = false;

        for (Reservation reservation : reservations) {

            Interval searchInterval = new Interval(search.getStartDate(), search.getEndDate());
            Interval reservationInterval = new Interval(reservation.getStartDate(), reservation.getEndDate());

            collision = searchInterval.overlaps(reservationInterval);
            if (collision)
                break;
        }

        return collision;
    }

    /**
     * Check to see if the search date range and reservation meet the gap rule provided.
     *
     * @param search
     *            reservation search date interval.
     * @param reservation
     *            reservation that the search is being compared to.
     * @param gapRule
     *            the amount of days we do not want not reserved between camp sites.
     * @return true if the two date ranges meet the gap rule : false if the two date ranges do not meet the gap rule.
     */
    private static boolean doDateWindowsAbideByGapRule(ReservationSearch search, Reservation reservation, int gapRule) {

        boolean result = false;

        if (search.getStartDate().equals(reservation.getEndDate())
                || reservation.getStartDate().equals(search.getEndDate())) {

            result = false;

        } else if (search.getStartDate().isAfter(reservation.getEndDate())) {

            result = getNumDaysBetween(reservation.getEndDate(), search.getStartDate()) < gapRule;

        } else {

            result = getNumDaysBetween(search.getEndDate(), reservation.getStartDate()) < gapRule;
        }

        return result;
    }

    /**
     * Get the number of days between the two dates.
     *
     * @param date1
     *            start date to compare.
     * @param date2
     *            end date to compare.
     * @return the number of days between the two dates.
     */
    private static int getNumDaysBetween(DateTime date1, DateTime date2) {

        return Days.daysBetween(date1.toLocalDate(), date2.toLocalDate()).getDays();
    }
}
