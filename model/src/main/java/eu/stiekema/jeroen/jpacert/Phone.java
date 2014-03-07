package eu.stiekema.jeroen.jpacert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

/**
 * @author Jeroen Stiekema
 */
@Entity
public class Phone {
    @Id
    @TableGenerator(name = "phoneGenerator", table="serial", pkColumnName="serial_id", pkColumnValue="phone", valueColumnName = "serial_value")
    @GeneratedValue(generator = "phoneGenerator")
    private long id;

    private String localNetNumber;

    private String number;

    public long getId() {
        return id;
    }

    public String getLocalNetNumber() {
        return localNetNumber;
    }

    public void setLocalNetNumber(String localNetNumber) {
        this.localNetNumber = localNetNumber;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
