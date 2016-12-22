package adapter;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * Created by mi on 16-12-22.
 */
public class EnumeratorIterator implements Iterator {

    Enumeration enumeration;

    @Override
    public boolean hasNext() {
        return enumeration.hasMoreElements();
    }

    @Override
    public Object next() {
        return enumeration.nextElement();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
