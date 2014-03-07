package eu.stiekema.jeroen.jpacert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * @author Jeroen Stiekema
 */
@Entity
@Access(AccessType.FIELD) // default
public class Employee extends Person {

    private static final String LOCAL_AREA_CODE = "031";

    @Id
    @TableGenerator(name = "employeeSeq", table = "serial", pkColumnName = "serial_id", pkColumnValue = "employee", valueColumnName = "serial_value")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "employeeSeq")
    private long id;

    @Column(name = "salary")
    private long employeeSalary;

    @Transient
    private String phoneNumber;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Basic(fetch = FetchType.LAZY)
    private String comments;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    private byte[] picture;

    @Enumerated(EnumType.STRING)
    private EmployeeType employeeType;

    @OneToOne
    @JoinColumn(name = "fk_parkingspace")
    private ParkingSpace parkingSpace;

    @ManyToOne
    @JoinColumn(name = "fk_department")
    private Department department;

    @ManyToMany
    @JoinTable(name = "EMP_ADDRESS",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    private Collection<Address> addresses;

    @OneToMany
    @JoinTable(name = "emp_phone", joinColumns = @JoinColumn(name = "emp_id"), inverseJoinColumns = @JoinColumn(name = "phone_id"))
    private List<Phone> phoneList;

    @Deprecated
    protected Employee() {
    }

    public Employee(String name, long employeeSalary) {
        super(name);
        this.employeeSalary = employeeSalary;
        this.addresses = new ArrayList<Address>();
        this.phoneList = new ArrayList<Phone>();
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

        if (this.phoneNumber.length() == 11) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", employeeSalary=" + employeeSalary +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", startDate=" + startDate +
                ", addresses=" + addresses +
                "} " + super.toString();
    }
}
