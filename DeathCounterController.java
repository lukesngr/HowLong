import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DeathCounterController {

	private LocalDateTime DEATH_DATE;
	private LocalDateTime currentDate;

    @FXML
    private Label deathDate;

    @FXML
    private TextField hoursField;

    @FXML
    private TextField daysField;

    @FXML
    private TextField monthsField;

    @FXML
    private TextField yearsField;

	public void initialize() {
		
		this.currentDate = LocalDateTime.now();
		this.DEATH_DATE = LocalDateTime.of(2078, 2, 27, 02, 00, 00);
		int yearsLeft = this.DEATH_DATE.getYear() - this.currentDate.getYear();
		int hoursLeft = this.DEATH_DATE.getHour() - this.currentDate.getHour();
		int daysLeft = yearsLeft * 365 + (this.DEATH_DATE.getDayOfYear() - this.currentDate.getDayOfYear());
		int monthsLeft = yearsLeft*12 + (this.DEATH_DATE.getMonthValue() - this.currentDate.getMonthValue());

		hoursField.setText(Integer.toString(hoursLeft));
		daysField.setText(Integer.toString(daysLeft));
		monthsField.setText(Integer.toString(monthsLeft));
		yearsField.setText(Integer.toString(yearsLeft));

		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		String labelName = "Your death date is " + this.DEATH_DATE.format(myFormat);
		deathDate.setText(labelName);
	}
}
