package com.campspot.gaprule;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Static class used to parse json objects passed.
 * 
 * @author mflood
 */
public class CampSpotJSONParser {

    // JSON Keys
    private static final String SEARCH = "search";
    private static final String START_DATE = "startDate";
    private static final String END_DATE = "endDate";
    private static final String CAMPSITES = "campsites";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String RESERVATIONS = "reservations";
    private static final String CAMPSITE_ID = "campsiteId";

    /* Private constructor used so we can use this class statically. */
    private CampSpotJSONParser() {

    }

    /**
     * The reservation search from the json passed.
     *
     * @param json
     *            json to parse for reservation search.
     * @return the reservation search found.
     */
    public static ReservationSearch getSearchFromJSON(JSONObject json) {

        ReservationSearch search = new ReservationSearch(null, null);

        if (json.containsKey(SEARCH)) {

            JSONObject searchJSON = (JSONObject) json.get(SEARCH);
            DateTime startDate = getDateFromJSON(START_DATE, searchJSON);
            DateTime endDate = getDateFromJSON(END_DATE, searchJSON);

            search = new ReservationSearch(startDate, endDate);
        }

        return search;
    }

    /**
     * Get the list of camp sites from the json passed.
     *
     * @param json
     *            json to parse for camp sites.
     * @return a list of camp sites found in the json.
     */
    public static List<CampSite> getCampSitesFromJSON(JSONObject json) {

        List<CampSite> campSitesFound = new ArrayList<CampSite>();

        if (json.containsKey(CAMPSITES)) {

            JSONArray campSites = (JSONArray) json.get(CAMPSITES);

            for (int x = 0; x < campSites.size(); x++) {

                JSONObject campsiteJSON = (JSONObject) campSites.get(x);
                int id = getIntFromJSON(ID, campsiteJSON);
                String name = getStringFromJSON(NAME, campsiteJSON);

                CampSite campSite = new CampSite(id, name);

                if (json.containsKey(RESERVATIONS)) {

                    JSONArray reservations = (JSONArray) json.get(RESERVATIONS);

                    for (int y = 0; y < reservations.size(); y++) {

                        JSONObject reservationJSON = (JSONObject) reservations.get(y);
                        int campsiteId = getIntFromJSON(CAMPSITE_ID, reservationJSON);

                        if (campsiteId == id) {

                            DateTime startDate = getDateFromJSON(START_DATE, reservationJSON);
                            DateTime endDate = getDateFromJSON(END_DATE, reservationJSON);

                            campSite.addReservation(new Reservation(startDate, endDate));
                        }
                    }
                }

                campSitesFound.add(campSite);
            }
        }

        return campSitesFound;
    }

    /**
     * Get the DateTime value from the json object using the key passed.
     *
     * @param key
     *            key value that will be used against the json object.
     * @param json
     *            object where the key and value are found.
     * @return the DateTime value found using the json and key passed.
     */
    public static DateTime getDateFromJSON(String key, JSONObject json) {

        DateTime dateFound = null;

        if (json.containsKey(key)) {

            String date = (String) json.get(key);

            if (date != null && !date.isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
                dateFound = formatter.parseDateTime(date);
            }
        }

        return dateFound;
    }

    /**
     * Get the String value from the json object using the key passed.
     *
     * @param key
     *            key value that will be used against the json object.
     * @param json
     *            object where the key and value are found.
     * @return the String value found using the json and key passed.
     */
    public static String getStringFromJSON(String key, JSONObject json) {

        String valueFound = "";

        if (json.containsKey(key)) {
            valueFound = (String) json.get(key);
        }

        return valueFound;
    }

    /**
     * Get the int value from the json object using the key passed.
     *
     * @param key
     *            key value that will be used against the json object.
     * @param json
     *            object where the key and value are found.
     * @return the int value found using the json and key passed.
     */
    public static int getIntFromJSON(String key, JSONObject json) {

        int valueFound = 0;

        if (json.containsKey(key)) {
            valueFound = Math.toIntExact((Long) json.get(key));
        }

        return valueFound;
    }
}
