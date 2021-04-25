 package HowLong; 
import javafx.fxml.FXML;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.Label;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.stage.WindowEvent;

public class HowLongSetDateController {

    @FXML
    private DatePicker deathDatePicker;

     @FXML
    private Slider timeSlider;

    @FXML
    private Label timeSliderLabel;


	public LocalDate selectedDeathDate;
	public LocalTime selectedDeathTime;

	public void setEvents(Stage stage) {
		
	    EventHandler<WindowEvent> grabDateFromPicker = new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent e) {

						selectedDeathDate = deathDatePicker.getValue();
								
		   		}
	   };
	   stage.setOnCloseRequest(grabDateFromPicker);
	}


	public void initialize() {
		
		 timeSlider.valueProperty().addListener(new ChangeListener<Number>() {
				 
				 public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
					

					String sliderIntValue = Integer.toString((int) timeSlider.getValue());
					
					if(sliderIntValue.length() == 1) {
						sliderIntValue = "000" + sliderIntValue;
					}

					if(sliderIntValue.length() == 3) {
						sliderIntValue = "0" + sliderIntValue;
						
					}else if(sliderIntValue.length() == 2) {
						sliderIntValue = "00" + sliderIntValue;
					}
					
					String minutes = sliderIntValue.substring(sliderIntValue.length() - 2, sliderIntValue.length());
					
					if(Integer.parseInt(minutes) >= 60) {
						String hours = sliderIntValue.substring(0, 2);
						sliderIntValue = Integer.toString((Integer.parseInt(hours) + 1) * 100);

						if(sliderIntValue.length() == 3) {
							sliderIntValue = "0" + sliderIntValue;
						}
						
					}

					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
					selectedDeathTime = LocalTime.parse(sliderIntValue, formatter);
					String displayValue = sliderIntValue.substring(0, 2) + ":" + sliderIntValue.substring(2, 4);
					timeSliderLabel.setText(displayValue);
                }
        });
			
	}

}
