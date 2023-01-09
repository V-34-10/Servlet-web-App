package edu.fpm.pz.model;

import java.io.Serializable;
import java.util.Objects;

public class Position implements Serializable {
    private int positionId;
    private String name;

    public Position() {}

    public Position(int positionId, String name) {
        this.positionId = positionId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    @Override
    public String toString() {
        return "" + positionId + " " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return Objects.equals(name, position.name);
    }

}
