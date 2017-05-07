package net.evlikat.epiccollections.beans;

import java.lang.reflect.Field;

public class ValueRemoved extends FieldChange {
    private final Object oldValue;

    public ValueRemoved(Field field, Object oldValue) {
        super(field);
        this.oldValue = oldValue;
    }

    @Override
    public Object getNewValue() {
        return null;
    }

    @Override
    public Object getOldValue() {
        return oldValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValueRemoved that = (ValueRemoved) o;

        return oldValue != null ? oldValue.equals(that.oldValue) : that.oldValue == null;

    }

    @Override
    public int hashCode() {
        return oldValue != null ? oldValue.hashCode() : 0;
    }
}
