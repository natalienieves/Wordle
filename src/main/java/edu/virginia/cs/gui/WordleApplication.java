package edu.virginia.cs.gui;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.application.Application;
import java.io.IOException;

public class WordleApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WordleApplication.class.getResource("wordle-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 600);
        EventHandler<MouseEvent> handler = MouseEvent::consume;
        scene.addEventFilter(MouseEvent.ANY, handler);
        stage.setTitle("WORDLE");
        stage.setScene(scene);
        stage.setResizable(false);



        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
