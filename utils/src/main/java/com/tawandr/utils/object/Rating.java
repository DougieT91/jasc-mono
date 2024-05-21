package com.tawandr.utils.object;

import com.tawandr.utils.crud.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
<pre>
 * Example
 public class Project implements Ratable<Project> {
     private Rating rating;
 }
</pre>
 */
@Getter
@Setter
//@Entity
public class Rating {
    @Id
    private int value;
    public Rating() {
        value = 0;
    }

    public Rating(int value) {
        if (value < 0 || value > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Rating rating)) return false;
        return value == rating.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

