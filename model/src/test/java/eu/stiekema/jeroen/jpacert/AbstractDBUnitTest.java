package eu.stiekema.jeroen.jpacert;

import java.sql.Connection;
import java.sql.SQLException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
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
            initialiseDatabase(iDataSet);
        }
    }

    private void initialiseDatabase(final IDataSet iDataSet) {
        Session session = em.unwrap(Session.class);
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                try {
                    IDatabaseConnection databaseConnection = new DatabaseConnection(connection);
                    DatabaseConfig databaseConfig = databaseConnection.getConfig();
                    databaseConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new DefaultDataTypeFactory());
                    DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, iDataSet);
                    connection.commit();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public abstract IDataSet getDataset() throws Exception;
}
