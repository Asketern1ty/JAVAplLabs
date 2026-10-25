import java.util.ArrayList;
import java.util.List;

public abstract class Cage<T extends Animal> {
    private int capacity;
    private List<T> animals;

    public Cage(int capacity) {
        this.capacity = capacity;
        this.animals = new ArrayList<>();
    }

    public int getCapacity() { return capacity; }
    
    public int getOccupiedSpaces() { return animals.size(); }

    public void addAnimal(T animal) throws CageFullException {
        if (animals.size() >= capacity) {
            throw new CageFullException("The enclosure is overcrowded!");
        }
        animals.add(animal);
    }

    public void removeAnimal(T animal) throws AnimalNotFoundException {
        if (!animals.remove(animal)) {
            throw new AnimalNotFoundException("There is no such animal in this enclosure.");
        }
    }
}