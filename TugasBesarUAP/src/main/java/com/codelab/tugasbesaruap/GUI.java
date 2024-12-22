package com.codelab.tugasbesaruap;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class GUI extends Application {
    private final ObservableList<String[]> taskList = FXCollections.observableArrayList();
    private Image taskImage;
    private String currentImagePath;

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

        // Add gradient background to root
        root.getChildren().add(gradientBackground);

        // Top section
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

        // Left panel "Create New List"
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

        // Choose Image Button
        Button chooseImageButton = new Button("Choose Image");
        chooseImageButton.setLayoutX(20);
        chooseImageButton.setLayoutY(170);

        // Delete Image Button
        Button deleteImageButton = new Button("Delete Image");
        deleteImageButton.setLayoutX(120);
        deleteImageButton.setLayoutY(170);

        Button saveButton = new Button("Save");
        saveButton.setLayoutX(20);
        saveButton.setLayoutY(210);

        Label lblListSaved = new Label();
        lblListSaved.setLayoutX(20);
        lblListSaved.setLayoutY(250);
        lblListSaved.setStyle("-fx-text-fill: white;");

        Button allListsButton = new Button("All Lists");
        allListsButton.setLayoutX(20);
        allListsButton.setLayoutY(280);

        Button todaysListsButton = new Button("Today's Lists");
        todaysListsButton.setLayoutX(20);
        todaysListsButton.setLayoutY(310);

        Button yesterdaysListsButton = new Button("Yesterday's Lists");
        yesterdaysListsButton.setLayoutX(20);
        yesterdaysListsButton.setLayoutY(340);

        Button logOutButton = new Button("LogOut");
        logOutButton.setLayoutX(20);
        logOutButton.setLayoutY(370);

        // Right panel "Recent To Do Lists"
        Label lblRecentToDo = new Label("Recent To Do Lists:");
        lblRecentToDo.setLayoutX(250);
        lblRecentToDo.setLayoutY(50);
        lblRecentToDo.setStyle("-fx-text-fill: white;");

        TableView<String[]> table = getTable();
        table.setLayoutX(250);
        table.setLayoutY(80);
        table.setItems(taskList);

        // Add functionality to choose an image
        chooseImageButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                taskImage = new Image(selectedFile.toURI().toString());
                currentImagePath = selectedFile.toURI().toString();
            }
        });

        // Add functionality to delete the image
        deleteImageButton.setOnAction(e -> {
            taskImage = null;
            currentImagePath = null;
            lblListSaved.setText("Image has been removed!");
            table.refresh();  // Refresh table to update "View Image" button visibility
        });

        // Add functionality to save or update task
        saveButton.setOnAction(e -> {
            String title = txtListTitle.getText();
            String description = txtDescription.getText();
            String date = txtDate.getText();

            if (!title.isEmpty() && !description.isEmpty() && !date.isEmpty()) {
                if (isValidDate(date)) {
                    if (saveButton.getText().equals("Update")) {
                        int selectedIndex = table.getSelectionModel().getSelectedIndex();
                        if (selectedIndex >= 0) {
                            String[] selectedTask = taskList.get(selectedIndex);
                            selectedTask[0] = title;
                            selectedTask[1] = description;
                            selectedTask[2] = date;
                            selectedTask[3] = currentImagePath;
                            table.refresh();
                            lblListSaved.setText("List has been updated!");
                        }
                        saveButton.setText("Save");
                    } else {
                        taskList.add(new String[]{title, description, date, currentImagePath});
                        lblListSaved.setText("List has been saved!");
                    }

                    // Clear input fields
                    txtListTitle.clear();
                    txtDescription.clear();
                    txtDate.clear();
                    currentImagePath = null;
                    taskImage = null;
                } else {
                    lblListSaved.setText("Invalid date format! Please use dd-MM-yyyy.");
                }
            } else {
                lblListSaved.setText("Please fill out all fields!");
            }
        });

        // Handle row double-click to edit
        table.setRowFactory(tv -> {
            TableRow<String[]> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    String[] rowData = row.getItem();
                    txtListTitle.setText(rowData[0]);
                    txtDescription.setText(rowData[1]);
                    txtDate.setText(rowData[2]);
                    currentImagePath = rowData[3];
                    saveButton.setText("Update");
                }
            });
            return row;
        });

        // Add all elements to the root container
        root.getChildren().addAll(
                lblWelcome, searchField, searchButton,
                lblCreateNewList, txtListTitle, txtDescription, txtDate,
                chooseImageButton, deleteImageButton, saveButton, lblListSaved, allListsButton, todaysListsButton, yesterdaysListsButton,
                logOutButton, lblRecentToDo, table
        );

        // Create Scene and Stage
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

        TableColumn<String[], String> colImage = new TableColumn<>("See Attachment");
        colImage.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    Button seeAttachmentButton = new Button("View Image");

                    // Hide View Image button if no image exists
                    String[] task = getTableView().getItems().get(getIndex());
                    if (task[3] == null || task[3].isEmpty()) {
                        seeAttachmentButton.setVisible(false);
                    }

                    seeAttachmentButton.setOnAction(e -> {
                        String[] taskData = getTableView().getItems().get(getIndex());
                        String imageUrl = taskData[3];
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            ImageView imageView = new ImageView(new Image(imageUrl));
                            imageView.setFitWidth(200);
                            imageView.setFitHeight(200);
                            Stage imageStage = new Stage();
                            imageStage.setTitle("Task Image");
                            imageStage.setScene(new Scene(new Pane(imageView), 250, 250));
                            imageStage.show();
                        }
                    });

                    setGraphic(seeAttachmentButton);
                } else {
                    setGraphic(null);
                }
            }
        });

        table.getColumns().addAll(colTitle, colDesc, colDate, colImage);

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
