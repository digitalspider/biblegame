package au.com.digitalspider.biblegame.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

@Service
public class HelperService {

	/**
	 * Return a series of random elements from this lists, limited by the number
	 * provided.
	 * 
	 * @param list the list provided
	 * @param limit the limit of values to return
	 * @return the list of random values in the list
	 */
	public <T> Iterable<T> getRandomFromList(List<T> list, int limit) {
		Set<T> randomIds = new HashSet<>();
		if (limit <= 0 || limit >= list.size()) {
			return list;
		}
		while (randomIds.size() < limit) {
			int index = ThreadLocalRandom.current().nextInt(list.size());
			randomIds.add(list.get(index));
		}
		return randomIds;
	}

}
