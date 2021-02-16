package restaurantChain;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Restaurant {

	public Map<String, Double> menus = new HashMap<>();
	public Map<String, Integer> reserved = new HashMap<>();
	public Map<String, String[]> orders = new TreeMap<>();
	public List<String> payed = new LinkedList<>();

	private String name;
	private int tables;

	private int getRefused = 0;
	private int unusedTables = 0;

	private double income = 0;

	Restaurant(String name, int tables) {
		this.name = name;
		this.tables = tables;
		this.unusedTables = tables;
	}

	public String getName() {
		return name;
	}

	public void addMenu(String name, double price) throws InvalidName {
		if (menus.isEmpty() || !menus.containsKey(name)) {
			menus.put(name, price);
		} else {
			throw new InvalidName();
		}
	}

	public int reserve(String name, int persons) throws InvalidName {
		if (reserved.containsKey(name)) {
			throw new InvalidName();
		}
		int tablesNum = 0;
		if (persons % 4 != 0) {
			tablesNum = (persons / 4) + 1;
		} else {
			tablesNum = persons / 4;
		}
		if (unusedTables < tablesNum || tablesNum > tables) {
			getRefused = getRefused + persons;
			tablesNum = 0;
		} else {
			reserved.put(name, persons);
			unusedTables = unusedTables - tablesNum;
		}
		return tablesNum;
	}

	public int getRefused() {
		return getRefused;
	}

	public int getUnusedTables() {
		return unusedTables;
	}

	public boolean order(String name, String... menu) throws InvalidName {
		if (!reserved.containsKey(name)) {
			throw new InvalidName();
		}
		for (int i = 0; i < menu.length; i++) {
			if (!menus.containsKey(menu[i])) {
				throw new InvalidName();
			} else {
				if (menu.length < reserved.get(name)) {
					return false;
				}
			}
		}
		orders.put(name, menu);
		return true;
	}

	public List<String> getUnordered() {
		List<String> unordered = reserved.keySet().stream().filter(name -> !orders.keySet().contains(name))
				.collect(Collectors.toList());
		return unordered;
	}

	public double pay(String name) throws InvalidName {
		double conto = 0;
		if (!reserved.containsKey(name)) {
			throw new InvalidName();
		}
		if (orders.containsKey(name)) {
			for (String menuName : orders.get(name)) {
				conto = conto + menus.get(menuName);
			}
		} else {
			conto = 0;
		}
		income = income + conto;
		payed.add(name);
		return conto;
	}

	public List<String> getUnpaid() {
		List<String> notPayed = orders.keySet().stream().filter(name -> !payed.contains(name))
				.collect(Collectors.toList());
		Collections.sort(notPayed);
		return notPayed;
	}

	public double getIncome() {
		return income;
	}

}
