 package HowLong; 
/*
Copyright (c) 2021, Luke Sanger
All rights reserved.

This source code is licensed under the BSD-style license found in the
LICENSE file in the root directory of this source tree.

*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HowLong extends Application {
    @Override
    public void start(Stage stage) throws Exception {
		FXMLLoader loader  = new FXMLLoader(getClass().getResource("/HowLong.fxml"));
		Parent root = loader.load();
		
		Scene scene = new Scene(root);
		HowLongController controller = loader.getController();
		controller.enableStyleSheets(scene);
		stage.setTitle("How Long Could I Possibly Have Left?");
		stage.setScene(scene);
		stage.show();

		
    }

    public static void main(String[] args) {
		launch(args);
    }

}
