package frazier.c482_pa;

/** The InHouse class represents a part that is manufactured in-house.  It extends the Part class and includes a machineID attribute*/
public class InHouse extends Part{
    private int machineID;

    /** Constructor that includes the machineID attribute*/
    public InHouse(String name, double price, int stock, int min, int max, int machineID) {
        super(name, price, stock, min, max);
        this.machineID = machineID;
    }

    /** @return  returns the machine id*/
    public int getMachineID() {
        return machineID;
    }

    /** @param machineID sets the machine id*/
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
