package au.com.digitalspider.biblegame.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.model.Location;
import au.com.digitalspider.biblegame.model.User;

@Service
public class LocationService {
	private static final Logger LOG = Logger.getLogger(LocationService.class);

	public Location get(String value) {
		return Location.valueOf(value);
	}

	public Location move(User user, Location fromLocation, Location toLocation) {
		try {
			return toLocation;
		} catch (Exception e) {
			LOG.error(e, e);
		}
		return fromLocation;
	}
}
