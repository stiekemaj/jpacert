package eu.stiekema.jeroen.jpacert;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * @author Jeroen Stiekema
 */
public abstract class AbstractEntityTest {

    protected EntityManager em;
    private EntityManagerFactory emf;

    @BeforeClass
    public final void setUp() throws Exception {
        emf = Persistence.createEntityManagerFactory("jpacertTestPU");
        em = emf.createEntityManager();
    }

    @AfterClass
    public final void tearDown() throws Exception {
        em.close();
        emf.close();
    }
}
