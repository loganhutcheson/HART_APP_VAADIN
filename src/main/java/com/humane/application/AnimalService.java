package com.humane.application;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An in memory dummy "database" for the example purposes. In a typical Java app
 * this class would be replaced by e.g. EJB or a Spring based service class.
 * <p>
 * In demos/tutorials/examples, get a reference to this service class with
 * {@link AnimalService#getInstance()}.
 */
public class AnimalService {

	private static AnimalService instance;
	private static final Logger LOGGER = Logger.getLogger(AnimalService.class.getName());

	private final HashMap<Long, Animal> contacts = new HashMap<>();
	private long nextId = 0;

	private AnimalService() {
	}

	/**
	 * @return a reference to an example facade for Animal objects.
	 */
	public static AnimalService getInstance() {
		if (instance == null) {
			instance = new AnimalService();
			instance.ensureTestData();
		}
		return instance;
	}

	/**
	 * @return all available Animal objects.
	 */
	public synchronized List<Animal> findAll() {
		return findAll(null);
	}

	/**
	 * Finds all Animal's that match given filter.
	 *
	 * @param stringFilter
	 *            filter that returned objects should match or null/empty string
	 *            if all objects should be returned.
	 * @return list a Animal objects
	 */
	public synchronized List<Animal> findAll(String stringFilter) {
		ArrayList<Animal> arrayList = new ArrayList<>();
		for (Animal contact : contacts.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(new Animal(contact));
				}
			} catch (Exception ex) {
				Logger.getLogger(AnimalService.class.getName()).log(Level.SEVERE, null, ex);
			}
        }
        
        Collections.sort(arrayList, (Animal o1, Animal o2) -> (int) (o2.getId() - o1.getId()));



	    return arrayList;
	}

	/**
	 * Finds all Animal's that match given filter and limits the resultset.
	 *
	 * @param stringFilter
	 *            filter that returned objects should match or null/empty string
	 *            if all objects should be returned.
	 * @param start
	 *            the index of first result
	 * @param maxresults
	 *            maximum result count
	 * @return list a Animal objects
	 */
	public synchronized List<Animal> findAll(String stringFilter, int start, int maxresults) {
		ArrayList<Animal> arrayList = new ArrayList<>();
		for (Animal contact : contacts.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(new Animal(contact));
				}
			} catch (Exception ex) {
				Logger.getLogger(AnimalService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

        Collections.sort(arrayList, (Animal o1, Animal o2) -> (int) (o2.getId() - o1.getId()));

		int end = start + maxresults;
		if (end > arrayList.size()) {
			end = arrayList.size();
		}
		return arrayList.subList(start, end);
	}

	/**
	 * @return the amount of all Animals in the system
	 */
	public synchronized long count() {
		return contacts.size();
	}

	/**
	 * Deletes a Animal from a system
	 *
	 * @param value
	 *            the Animal to be deleted
	 */
	public synchronized void delete(Animal value) {
		contacts.remove(value.getId());
	}

	/**
	 * Persists or updates Animal in the system. Also assigns an identifier
	 * for new Animal instances.
	 *
	 * @param entry
	 */
	public synchronized void save(Animal entry) {
		if (entry == null) {
			LOGGER.log(Level.SEVERE,
					"Animal is null. Are you sure you have connected your form to the application as described in tutorial chapter 7?");
			return;
		}
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		try {
			entry = new Animal(entry);
		} catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            return;
		}
		contacts.put(entry.getId(), entry);
	}

	/**
	 * Sample data generation
	 */
	public void ensureTestData() {
		if (findAll().isEmpty()) {
			final String[] names = new String[] { "Gaby", "Brian", "Eduardo",
					"Koko", "Alejandro", "Angel", "Yami", "Keke",
					"Stewart", "Corinne", "Jackie", "Jackson", "Gustavsson",
					"Walker", "Martin", "Israel Carlsson", "Quinn", "Kye",
					"Danielle ", "Lelo", "Gunnern", "Jamar", "Lari",
					"Annie", "Remipoo", "Renene", "Elvis", "Solomon",
					"Jayjay", "Bernard" };
			Random r = new Random(0);
			for (String name : names) {
				Animal c = new Animal();
				c.setName(name);
				c.setStatus(AnimalStatus.values()[r.nextInt(3)%2]);
				c.setBirthDate(LocalDate.now().minusDays(r.nextInt(365*10)));
				c.setBreed(DogBreed.values()[r.nextInt(DogBreed.values().length)]);
				c.setWeight(r.nextInt(100)+10);
				c.setGender(Gender.values()[r.nextInt(2)]);
				c.setColor(Color.values()[r.nextInt(Color.values().length)]);
				c.setSpayed(Spayed.values()[r.nextInt(Spayed.values().length)]);
				save(c);
			}
		}
	}
}