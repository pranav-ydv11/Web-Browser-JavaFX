package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class WebController implements Initializable{
	
	
	@FXML
	private WebView myWebView;
	
//	@FXML
//	private Label mylabel;
	
	@FXML
	private TextField myTextField;
	
	@FXML
	private Button mySearch, refresh;
	
	@FXML
	private WebEngine engine;
	
	private String homepage;
	private double Zoomm;
	private WebHistory history;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		engine =  myWebView.getEngine();
//		
//		 myWebView.setPrefWidth(800);
//	     myWebView.setPrefHeight(600);
		 double preferredWidth = 0.8 * Screen.getPrimary().getVisualBounds().getWidth();
	     myWebView.setPrefWidth(preferredWidth);

	     configureLayout();
		
		homepage="www.google.com";
		Zoomm=0;
		
		myTextField.setText(homepage);
		 Platform.runLater(() -> {
		        Searching();
		    });
		//Searching();
	}
	
	 @FXML
	    public void openNewWindow() {
	        Stage newStage = new Stage();

	        // Load a new FXML file for the new window content (you may need a separate FXML file)
	        // FXMLLoader loader = new FXMLLoader(getClass().getResource("newWindow.fxml"));
	        // Parent root = loader.load();
	        WebView newWebView = new WebView();
	        WebEngine newEngine = newWebView.getEngine();

	        newStage.setScene(new Scene(newWebView));
	        newStage.show();

	        newEngine.load("https://www.youtube.com");
	    }
	
	
	
	private void configureLayout() {
	    if (myWebView.getScene() != null) {
	        // Allow WebView to grow or shrink with the window
	        myWebView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
	        myWebView.prefWidthProperty().bind(myWebView.getScene().widthProperty());
	        myWebView.prefHeightProperty().bind(myWebView.getScene().heightProperty());
	    } else {
	        // Log or handle the case where the scene is null
	        System.err.println("Scene is null in configureLayout");
	    }
	}
	
	
	
	public void Searching() {
		//engine.load("https://www.google.com");
		engine.load("https:"+myTextField.getText());

	}
	
	public void Refresh() {
		engine.reload();
	}
	
	public void zoomIn() {
		Zoomm+=1.25;
		myWebView.setZoom(Zoomm);
		
	}
	public void zoomOut() {
		Zoomm-=1.25;
		myWebView.setZoom(Zoomm);
		
	}
	public void displayHistory() {
		history=engine.getHistory();
		
		ObservableList<WebHistory.Entry> entries=history.getEntries();
		
		for(WebHistory.Entry entry : entries) {
			System.out.println(entry);
			System.out.println(entry.getUrl()+" "+entry.getLastVisitedDate());
		}
	}
	public void forward() {
		history=engine.getHistory();
		ObservableList<WebHistory.Entry> entries=history.getEntries();
		history.go(1);
		myTextField.setText(entries.get(history.getCurrentIndex()).getUrl());
	}
	public void backward() {
		history=engine.getHistory();
		ObservableList<WebHistory.Entry> entries=history.getEntries();
		history.go(-1);
		myTextField.setText(entries.get(history.getCurrentIndex()).getUrl());


	}
	

}
