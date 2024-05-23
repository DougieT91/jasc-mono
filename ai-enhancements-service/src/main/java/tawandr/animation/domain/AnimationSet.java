package tawandr.animation.domain;

import com.tawandr.utils.crud.domain.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Getter
@Setter
@Entity
public class AnimationSet extends BaseEntity {
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "animationSet", cascade = CascadeType.ALL)
    private List<Animation> animations = new ArrayList<>(5);

    @Override
    protected void secondaryPreCreate() {

    }
    @Override
    protected void secondaryPreUpdate() {

    }

}
