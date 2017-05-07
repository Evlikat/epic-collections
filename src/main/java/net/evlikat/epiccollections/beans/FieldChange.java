package net.evlikat.epiccollections.beans;

import java.lang.reflect.Field;

public abstract class FieldChange implements Change {

    private final Field field;

    public FieldChange(Field field) {
        this.field = field;
    }

    public Field getField() {
        return field;
    }
}
