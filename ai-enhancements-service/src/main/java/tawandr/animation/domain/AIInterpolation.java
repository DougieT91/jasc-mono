package tawandr.animation.domain;

import com.tawandr.utils.crud.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class AIInterpolation extends BaseEntity {

    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long recordId;

    @Column(nullable = false)
    private Long projectId;

    @Column(nullable = false)
    private Long frameIdStart;

    @Column(nullable = false)
    private Long frameIdEnd;

    @Column(nullable = false)
    private Long aiModelId;

    @Column(nullable = false)
    private String status;

    @Column
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "aiInterpolation")
    private Set<InterpolatedFrame> interpolatedFrames;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    @Override
    protected void secondaryPreCreate() {

    }

    @Override
    protected void secondaryPreUpdate() {

    }
}
