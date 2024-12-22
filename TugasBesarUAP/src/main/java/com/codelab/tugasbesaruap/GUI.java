package com.codelab.tugasbesaruap;

import javax.swing.*;
import java.awt.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //Frame utama
        JFrame frame = new JFrame("TaskFlow - the list that works for you");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        //Bagian atas
        JLabel lblWelcome = new JLabel("Welcome!");
        lblWelcome.setBounds(20, 10, 200, 20);
        frame.add(lblWelcome);

        JTextField searchField = new JTextField();
        searchField.setBounds(500, 10, 200, 25);
        frame.add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(710, 10, 80, 25);
        frame.add(searchButton);

        //Panel kiri "Create New List"
        JLabel lblCreateNewList = new JLabel("Create New List");
        lblCreateNewList.setBounds(20, 50, 200, 20);
        frame.add(lblCreateNewList);

        JTextField txtListTitle = new JTextField("List Title");
        txtListTitle.setBounds(20, 80, 200, 25);
        frame.add(txtListTitle);

        JTextField txtDescription = new JTextField("Description");
        txtDescription.setBounds(20, 110, 200, 25);
        frame.add(txtDescription);

        JTextField txtDate = new JTextField("Date (dd-mm-yyyy)");
        txtDate.setBounds(20, 140, 200, 25);
        frame.add(txtDate);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(20, 170, 80, 25);
        frame.add(saveButton);

        JLabel lblListSaved = new JLabel("List has saved");
        lblListSaved.setBounds(20, 210, 200, 20);
        frame.add(lblListSaved);

        JButton allListsButton = new JButton("All Lists");
        allListsButton.setBounds(20, 240, 100, 25);
        frame.add(allListsButton);

        JButton todaysListsButton = new JButton("Today's Lists");
        todaysListsButton.setBounds(20, 270, 120, 25);
        frame.add(todaysListsButton);

        JButton yesterdaysListsButton = new JButton("Yesterday's Lists");
        yesterdaysListsButton.setBounds(20, 300, 150, 25);
        frame.add(yesterdaysListsButton);

        JButton logOutButton = new JButton("LogOut");
        logOutButton.setBounds(20, 340, 100, 25);
        frame.add(logOutButton);

        //Panel kanan "Recent To Do Lists"
        JLabel lblRecentToDo = new JLabel("Recent To Do Lists:");
        lblRecentToDo.setBounds(250, 50, 200, 20);
        frame.add(lblRecentToDo);

        String[]columnNames = {"List Title", "Description", "Date"};
        Object[][] data = {
                {"Study", "Math Numeric", "25-12-2014"},
                {"Buy new album", "Will buy new Metallica album yoww", "30-12-2014"},
                {"Python chrome", "Will make a chrome python", "30-12-2014"},
                {"Travel", "Travel to friend's house", "2-1-2014"}
        };

        JTable table = new JTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(250, 80, 540, 300);
        frame.add(tableScrollPane);

        //Warna background
        frame.getContentPane().setBackground(new Color(184, 134, 11));

        //Tampilkan frame
        frame.setVisible(true);
    }
}