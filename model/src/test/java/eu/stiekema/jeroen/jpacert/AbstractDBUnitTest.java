package eu.stiekema.jeroen.jpacert;

import java.sql.Connection;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;

/**
 * @author Jeroen Stiekema
 */
public abstract class AbstractDBUnitTest extends AbstractJpaTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();

        IDataSet iDataSet = getDataset();
        if (iDataSet != null) {
            em.getTransaction().begin();
            Connection connection = em.unwrap(Connection.class);
            IDatabaseConnection databaseConnection = new DatabaseConnection(connection);
            DatabaseConfig databaseConfig = databaseConnection.getConfig();
            databaseConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new DefaultDataTypeFactory());
            DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, iDataSet);
            em.getTransaction().commit();
        }
    }

    public abstract IDataSet getDataset() throws Exception;
}
