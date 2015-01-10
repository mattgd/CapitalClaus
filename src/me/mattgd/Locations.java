package me.mattgd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Locations {
	
	public static List<String> locationList = new ArrayList<String>();
	
	public static void removeLocationFromList(String location) {
		locationList.remove(location);
	}
	
	public static void populateLocationList() {
		for (String location: locations) {
			locationList.add(location);
		}
	}
	
	// Creates a list of locations that start with the passed letter
	public static String getListOfLocations(String letter) {
		
		String locationString = "";
		
		Iterator<String> iterator = locationList.iterator();
		if (iterator.hasNext()) {
			locationString += iterator.next();
		}
		while (iterator.hasNext()) {
			if (iterator.next().startsWith(letter)) {
				locationString += ", " + iterator.next();
			}
		}
		
		return locationString;
		
	}
	
	public static String formatLocationInput(String rawLocation) {
		String formattedLocation = rawLocation;
		
		// Trim extra spaces
		formattedLocation.trim();
		
		// Capitalize first letter
		formattedLocation = formattedLocation.substring(0, 1).toUpperCase() + formattedLocation.substring(1, formattedLocation.length());
		
		String firstPart, secondPart, letterToCapitalize;
		
		List<Integer> spacesList = new ArrayList<Integer>();
		
		// Find all of the spaces in the phrase
		int index = formattedLocation.indexOf(" ");
		while (index >= 0) {
		    spacesList.add(index);
		    index = formattedLocation.indexOf(" ", index + 1);
		}
		
		// Capitalize first letter of each word
		for (int spaceIndex : spacesList) {
			firstPart = formattedLocation.substring(0, spaceIndex + 1);
			letterToCapitalize = formattedLocation.substring(spaceIndex + 1, spaceIndex + 2).toUpperCase();
			secondPart = formattedLocation.substring(spaceIndex + 2, formattedLocation.length());
			formattedLocation = firstPart + letterToCapitalize + secondPart;
		}
		
		return formattedLocation;
	}

	public static String[] locations = {
	                            "Accra",
	                            "Addis Ababa",
	                            "Adelaide",
	                            "Algiers",
	                            "Almaty",
	                            "Amman",
	                            "Amsterdam",
	                            "Anadyr",
	                            "Anchorage",
	                            "Ankara",
	                            "Antananarivo",
	                            "Asuncion",
								"Athens",
								"Atlanta",
								"Auckland",
								"Baghdad",
								"Bangalore",
								"Bangkok",
								"Barcelona",
								"Beijing",
								"Beirut",
								"Belgrade",
								"Berlin",
								"Bogota",
								"Boston",
								"Brasilia",
								"Brisbane",
								"Brussels",
								"Bucharest",
								"Budapest",
								"Buenos Aires",
								"Cairo",
								"Calgary",
								"Canberra",
								"Cape Town",
								"Caracas",
								"Casablanca",
								"Chicago",
								"Columbus",
								"Copenhagen",
								"Dallas",
								"Dar es Salaam", // Remove?
								"Darwin",
								"Denver",
								"Detroit",
								"Dhaka",
								"Doha",
								"Dubai",
								"Dublin",
								"Edmonton",
								"Frankfurt",
								"Guatemala",
								"Halifax",
								"Hanoi",
								"Harare",
								"Havana",
								"Helsinki",
								"Hong Kong",
								"Honolulu",
								"Houston",
								"Indianapolis",
								"Islamabad",
								"Istanbul",
								"Jakarta",
								"Jerusalem",
								"Johannesburg",
								"Kabul",
								"Karachi",
								"Karaj",
								"Kathmandu",
								"Khartoum",
								"Kingston",
								"Kinshasa",
								"Kiritimati",
								"Kolkata",
								"Kuala Lumpur",
								"Kuwait City",
								"Kyiv",
								"La Paz",
								"Lagos",
								"Lahore",
								"Las Vegas",
								"Lima",
								"Lisbon",
								"London",
								"Los Angeles",
								"Madrid",
								"Managua",
								"Manila",
								"Melbourne",
								"Mexico City",
								"Miami",
								"Minneapolis",
								"Minsk",
								"Montevideo",
								"Montreal",
								"Moscow",
								"Mumbai",
								"Nairobi",
								"Nassau",
								"New Delhi",
								"New Orleans",
								"New York",
								"Oslo",
								"Ottawa",
								"Paris",
								"Perth",
								"Philadelphia",
								"Phoenix",
								"Prague",
								"Reykjavik",
								"Rio de Janeiro", // Remove?
								"Riyadh",
								"Rome",
								"Salt Lake City",
								"San Francisco",
								"San Juan",
								"San Salvador",
								"Santiago",
								"Santo Domingo",
								"Sao Paulo",
								"Seattle",
								"Seoul",
								"Shanghai",
								"Singapore",
								"Sofia",
								"St. John's", // Remove?
								"Stockholm",
								"Suva",
								"Sydney",
								"Taipei",
								"Tallinn",
								"Tashkent",
								"Tegucigalpa",
								"Tehran",
								"Tokyo",
								"Toronto",
								"Vancouver",
								"Vienna",
								"Warsaw",
								"Washington DC",
								"Winnipeg",
								"Yangon",
								"Zagreb",
								"Zurich"
	};
}
