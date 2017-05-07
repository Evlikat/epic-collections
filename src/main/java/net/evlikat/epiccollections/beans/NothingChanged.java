package net.evlikat.epiccollections.beans;

import java.lang.reflect.Field;

public class NothingChanged extends FieldChange {

    private final Object value;

    public NothingChanged(Field field, Object value) {
        super(field);
        this.value = value;
    }

    @Override
    public Object getOldValue() {
        return value;
    }

    @Override
    public Object getNewValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NothingChanged that = (NothingChanged) o;

        return value != null ? value.equals(that.value) : that.value == null;

    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
