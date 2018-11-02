package okon.CPD_APP2;

import java.io.Closeable;

public interface Connection extends Closeable {

    String response();

    default void close() {}
}
