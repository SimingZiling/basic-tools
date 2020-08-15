package org.yiming.localtools.basicinfo;

public enum Charact {

    UTF8("UTF8"),GBK("GBK");

    private String name;

    public String getName() {
        return name;
    }

    Charact(String name) {
        this.name = name;
    }
}
