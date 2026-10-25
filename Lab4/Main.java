public class Main {
    public static void main(String[] args) {
        Zoo zoo = new Zoo();
        System.out.println("--- Running Unit Tests ---");

        // Тест 1
        try {
            LionCage lionCage = new LionCage(2);
            lionCage.addAnimal(new Lion());
            zoo.addCage(lionCage);
            System.out.println("Test 1 (Add Animal): PASSED");
        } catch (CageFullException e) {
            System.out.println("Test 1: FAILED");
        }

        // Тест 2
        try {
            BirdCage birdCage = new BirdCage(1);
            birdCage.addAnimal(new Eagle());
            birdCage.addAnimal(new Eagle()); 
            System.out.println("Test 2: FAILED (Exception not thrown)");
        } catch (CageFullException e) {
            System.out.println("Test 2 (Cage Full): PASSED. Exception message: " + e.getMessage());
        }

        // Тест 3
        try {
            LionCage lionCage = new LionCage(2);
            Lion lion = new Lion();
            lionCage.removeAnimal(lion); 
            System.out.println("Test 3: FAILED (Exception not thrown)");
        } catch (AnimalNotFoundException e) {
            System.out.println("Test 3 (Animal Not Found): PASSED. Exception message: " + e.getMessage());
        }

        // Тест 4
        try {
            UngulateCage ungulateCage = new UngulateCage(3);
            ungulateCage.addAnimal(new Zebra());
            ungulateCage.addAnimal(new Giraffe());
            zoo.addCage(ungulateCage);
            
            if (zoo.getCountOfAnimals() == 3) {
                System.out.println("Test 4 (Total Animals Count): PASSED");
            } else {
                System.out.println("Test 4: FAILED");
            }
        } catch (CageFullException e) {
            System.out.println("Test 4: FAILED");
        }
    }
}