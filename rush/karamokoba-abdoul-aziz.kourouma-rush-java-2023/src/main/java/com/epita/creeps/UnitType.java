package com.epita.creeps;

public enum UnitType {
    Nexus("nexus"),
    Pylon("pylon"),
    PhotonCannon("photon-cannon"),
    Probe("probe"),
    Observer("observer"),
    Dragoon("dragoon");

    private final String unitType;
    private UnitType(String unitType){
        this.unitType = unitType;
    }

    public String getUnitType() {
        return unitType;
    }
}
