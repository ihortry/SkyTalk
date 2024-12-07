package griffith.skytalkpro.model;

import java.time.LocalDate;

public class Place {
    private String name;
    private LocalDate date;

    public Place(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Place{name='" + name + "', date=" + date + "}";
    }
}
