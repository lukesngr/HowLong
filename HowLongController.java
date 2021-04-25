 package HowLong; 
/*
Copyright (c) 2021, Luke Sanger
All rights reserved.

This source code is licensed under the BSD-style license found in the
LICENSE file in the root directory of this source tree.

*/

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.TilePane;
import javafx.scene.control.DatePicker;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.Files;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.IOException;
import java.util.Scanner;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.WindowEvent;
import java.lang.IllegalArgumentException;
import java.io.IOException;

public class HowLongController {

    @FXML
    private MenuItem setDate;

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

	
	private LocalDateTime DEATH_DATE;
	private HowLongTokenizer tokenizer;
	private String styleSheet = "default";
	private DateTimeFormatter formatter;
    

	private void setTimeLeft() {

		LocalDateTime currentDate = LocalDateTime.now();

		int yearsLeft = DEATH_DATE.getYear() - currentDate.getYear();		
		int monthsLeft = (yearsLeft*12)+(DEATH_DATE.getMonthValue()-currentDate.getMonthValue());
		int daysLeft = (yearsLeft*365)+(DEATH_DATE.getDayOfYear()-currentDate.getDayOfYear());
		int hoursLeft = daysLeft*24;

		hoursField.setText(Integer.toString(hoursLeft));
		daysField.setText(Integer.toString(daysLeft));
		monthsField.setText(Integer.toString(monthsLeft));
		yearsField.setText(Integer.toString(yearsLeft));

		String labelName = "Your death date is " + DEATH_DATE.format(formatter);
		deathDate.setText(labelName);
		
	}

	private void writeToFile() {
		String deathDate = DEATH_DATE.format(formatter);
		tokenizer.writeToFile(deathDate, styleSheet);
	}

	private void loadConfig() {
		
		String[] tokens = tokenizer.tokenizeDataFromConfig();
		
		LocalDateTime deathDate = LocalDateTime.parse(tokens[0], formatter);
	    DEATH_DATE = deathDate;

	    styleSheet = tokens[1];
	}

	public void enableStyleSheets(Scene currentScene) {
		
		if(!(styleSheet.equals("default"))) {

			File styleSheetFile = new File(styleSheet);
			currentScene.getStylesheets().clear();
			currentScene.getStylesheets().add("file://"+styleSheetFile.getAbsolutePath().replace("\\", "/"));
		}
	}

	public void initialize() {

		tokenizer = new HowLongTokenizer();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");
	    loadConfig();
		setTimeLeft();

		
		

		EventHandler<ActionEvent> setDeathDate = new EventHandler<ActionEvent>() {
				
			public void handle(ActionEvent e) {

				try {


					FXMLLoader loader  = new FXMLLoader(getClass().getResource("/HowLongSetDate.fxml"));
					Parent root = loader.load();
				
					LocalDate selectedDeathDate = LocalDate.of(1999, 1, 1);
					LocalTime selectedDeathTime = LocalTime.of(10,43,12);

					HowLongSetDateController setDateController = loader.getController();

					Stage stage = new Stage();

					setDateController.setEvents(stage);
					Scene scene = new Scene(root);
					Modality modal = Modality.APPLICATION_MODAL;
					stage.initModality(modal);
					stage.setScene(scene);
					stage.setTitle("Pick Death Date");
					stage.showAndWait();

					LocalDateTime currentDate = LocalDateTime.now();

					if(setDateController.selectedDeathDate == null | setDateController.selectedDeathTime == null) {
						
						HowLongError.ErrorBox("You have to set the value");
						DEATH_DATE = LocalDateTime.parse(tokenizer.defaultDate, formatter);
						
					}else{
						
						LocalDateTime newDeathDate = LocalDateTime.of(setDateController.selectedDeathDate, setDateController.selectedDeathTime);

						if(newDeathDate.equals(null)) {
							HowLongError.ErrorBox("You have to set the value");
							DEATH_DATE = LocalDateTime.parse(tokenizer.defaultDate, formatter);

						}else{
							DEATH_DATE = newDeathDate;
						}

						if(DEATH_DATE.isBefore(currentDate) | DEATH_DATE.equals(currentDate)) {
							HowLongError.ErrorBox("Incorrect death date set");
							DEATH_DATE = LocalDateTime.parse(tokenizer.defaultDate, formatter);
						}
					}
					
					
								
					setTimeLeft();
					writeToFile();
				
				}catch(IOException except) {
					except.printStackTrace();
				}
			
			}

		};

		setDate.setOnAction(setDeathDate);
	}
}
