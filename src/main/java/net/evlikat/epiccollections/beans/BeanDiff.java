package net.evlikat.epiccollections.beans;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiPredicate;

public class BeanDiff<T> {

    private final Map<String, FieldChange> changes;
    private final boolean anythingChanged;

    private BeanDiff(Map<String, FieldChange> changes, boolean anythingChanged) {
        this.changes = changes;
        this.anythingChanged = anythingChanged;
    }

    public boolean isAnythingChanged() {
        return anythingChanged;
    }

    public Map<String, FieldChange> getChanges() {
        return Collections.unmodifiableMap(changes);
    }

    public static <T> BeanDiff<T> compareIgnoringNulls(T a, T b) {
        return compare(a, b, true);
    }

    public static <T> BeanDiff<T> compare(T a, T b) {
        return compare(a, b, false);
    }

    private static <T> BeanDiff<T> compare(T a, T b, boolean considerNullsAsNotChanged) {
        BeanDiffBuilder<T> diffBuilder = new BeanDiffBuilder<>();
        Field[] declaredFields = a.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);

            try {
                Object fa = field.get(a);
                Object fb = field.get(b);
                EqualCheck<Object, Object> equalCheck = equalCheckFor(field.getType());
                if (considerNullsAsNotChanged) {
                    innerCompareIgnoringNulls(diffBuilder, field, fa, fb, equalCheck);
                } else {
                    innerCompare(diffBuilder, field, fa, fb, equalCheck);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return diffBuilder.build();
    }

    private static <V1, V2> EqualCheck<V1, V2> equalCheckFor(Class<?> cls) {
        if (cls.isArray()) {
            if (int.class == cls.getComponentType()) {
                return (a, b) -> Arrays.equals((int[]) a, (int[]) b);
            } else if (long.class == cls.getComponentType()) {
                return (a, b) -> Arrays.equals((long[]) a, (long[]) b);
            } else if (short.class == cls.getComponentType()) {
                return (a, b) -> Arrays.equals((short[]) a, (short[]) b);
            } else if (float.class == cls.getComponentType()) {
                return (a, b) -> Arrays.equals((float[]) a, (float[]) b);
            } else if (double.class == cls.getComponentType()) {
                return (a, b) -> Arrays.equals((double[]) a, (double[]) b);
            } else if (boolean.class == cls.getComponentType()) {
                return (a, b) -> Arrays.equals((boolean[]) a, (boolean[]) b);
            } else if (char.class == cls.getComponentType()) {
                return (a, b) -> Arrays.equals((char[]) a, (char[]) b);
            } else {
                return (a, b) -> Arrays.equals((Object[]) a, (Object[]) b);
            }
        }
        if (Collection.class.isAssignableFrom(cls)) {
            //todo
        }
        if (Map.class.isAssignableFrom(cls)) {
            //todo
        }
        return Objects::equals;
    }

    private static <T> void innerCompare(BeanDiffBuilder<T> diffBuilder,
                                         Field field,
                                         Object fa,
                                         Object fb,
                                         EqualCheck<Object, Object> equalCheck) {
        if (fa == null) {
            if (fb == null) {
                diffBuilder.nothingChanged(field, null);
            } else {
                diffBuilder.newValue(field, fb);
            }
        } else {
            if (fb == null) {
                diffBuilder.valueRemoved(field, fa);
            } else {
                if (equalCheck.test(fa, fb)) {
                    diffBuilder.nothingChanged(field, fa);
                } else {
                    diffBuilder.valueChanged(field, fa, fb);
                }
            }
        }
    }

    private static <T> void innerCompareIgnoringNulls(BeanDiffBuilder<T> diffBuilder,
                                                      Field field,
                                                      Object fa,
                                                      Object fb,
                                                      EqualCheck<Object, Object> equalCheck) {
        if (fb == null) {
            diffBuilder.nothingChanged(field, fa);
        } else {
            if (fa == null) {
                diffBuilder.newValue(field, fb);
            } else {
                if (equalCheck.test(fa, fb)) {
                    diffBuilder.nothingChanged(field, fa);
                } else {
                    diffBuilder.valueChanged(field, fa, fb);
                }
            }
        }
    }

    private interface EqualCheck<V1, V2> extends BiPredicate<V1, V2> {
    }

    private static final class BeanDiffBuilder<T> {

        private final Map<String, FieldChange> changes = new LinkedHashMap<>();
        private boolean anythingChanged = false;

        public void nothingChanged(Field f, Object v) {
            changes.put(f.getName(), new NothingChanged(f, v));
        }

        public void newValue(Field f, Object v) {
            changes.put(f.getName(), new ValueCreated(f, v));
            anythingChanged = true;
        }

        public void valueChanged(Field f, Object oldValue, Object newValue) {
            changes.put(f.getName(), new ValueChanged(f, oldValue, newValue));
            anythingChanged = true;
        }

        public void valueRemoved(Field f, Object oldValue) {
            changes.put(f.getName(), new ValueRemoved(f, oldValue));
            anythingChanged = true;
        }

        public BeanDiff<T> build() {
            return new BeanDiff<T>(changes, anythingChanged);
        }
    }
}
