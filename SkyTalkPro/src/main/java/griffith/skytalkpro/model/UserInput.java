package griffith.skytalkpro.model;

import java.util.List;

public class UserInput {
    private List<Place> places;

    public UserInput(List<Place> places) {
        this.places = places;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return "UserInput{places=" + places + "}";
    }
}
