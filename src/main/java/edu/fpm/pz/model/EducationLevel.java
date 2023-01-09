package edu.fpm.pz.model;

import java.io.Serializable;
import java.util.Objects;

public class EducationLevel implements Serializable {
    private int educationLevelId;
    private String name;

    public EducationLevel() {}

    public EducationLevel(int educationLevelId, String name) {
        this.educationLevelId = educationLevelId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getEducationLevelId() {
        return educationLevelId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEducationLevelId(int educationLevelId) {
        this.educationLevelId = educationLevelId;
    }

    @Override
    public String toString() {
        return "" + educationLevelId + " " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EducationLevel)) return false;
        EducationLevel educationLevel = (EducationLevel) o;
        return Objects.equals(name, educationLevel.name);
    }

}

