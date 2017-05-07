package net.evlikat.epiccollections.beans;

import java.lang.reflect.Field;

public class ValueChanged extends FieldChange {
    private final Object oldValue;
    private final Object newValue;

    public ValueChanged(Field field, Object oldValue, Object newValue) {
        super(field);
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public Object getNewValue() {
        return newValue;
    }

    public Object getOldValue() {
        return oldValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValueChanged that = (ValueChanged) o;

        if (oldValue != null ? !oldValue.equals(that.oldValue) : that.oldValue != null) return false;
        return newValue != null ? newValue.equals(that.newValue) : that.newValue == null;

    }

    @Override
    public int hashCode() {
        int result = oldValue != null ? oldValue.hashCode() : 0;
        result = 31 * result + (newValue != null ? newValue.hashCode() : 0);
        return result;
    }
}
