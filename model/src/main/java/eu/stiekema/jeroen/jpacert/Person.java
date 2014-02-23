package eu.stiekema.jeroen.jpacert;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author Jeroen Stiekema
 */
@MappedSuperclass
@Access(AccessType.PROPERTY)
public class Person {

    private String name;

    @Deprecated
    protected Person() {

    }

    public Person(String name) {
        this.name = name;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
