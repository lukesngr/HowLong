 package HowLong; 
/*
Copyright (c) 2021, Luke Sanger
All rights reserved.

This source code is licensed under the BSD-style license found in the
LICENSE file in the root directory of this source tree.

*/

import java.nio.file.Files;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Scanner;


public class HowLongTokenizer {
	
	private String config = "howlongrc";
	public String defaultStyle = "default";
	public String defaultDate = "2075-12-30-12-30";

	private String tokenize(String[] tokens, String[] syntaxTokens) throws RuntimeException {
		boolean syntaxCorrect = true;
		boolean awaitingCustomString = false;
		
		int i;

		if((tokens.length < (syntaxTokens.length+1)) | (tokens.equals(null))) {
			throw new RuntimeException("The config file is incorrectly formatted");
		}
	
		for(i = 0; i < syntaxTokens.length; i++){
			
		    if((tokens[i]).equals(syntaxTokens[syntaxTokens.length-1])) {
					awaitingCustomString = true;
			}else if(!((tokens[i]).equals(syntaxTokens[i]))){
					syntaxCorrect = false;	
			}
		    
		}

		String userConfigString = new String();

		if(awaitingCustomString) {
			userConfigString = tokens[i];
		}

		

		if(!syntaxCorrect) {
			throw new RuntimeException("The config file is incorrectly formatted");
		}

		return userConfigString;
	}

	private String tokenizeStyleTokens(String[] styleTokens){
		String[] syntax = {"stylesheet", "="};
		String styleSheet = new String();

		try {
		
			styleSheet = tokenize(styleTokens, syntax);
			
		}catch(RuntimeException e) {
			HowLongError.ErrorBox(e.getMessage());
			styleSheet = defaultStyle;
		}

		return styleSheet;
	}
	
	private String tokenizeDateTokens(String[] dateTokens){
		String[] syntax = {"date", "="};
		String date = new String();

		try {
			date = tokenize(dateTokens, syntax);
		}catch(RuntimeException e) {
			HowLongError.ErrorBox(e.getMessage());
			date = defaultDate;
		}

		return date;
		
	}

	public String[] tokenizeDataFromConfig(){

		Path path = Paths.get(config);
		String[] tokens = {defaultDate, defaultStyle};

		if(Files.exists(path)) {
			
			try(Scanner input = new Scanner(path)){

	   
				
			    String[] dateTokens = input.nextLine().split(" ");
				String[] styleTokens = input.nextLine().split(" ");

			    String dateString = tokenizeDateTokens(dateTokens);
				String styleString = tokenizeStyleTokens(styleTokens);
				
				tokens[0] = dateString;
				tokens[1] = styleString;
			    
			}catch(IOException e){
			    HowLongError.ErrorBox("An error has occured with the config file");
				
			}
			
		}else{
			
			HowLongError.ErrorBox("Sorry the config file does not exist, please select a date!");
	    }
		
		return tokens;		
	   
	}

	public void writeToFile(String deathDate, String styleSheet){
		
		Path path = Paths.get(this.config);
		try {
			FileWriter deathConfig = new FileWriter(path.toFile());
			String configLine = String.format("date = %s%nstylesheet = %s", deathDate, styleSheet);
			deathConfig.write(configLine);
			deathConfig.close();
			
		}catch(IOException e) {
		    HowLongError.ErrorBox("Issue writing to config file");
		}
	}
}
