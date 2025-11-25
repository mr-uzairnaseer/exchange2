package org.me.gcu.syed_faiq_ali_s2238968.model;

public class CurrencyRate {
    private String code;
    private String title;
    private double rate;
    private String description;
    private String pubDate;

    public CurrencyRate() {}

    public CurrencyRate(String code, String title, double rate, String description, String pubDate) {
        this.code = code;
        this.title = title;
        this.rate = rate;
        this.description = description;
        this.pubDate = pubDate;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public double getRate() { return rate; }
    public void setRate(double rate) { this.rate = rate; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPubDate() { return pubDate; }
    public void setPubDate(String pubDate) { this.pubDate = pubDate; }

    public String getCountryName() {
        if (description != null && description.toLowerCase().contains("to ")) {
            int idx = description.toLowerCase().indexOf("to ");
            if (idx > 0) {
                String after = description.substring(idx + 3).trim();
                for (int i = 0; i < after.length(); i++) {
                    if (Character.isDigit(after.charAt(i))) {
                        return after.substring(0, i).trim();
                    }
                }
                return after;
            }
        }
        return "";
    }

    @Override
    public String toString() {
        return "CurrencyRate{" +
                "code='" + code + '\'' +
                ", rate=" + rate +
                '}';
    }
}
