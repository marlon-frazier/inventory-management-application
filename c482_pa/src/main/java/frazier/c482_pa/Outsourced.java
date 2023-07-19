package frazier.c482_pa;

/** The Outsourced class represents a part that is manufactured outsourced.  It extends the Part class and includes a Company Name attribute*/
public class Outsourced extends Part{
    private String companyName;

    /** Constructor that includes the Company Name attribute*/
    public Outsourced(String name, double price, int stock, int min, int max, String companyName) {
        super(name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** @return returns the company name*/
    public String getCompanyName() {
        return companyName;
    }

    /** @param companyName sets the company name*/
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
