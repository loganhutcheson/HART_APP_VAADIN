package com.humane.application;

import java.time.LocalTime;

public class VaccineRecord {
    private String name;
    private LocalTime date;
    private String providedBy;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getDate() {
        return this.date;
    }

    public void setDate(LocalTime date) {
        this.date = date;
    }

    public String getProvidedBy() {
        return this.providedBy;
    }

    public void setProvidedBy(String providedBy) {
        this.providedBy = providedBy;
    }


}
