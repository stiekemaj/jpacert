package eu.stiekema.jeroen.jpacert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;

/**
 * @author Jeroen Stiekema
 */
@Entity
public class ParkingSpace {

    @Id
    @TableGenerator(name = "parkingSpace", table = "serial", pkColumnName = "serial_id", pkColumnValue = "parkingSpace", valueColumnName = "serial_value")
    @GeneratedValue(generator = "parkingSpace")
    private long id;

    private int lot;

    private String location;

    @OneToOne(mappedBy = "parkingSpace")
    private Employee employee;

    @Deprecated
    protected ParkingSpace() {
    }

    public ParkingSpace(int lot, String location) {
        this.lot = lot;
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public int getLot() {
        return lot;
    }

    public String getLocation() {
        return location;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
