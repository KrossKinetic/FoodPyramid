/**
 * Represents a tree structure for organizing organisms in a simple food pyramid.
 */
public class OrganismTree {
    private OrganismNode root;
    private OrganismNode cursor;

    /**
     * Returns the cursor.
     * 
     * @return
     * The cursor.
     */
    public OrganismNode getCursor() {
        return cursor;
    }

    /**
     * Sets the cursor.
     * 
     * @param cursor
     * The cursor.
     */
    public void setCursor(OrganismNode cursor) {
        this.cursor = cursor;
    }

    /**
     * Constructs an OrganismTree object with the given apex predator.
     * 
     * @param apexPredator
     * The apex predator.
     * @throws IsPlantException
     * If the apex predator is a plant.
     * @custom.precondition
     * apexPredator is not null and apexPredator is not a plant.
     * @custom.postcondition
     * This OrganismTree has been initialized with apexPredator as its root and cursor.
     */
    public OrganismTree(OrganismNode apexPredator) throws IsPlantException{
        if (apexPredator.getIsPlant()) throw new IsPlantException("Apex Predator must be an animal.");

        root = apexPredator;
        cursor = root;
    }

    /**
     * Resets the cursor to the root of the tree.
     * 
     * @custom.precondition
     * This OrganismTree has been instantiated.
     * @custom.postcondition
     * The cursor has been reset to the root of the tree.
     */
    public void cursorReset(){
        cursor = root;
    }

    /**
     * Moves the cursor to the child with the given name.
     * 
     * @param name
     * The name of the child to move the cursor to.
     * @throws IllegalArgumentException
     * If the given name does not match any of the cursor's children.
     * @custom.precondition
     * This OrganismTree has been instantiated.
     * @custom.postcondition
     * The cursor will be moved to the child with the given name.
     */
    public void moveCursor(String name) throws IllegalArgumentException{

        if (cursor.getLeft() != null && cursor.getLeft().getName().equals(name)){
            cursor = cursor.getLeft();
        } else if (cursor.getMiddle() != null && cursor.getMiddle().getName().equals(name)){
            cursor = cursor.getMiddle();
        } else if (cursor.getRight() != null && cursor.getRight().getName().equals(name)){
            cursor = cursor.getRight();
        } else{
            throw new IllegalArgumentException("ERROR: This prey does not exist for this predator.");
        }
    }

    /**
     * Returns a string representation of the prey of the cursor.
     * 
     * @return
     * A string representation of the prey of the cursor.
     * @throws IsPlantException
     * If the cursor is a plant.
     * @custom.precondition
     * This OrganismTree has been instantiated.
     * @custom.postcondition
     * The string representation of the cursor's prey will be returned.
     */
    public String listPrey() throws IsPlantException{
        if (cursor.getIsPlant()) throw new IsPlantException("Cursor is a plant.");
        OrganismNode right = cursor.getRight(), left = cursor.getLeft(), mid = cursor.getMiddle();
        String temp = "";
        if (right != null){
            temp = cursor.getName() + " -> " + left.getName() + ", " + mid.getName() + ", "  + right.getName();
        } else if (mid != null){
            temp = cursor.getName() + " -> " + left.getName() + ", " + mid.getName();
        } else if (left != null){
            temp = cursor.getName() + " -> " + left.getName();
        }

        return temp;
    }

    /**
     * Returns a string representation of the food chain from the apex predator to the cursor.
     * 
     * @return
     * A string representation of the food chain from the apex predator to the cursor.
     * @custom.precondition
     * This OrganismTree has been instantiated.
     * @custom.postcondition
     * The string representation of the food chain from the apex predator to the cursor will be returned.
     */
    public String listFoodChain() {
        OrganismNode nameToFind = cursor;
        String path = SearchListFoodChain(root, "", nameToFind); 
    
        if (path != null) {
            return path.substring(2); 
        } else {
            return "Cursor not found in the tree.";
        }
    }
    
    /**
     * Helper method for listFoodChain().
     * 
     * @param currentNode
     * The current node.
     * @param path
     * The path from the apex predator to the current node.
     * @param toFind
     * The node to find.
     * @return
     * The path from the apex predator to the node to find.
     */
    public String SearchListFoodChain(OrganismNode currentNode, String path, OrganismNode toFind) {
        if (currentNode == toFind) {
            return path + "-> " + currentNode.getName(); 
        }
    
        if (currentNode == null) {
            return null; 
        }
    
        String leftPath = SearchListFoodChain(currentNode.getLeft(), path + "-> " + currentNode.getName(), toFind);
        if (leftPath != null) {
            return leftPath; 
        }
    
        String middlePath = SearchListFoodChain(currentNode.getMiddle(), path + "-> " + currentNode.getName(), toFind);
        if (middlePath != null) {
            return middlePath; 
        }
    
        String rightPath = SearchListFoodChain(currentNode.getRight(), path + "-> " + currentNode.getName(), toFind);
        if (rightPath != null) {
            return rightPath;
        }
    
        return null; 
    }

    /**
     * Returns a string representation of all the plants in the tree that support the cursor.
     * 
     * @return
     * A string representation of all the plants in the tree that support the cursor.
     * @custom.precondition
     * This OrganismTree has been instantiated.
     * @custom.postcondition
     * The string representation of all the plants in the tree that support the cursor will be returned.
     */
    public String listAllPlants() {
        String plantList = printPlantsHelper(cursor, "");
        if (plantList.isEmpty()) {
            return "No plants found supporting the cursor.";
        } else {
            return plantList.substring(1);
        }
    }
    
    /**
     * Helper method for getAllPlants().
     * 
     * @param curNode
     * The current node.
     * @param temp
     * The string representation of all the plants in the tree that support the cursor.
     * @return
     * The string representation of all the plants in the tree that support the cursor.
     */
    private String printPlantsHelper(OrganismNode curNode, String temp) {
        if (curNode == null) { 
            return "";
        }
    
        if (curNode.getIsPlant()) {
            temp += "," + curNode.getName();
        }
    
        temp += printPlantsHelper(curNode.getLeft(), "");
        temp += printPlantsHelper(curNode.getMiddle(), "");
        temp += printPlantsHelper(curNode.getRight(), "");
    
        return temp;
    }
    
    
    /**
     * Prints the tree with the cursor as the root.
     * 
     * @custom.precondition
     * This OrganismTree has been instantiated.
     * @custom.postcondition
     * The tree will be printed to the console.
     */
    public void printOrganismTree(){
        OrganismNode node = cursor;
        printOrganismTreeHelper("", node);
    }

    /**
     * Helper method for printOrganismTree().
     * 
     * @param indents
     * The number of indents to print.
     * @param node
     * The node to print.
     */
    public void printOrganismTreeHelper(String indents, OrganismNode node){
        String temp = "-";
        if (node.getIsPlant()){
            System.out.println(indents + temp + node.getName());
            return;
        } else {
            System.out.println(indents + "|- " + node.getName());
        }

        if (node.getLeft() != null){
            printOrganismTreeHelper(indents + "\t", node.getLeft());
        }

        if (node.getMiddle() != null){
            printOrganismTreeHelper(indents + "\t", node.getMiddle());
        }

        if (node.getRight() != null){
            printOrganismTreeHelper(indents + "\t", node.getRight());
        }
    }

    /**
     * Checks if the cursor has three children.
     * 
     * @return
     * True if the cursor has three children, false otherwise.
     * @custom.precondition
     * This OrganismTree has been instantiated.
     * @custom.postcondition
     * True will be returned if the cursor has three children, false otherwise.
     */
    public boolean isFull(){
        if (cursor.getLeft() != null && cursor.getMiddle() != null && cursor.getRight() != null){
            return true;
        }
        return false;
    }

    /**
     * Adds an animal child to the tree.
     * 
     * @param name
     * The name of the animal child.
     * @param isHerbivore
     * True if the animal child is a herbivore, false otherwise.
     * @param isCarnivore
     * True if the animal child is a carnivore, false otherwise.
     * @throws IllegalArgumentException
     * If the given name is already taken by a sibling.
     * @throws PositionNotAvailableException
     * If the cursor already has three children.
     * @throws DietMismatchException
     * If the animal child's diet is incompatible with the cursor's diet.
     * @throws IsPlantException
     * If the cursor is a plant.
     * @custom.precondition
     * This OrganismTree has been instantiated.
     * @custom.postcondition
     * The animal child will be added to the tree.
     */
    public void addAnimalChild(String name, boolean isHerbivore, boolean isCarnivore) throws IllegalArgumentException, PositionNotAvailableException, DietMismatchException, IsPlantException{
        if ((cursor.getLeft() != null && cursor.getLeft().getName().equals(name)) || (cursor.getMiddle() != null && cursor.getMiddle().getName().equals(name)) || (cursor.getRight() != null && cursor.getRight().getName().equals(name))){
            throw new IllegalArgumentException("ERROR: This prey already exists for this predator.");
        }

        OrganismNode newNode = new OrganismNode(name, false, isHerbivore, isCarnivore, null, null, null);
        cursor.addPrey(newNode);
    }

    /**
     * Adds a plant child to the tree.
     * 
     * @param name
     * The name of the plant child.
     * @throws IllegalArgumentException
     * If the given name is already taken by a sibling.
     * @throws PositionNotAvailableException
     * If the cursor already has three children.
     * @throws DietMismatchException
     * If the plant child's diet is incompatible with the cursor's diet.
     * @throws IsPlantException
     * If the cursor is a plant.
     * @custom.precondition
     * This OrganismTree has been instantiated.
     * @custom.postcondition
     * The plant child will be added to the tree.
     */
    public void addPlantChild(String name) throws IllegalArgumentException, PositionNotAvailableException, DietMismatchException, IsPlantException{
        if ((cursor.getLeft() != null && cursor.getLeft().getName().equals(name)) || (cursor.getMiddle() != null && cursor.getMiddle().getName().equals(name)) || (cursor.getRight() != null && cursor.getRight().getName().equals(name))){
            throw new IllegalArgumentException("ERROR: This prey already exists for this predator.");
        }

        OrganismNode newNode = new OrganismNode(name, true, false, false, null, null, null);
        cursor.addPrey(newNode);
    }

    /**
     * Removes the child with the given name from the tree.
     * 
     * @param name
     * The name of the child to remove.
     * @throws IllegalArgumentException
     * If the given name does not match any of the cursor's children.
     * @custom.precondition
     * This OrganismTree has been instantiated.
     * @custom.postcondition
     * The child with the given name will be removed from the tree.
     */
    public void removeChild(String name) throws IllegalArgumentException{
        if (cursor.getLeft() != null && cursor.getLeft().getName().equalsIgnoreCase(name)){
            cursor.setLeft(cursor.getMiddle());
            cursor.setMiddle(cursor.getRight());
            cursor.setRight(null);
        } else if (cursor.getMiddle() != null &&cursor.getMiddle().getName().equalsIgnoreCase(name)){
            cursor.setMiddle(cursor.getRight());
            cursor.setRight(null);
        } else if (cursor.getRight() != null && cursor.getRight().getName().equalsIgnoreCase(name)){
            cursor.setRight(null);
        } else{
            throw new IllegalArgumentException("Name does not reference a direct, valid child of cursor");
        }
    }
}
