package eu.stiekema.jeroen.jpacert;

import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnitUtil;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.junit.Ignore;
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

    /**
     * Het veld comments is ingesteld op lazy fetch. Met PersistenceUnitUtil kan getest worden of het veld al geladen is
     * @return
     * @throws Exception
     */
    @Test
    @Ignore("static weaving uitgezet")
    public void testLazyFetch_commentIsLazyLoaded() {
        Employee employee = em.find(Employee.class, 100L);
        em.detach(employee);

        // test if comment is null
        PersistenceUnitUtil persistenceUnitUtil = em.getEntityManagerFactory().getPersistenceUnitUtil();
        assertFalse(persistenceUnitUtil.isLoaded(employee, "comments"));

        // call method and assert that comment is filled
        assertEquals("Kan wel wat loonsverhoging gebruiken", employee.getComments());
    }

    @Test
    public void testEnumeration() {
        Employee employee = em.find(Employee.class, 100L);
        assertEquals(EmployeeType.FULL_TIME_EMPLOYEE, employee.getEmployeeType());
    }

    @Test
    public void testGeneratedValueOfPrimaryKey() {
        Employee employee = new Employee("Test", 1000);
        em.persist(employee);
        assertNotNull(employee.getId());
    }

    @Test
    public void testEmployeeDepartment() {
        Employee employee = em.find(Employee.class, 100L);
        assertNotNull(employee);
        assertNotNull(employee.getDepartment());
        assertEquals(100L, employee.getId());
    }

    @Override
    public IDataSet getDataset() throws Exception {
        return new XlsDataSet(getClass().getResourceAsStream("employee.xls"));
    }
}
