package frazier.c482_pa;

/** Part is an abstract class that defines common fields and methods for parts in the inventory management system */
public abstract class Part {
    private static int lastId = 0;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** Part constructor; accepts a name, price, stock, min, max parameters and initializes a new part.  The id is auto generated and increments by one everytime a new object is created*/
    public Part(String name, double price, int stock, int min, int max) {
        id = ++lastId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return returns the id*/
    public int getId() {
        return id;
    }

    /**@param id sets id*/
    public void setId(int id) {
        this.id = id;
    }

    /**@return returns the part name*/
    public String getName() {
        return name;
    }

    /**@param name sets the part name*/
    public void setName(String name) {
        this.name = name;
    }

    /**@return returns the part price*/
    public double getPrice() {
        return price;
    }

    /**@param price sets the part price*/
    public void setPrice(double price) {
        this.price = price;
    }

    /**@return returns the part stock / inventory count*/
    public int getStock() {
        return stock;
    }

    /**@param stock sets the part stock / inventory count*/
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**@return returns the part minimum*/
    public int getMin() {
        return min;
    }
    /**@param min sets the part minimum*/
    public void setMin(int min) {
        this.min = min;
    }

    /**@return returns the part maximum*/
    public int getMax() {
        return max;
    }

    /**@param max sets the part maximum*/
    public void setMax(int max) {
        this.max = max;
    }
}
