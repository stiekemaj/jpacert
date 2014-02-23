package eu.stiekema.jeroen.jpacert;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * @author Jeroen Stiekema
 */
@Entity
@Access(AccessType.FIELD) // default
public class Employee {

    private static final String LOCAL_AREA_CODE = "031";

    @Id
    private long id;

    @Column(name = "name")
    private String employeeName;

    @Column(name = "salary")
    private long employeeSalary;

    @Transient
    private String phoneNumber;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "EMP_ADDRESS",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    private Collection<Address> addresses;

    @Deprecated
    protected Employee() {

    }

    public Employee(long id, String employeeName, long employeeSalary) {
        this.id = id;
        this.employeeName = employeeName;
        this.employeeSalary = employeeSalary;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Access(AccessType.PROPERTY)
    @Column(name = "PHONE")
    protected String getPhoneNumberForDb() {
        if (this.phoneNumber == null) {
            return null;
        }

        if (this.phoneNumber.length() == 10) {
            return this.phoneNumber;
        } else {
            return LOCAL_AREA_CODE + this.phoneNumber;
        }
    }

    protected void setPhoneNumberForDb(String phoneNumber) {
        if (phoneNumber != null && phoneNumber.startsWith(LOCAL_AREA_CODE)) {
            this.phoneNumber = phoneNumber.substring(3);
        } else {
            this.phoneNumber = phoneNumber;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String name) {
        this.employeeName = name;
    }

    @Column(name = "salary")
    public long getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(long salary) {
        this.employeeSalary = salary;
    }

    public Collection<Address> getAddresses() {
        return addresses;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", employeeName='" + employeeName + '\'' +
                ", employeeSalary=" + employeeSalary +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", startDate=" + startDate +
                ", addresses=" + addresses +
                '}';
    }
}
