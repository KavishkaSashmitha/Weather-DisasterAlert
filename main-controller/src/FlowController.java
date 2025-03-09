

import java.util.Scanner;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import com.weatherguard.producer.weatherstation.WeatherProducer;
import com.weatherguard.producer.disasteralert.DisasterAlertProducer;
import com.weatherguard.consumer.touroperator.TourOperatorConsumer;

public class FlowController implements BundleActivator {
    private ServiceReference<WeatherProducer> weatherRef;
    private WeatherProducer weatherProducer;
    
    private ServiceReference<DisasterAlertProducer> alertRef;
    private DisasterAlertProducer alertProducer;
    
    private ServiceReference<TourOperatorConsumer> tourRef;
    private TourOperatorConsumer tourConsumer;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Flow Controller Started ✅");

        // Get producer services
        weatherRef = context.getServiceReference(WeatherProducer.class);
        alertRef = context.getServiceReference(DisasterAlertProducer.class);
        
        if (weatherRef != null) weatherProducer = context.getService(weatherRef);
        if (alertRef != null) alertProducer = context.getService(alertRef);

        // Get consumer services
        tourRef = context.getServiceReference(TourOperatorConsumer.class);
        if (tourRef != null) tourConsumer = context.getService(tourRef);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1 - Get Weather Report");
            System.out.println("2 - Get Disaster Alerts");
            System.out.println("3 - Adjust Tour Schedule");
            System.out.println("0 - Exit");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            if (choice == 0) break;
            
            switch (choice) {
                case 1:
                    System.out.println(weatherProducer.getWeatherData());
                    break;
                case 2:
                    System.out.println(alertProducer.getLatestAlert("Colombo"));
                    break;
                case 3:
                    System.out.println("Adjusting tour schedule...");
                    tourConsumer.updateSchedule();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Flow Controller Stopped ❌");
    }
}
