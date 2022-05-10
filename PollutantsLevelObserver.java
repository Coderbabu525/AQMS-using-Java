public class PollutantsLevelObserver{
    public String pm25_status = "";
    public String pm10_status = "";
    
    public void change_status_pm25(String status){
        pm25_status = status;
    }
    public void change_status_pm10(String status){
        pm10_status = status;
    }
};