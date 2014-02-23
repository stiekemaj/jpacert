package eu.stiekema.jeroen.jpacert;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

/**
 * @author Jeroen Stiekema
 */
@Stateless
public class EmployeeService {

    @PersistenceContext(unitName = "EmployeeService")
    private EntityManager em;

    @Resource
    private UserTransaction userTransaction;

    EntityManager getEntityManager() {
        return this.em;
    }

    public void createEmployee(long id, String name, long salary) {
        Employee employee = new Employee(id, name, salary);
        getEntityManager().persist(employee);
    }

    public void createAddress(long id, String street, String city, String state) {
        Address address = new Address(id, street, city, state, null);
        getEntityManager().persist(address);
    }

    public Employee findEmployeeById(long id) {
        return getEntityManager().find(Employee.class, id);
    }

    public void removeEmployee(long id) {
        Employee employee = findEmployeeById(id);
        if (employee != null) { // em.remove throws an IllegalArgumentException when argument is null
            getEntityManager().remove(employee);
        }
    }

    public Address findAddressById(long id) {
        return getEntityManager().find(Address.class, id);
    }

    public List<Employee> findEmployeesByName(String name) {
        TypedQuery<Employee> query = em.createQuery("select o from Employee o where o.employeeName = :name", Employee.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    public void addAddressToEmployee(Long employeeId, Long addressId) {
        Employee employee = findEmployeeById(employeeId);
        Address address = findAddressById(addressId);
        employee.getAddresses().add(address);
    }

    public void addAddressToEmployee(Employee employee, Address address) {
        Employee mergedEmployee = getEntityManager().merge(employee);
        Address mergedAddress = getEntityManager().merge(address);
        mergedEmployee.getAddresses().add(mergedAddress);
    }
}
