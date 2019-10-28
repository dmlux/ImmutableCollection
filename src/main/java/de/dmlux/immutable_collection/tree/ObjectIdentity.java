package de.dmlux.immutable_collection.tree;

import java.util.Objects;

public class ObjectIdentity<T> {
    private final int sysHash;
    public final T object;

    public ObjectIdentity(T object) {
        this.object = object;
        this.sysHash = object == null ? 0 : System.identityHashCode(object);
    }

    @Override
    public int hashCode() {
        return sysHash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ObjectIdentity)) return false;
        return Objects.equals(object, ((ObjectIdentity) obj).object);
    }
}
