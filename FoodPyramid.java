/**
 * Simulates a food pyramid ecosystem.
 */
import java.util.Scanner;

public class FoodPyramid {
    private static OrganismTree tree;

    /**
     * Prompts the user to specify the dietary type of the apex predator.
     * 
     * @param scanner
     * The Scanner object to use for user input.
     * @return
     * A boolean array where the first element indicates if the apex predator is a carnivore and the second element indicates if it is a herbivore.
     * @custom.precondition
     * scanner is not null.
     * @custom.postcondition
     * A boolean array representing the diet has been returned.
     */
    private static boolean[] dietFinderApex(Scanner scanner){
        boolean isCar = false;
        boolean isHer = false;
        while (true){
            System.out.print("Is the organism an herbivore / a carnivore / an omnivore? (H / C / O): ");
            String preddiet = scanner.nextLine();
            if (preddiet.equalsIgnoreCase("H")){
                System.out.println();
                System.out.println("Apex Predator cannot be herbivore. Try again.");
            } else if (preddiet.equalsIgnoreCase("C")){
                isCar = true;
                break;
            } else if (preddiet.equalsIgnoreCase("O")){
                isCar = true;
                isHer = true;
                break;
            } else {
                System.out.println();
                System.out.println("Invalid option. Try again.");
            }
            System.out.println();
        }
        return new boolean[]{isCar,isHer};
    }

    /**
     * Prompts the user to specify the dietary type of an organism.
     * 
     * @param scanner
     * The Scanner object to use for user input.
     * @return
     * A boolean array where the first element indicates if the organism is a carnivore and the second element indicates if it is a herbivore.
     * @custom.precondition
     * scanner is not null.
     * @custom.postcondition
     * A boolean array representing the diet has been returned.
     */
    private static boolean[] dietFinder(Scanner scanner){
        boolean isCar = false;
        boolean isHer = false;
        while (true){
            System.out.print("Is the organism an herbivore / a carnivore / an omnivore? (H / C / O): ");
            String preddiet = scanner.nextLine();
            if (preddiet.equalsIgnoreCase("H")){
                isHer = true;
                break;
            } else if (preddiet.equalsIgnoreCase("C")){
                isCar = true;
                break;
            } else if (preddiet.equalsIgnoreCase("O")){
                isCar = true;
                isHer = true;
                break;
            } else {
                System.out.println("Invalid option. Try again.");
            }
        }
        return new boolean[]{isCar,isHer};
    }

    /**
     * Main method.
     */
    public FoodPyramid(){}
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("What is the name of the apex predator?: ");
        String predname = scanner.nextLine();

        boolean diet[] = dietFinderApex(scanner);
        
        OrganismNode apex = new OrganismNode(predname,false,diet[1],diet[0],null,null,null);
        
        try{
            tree = new OrganismTree(apex);
        } catch(Exception e){
            System.out.println(e);
        }
        

        System.out.println("Constructing food pyramid. . .");

        String choice = "";

        System.out.println("Menu:");
        System.out.println();
        System.out.println("(PC) - Create New Plant Child");
        System.out.println("(AC) - Create New Animal Child");
        System.out.println("(RC) - Remove Child");
        System.out.println("(P)  - Print Out Cursor's Prey");
        System.out.println("(C)  - Print Out Food Chain");
        System.out.println("(F)  - Print Out Food Pyramid at Cursor");
        System.out.println("(LP) - List All Plants Supporting Cursor");
        System.out.println("(R)  - Reset Cursor to Root");
        System.out.println("(M)  - Move Cursor to Child");
        System.out.println("(Q)  - Quit");
        System.out.println();
        
        while (!choice.equalsIgnoreCase("Q")) {
            try{
                System.out.print("Enter your choice: ");
                choice = scanner.nextLine().toUpperCase();

                switch (choice) {
                    case "PC":
                        if (tree.getCursor().getIsPlant()) throw new IllegalArgumentException("ERROR: The cursor is at a plant node. Plants cannot be predators.");
                        if (!tree.getCursor().getIsPlant() && !tree.getCursor().getIsHerbivore()) throw new DietMismatchException("ERROR: This prey cannot be added as it does not match the diet of the predator.");
                        if (tree.isFull()) throw new PositionNotAvailableException("ERROR: There is no more room for more prey for this predator.");

                        System.out.print("What is the name of the organism?: ");
                        predname = scanner.nextLine();
                        tree.addPlantChild(predname);
                        System.out.println();
                        System.out.println(predname + " has successfully been added as prey for the " + tree.getCursor().getName());                        
                        break;
                    case "AC":
                        if (tree.getCursor().getIsPlant()) throw new IllegalArgumentException("ERROR: The cursor is at a plant node. Plants cannot be predators.");
                        if (!tree.getCursor().getIsPlant() && !tree.getCursor().getIsCarnivore()) throw new DietMismatchException("ERROR: This prey cannot be added as it does not match the diet of the predator.");
                        if (tree.isFull()) throw new PositionNotAvailableException("ERROR: There is no more room for more prey for this predator.");

                        System.out.print("What is the name of the organism?: ");
                        String name = scanner.nextLine();

                        if ((tree.getCursor().getLeft() != null && tree.getCursor().getLeft().getName().equals(name)) || (tree.getCursor().getMiddle() != null && tree.getCursor().getMiddle().getName().equals(name)) || (tree.getCursor().getRight() != null && tree.getCursor().getRight().getName().equals(name))){
                            throw new IllegalArgumentException("ERROR: This prey already exists for this predator.");
                        }

                        boolean new_diet[] = dietFinder(scanner);
                        tree.addAnimalChild(name, new_diet[1], new_diet[0]);
                        System.out.println();
                        System.out.println("A(n) " + name + " has successfully been added as prey for the " + tree.getCursor().getName());
                        break;
                    case "RC":
                        System.out.print("What is the name of the organism to be removed?: ");
                        predname = scanner.nextLine();

                        tree.removeChild(predname);
                        System.out.println();
                        System.out.println("A(n) " + predname + " has been successfully removed as prey for the " + tree.getCursor().getName() +"!");
                        break;
                    case "P":
                        System.out.println(tree.listPrey());
                        break;
                    case "C":
                        System.out.println(tree.listFoodChain());
                        break;

                    case "F":
                        tree.printOrganismTree();
                        break;

                    case "LP":
                        System.out.println(tree.listAllPlants());
                        break;

                    case "R":
                        tree.cursorReset();
                        System.out.println("Cursor successfully reset to root!");
                        break;

                    case "M":
                        System.out.print("Move to?: ");
                        predname = scanner.nextLine();

                        tree.moveCursor(predname);
                        System.out.println();
                        System.out.println("Cursor successfully moved to " + predname +"!");
                        break;

                    case "Q":
                        System.out.println("Quitting program...");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
                System.out.println();
            } catch(Exception e){
                System.out.println(e);
            }
        }
        scanner.close();
    }
}