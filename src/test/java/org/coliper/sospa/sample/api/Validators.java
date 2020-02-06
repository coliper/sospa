package org.coliper.sospa.sample.api;

import java.util.Objects;

public class Validators {

    public Validators() {
    }

    public boolean isZipcodeValid(String zipCode) {
        Objects.requireNonNull(zipCode, "zipCode");
        return zipCode.length() == 5 && !"00000".contentEquals(zipCode);
    }

}
