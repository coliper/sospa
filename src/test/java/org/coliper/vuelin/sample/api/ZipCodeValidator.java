package org.coliper.vuelin.sample.api;

import java.util.Objects;

public class ZipCodeValidator {

    public ZipCodeValidator() {}

    public boolean isValid(String zipCode) {
        Objects.requireNonNull(zipCode, "zipCode");
        return zipCode.length() == 5 && !"00000".contentEquals(zipCode);
    }

}
