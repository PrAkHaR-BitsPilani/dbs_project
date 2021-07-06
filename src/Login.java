import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

import Backend.*;

public class Login extends Application
{
	private double xoffset = 0.0;
	private double yoffset = 0.0;
	
    public static void main(String args[])
    {
        SQL obj = new SQL();        
        if(!obj.checkDriver())
            System.exit(0);

        if(!obj.createConnection())
            System.exit(0);

        EnvironmentCreator.createDatabase(obj, "library");
        EnvironmentCreator.useDatabase(obj, "library");
        EnvironmentCreator.createEnvironment(obj);
        obj.disconnect();
        
        launch(args);
    	
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
		Scene scene = new Scene(root);
		
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xoffset = event.getSceneX();
				yoffset = event.getSceneY();
			}
		});
		
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				primaryStage.setX(event.getScreenX() - xoffset);
				primaryStage.setY(event.getScreenY() - yoffset);
			}
		});		
		scene.getStylesheets().add(getClass().getResource("/CSS/Admin.css").toExternalForm());
		
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}