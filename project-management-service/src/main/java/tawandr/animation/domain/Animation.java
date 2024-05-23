package tawandr.animation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tawandr.utils.crud.domain.BaseEntity;
import com.tawandr.utils.object.Percentage;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
public class Animation extends BaseEntity {
    private String name;
    private String description;
    @Percentage
    private BigDecimal level;

    public Animation(String name) {
        this.name = name;
        this.description = "";
        this.level = new BigDecimal("0.7");
    }

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "animation_set_id")
    private AnimationSet animationSet;

    @Override
    protected void secondaryPreCreate() {

    }
    @Override
    protected void secondaryPreUpdate() {

    }

}
