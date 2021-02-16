package restaurantChain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Chain {

	public List<Restaurant> restaurants = new ArrayList<>();

	public void addRestaurant(String name, int tables) throws InvalidName {
		Restaurant nuovo = new Restaurant(name, tables);
		if (restaurants.isEmpty()) {
			restaurants.add(nuovo);
			return;
		} else {
			for (Restaurant restaurant : restaurants) {
				if (restaurant.getName().equals(name)) {
					throw new InvalidName();
				}
			}
		}
		restaurants.add(nuovo);
	}

	public Restaurant getRestaurant(String name) throws InvalidName {
		for (Restaurant restaurant : restaurants) {
			if (restaurant.getName().equals(name)) {
				return restaurant;
			}
		}
		throw new InvalidName();
	}

	public List<Restaurant> sortByIncome() {
		List<Restaurant> restaurantByIncome = restaurants.stream()
				.sorted((r1, r2) -> Double.compare(r2.getIncome(), r1.getIncome()))
				.collect(Collectors.toList());

		return restaurantByIncome;
	}

	public List<Restaurant> sortByRefused() {
		
		List<Restaurant> restaurantByRefused = restaurants.stream()
				.sorted((r1, r2) -> Integer.compare(r1.getRefused(), r2.getRefused()))
				.collect(Collectors.toList());
				
		return restaurantByRefused;
	}

	public List<Restaurant> sortByUnusedTables() {
		List<Restaurant> restaurantByUnusedTables = restaurants.stream()
				.sorted((r1, r2) -> Integer.compare(r1.getUnusedTables(), r2.getUnusedTables()))
				.collect(Collectors.toList());
				
		return restaurantByUnusedTables;
	}
}
