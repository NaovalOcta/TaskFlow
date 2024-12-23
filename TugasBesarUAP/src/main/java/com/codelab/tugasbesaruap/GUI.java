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

// Bismillah UAP lancar

public class GUI extends Application {
    private final ObservableList<String[]> taskList = FXCollections.observableArrayList();
    private Image taskImage;
    private String currentImagePath;

    @Override
    public void start(Stage stage) {
        // Deklarasi untuk panel awal root
        Pane root = new Pane();

        // Gradasi background
        Rectangle gradientBackground = new Rectangle(800, 600);
        LinearGradient gradient = new LinearGradient(
                0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#4286f4")),
                new Stop(1, Color.web("#373B44"))
        );
        gradientBackground.setFill(gradient);

        // Menambahkan gradient di root
        root.getChildren().add(gradientBackground);

        Label lblWelcome = new Label("Welcome!");
        lblWelcome.setLayoutX(20);
        lblWelcome.setLayoutY(10);
        lblWelcome.setStyle("-fx-text-fill: white;");

        TextField searchField = new TextField();
        searchField.setPromptText("Search List");
        searchField.setLayoutX(550);
        searchField.setLayoutY(10);
        searchField.setStyle("-fx-text-fill: black; -fx-prompt-text-fill: darkgray;");

        Button searchButton = new Button("Search");
        searchButton.setLayoutX(710);
        searchButton.setLayoutY(10);

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

        Button chooseImageButton = new Button("Choose Image");
        chooseImageButton.setLayoutX(20);
        chooseImageButton.setLayoutY(170);

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

        Button logOutButton = new Button("LogOut");
        logOutButton.setLayoutX(20);
        logOutButton.setLayoutY(350);

        Label lblRecentToDo = new Label("Recent To Do Lists:");
        lblRecentToDo.setLayoutX(250);
        lblRecentToDo.setLayoutY(50);
        lblRecentToDo.setStyle("-fx-text-fill: white;");

        TableView<String[]> table = getTable();
        table.setLayoutX(250);
        table.setLayoutY(80);
        table.setItems(taskList);

        // Menambahkan method untuk Choose Image
        chooseImageButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                taskImage = new Image(selectedFile.toURI().toString());
                currentImagePath = selectedFile.toURI().toString();

                information_notif("Gambar berhasil dipilih");
            }
        });

        // Menambahkan method untuk Delete Image
        deleteImageButton.setOnAction(e -> {
            taskImage = null;
            currentImagePath = null;
            information_notif("Gambar telah berhasil dihapus!");
            table.refresh();  // Refresh table untuk update tombol "View Image"
        });

        // Menambahkan method untuk Save or Update Task
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
                            information_notif("List telah diperbarui!");
                        }
                        saveButton.setText("Save");
                    } else {
                        taskList.add(new String[]{title, description, date, currentImagePath});
                        information_notif("List telah berhasil disimpan!");
                    }

                    // Mengosongkan Input Fields
                    txtListTitle.clear();
                    txtDescription.clear();
                    txtDate.clear();
                    currentImagePath = null;
                    taskImage = null;
                } else {
                    warning_notif("Format tanggal tidak valid! Silahkan gunakan dd-MM-yyyy");
                }
            } else {
                warning_notif("Kolom tidak boleh kosong, silahkan isi!");
            }
        });

        // Method untuk Handle tabel dan fungsi double-click untuk mengedit
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

        // Event handling untuk tombol Search
        searchButton.setOnAction(e -> {
            String searchQuery = searchField.getText().toLowerCase();
            if (searchQuery.isEmpty()) {
                lblListSaved.setText("Search query is empty!");
                return;
            }

            boolean found = false;
            for (String[] task : taskList) {
                if (task[0].toLowerCase().contains(searchQuery) || task[1].toLowerCase().contains(searchQuery)) {
                    lblListSaved.setText("Task found: " + task[0]);
                    found = true;
                    break;
                }
            }

            if (!found) {
                lblListSaved.setText("No task matches the search query.");
            }
        });

        // Event handling untuk tombol Logout
        logOutButton.setOnAction(e -> stage.close());

        // Menambahkan semua elemen ke root
        root.getChildren().addAll(
                lblWelcome, searchField, searchButton,
                lblCreateNewList, txtListTitle, txtDescription, txtDate,
                chooseImageButton, deleteImageButton, saveButton, lblListSaved, logOutButton, lblRecentToDo, table
        );

        // Membuat Scene dan Stage
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("TaskFlow - the list that works for you");
        stage.setScene(scene);
        stage.show();
    }

    protected TableView<String[]> getTable() {
        TableView<String[]> table = new TableView<>();
        table.setPrefSize(540, 300);

        TableColumn<String[], String> colTitle = new TableColumn<>("List Title");
        colTitle.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[0]));

        TableColumn<String[], String> colDesc = new TableColumn<>("Description");
        colDesc.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[1]));

        TableColumn<String[], String> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[2]));

        TableColumn<String[], String> colImage = new TableColumn<>("Images");
        colImage.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    Button seeAttachmentButton = new Button("VIEW");

                    // Menyembunyikan tombol View Image jika tidak ada image yang ditambahkan
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

        TableColumn<String[], String> colDelete = new TableColumn<>("Delete");
        colDelete.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (!empty) {
                    Button deleteButton = new Button("Delete");

                    deleteButton.setOnAction(e -> {
                        int index = getIndex();
                        taskList.remove(index);
                        information_notif("List telah berhasil dihapus!");
                    });

                    setGraphic(deleteButton);
                } else {
                    setGraphic(null);
                }
            }
        });

        table.getColumns().addAll(colTitle, colDesc, colDate, colImage, colDelete);

        return table;
    }

    protected boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private void information_notif(String pesan) {
        // Membuat notifikasi tipe informasi
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informasi");
        alert.setHeaderText(null);
        alert.setContentText(pesan);

        // Menampilkan notifikasi
        alert.showAndWait();
    }

    private void warning_notif(String pesan) {
        // Membuat notifikasi tipe informasi
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Peringatan");
        alert.setHeaderText(null);
        alert.setContentText(pesan);

        // Menampilkan notifikasi
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}
