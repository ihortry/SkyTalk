package griffith.skytalkpro.model;

public class WeatherData {
    private double minTemp;
    private double maxTemp;
    private double precipitation;
    private double cloudCover;

    public WeatherData(double minTemp, double maxTemp, double precipitation, double cloudCover) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.precipitation = precipitation;
        this.cloudCover = cloudCover;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }

    public double getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(double cloudCover) {
        this.cloudCover = cloudCover;
    }

    @Override
    public String toString() {
        return "WeatherData{minTemp=" + minTemp + ", maxTemp=" + maxTemp +
               ", precipitation=" + precipitation + ", cloudCover=" + cloudCover + "}";
    }


}
