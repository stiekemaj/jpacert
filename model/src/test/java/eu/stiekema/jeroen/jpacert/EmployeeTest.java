package eu.stiekema.jeroen.jpacert;

import javax.persistence.EntityTransaction;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * @author Jeroen Stiekema
 */
public class EmployeeTest extends AbstractDBUnitTest {

    /**
     * Op de superclass Person is de Access annotation op PROPERTY gezet,
     * terwijl op de subclass Employee de Access annotation op FIELD is gezet. Let op dat Person geannoteerd is met
     * MappedSuperclass (wordt behandeld in hoofdstuk 10)
     * Controleren of alle velden worden gemapped.
     */
    @Test
    public void testMixedAccess_SuperClassHasPropertyAccess_SubClassHasFieldAccess() {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Employee employee = new Employee(1L, "Name", 10000);
        em.persist(employee);
        transaction.commit();

        clearFirstLevelCache();

        // verify if entity is in database
        Employee result = em.find(Employee.class, 1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Name", result.getName());
        assertEquals(10000, result.getEmployeeSalary());
        assertNotNull(result.getAddresses());
        assertTrue(result.getAddresses().isEmpty());
    }

    /**
     * Binnen Employee, waar Access type op FIELD staat, is alleen de phonenumber
     * op PROPERTY access gezet.
     */
    @Test
    public void testMixedAccess_SinglePropertyAccessInFieldAccessClass() {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Employee employee = new Employee(1L, "Name", 10000);
        em.persist(employee);

        // set phonenumber property
        employee.setPhoneNumber("595543210");
        transaction.commit();

        clearFirstLevelCache();

        Employee result = em.find(Employee.class, 1L);
        assertNotNull(result);
        assertEquals("031595543210", result.getPhoneNumberForDb());
    }

    @Override
    public IDataSet getDataset() throws Exception {
        return new XlsDataSet(getClass().getResourceAsStream("employee.xls"));
    }
}
