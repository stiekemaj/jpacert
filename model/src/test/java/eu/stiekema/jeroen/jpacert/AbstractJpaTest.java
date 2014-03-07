package eu.stiekema.jeroen.jpacert;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Before;

/**
 * @author Jeroen Stiekema
 */
public abstract class AbstractJpaTest {

    protected EntityManager em;
    private EntityManagerFactory emf;

    @Before
    public void setUp() throws Exception {
        emf = Persistence.createEntityManagerFactory("jpacertTestPUHibernate");
        em = emf.createEntityManager();
    }

    protected void clearFirstLevelCache() {
        if (em.getTransaction().isActive()) {
            em.flush();
            em.clear();
        }
    }
}
