public class SpotAirPollutionInformer {
    public boolean is_on = true;
    public String spot_name = "";
    public SpotAirPollutionInformer(String name){
        spot_name = name;
    }
    public void pm25_alert(String s){
        System.out.println("The PM2.5 pollution level in " + spot_name + " is " + s + " now.");
    }
    public void pm10_alert(String s){
        System.out.println("The PM10 pollution level in " + spot_name + " is " + s + " now.");
    }
    public void pm_pollution_alert_onSpot(String s){
        System.out.println("The overall PM pollution level in " + spot_name + " is " + s + " now.");
    }
    
}
