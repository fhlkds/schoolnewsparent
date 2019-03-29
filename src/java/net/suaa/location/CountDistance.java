package net.suaa.location;

public class CountDistance {

    //经度
    private Double longitude;

    //维度
    private Double dimensionality;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getDimensionality() {
        return dimensionality;
    }

    public void setDimensionality(Double dimensionality) {
        this.dimensionality = dimensionality;
    }

    public static Double getDistance(CountDistance start,CountDistance end){
        double lon1 = (Math.PI/180)*start.getLongitude();
        double lon2 = (Math.PI/180)*end.getLongitude();

        double dimensionality1 = (Math.PI/180)*start.getDimensionality();
        double dimensionality2 = (Math.PI/180)*end.getDimensionality();

        double R = 6371;

       double d = Math.acos(Math.sin(dimensionality1)*Math.sin(dimensionality2)+Math.cos(dimensionality1)*Math.cos(dimensionality2)*Math.cos(lon2-lon1));
       return d*1000;

    }
}
