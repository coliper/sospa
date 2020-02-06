/*
 * Copyright (C) 2017 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.coliper.sospa.sample.service;

import java.util.List;
import java.util.Objects;

import org.coliper.sospa.sample.model.Product;

import com.google.common.collect.ImmutableList;

/**
 * @author alex@coliper.org
 *
 */
public class Service {

    public static boolean isZipcodeValid(String zipCode) {
        Objects.requireNonNull(zipCode, "zipCode");
        return zipCode.length() == 5 && !"00000".contentEquals(zipCode);
    }

    private static final List<Product> AVAILABLE_PRODUCTS = ImmutableList.of(new Product());

    public static List<Product> loadProductsForRegion(String zipCode) {
        return AVAILABLE_PRODUCTS;
    }

}
