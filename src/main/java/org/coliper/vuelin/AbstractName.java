package org.coliper.vuelin;

import java.util.Objects;

public abstract class AbstractName {
    private final String nameAsString;

    protected AbstractName(String nameAsString) {
        Objects.requireNonNull(nameAsString, "nameAsString");
        this.nameAsString = nameAsString;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nameAsString == null) ? 0 : nameAsString.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractName other = (AbstractName) obj;
        if (nameAsString == null) {
            if (other.nameAsString != null)
                return false;
        } else if (!nameAsString.equals(other.nameAsString))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AbstractName [nameAsString=" + nameAsString + "]";
    }

    public String getNameAsString() {
        return this.nameAsString;
    }
}
