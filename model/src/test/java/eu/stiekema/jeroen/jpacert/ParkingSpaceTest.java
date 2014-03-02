package eu.stiekema.jeroen.jpacert;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Jeroen Stiekema
 */
public class ParkingSpaceTest extends AbstractDBUnitTest {

    @Test
    public void testOneToOneRelationWithEmployee() {
        ParkingSpace parkingSpace = em.find(ParkingSpace.class, 2L);
        assertNotNull(parkingSpace);
        assertNotNull(parkingSpace.getEmployee());
        assertEquals(100L, parkingSpace.getEmployee().getId());
    }

    @Override
    public IDataSet getDataset() throws Exception {
        return new XlsDataSet(getClass().getResourceAsStream("employee.xls"));
    }
}
