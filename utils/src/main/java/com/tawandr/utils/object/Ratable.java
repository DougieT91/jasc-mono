package com.tawandr.utils.object;


public interface Ratable<T> extends Comparable<T> {
    Rating getRating();

    @Override
    default int compareTo(T other) {
        if (other instanceof Ratable) {
            final int ratingValue = this.getRating().getValue();
            return Integer.compare(ratingValue, ((Ratable<?>) other).getRating().getValue());
        } else {
            throw new IllegalArgumentException("Cannot compare objects of different types");
        }
    }
}