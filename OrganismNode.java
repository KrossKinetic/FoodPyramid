/**
 * Represents a node in an OrganismTree, modeling a simple food pyramid.
 */
public class OrganismNode {
    private String name;
    private boolean isPlant, isHerbivore, isCarnivore;
    private OrganismNode left, middle, right;

    /**
     * Constructs an empty OrganismNode object. 
     */
    public OrganismNode(){}

    /**
     * Constructs an OrganismNode with the specified parameters.
     *
     * @param name          
     * The name of the organism.
     * @param isPlant       
     * True if the organism is a plant, false otherwise.
     * @param isHerbivore   
     * True if the organism is a herbivore, false otherwise.
     * @param isCarnivore   
     * True if the organism is a carnivore, false otherwise.
     * @param left         
     * The left child node.
     * @param mid          
     * The middle child node.
     * @param right        
     * The right child node.
     */
    public OrganismNode(String name, boolean isPlant, boolean isHerbivore, boolean isCarnivore, OrganismNode left, OrganismNode mid, OrganismNode right){
        this.name = name;
        this.isPlant = isPlant;
        this.isHerbivore = isHerbivore;
        this.isCarnivore = isCarnivore;
        this.left = left;
        this.middle = mid;
        this.right = right;
    }

    /**
     * Get the name of the organism.
     * @return 
     * The name of the organism.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the organism.
     * @param name 
     * The new name of the organism.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Check if the organism is a plant.
     * @return 
     * True if the organism is a plant, false otherwise.
     */
    public boolean getIsPlant() {
        return isPlant;
    }

    /**
     * Set whether the organism is a plant.
     * @param isPlant 
     * True if the organism is a plant, false otherwise.
     */
    public void setPlant(boolean isPlant) {
        this.isPlant = isPlant;
    }

    /**
     * Check if the organism is a herbivore.
     * @return 
     * True if the organism is a herbivore, false otherwise.
     */
    public boolean getIsHerbivore() {
        return isHerbivore;
    }

    /**
     * Set whether the organism is a herbivore.
     * @param isHerbivore 
     * True if the organism is a herbivore, false otherwise.
     */
    public void setHerbivore(boolean isHerbivore) {
        this.isHerbivore = isHerbivore;
    }

    /**
     * Check if the organism is a carnivore.
     * @return 
     * True if the organism is a carnivore, false otherwise.
     */
    public boolean getIsCarnivore() {
        return isCarnivore;
    }

    /**
     * Set whether the organism is a carnivore.
     * @param isCarnivore 
     * True if the organism is a carnivore, false otherwise.
     */
    public void setCarnivore(boolean isCarnivore) {
        this.isCarnivore = isCarnivore;
    }

    /**
     * Get the left child node.
     * @return 
     * The left child node.
     */
    public OrganismNode getLeft() {
        return left;
    }

    /**
     * Set the left child node.
     * @param left 
     * The new left child node.
     */
    public void setLeft(OrganismNode left) {
        this.left = left;
    }

    /**
     * Get the middle child node.
     * @return 
     * The middle child node.
     */
    public OrganismNode getMiddle() {
        return middle;
    }

    /**
     * Set the middle child node.
     * @param middle 
     * The new middle child node.
     */
    public void setMiddle(OrganismNode middle) {
        this.middle = middle;
    }

    /**
     * Get the right child node.
     * @return 
     * The right child node.
     */
    public OrganismNode getRight() {
        return right;
    }

    /**
     * Set the right child node.
     * @param right 
     * The new right child node.
     */
    public void setRight(OrganismNode right) {
        this.right = right;
    }

    /**
     * Adds a prey node as a child to this OrganismNode.
     * 
     * @param preyNode 
     * The OrganismNode representing the prey.
     * @throws IsPlantException 
     * If this OrganismNode is a plant.
     * @throws PositionNotAvailableException 
     * If there is no available position for a new child.
     * @throws DietMismatchException 
     * If the preyNode's type (plant/animal) is incompatible with this OrganismNode's diet.
     * @custom.precondition 
     * This OrganismNode must not represent a plant. The preyNode must be compatible with the diet of this OrganismNode. There must be space available for a new child node.
     * @custom.postcondition 
     * The preyNode is added as a child of this OrganismNode.
     */
    public void addPrey(OrganismNode preyNode) throws IsPlantException, PositionNotAvailableException, DietMismatchException{
        if (this.getIsPlant() == true) throw new IsPlantException("Plant nodes cannot have children.");
        if ((preyNode.getIsPlant() && !getIsHerbivore()) || (!preyNode.getIsPlant() && !getIsCarnivore())) throw new DietMismatchException("ERROR: This prey cannot be added as it does not match the diet of the predator.");
        
        if (left == null){
            left = preyNode;
        } else if (middle == null){
            middle = preyNode;
        } else if (right == null){
            right = preyNode;
        } else {
            throw new PositionNotAvailableException("ERROR: There is no more room for more prey for this predator.");
        }
        
    }

    
}
