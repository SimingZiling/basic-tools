package org.yiming.networktools.request;

/**
 *
 */
public enum SSLProtocol {

    SSL("SSL"),TLS("TLS");

    private String protocol;

    public String getProtocol() {
        return protocol;
    }

    SSLProtocol(String protocol) {
        this.protocol = protocol;
    }
}
