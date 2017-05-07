package net.evlikat.epiccollections.beans;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class BeanDiffTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void compareNothingChanged() throws Exception {
        TestBean o = new TestBean("a", "b", "c", "d");
        TestBean n = new TestBean("a", "b", "c", "d");
        BeanDiff<TestBean> diff = BeanDiff.compare(o, n);
        assertThat(diff.isAnythingChanged()).isFalse();
    }

    @Test
    public void compareAllTypesOfChanged() throws Exception {
        TestBean o = new TestBean("av", null, "cv", "dv");
        TestBean n = new TestBean(null, "bv", "nv", "dv");
        BeanDiff<TestBean> diff = BeanDiff.compare(o, n);
        assertThat(diff.isAnythingChanged()).isTrue();

        FieldChange aChange = diff.getChanges().get("a");
        assertThat(aChange).isInstanceOf(ValueRemoved.class);
        assertThat(aChange.getOldValue()).isEqualTo("av");
        assertThat(aChange.getNewValue()).isNull();

        FieldChange bChange = diff.getChanges().get("b");
        assertThat(bChange).isInstanceOf(ValueCreated.class);
        assertThat(bChange.getOldValue()).isNull();
        assertThat(bChange.getNewValue()).isEqualTo("bv");

        FieldChange cChange = diff.getChanges().get("c");
        assertThat(cChange).isInstanceOf(ValueChanged.class);
        assertThat(cChange.getOldValue()).isEqualTo("cv");
        assertThat(cChange.getNewValue()).isEqualTo("nv");

        FieldChange dChange = diff.getChanges().get("d");
        assertThat(dChange).isInstanceOf(NothingChanged.class);
        assertThat(dChange.getOldValue()).isEqualTo("dv");
        assertThat(dChange.getNewValue()).isEqualTo("dv");
    }

    @Test
    public void compareAllTypesOfChangedIgnoringNulls() throws Exception {
        TestBean o = new TestBean("av", null, "cv", "dv");
        TestBean n = new TestBean(null, "bv", "nv", "dv");
        BeanDiff<TestBean> diff = BeanDiff.compareIgnoringNulls(o, n);
        assertThat(diff.isAnythingChanged()).isTrue();

        FieldChange aChange = diff.getChanges().get("a");
        assertThat(aChange).isInstanceOf(NothingChanged.class);
        assertThat(aChange.getOldValue()).isEqualTo("av");
        assertThat(aChange.getNewValue()).isEqualTo("av");

        FieldChange bChange = diff.getChanges().get("b");
        assertThat(bChange).isInstanceOf(ValueCreated.class);
        assertThat(bChange.getOldValue()).isNull();
        assertThat(bChange.getNewValue()).isEqualTo("bv");

        FieldChange cChange = diff.getChanges().get("c");
        assertThat(cChange).isInstanceOf(ValueChanged.class);
        assertThat(cChange.getOldValue()).isEqualTo("cv");
        assertThat(cChange.getNewValue()).isEqualTo("nv");

        FieldChange dChange = diff.getChanges().get("d");
        assertThat(dChange).isInstanceOf(NothingChanged.class);
        assertThat(dChange.getOldValue()).isEqualTo("dv");
        assertThat(dChange.getNewValue()).isEqualTo("dv");
    }

    @Test
    public void comparePrimBean() throws Exception {
        BeanDiff<TestPrimBean> diff = BeanDiff.compare(new TestPrimBean(1), new TestPrimBean(2));
        assertThat(diff.isAnythingChanged()).isTrue();
        FieldChange aChange = diff.getChanges().get("a");
        assertThat(aChange).isInstanceOf(ValueChanged.class);
        assertThat(aChange.getOldValue()).isEqualTo(1);
        assertThat(aChange.getNewValue()).isEqualTo(2);
    }

    @Test
    public void comparePrimArrBean() throws Exception {
        BeanDiff<TestPrimArrBean> diff = BeanDiff.compare(
                new TestPrimArrBean(new int[]{1, 2}),
                new TestPrimArrBean(new int[]{3, 4})
        );
        assertThat(diff.isAnythingChanged()).isTrue();
        FieldChange aChange = diff.getChanges().get("a");
        assertThat(aChange).isInstanceOf(ValueChanged.class);
        assertThat(aChange.getOldValue()).isEqualTo(new int[]{1, 2});
        assertThat(aChange.getNewValue()).isEqualTo(new int[]{3, 4});
    }

    @Test
    public void comparePrimArrBeanNothingChanged() throws Exception {
        BeanDiff<TestPrimArrBean> diff = BeanDiff.compare(
                new TestPrimArrBean(new int[]{1, 2}),
                new TestPrimArrBean(new int[]{1, 2})
        );
        assertThat(diff.isAnythingChanged()).isFalse();
        FieldChange aChange = diff.getChanges().get("a");
        assertThat(aChange).isInstanceOf(NothingChanged.class);
        assertThat(aChange.getOldValue()).isEqualTo(new int[]{1, 2});
        assertThat(aChange.getNewValue()).isEqualTo(new int[]{1, 2});
    }

    private static final class TestBean {
        public final String a;
        public final String b;
        public final String c;
        public final String d;

        public TestBean(String a, String b, String c, String d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }
    }

    private static final class TestPrimBean {
        public final int a;

        public TestPrimBean(int a) {
            this.a = a;
        }
    }

    private static final class TestPrimArrBean {
        public final int[] a;

        public TestPrimArrBean(int[] a) {
            this.a = a;
        }
    }
}
