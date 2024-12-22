package com.codelab.tugasbesaruap;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class GUI extends Application {
    private final ObservableList<String[]> taskList = FXCollections.observableArrayList();

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

        Label lblListSaved = new Label();
        lblListSaved.setLayoutX(20);
        lblListSaved.setLayoutY(210);
        lblListSaved.setStyle("-fx-text-fill: white;");

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
        table.setItems(taskList);

        // Tambahkan event handler untuk tombol Save
        saveButton.setOnAction(e -> {
            String title = txtListTitle.getText();
            String description = txtDescription.getText();
            String date = txtDate.getText();

            if (!title.isEmpty() && !description.isEmpty() && !date.isEmpty()) {
                if (isValidDate(date)) {
                    taskList.add(new String[]{title, description, date});
                    lblListSaved.setText("List has been saved!");

                    // Clear input fields
                    txtListTitle.clear();
                    txtDescription.clear();
                    txtDate.clear();
                } else {
                    lblListSaved.setText("Invalid date format! Please use dd-MM-yyyy.");
                }
            } else {
                lblListSaved.setText("Please fill out all fields!");
            }
        });

        // Tambahkan event handler untuk mengedit item di tabel
        table.setRowFactory(tv -> {
            TableRow<String[]> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    String[] rowData = row.getItem();

                    txtListTitle.setText(rowData[0]);
                    txtDescription.setText(rowData[1]);
                    txtDate.setText(rowData[2]);

                    saveButton.setText("Update");

                    saveButton.setOnAction(updateEvent -> {
                        String updatedTitle = txtListTitle.getText();
                        String updatedDescription = txtDescription.getText();
                        String updatedDate = txtDate.getText();

                        if (!updatedTitle.isEmpty() && !updatedDescription.isEmpty() && !updatedDate.isEmpty()) {
                            if (isValidDate(updatedDate)) {
                                rowData[0] = updatedTitle;
                                rowData[1] = updatedDescription;
                                rowData[2] = updatedDate;
                                table.refresh();

                                lblListSaved.setText("List has been updated!");

                                // Clear input fields and reset button text
                                txtListTitle.clear();
                                txtDescription.clear();
                                txtDate.clear();
                                saveButton.setText("Save");
                                saveButton.setOnAction(saveEvent -> {
                                    // Handle new save
                                    String title = txtListTitle.getText();
                                    String description = txtDescription.getText();
                                    String date = txtDate.getText();

                                    if (!title.isEmpty() && !description.isEmpty() && !date.isEmpty()) {
                                        if (isValidDate(date)) {
                                            taskList.add(new String[]{title, description, date});
                                            lblListSaved.setText("List has been saved!");

                                            // Clear input fields
                                            txtListTitle.clear();
                                            txtDescription.clear();
                                            txtDate.clear();
                                        } else {
                                            lblListSaved.setText("Invalid date format! Please use dd-MM-yyyy.");
                                        }
                                    } else {
                                        lblListSaved.setText("Please fill out all fields!");
                                    }
                                });
                            } else {
                                lblListSaved.setText("Invalid date format! Please use dd-MM-yyyy.");
                            }
                        } else {
                            lblListSaved.setText("Please fill out all fields!");
                        }
                    });
                }
            });
            return row;
        });

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
        table.setPrefSize(540, 300);

        TableColumn<String[], String> colTitle = new TableColumn<>("List Title");
        colTitle.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[0]));

        TableColumn<String[], String> colDesc = new TableColumn<>("Description");
        colDesc.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[1]));

        TableColumn<String[], String> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[2]));

        table.getColumns().addAll(colTitle, colDesc, colDate);

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
