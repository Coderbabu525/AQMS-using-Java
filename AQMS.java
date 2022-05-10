import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;

public class AQMS {
    public static void main(String[] args) throws FileNotFoundException{
    AirQualitySensor AQSensor_uni = new AirQualitySensor();
    AirQualitySensor AQSensor_road = new AirQualitySensor();
    AirQualitySensor AQSensor_center = new AirQualitySensor();
    PollutantsLevelObserver PLO_uni = new PollutantsLevelObserver();
    PollutantsLevelObserver PLO_road = new PollutantsLevelObserver();
    PollutantsLevelObserver PLO_center = new PollutantsLevelObserver();
    SpotAirPollutionInformer SAPInformer_uni = new SpotAirPollutionInformer("University");
    SpotAirPollutionInformer SAPInformer_road = new SpotAirPollutionInformer("RoadSide");
    SpotAirPollutionInformer SAPInformer_center = new SpotAirPollutionInformer("CityCenter");
    CityAirPollutionInformer CAPInformer_city = new CityAirPollutionInformer();

    double elapsed_time = 0.0;

    final String delimiter = ";";
    try {
      /*File file_u = new File("University.csv");
      File file_r = new File("RoadSide.csv");
      File file_c = new File("CityCenter.csv");*/
      File file_u = new File("data3.csv");
      File file_r = new File("data2.csv");
      File file_c = new File("data1.csv");

      FileReader fr_u = new FileReader(file_u);
      FileReader fr_r = new FileReader(file_r);
      FileReader fr_c = new FileReader(file_c);

      BufferedReader br_u = new BufferedReader(fr_u);
      BufferedReader br_r = new BufferedReader(fr_r);
      BufferedReader br_c = new BufferedReader(fr_c);
      String header = br_u.readLine();
      header = br_r.readLine();
      header = br_c.readLine();
      String line_u = "", line_r = "", line_c = "";
      String[] tempArr_u, tempArr_r, tempArr_c;
      while ((line_c = br_c.readLine()) != null) {
        line_u = br_u.readLine();
        line_r = br_r.readLine();
        tempArr_u = line_u.split(delimiter);
        tempArr_r = line_r.split(delimiter);
        tempArr_c = line_c.split(delimiter);
        float pm25 = Float.valueOf(tempArr_u[8]);
        float pm10 = Float.valueOf(tempArr_u[3]);
        AQSensor_uni.pm10_sensor = pm10;
        AQSensor_uni.pm25_sensor = pm25;

        pm25 = Float.valueOf(tempArr_r[8]);
        pm10 = Float.valueOf(tempArr_r[3]);
        AQSensor_road.pm10_sensor = pm10;
        AQSensor_road.pm25_sensor = pm25;

        pm25 = Float.valueOf(tempArr_c[8]);
        pm10 = Float.valueOf(tempArr_c[3]);
        AQSensor_center.pm10_sensor = pm10;
        AQSensor_center.pm25_sensor = pm25;

        // Time counting starts here
        double start = System.currentTimeMillis();

        // Rules 1 - 18 for pm2.5 in 3 different spots
        // Rule 1 
        // CONDITION
        if ( 
            AQSensor_uni.is_on          == true             &&      // Premise 1
            AQSensor_uni.pm25_sensor    <= 5.0              &&   	// Premise 2
            PLO_uni.pm25_status         != "Excellent" 	    &&    	// Premise 3
            SAPInformer_uni.is_on 	    == true)		            // Premise 4
        
        // ACTION
        {
            SAPInformer_uni.pm25_alert("Excellent");            // Instigation 1
            PLO_uni.change_status_pm25("Excellent");			// Instigation 2
        }

        // Rule 2
        // CONDITION
        if ( 
            AQSensor_uni.is_on          == true             &&      // Premise 1
            AQSensor_uni.pm25_sensor    <= 10.0             &&   	// Premise 2
            AQSensor_uni.pm25_sensor    >  5.0              &&      // Premise 3
            PLO_uni.pm25_status         != "Fine" 	        &&    	// Premise 4
            SAPInformer_uni.is_on 	    == true)		            // Premise 5
        
        // ACTION
        {
            SAPInformer_uni.pm25_alert("Fine");             // Instigation 1
            PLO_uni.change_status_pm25("Fine");			    // Instigation 2
        }

        // Rule 3
        // CONDITION
        if ( 
            AQSensor_uni.is_on          == true             &&      // Premise 1
            AQSensor_uni.pm25_sensor    <= 20.0             &&   	// Premise 2
            AQSensor_uni.pm25_sensor    >  10.0             &&      // Premise 3
            PLO_uni.pm25_status         != "Moderate" 	    &&    	// Premise 4
            SAPInformer_uni.is_on 	    == true)		            // Premise 5
        
        // ACTION
        {
            SAPInformer_uni.pm25_alert("Moderate");             // Instigation 1
            PLO_uni.change_status_pm25("Moderate");			    // Instigation 2
        }

        // Rule 4
        // CONDITION
        if ( 
            AQSensor_uni.is_on          == true             &&      // Premise 1
            AQSensor_uni.pm25_sensor    <= 25.0             &&   	// Premise 2
            AQSensor_uni.pm25_sensor    >  20.0             &&      // Premise 3
            PLO_uni.pm25_status         != "Poor" 	        &&    	// Premise 4
            SAPInformer_uni.is_on 	    == true)		            // Premise 5
        
        // ACTION
        {
            SAPInformer_uni.pm25_alert("Poor");             // Instigation 1
            PLO_uni.change_status_pm25("Poor");			    // Instigation 2
        }

        // Rule 5
        // CONDITION
        if ( 
            AQSensor_uni.is_on          == true             &&      // Premise 1
            AQSensor_uni.pm25_sensor    <= 60.0             &&   	// Premise 2
            AQSensor_uni.pm25_sensor    >  25.0             &&      // Premise 3
            PLO_uni.pm25_status         != "Very Poor" 	    &&    	// Premise 4
            SAPInformer_uni.is_on 	    == true)		            // Premise 5
        
        // ACTION
        {
            SAPInformer_uni.pm25_alert("Very Poor");             // Instigation 1
            PLO_uni.change_status_pm25("Very Poor");			 // Instigation 2
        }

        // Rule 6
        // CONDITION
        if ( 
            AQSensor_uni.is_on          == true             &&      // Premise 1
            AQSensor_uni.pm25_sensor    >  60.0             &&      // Premise 2
            PLO_uni.pm25_status         != "Severe" 	    &&    	// Premise 3
            SAPInformer_uni.is_on 	    == true)		            // Premise 4
        
        // ACTION
        {
            SAPInformer_uni.pm25_alert("Severe");               // Instigation 1
            PLO_uni.change_status_pm25("Severe");			    // Instigation 2
        }

        // Rule 7 
        // CONDITION
        if ( 
            AQSensor_road.is_on          == true             &&     // Premise 1
            AQSensor_road.pm25_sensor    <= 5.0              &&   	// Premise 2
            PLO_road.pm25_status         != "Excellent" 	 &&    	// Premise 3
            SAPInformer_road.is_on 	     == true)		            // Premise 4
        
        // ACTION
        {
            SAPInformer_road.pm25_alert("Excellent");           // Instigation 1
            PLO_road.change_status_pm25("Excellent");			// Instigation 2
        }

        // Rule 8
        // CONDITION
        if ( 
            AQSensor_road.is_on          == true             &&      // Premise 1
            AQSensor_road.pm25_sensor    <= 10.0             &&   	 // Premise 2
            AQSensor_road.pm25_sensor    >  5.0              &&      // Premise 3
            PLO_road.pm25_status         != "Fine" 	         &&    	 // Premise 4
            SAPInformer_road.is_on 	     == true)		             // Premise 5
        
        // ACTION
        {
            SAPInformer_road.pm25_alert("Fine");             // Instigation 1
            PLO_road.change_status_pm25("Fine");			 // Instigation 2
        }

        // Rule 9
        // CONDITION
        if ( 
            AQSensor_road.is_on          == true             &&      // Premise 1
            AQSensor_road.pm25_sensor    <= 20.0             &&   	 // Premise 2
            AQSensor_road.pm25_sensor    >  10.0             &&      // Premise 3
            PLO_road.pm25_status         != "Moderate" 	     &&    	 // Premise 4
            SAPInformer_road.is_on 	     == true)		             // Premise 5
        
        // ACTION
        {
            SAPInformer_road.pm25_alert("Moderate");             // Instigation 1
            PLO_road.change_status_pm25("Moderate");			 // Instigation 2
        }

        // Rule 10
        // CONDITION
        if ( 
            AQSensor_road.is_on          == true             &&      // Premise 1
            AQSensor_road.pm25_sensor    <= 25.0             &&   	 // Premise 2
            AQSensor_road.pm25_sensor    >  20.0             &&      // Premise 3
            PLO_road.pm25_status         != "Poor" 	         &&    	 // Premise 4
            SAPInformer_road.is_on 	     == true)		             // Premise 5
        
        // ACTION
        {
            SAPInformer_road.pm25_alert("Poor");             // Instigation 1
            PLO_road.change_status_pm25("Poor");			 // Instigation 2
        }

        // Rule 11
        // CONDITION
        if ( 
            AQSensor_road.is_on          == true             &&      // Premise 1
            AQSensor_road.pm25_sensor    <= 60.0             &&   	 // Premise 2
            AQSensor_road.pm25_sensor    >  25.0             &&      // Premise 3
            PLO_road.pm25_status         != "Very Poor" 	 &&    	 // Premise 4
            SAPInformer_road.is_on 	     == true)		             // Premise 5
        
        // ACTION
        {
            SAPInformer_road.pm25_alert("Very Poor");             // Instigation 1
            PLO_road.change_status_pm25("Very Poor");			  // Instigation 2
        }

        // Rule 12
        // CONDITION
        if ( 
            AQSensor_road.is_on          == true             &&      // Premise 1
            AQSensor_road.pm25_sensor    >  60.0             &&      // Premise 2
            PLO_road.pm25_status         != "Severe" 	     &&    	 // Premise 3
            SAPInformer_road.is_on 	     == true)		             // Premise 4
        
        // ACTION
        {
            SAPInformer_road.pm25_alert("Severe");               // Instigation 1
            PLO_road.change_status_pm25("Severe");			     // Instigation 2
        }

        // Rule 13 
        // CONDITION
        if ( 
            AQSensor_center.is_on          == true             &&       // Premise 1
            AQSensor_center.pm25_sensor    <= 5.0              &&   	// Premise 2
            PLO_center.pm25_status         != "Excellent" 	   &&    	// Premise 3
            SAPInformer_center.is_on 	   == true)		                // Premise 4
        
        // ACTION
        {
            SAPInformer_center.pm25_alert("Excellent");            // Instigation 1
            PLO_center.change_status_pm25("Excellent");			   // Instigation 2
        }

        // Rule 14
        // CONDITION
        if ( 
            AQSensor_center.is_on          == true             &&       // Premise 1
            AQSensor_center.pm25_sensor    <= 10.0             &&   	// Premise 2
            AQSensor_center.pm25_sensor    >  5.0              &&       // Premise 3
            PLO_center.pm25_status         != "Fine" 	       &&    	// Premise 4
            SAPInformer_center.is_on 	   == true)		                // Premise 5
        
        // ACTION
        {
            SAPInformer_center.pm25_alert("Fine");              // Instigation 1
            PLO_center.change_status_pm25("Fine");			    // Instigation 2
        }

        // Rule 15
        // CONDITION
        if ( 
            AQSensor_center.is_on          == true             &&       // Premise 1
            AQSensor_center.pm25_sensor    <= 20.0             &&   	// Premise 2
            AQSensor_center.pm25_sensor    >  10.0             &&       // Premise 3
            PLO_center.pm25_status         != "Moderate" 	   &&    	// Premise 4
            SAPInformer_center.is_on 	   == true)		                // Premise 5
        
        // ACTION
        {
            SAPInformer_center.pm25_alert("Moderate");              // Instigation 1
            PLO_center.change_status_pm25("Moderate");			    // Instigation 2
        }

        // Rule 16
        // CONDITION
        if ( 
            AQSensor_center.is_on          == true             &&       // Premise 1
            AQSensor_center.pm25_sensor    <= 25.0             &&   	// Premise 2
            AQSensor_center.pm25_sensor    >  20.0             &&       // Premise 3
            PLO_center.pm25_status         != "Poor" 	       &&    	// Premise 4
            SAPInformer_center.is_on 	   == true)		                // Premise 5
        
        // ACTION
        {
            SAPInformer_center.pm25_alert("Poor");              // Instigation 1
            PLO_center.change_status_pm25("Poor");			    // Instigation 2
        }

        // Rule 17
        // CONDITION
        if ( 
            AQSensor_center.is_on          == true             &&       // Premise 1
            AQSensor_center.pm25_sensor    <= 60.0             &&   	// Premise 2
            AQSensor_center.pm25_sensor    >  25.0             &&       // Premise 3
            PLO_center.pm25_status         != "Very Poor" 	   &&    	// Premise 4
            SAPInformer_center.is_on 	   == true)		                // Premise 5
        
        // ACTION
        {
            SAPInformer_center.pm25_alert("Very Poor");             // Instigation 1
            PLO_center.change_status_pm25("Very Poor");			    // Instigation 2
        }

        // Rule 18
        // CONDITION
        if ( 
            AQSensor_center.is_on          == true             &&       // Premise 1
            AQSensor_center.pm25_sensor    >  60.0             &&       // Premise 2
            PLO_center.pm25_status         != "Severe" 	       &&    	// Premise 3
            SAPInformer_center.is_on 	   == true)		                // Premise 4
        
        // ACTION
        {
            SAPInformer_center.pm25_alert("Severe");                // Instigation 1
            PLO_center.change_status_pm25("Severe");			    // Instigation 2
        }

        // Rules 19 - 36 for pm10 in 3 different spots

        // Rule 19 
        // CONDITION
        if ( 
            AQSensor_uni.is_on          == true             &&      // Premise 1
            AQSensor_uni.pm10_sensor    <= 10.0             &&   	// Premise 2
            PLO_uni.pm10_status         != "Excellent" 	    &&    	// Premise 3
            SAPInformer_uni.is_on 	    == true)		            // Premise 4
        
        // ACTION
        {
            SAPInformer_uni.pm10_alert("Excellent");            // Instigation 1
            PLO_uni.change_status_pm10("Excellent");			// Instigation 2
        }

        // Rule 20
        // CONDITION
        if ( 
            AQSensor_uni.is_on          == true             &&      // Premise 1
            AQSensor_uni.pm10_sensor    <= 20.0             &&   	// Premise 2
            AQSensor_uni.pm10_sensor    >  10.0             &&      // Premise 3
            PLO_uni.pm10_status         != "Fine" 	        &&    	// Premise 4
            SAPInformer_uni.is_on 	    == true)		            // Premise 5
        
        // ACTION
        {
            SAPInformer_uni.pm10_alert("Fine");             // Instigation 1
            PLO_uni.change_status_pm10("Fine");			    // Instigation 2
        }

        // Rule 21
        // CONDITION
        if ( 
            AQSensor_uni.is_on          == true             &&      // Premise 1
            AQSensor_uni.pm10_sensor    <= 35.0             &&   	// Premise 2
            AQSensor_uni.pm10_sensor    >  20.0             &&      // Premise 3
            PLO_uni.pm10_status         != "Moderate" 	    &&    	// Premise 4
            SAPInformer_uni.is_on 	    == true)		            // Premise 5
        
        // ACTION
        {
            SAPInformer_uni.pm10_alert("Moderate");             // Instigation 1
            PLO_uni.change_status_pm10("Moderate");			    // Instigation 2
        }

        // Rule 22
        // CONDITION
        if ( 
            AQSensor_uni.is_on          == true             &&      // Premise 1
            AQSensor_uni.pm10_sensor    <= 50.0             &&   	// Premise 2
            AQSensor_uni.pm10_sensor    >  35.0             &&      // Premise 3
            PLO_uni.pm10_status         != "Poor" 	        &&    	// Premise 4
            SAPInformer_uni.is_on 	    == true)		            // Premise 5
        
        // ACTION
        {
            SAPInformer_uni.pm10_alert("Poor");             // Instigation 1
            PLO_uni.change_status_pm10("Poor");			    // Instigation 2
        }

        // Rule 23
        // CONDITION
        if ( 
            AQSensor_uni.is_on          == true             &&      // Premise 1
            AQSensor_uni.pm10_sensor    <= 100.0            &&   	// Premise 2
            AQSensor_uni.pm10_sensor    >  50.0             &&      // Premise 3
            PLO_uni.pm10_status         != "Very Poor" 	    &&    	// Premise 4
            SAPInformer_uni.is_on 	    == true)		            // Premise 5
        
        // ACTION
        {
            SAPInformer_uni.pm10_alert("Very Poor");             // Instigation 1
            PLO_uni.change_status_pm10("Very Poor");			 // Instigation 2
        }

        // Rule 24
        // CONDITION
        if ( 
            AQSensor_uni.is_on          == true             &&      // Premise 1
            AQSensor_uni.pm10_sensor    >  100.0            &&      // Premise 2
            PLO_uni.pm10_status         != "Severe" 	    &&    	// Premise 3
            SAPInformer_uni.is_on 	    == true)		            // Premise 4
        
        // ACTION
        {
            SAPInformer_uni.pm10_alert("Severe");               // Instigation 1
            PLO_uni.change_status_pm10("Severe");			    // Instigation 2
        }

        // Rule 25 
        // CONDITION
        if ( 
            AQSensor_road.is_on          == true             &&     // Premise 1
            AQSensor_road.pm10_sensor    <= 10.0             &&   	// Premise 2
            PLO_road.pm10_status         != "Excellent" 	 &&    	// Premise 3
            SAPInformer_road.is_on 	     == true)		            // Premise 4
        
        // ACTION
        {
            SAPInformer_road.pm10_alert("Excellent");           // Instigation 1
            PLO_road.change_status_pm10("Excellent");			// Instigation 2
        }

        // Rule 26
        // CONDITION
        if ( 
            AQSensor_road.is_on          == true             &&      // Premise 1
            AQSensor_road.pm10_sensor    <= 20.0             &&   	 // Premise 2
            AQSensor_road.pm10_sensor    >  10.0             &&      // Premise 3
            PLO_road.pm10_status         != "Fine" 	         &&    	 // Premise 4
            SAPInformer_road.is_on 	     == true)		             // Premise 5
        
        // ACTION
        {
            SAPInformer_road.pm10_alert("Fine");             // Instigation 1
            PLO_road.change_status_pm10("Fine");			 // Instigation 2
        }

        // Rule 27
        // CONDITION
        if ( 
            AQSensor_road.is_on          == true             &&      // Premise 1
            AQSensor_road.pm10_sensor    <= 35.0             &&   	 // Premise 2
            AQSensor_road.pm10_sensor    >  20.0             &&      // Premise 3
            PLO_road.pm10_status         != "Moderate" 	     &&    	 // Premise 4
            SAPInformer_road.is_on 	     == true)		             // Premise 5
        
        // ACTION
        {
            SAPInformer_road.pm10_alert("Moderate");             // Instigation 1
            PLO_road.change_status_pm10("Moderate");			 // Instigation 2
        }

        // Rule 28
        // CONDITION
        if ( 
            AQSensor_road.is_on          == true             &&      // Premise 1
            AQSensor_road.pm10_sensor    <= 50.0             &&   	 // Premise 2
            AQSensor_road.pm10_sensor    >  35.0             &&      // Premise 3
            PLO_road.pm10_status         != "Poor" 	         &&    	 // Premise 4
            SAPInformer_road.is_on 	     == true)		             // Premise 5
        
        // ACTION
        {
            SAPInformer_road.pm10_alert("Poor");             // Instigation 1
            PLO_road.change_status_pm10("Poor");			 // Instigation 2
        }

        // Rule 29
        // CONDITION
        if ( 
            AQSensor_road.is_on          == true             &&      // Premise 1
            AQSensor_road.pm10_sensor    <= 100.0            &&   	 // Premise 2
            AQSensor_road.pm10_sensor    >  50.0             &&      // Premise 3
            PLO_road.pm10_status         != "Very Poor" 	 &&    	 // Premise 4
            SAPInformer_road.is_on 	     == true)		             // Premise 5
        
        // ACTION
        {
            SAPInformer_road.pm10_alert("Very Poor");             // Instigation 1
            PLO_road.change_status_pm10("Very Poor");			  // Instigation 2
        }

        // Rule 30
        // CONDITION
        if ( 
            AQSensor_road.is_on          == true             &&      // Premise 1
            AQSensor_road.pm10_sensor    >  100.0            &&      // Premise 2
            PLO_road.pm10_status         != "Severe" 	     &&    	 // Premise 3
            SAPInformer_road.is_on 	     == true)		             // Premise 4
        
        // ACTION
        {
            SAPInformer_road.pm10_alert("Severe");               // Instigation 1
            PLO_road.change_status_pm10("Severe");			     // Instigation 2
        }

        // Rule 31 
        // CONDITION
        if ( 
            AQSensor_center.is_on          == true             &&       // Premise 1
            AQSensor_center.pm10_sensor    <= 10.0             &&   	// Premise 2
            PLO_center.pm10_status         != "Excellent" 	   &&    	// Premise 3
            SAPInformer_center.is_on 	   == true)		                // Premise 4
        
        // ACTION
        {
            SAPInformer_center.pm10_alert("Excellent");            // Instigation 1
            PLO_center.change_status_pm10("Excellent");			   // Instigation 2
        }

        // Rule 32
        // CONDITION
        if ( 
            AQSensor_center.is_on          == true             &&       // Premise 1
            AQSensor_center.pm10_sensor    <= 20.0             &&   	// Premise 2
            AQSensor_center.pm10_sensor    >  10.0             &&       // Premise 3
            PLO_center.pm10_status         != "Fine" 	       &&    	// Premise 4
            SAPInformer_center.is_on 	   == true)		                // Premise 5
        
        // ACTION
        {
            SAPInformer_center.pm10_alert("Fine");              // Instigation 1
            PLO_center.change_status_pm10("Fine");			    // Instigation 2
        }

        // Rule 33
        // CONDITION
        if ( 
            AQSensor_center.is_on          == true             &&       // Premise 1
            AQSensor_center.pm10_sensor    <= 35.0             &&   	// Premise 2
            AQSensor_center.pm10_sensor    >  20.0             &&       // Premise 3
            PLO_center.pm10_status         != "Moderate" 	   &&    	// Premise 4
            SAPInformer_center.is_on 	   == true)		                // Premise 5
        
        // ACTION
        {
            SAPInformer_center.pm10_alert("Moderate");              // Instigation 1
            PLO_center.change_status_pm10("Moderate");			    // Instigation 2
        }

        // Rule 34
        // CONDITION
        if ( 
            AQSensor_center.is_on          == true             &&       // Premise 1
            AQSensor_center.pm10_sensor    <= 50.0             &&   	// Premise 2
            AQSensor_center.pm10_sensor    >  35.0             &&       // Premise 3
            PLO_center.pm10_status         != "Poor" 	       &&    	// Premise 4
            SAPInformer_center.is_on 	   == true)		                // Premise 5
        
        // ACTION
        {
            SAPInformer_center.pm10_alert("Poor");              // Instigation 1
            PLO_center.change_status_pm10("Poor");			    // Instigation 2
        }

        // Rule 35
        // CONDITION
        if ( 
            AQSensor_center.is_on          == true             &&       // Premise 1
            AQSensor_center.pm10_sensor    <= 100.0            &&   	// Premise 2
            AQSensor_center.pm10_sensor    >  50.0             &&       // Premise 3
            PLO_center.pm10_status         != "Very Poor" 	   &&    	// Premise 4
            SAPInformer_center.is_on 	   == true)		                // Premise 5
        
        // ACTION
        {
            SAPInformer_center.pm10_alert("Very Poor");             // Instigation 1
            PLO_center.change_status_pm10("Very Poor");			    // Instigation 2
        }

        // Rule 36
        // CONDITION
        if ( 
            AQSensor_center.is_on          == true             &&       // Premise 1
            AQSensor_center.pm10_sensor    >  100.0            &&       // Premise 2
            PLO_center.pm10_status         != "Severe" 	       &&    	// Premise 3
            SAPInformer_center.is_on 	   == true)		                // Premise 4
        
        // ACTION
        {
            SAPInformer_center.pm10_alert("Severe");                // Instigation 1
            PLO_center.change_status_pm10("Severe");			    // Instigation 2
        }

        // Rules 37 - 42 notifies when pm2.5 status in all the spots are same
        // Rule 37
        // CONDITION
        if ( 
            CAPInformer_city.is_on          == true             &&       // Premise 1
            PLO_uni.pm25_status             == "Excellent"      &&       // Premise 2
            PLO_road.pm25_status 	        == "Excellent"		&&       // Premise 3
            PLO_center.pm25_status          == "Excellent")              // Premise 4
        
        // ACTION
        {
            CAPInformer_city.pm25_alert_wholeCity("Excellent");          // Instigation 1
        }

        // Rule 38
        // CONDITION
        if ( 
            CAPInformer_city.is_on          == true             &&       // Premise 1
            PLO_uni.pm25_status             == "Fine"           &&       // Premise 2
            PLO_road.pm25_status 	        == "Fine"		    &&       // Premise 3
            PLO_center.pm25_status          == "Fine")                   // Premise 4
        
        // ACTION
        {
            CAPInformer_city.pm25_alert_wholeCity("Fine");               // Instigation 1
        }

        // Rule 39
        // CONDITION
        if ( 
            CAPInformer_city.is_on          == true             &&       // Premise 1
            PLO_uni.pm25_status             == "Moderate"       &&       // Premise 2
            PLO_road.pm25_status 	        == "Moderate"		&&       // Premise 3
            PLO_center.pm25_status          == "Moderate")               // Premise 4
        
        // ACTION
        {
            CAPInformer_city.pm25_alert_wholeCity("Moderate");           // Instigation 1
        }

        // Rule 40
        // CONDITION
        if ( 
            CAPInformer_city.is_on          == true             &&       // Premise 1
            PLO_uni.pm25_status             == "Poor"           &&       // Premise 2
            PLO_road.pm25_status 	        == "Poor"		    &&       // Premise 3
            PLO_center.pm25_status          == "Poor")                   // Premise 4
        
        // ACTION
        {
            CAPInformer_city.pm25_alert_wholeCity("Poor");               // Instigation 1
        }

        // Rule 41
        // CONDITION
        if ( 
            CAPInformer_city.is_on          == true             &&       // Premise 1
            PLO_uni.pm25_status             == "Very Poor"      &&       // Premise 2
            PLO_road.pm25_status 	        == "Very Poor"		&&       // Premise 3
            PLO_center.pm25_status          == "Very Poor")              // Premise 4
        
        // ACTION
        {
            CAPInformer_city.pm25_alert_wholeCity("Very Poor");          // Instigation 1
        }

        // Rule 42
        // CONDITION
        if ( 
            CAPInformer_city.is_on          == true             &&       // Premise 1
            PLO_uni.pm25_status             == "Severe"         &&       // Premise 2
            PLO_road.pm25_status 	        == "Severe"		    &&       // Premise 3
            PLO_center.pm25_status          == "Severe")                 // Premise 4
        
        // ACTION
        {
            CAPInformer_city.pm25_alert_wholeCity("Severe");             // Instigation 1
        }

        // Rules 43 - 48 notifies when pm10 status in all the spots are same
        // Rule 43
        // CONDITION
        if ( 
            CAPInformer_city.is_on          == true             &&       // Premise 1
            PLO_uni.pm10_status             == "Excellent"      &&       // Premise 2
            PLO_road.pm10_status 	        == "Excellent"		&&       // Premise 3
            PLO_center.pm10_status          == "Excellent")              // Premise 4
        
        // ACTION
        {
            CAPInformer_city.pm10_alert_wholeCity("Excellent");          // Instigation 1
        }

        // Rule 44
        // CONDITION
        if ( 
            CAPInformer_city.is_on          == true             &&       // Premise 1
            PLO_uni.pm10_status             == "Fine"           &&       // Premise 2
            PLO_road.pm10_status 	        == "Fine"		    &&       // Premise 3
            PLO_center.pm10_status          == "Fine")                   // Premise 4
        
        // ACTION
        {
            CAPInformer_city.pm10_alert_wholeCity("Fine");               // Instigation 1
        }

        // Rule 45
        // CONDITION
        if ( 
            CAPInformer_city.is_on          == true             &&       // Premise 1
            PLO_uni.pm10_status             == "Moderate"       &&       // Premise 2
            PLO_road.pm10_status 	        == "Moderate"		&&       // Premise 3
            PLO_center.pm10_status          == "Moderate")               // Premise 4
        
        // ACTION
        {
            CAPInformer_city.pm10_alert_wholeCity("Moderate");           // Instigation 1
        }

        // Rule 46
        // CONDITION
        if ( 
            CAPInformer_city.is_on          == true             &&       // Premise 1
            PLO_uni.pm10_status             == "Poor"           &&       // Premise 2
            PLO_road.pm10_status 	        == "Poor"		    &&       // Premise 3
            PLO_center.pm10_status          == "Poor")                   // Premise 4
        
        // ACTION
        {
            CAPInformer_city.pm10_alert_wholeCity("Poor");               // Instigation 1
        }

        // Rule 47
        // CONDITION
        if ( 
            CAPInformer_city.is_on          == true             &&       // Premise 1
            PLO_uni.pm10_status             == "Very Poor"      &&       // Premise 2
            PLO_road.pm10_status 	        == "Very Poor"		&&       // Premise 3
            PLO_center.pm10_status          == "Very Poor")              // Premise 4
        
        // ACTION
        {
            CAPInformer_city.pm10_alert_wholeCity("Very Poor");          // Instigation 1
        }

        // Rule 48
        // CONDITION
        if ( 
            CAPInformer_city.is_on          == true             &&       // Premise 1
            PLO_uni.pm10_status             == "Severe"         &&       // Premise 2
            PLO_road.pm10_status 	        == "Severe"		    &&       // Premise 3
            PLO_center.pm10_status          == "Severe")                 // Premise 4
        
        // ACTION
        {
            CAPInformer_city.pm10_alert_wholeCity("Severe");             // Instigation 1
        }

        // Rules 49 - 66 notifies when both pm2.5 and pm10 status is same in a particular spot

        // Rule 49
        // CONDITION
        if ( 
            SAPInformer_uni.is_on           == true                 &&       // Premise 1
            PLO_uni.pm25_status             == "Excellent"          &&       // Premise 2
            PLO_uni.pm10_status 	        == "Excellent")		             // Premise 3
            
        // ACTION
        {
            SAPInformer_uni.pm_pollution_alert_onSpot("Excellent");          // Instigation 1
        }

        // Rule 50
        // CONDITION
        if ( 
            SAPInformer_uni.is_on           == true             &&       // Premise 1
            PLO_uni.pm25_status             == "Fine"           &&       // Premise 2
            PLO_uni.pm10_status 	        == "Fine")		             // Premise 3
            
        // ACTION
        {
            SAPInformer_uni.pm_pollution_alert_onSpot("Fine");          // Instigation 1
        }

        // Rule 51
        // CONDITION
        if ( 
            SAPInformer_uni.is_on           == true                 &&       // Premise 1
            PLO_uni.pm25_status             == "Moderate"           &&       // Premise 2
            PLO_uni.pm10_status 	        == "Moderate")		             // Premise 3
            
        // ACTION
        {
            SAPInformer_uni.pm_pollution_alert_onSpot("Moderate");          // Instigation 1
        }

        // Rule 52
        // CONDITION
        if ( 
            SAPInformer_uni.is_on           == true                 &&       // Premise 1
            PLO_uni.pm25_status             == "Poor"               &&       // Premise 2
            PLO_uni.pm10_status 	        == "Poor") 		                 // Premise 3
            
        // ACTION
        {
            SAPInformer_uni.pm_pollution_alert_onSpot("Poor");              // Instigation 1
        }

        // Rule 53
        // CONDITION
        if ( 
            SAPInformer_uni.is_on           == true                 &&       // Premise 1
            PLO_uni.pm25_status             == "Very Poor"          &&       // Premise 2
            PLO_uni.pm10_status 	        == "Very Poor")		             // Premise 3
            
        // ACTION
        {
            SAPInformer_uni.pm_pollution_alert_onSpot("Very Poor");          // Instigation 1
        }

        // Rule 54
        // CONDITION
        if ( 
            SAPInformer_uni.is_on           == true                 &&       // Premise 1
            PLO_uni.pm25_status             == "Severe"             &&       // Premise 2
            PLO_uni.pm10_status 	        == "Severe")		             // Premise 3
            
        // ACTION
        {
            SAPInformer_uni.pm_pollution_alert_onSpot("Severe");            // Instigation 1
        }

        // Rule 55
        // CONDITION
        if ( 
            SAPInformer_road.is_on           == true                 &&       // Premise 1
            PLO_road.pm25_status             == "Excellent"          &&       // Premise 2
            PLO_road.pm10_status 	         == "Excellent")		          // Premise 3
            
        // ACTION
        {
            SAPInformer_road.pm_pollution_alert_onSpot("Excellent");          // Instigation 1
        }

        // Rule 56
        // CONDITION
        if ( 
            SAPInformer_road.is_on           == true             &&       // Premise 1
            PLO_road.pm25_status             == "Fine"           &&       // Premise 2
            PLO_road.pm10_status 	         == "Fine")		              // Premise 3
            
        // ACTION
        {
            SAPInformer_road.pm_pollution_alert_onSpot("Fine");          // Instigation 1
        }

        // Rule 57
        // CONDITION
        if ( 
            SAPInformer_road.is_on           == true                 &&       // Premise 1
            PLO_road.pm25_status             == "Moderate"           &&       // Premise 2
            PLO_road.pm10_status 	         == "Moderate")		              // Premise 3
            
        // ACTION
        {
            SAPInformer_road.pm_pollution_alert_onSpot("Moderate");          // Instigation 1
        }

        // Rule 58
        // CONDITION
        if ( 
            SAPInformer_road.is_on           == true                 &&       // Premise 1
            PLO_road.pm25_status             == "Poor"               &&       // Premise 2
            PLO_road.pm10_status 	         == "Poor")		                  // Premise 3
            
        // ACTION
        {
            SAPInformer_road.pm_pollution_alert_onSpot("Poor");              // Instigation 1
        }

        // Rule 59
        // CONDITION
        if ( 
            SAPInformer_road.is_on           == true                 &&       // Premise 1
            PLO_road.pm25_status             == "Very Poor"          &&       // Premise 2
            PLO_road.pm10_status 	         == "Very Poor")		          // Premise 3
            
        // ACTION
        {
            SAPInformer_road.pm_pollution_alert_onSpot("Very Poor");          // Instigation 1
        }

        // Rule 60
        // CONDITION
        if ( 
            SAPInformer_road.is_on           == true                 &&       // Premise 1
            PLO_road.pm25_status             == "Severe"             &&       // Premise 2
            PLO_road.pm10_status 	         == "Severe")		              // Premise 3
            
        // ACTION
        {
            SAPInformer_road.pm_pollution_alert_onSpot("Severe");            // Instigation 1
        }

        // Rule 61
        // CONDITION
        if ( 
            SAPInformer_center.is_on           == true                 &&       // Premise 1
            PLO_center.pm25_status             == "Excellent"          &&       // Premise 2
            PLO_center.pm10_status 	           == "Excellent") 		            // Premise 3
            
        // ACTION
        {
            SAPInformer_center.pm_pollution_alert_onSpot("Excellent");          // Instigation 1
        }

        // Rule 62
        // CONDITION
        if ( 
            SAPInformer_center.is_on           == true             &&       // Premise 1
            PLO_center.pm25_status             == "Fine"           &&       // Premise 2
            PLO_center.pm10_status 	           == "Fine")		            // Premise 3
            
        // ACTION
        {
            SAPInformer_center.pm_pollution_alert_onSpot("Fine");          // Instigation 1
        }

        // Rule 63
        // CONDITION
        if ( 
            SAPInformer_center.is_on           == true                 &&       // Premise 1
            PLO_center.pm25_status             == "Moderate"           &&       // Premise 2
            PLO_center.pm10_status 	           == "Moderate")		            // Premise 3
            
        // ACTION
        {
            SAPInformer_center.pm_pollution_alert_onSpot("Moderate");          // Instigation 1
        }

        // Rule 64
        // CONDITION
        if ( 
            SAPInformer_center.is_on           == true                 &&       // Premise 1
            PLO_center.pm25_status             == "Poor"               &&       // Premise 2
            PLO_center.pm10_status 	           == "Poor")		                // Premise 3
            
        // ACTION
        {
            SAPInformer_center.pm_pollution_alert_onSpot("Poor");              // Instigation 1
        }

        // Rule 65
        // CONDITION
        if ( 
            SAPInformer_center.is_on           == true                 &&       // Premise 1
            PLO_center.pm25_status             == "Very Poor"          &&       // Premise 2
            PLO_center.pm10_status 	           == "Very Poor")		            // Premise 3
            
        // ACTION
        {
            SAPInformer_center.pm_pollution_alert_onSpot("Very Poor");          // Instigation 1
        }

        // Rule 66
        // CONDITION
        if ( 
            SAPInformer_center.is_on           == true                 &&       // Premise 1
            PLO_center.pm25_status             == "Severe"             &&       // Premise 2
            PLO_center.pm10_status 	           == "Severe")		                // Premise 3
            
        // ACTION
        {
            SAPInformer_center.pm_pollution_alert_onSpot("Severe");            // Instigation 1
        }
        double end = System.currentTimeMillis();
        elapsed_time += (end - start);
      }
    System.out.println("Total Elapsed Time: " + elapsed_time + " milli seconds");
    br_u.close();
    br_r.close();
    br_c.close();
    MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
    //long maxMB = heapMemoryUsage.getMax()/(1024*1024);
    double usedMB = heapMemoryUsage.getUsed()/(1024.0*1024.0);
    System.out.println("Total Memory Used: " + usedMB + " MB");
    }
    catch(IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
