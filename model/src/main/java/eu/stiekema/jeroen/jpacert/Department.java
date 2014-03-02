package eu.stiekema.jeroen.jpacert;

import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

/**
 * @author Jeroen Stiekema
 */
@Entity
public class Department {

    @Id
    @TableGenerator(name = "departmentSeq", table = "serial", pkColumnName = "serial_id", pkColumnValue = "department", valueColumnName = "serial_value")
    @GeneratedValue(generator = "departmentSeq")
    private long id;

    private String name;

//    @OneToMany(mappedBy = "department")
//    private Collection<Employee> employees;

    @Deprecated
    protected Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
