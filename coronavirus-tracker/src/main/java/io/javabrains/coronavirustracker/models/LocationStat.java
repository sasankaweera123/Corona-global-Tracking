package io.javabrains.coronavirustracker.models;

public class LocationStat {

    private String state;
    private String country;
    private int latestCases;
    private int diffFromPast;

    public int getDiffFromPast() {
        return diffFromPast;
    }

    public void setDiffFromPast(int diffFromPast) {
        this.diffFromPast = diffFromPast;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatestCases() {
        return latestCases;
    }

    public void setLatestCases(int latestCases) {
        this.latestCases = latestCases;
    }

    @Override
    public String toString() {
        return "LocationStat{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", latestCases=" + latestCases +
                '}';
    }
}
