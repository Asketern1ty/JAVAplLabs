import java.util.ArrayList;
import java.util.List;

public class Zoo {
    public List<Cage<? extends Animal>> cages = new ArrayList<>();

    public int getCountOfAnimals() {
        int totalAnimals = 0;
        for (Cage<? extends Animal> cage : cages) {
            totalAnimals += cage.getOccupiedSpaces();
        }
        return totalAnimals;
    }

    public void addCage(Cage<? extends Animal> cage) {
        cages.add(cage);
    }
}