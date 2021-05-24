package keray.ui;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

//class representing UI for the stats window
public class StatsUI {

    public VBox getStatsUI() {

        //creating new layout for the stats window
        VBox layout = new VBox();
        layout.setPrefSize(360, 610);
        layout.setPadding(new Insets(25, 25, 25, 25));


        return layout;
    }

}