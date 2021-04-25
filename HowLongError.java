 package HowLong; 
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class HowLongError {

	public static void ErrorBox(String message) {
		Stage errorBoxStage = new Stage();
		errorBoxStage.setTitle("Error");
		Label label = new Label(message);
		Scene scene = new Scene(label, 200, 200);
		errorBoxStage.setScene(scene);
		errorBoxStage.show();
	}
}
