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

package org.coliper.sospa;

import java.util.List;

import org.coliper.lontano.InterfaceMetaInfo;
import org.coliper.lontano.OperationMetaInfo;

/**
 * @author alex@coliper.org
 *
 */
public class PageMetaInfo {
    private final SospaPage page;
    private final InterfaceMetaInfo interfaceMetaInfo;

    PageMetaInfo(SospaPage page, InterfaceMetaInfo interfaceMetaInfo) {
        this.page = page;
        this.interfaceMetaInfo = interfaceMetaInfo;
    }

    public String getPageName() {
        return this.page.getPageName().getNameAsString();
    }

    public List<OperationMetaInfo> getOperations() {
        return this.interfaceMetaInfo.getOperations();
    }

    public String lontanoInterfaceName() {
        return this.interfaceMetaInfo.getInterfaceName();
    }
}
