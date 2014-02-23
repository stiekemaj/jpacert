package eu.stiekema.jeroen.jpacert;

import javax.persistence.EntityTransaction;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * @author Jeroen Stiekema
 */
public class EmployeeTest extends AbstractEntityTest {
    @Test
    public void createEmployee() {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Employee employee = new Employee(1L, "Name", 10000);
        em.persist(employee);
        transaction.commit();

        // verify if entity is in database
        Employee result = em.find(Employee.class, 1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Name", result.getEmployeeName());
        assertEquals(10000, result.getEmployeeSalary());
        assertNotNull(result.getAddresses());
        assertTrue(result.getAddresses().isEmpty());
    }


}
