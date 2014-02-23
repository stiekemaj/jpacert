package eu.stiekema.jeroen.jpacert;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;

/**
 * @author Jeroen Stiekema
 */
public abstract class AbstractJpaTest {

    protected EntityManager em;
    private EntityManagerFactory emf;

    @Before
    public void setUp() throws Exception {
        emf = Persistence.createEntityManagerFactory("jpacertTestPU");
        em = emf.createEntityManager();
    }

    @After
    public void tearDown() throws Exception {
        em.close();
        emf.close();
    }

    protected void clearFirstLevelCache() {
        em.getTransaction().begin();
        em.flush();
        em.getTransaction().commit();
        em.clear();
    }
}
