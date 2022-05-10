public class AirQualitySensor {
    public float pm25_sensor = 0;
    public float pm10_sensor = 0;
    public boolean is_on = true;
    boolean is_connected_to_sensorHardware = true;

    public void turn_on(){
        is_on = true;
    }
    public void turn_off(){
        is_on = false;
    }
    public void connect_to_sensorHardware(){
        is_connected_to_sensorHardware = true;
    }
}
