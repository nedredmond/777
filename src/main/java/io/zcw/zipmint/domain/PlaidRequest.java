package io.zcw.zipmint.domain;

public class PlaidRequest {
    private String publicToken;

    public PlaidRequest() {
    }

    public PlaidRequest(String publicToken) {
        this.publicToken = publicToken;
    }

    public String getPublicToken() {
        return publicToken;
    }

    public void setPublicToken(String publicToken) {
        this.publicToken = publicToken;
    }
}
