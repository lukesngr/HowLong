#OPT = --module-path /usr/lib/jvm/java-15-openjdk/lib/javafx-modules --add-modules javafx.controls,javafx.fxml
#FILES = HowLongStart.java HowLongError.java HowLongTokenizer.java HowLongController.java HowLong.java HowLongSetDateController.java
compile:
	rm maven/src/main/java/*.java
	rm maven/src/main/resources/*.fxml
	#for FILE in *.java; do sed -e '1i\ package HowLong; ' <$FILE >> maven/src/main/java/$FILE; done
	#sed -i 's@HowLong.fxml@/HowLong.fxml@g' maven/src/main/java/HowLong.java
	cp *.fxml maven/src/main/resources
	cp *.java maven/src/main/java
	cd maven
	mvn clean
	mvn package
run-deployment:
	java -jar maven/target/HowLong-jar-with-dependencies.jar

