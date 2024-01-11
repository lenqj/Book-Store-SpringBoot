package proiect.Model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Objects;
@Getter
@MappedSuperclass
@DynamicUpdate
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int ID;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity entity = (AbstractEntity) o;
        return ID == entity.ID;
    }
    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public AbstractEntity(int ID) {
        this.ID = ID;
    }

    public AbstractEntity() {
    }
}
