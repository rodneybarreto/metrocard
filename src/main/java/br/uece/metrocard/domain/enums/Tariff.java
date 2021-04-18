package br.uece.metrocard.domain.enums;

public enum Tariff {

    ZONE_A_UNIC(6.0, "A"),
    ZONE_A_DAY(10.0, "A"),
    ZONE_A_WEEK(30.0, "A"),
    ZONE_A_MONTH(130.0, "A"),
    ZONE_B_UNIC(7.0, "B"),
    ZONE_B_DAY(12.0, "B"),
    ZONE_B_WEEK(45.0, "B"),
    ZONE_B_MONTH(170.0, "B");

    private Double value;
    private String zone;

    Tariff(double value, String zone) {
        this.value = value;
        this.zone = zone;
    }

    public Double getValue() {
        return value;
    }

    public String getZone() {
        return zone;
    }

}
