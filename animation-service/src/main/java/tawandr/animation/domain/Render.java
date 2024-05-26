package tawandr.animation.domain;

import com.tawandr.utils.crud.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Render extends BaseEntity {
    private Long projectId;
    private String status;
    private Date startedAt;
    private Date completedAt;
    @Lob
    private byte[] renderedFileData;

    @Override
    protected void secondaryPreCreate() {

    }

    @Override
    protected void secondaryPreUpdate() {

    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Render render = (Render) o;
        return getId() != null && Objects.equals(getId(), render.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
