package br.com.model;

public class EmailModel {
    @SuppressWarnings("deprecation")
    private String titleEmail;
    @SuppressWarnings("deprecation")
    private StringBuilder bodyEmail;

    public @SuppressWarnings("deprecation")
    String getTitleEmail() {
        return titleEmail;
    }

    public void setTitleEmail(@SuppressWarnings("deprecation") String titleEmail) {
        this.titleEmail = titleEmail;
    }

    public @SuppressWarnings("deprecation")
    StringBuilder getBodyEmail() {
        return bodyEmail;
    }

    public void setBodyEmail(@SuppressWarnings("deprecation") StringBuilder bodyEmail) {
        this.bodyEmail = bodyEmail;
    }

}
