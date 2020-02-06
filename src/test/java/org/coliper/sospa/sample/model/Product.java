package org.coliper.sospa.sample.model;

public class Product extends Entity {
    private String shortDescription;

    public Product() {
    }

    /**
     * @return the shortDescription
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * @param shortDescription
     *            the shortDescription to set
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

}
