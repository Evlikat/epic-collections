package net.evlikat.epiccollections.beans;

import java.lang.reflect.Field;

public class ValueCreated extends FieldChange {
    private final Object v;

    public ValueCreated(Field field, Object v) {
        super(field);
        this.v = v;
    }

    @Override
    public Object getOldValue() {
        return null;
    }

    @Override
    public Object getNewValue() {
        return v;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValueCreated that = (ValueCreated) o;

        return v != null ? v.equals(that.v) : that.v == null;

    }

    @Override
    public int hashCode() {
        return v != null ? v.hashCode() : 0;
    }
}
