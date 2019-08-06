package org.coliper.vuelin;

public class PageName<T extends VuelinPage> extends AbstractName implements EndpointNamespace {

    public PageName(String nameAsString) {
        super(nameAsString);
    }

}
