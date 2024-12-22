package com.codelab.tugasbesaruap;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

// Bismillah UAP lancar awal hingga akhir

public class GUI extends Application {

    private final List<String[]> tableData = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        // Root container
        Pane root = new Pane();

        // Gradasi background
        Rectangle gradientBackground = new Rectangle(800, 600);
        LinearGradient gradient = new LinearGradient(
                0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#4286f4")),
                new Stop(1, Color.web("#373B44"))
        );
        gradientBackground.setFill(gradient);

        // Tambahkan background gradasi ke root
        root.getChildren().add(gradientBackground);

        // Bagian atas
        Label lblWelcome = new Label("Welcome!");
        lblWelcome.setLayoutX(20);
        lblWelcome.setLayoutY(10);
        lblWelcome.setStyle("-fx-text-fill: white;");

        TextField searchField = new TextField();
        searchField.setPromptText("Search List");
        searchField.setLayoutX(500);
        searchField.setLayoutY(10);
        searchField.setStyle("-fx-text-fill: white; -fx-prompt-text-fill: darkgray;");

        Button searchButton = new Button("Search");
        searchButton.setLayoutX(710);
        searchButton.setLayoutY(10);

        // Panel kiri "Create New List"
        Label lblCreateNewList = new Label("Create New List");
        lblCreateNewList.setLayoutX(20);
        lblCreateNewList.setLayoutY(50);
        lblCreateNewList.setStyle("-fx-text-fill: white;");

        TextField txtListTitle = new TextField();
        txtListTitle.setPromptText("List Title");
        txtListTitle.setLayoutX(20);
        txtListTitle.setLayoutY(80);

        TextField txtDescription = new TextField();
        txtDescription.setPromptText("Description");
        txtDescription.setLayoutX(20);
        txtDescription.setLayoutY(110);

        TextField txtDate = new TextField();
        txtDate.setPromptText("Date (dd-MM-yyyy)");
        txtDate.setLayoutX(20);
        txtDate.setLayoutY(140);

        Button saveButton = new Button("Save");
        saveButton.setLayoutX(20);
        saveButton.setLayoutY(170);

        Label lblListSaved = new Label("");
        lblListSaved.setLayoutX(20);
        lblListSaved.setLayoutY(210);
        lblListSaved.setStyle("-fx-text-fill: white;");

        // Event saat tombol "Save" diklik
        saveButton.setOnAction(event -> {
            String listTitle = txtListTitle.getText();
            String description = txtDescription.getText();
            String date = txtDate.getText();

            if (!listTitle.isEmpty() && !description.isEmpty() && !date.isEmpty()) {
                if (isValidDate(date)) {
                    tableData.add(new String[]{listTitle, description, date});
                    lblListSaved.setText("List saved successfully!");

                    // Reset input field
                    txtListTitle.clear();
                    txtDescription.clear();
                    txtDate.clear();
                } else {
                    lblListSaved.setText("Invalid date format! Use dd-MM-yyyy.");
                }
            } else {
                lblListSaved.setText("Please fill all fields!");
            }
        });

        Button allListsButton = new Button("All Lists");
        allListsButton.setLayoutX(20);
        allListsButton.setLayoutY(240);

        Button todaysListsButton = new Button("Today's Lists");
        todaysListsButton.setLayoutX(20);
        todaysListsButton.setLayoutY(270);

        Button yesterdaysListsButton = new Button("Yesterday's Lists");
        yesterdaysListsButton.setLayoutX(20);
        yesterdaysListsButton.setLayoutY(300);

        Button logOutButton = new Button("LogOut");
        logOutButton.setLayoutX(20);
        logOutButton.setLayoutY(340);

        // Panel kanan "Recent To Do Lists"
        Label lblRecentToDo = new Label("Recent To Do Lists:");
        lblRecentToDo.setLayoutX(250);
        lblRecentToDo.setLayoutY(50);
        lblRecentToDo.setStyle("-fx-text-fill: white;");

        TableView<String[]> table = getTable();
        table.setLayoutX(250);
        table.setLayoutY(80);

        // Tambahkan semua elemen ke root
        root.getChildren().addAll(
                lblWelcome, searchField, searchButton,
                lblCreateNewList, txtListTitle, txtDescription, txtDate,
                saveButton, lblListSaved, allListsButton, todaysListsButton, yesterdaysListsButton,
                logOutButton, lblRecentToDo, table
        );

        // Scene dan Stage
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("TaskFlow - the list that works for you");
        stage.setScene(scene);
        stage.show();
    }

    private TableView<String[]> getTable() {
        TableView<String[]> table = new TableView<>();

        TableColumn<String[], String> colTitle = new TableColumn<>("List Title");
        colTitle.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[0]));

        TableColumn<String[], String> colDesc = new TableColumn<>("Description");
        colDesc.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[1]));

        TableColumn<String[], String> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[2]));

        table.getColumns().addAll(colTitle, colDesc, colDate);
        table.setPrefSize(540, 300);

        return table;
    }

    private boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
