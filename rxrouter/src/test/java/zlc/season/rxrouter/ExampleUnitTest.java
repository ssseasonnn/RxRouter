package zlc.season.rxrouter;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void datagram_equals() throws Exception {
        Datagram obj1 = new Datagram();
        Datagram obj2 = new Datagram();

        System.out.println(obj1);
        System.out.println(obj2);

        System.out.println(obj1.hashCode());
        System.out.println(obj2.hashCode());

        Datagram obj3 = Datagram.Companion.empty();
        Datagram obj4 = Datagram.Companion.empty();

        System.out.println(obj3);
        System.out.println(obj4);

        System.out.println(obj3.hashCode());
        System.out.println(obj4.hashCode());

        boolean first = obj1.equals(obj2);
        boolean second = obj3.equals(obj4);

        System.out.println(first);
        System.out.println(second);

        assertEquals(true, first);
        assertEquals(false, second);

    }
}