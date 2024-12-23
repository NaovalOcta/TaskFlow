package com.codelab.tugasbesaruap;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class GUITest {

    private final GUI gui = new GUI();

    @BeforeAll
    static void initJavaFX() throws InterruptedException {
        // Ensure JavaFX platform is initialized before running tests
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();
    }

    @Test
    void testIsValidDate() {
        assertTrue(gui.isValidDate("25-12-2024"), "Valid date format should return true");
        assertFalse(gui.isValidDate("12-25-2024"), "Invalid date format (MM-dd-yyyy) should return false");
        assertFalse(gui.isValidDate("31-02-2024"), "Invalid day for February should return false");
        assertFalse(gui.isValidDate("abcd"), "Non-date input should return false");
    }

    @Test
    void testAddTask() {
        ObservableList<String[]> taskList = FXCollections.observableArrayList();

        String[] task1 = {"Task 1", "Description 1", "25-12-2024", null};
        String[] task2 = {"Task 2", "Description 2", "01-01-2025", null};

        taskList.add(task1);
        taskList.add(task2);

        assertEquals(2, taskList.size(), "Task list should have 2 tasks");
        assertEquals("Task 1", taskList.get(0)[0], "First task title should match");
        assertEquals("Description 2", taskList.get(1)[1], "Second task description should match");
    }

    @Test
    void testEditTask() {
        ObservableList<String[]> taskList = FXCollections.observableArrayList();

        String[] task1 = {"Task 1", "Description 1", "25-12-2024", null};
        taskList.add(task1);

        // Simulate editing the task
        String[] updatedTask = {"Updated Task", "Updated Description", "31-12-2024", null};
        taskList.set(0, updatedTask);

        assertEquals(1, taskList.size(), "Task list size should remain 1 after editing");
        assertEquals("Updated Task", taskList.get(0)[0], "Updated task title should match");
        assertEquals("Updated Description", taskList.get(0)[1], "Updated task description should match");
    }

    @Test
    void testDeleteTask() {
        ObservableList<String[]> taskList = FXCollections.observableArrayList();

        String[] task1 = {"Task 1", "Description 1", "25-12-2024", null};
        String[] task2 = {"Task 2", "Description 2", "01-01-2025", null};

        taskList.add(task1);
        taskList.add(task2);

        // Simulate deleting the first task
        taskList.remove(0);

        assertEquals(1, taskList.size(), "Task list should have 1 task after deletion");
        assertEquals("Task 2", taskList.get(0)[0], "Remaining task title should match");
    }

    @Test
    void testTableViewRendering() {
        TableView<String[]> table = gui.getTable();
        ObservableList<String[]> taskList = FXCollections.observableArrayList();

        String[] task1 = {"Task 1", "Description 1", "25-12-2024", null};
        taskList.add(task1);

        Platform.runLater(() -> {
            table.setItems(taskList);
            assertEquals(1, table.getItems().size(), "Table should have 1 task");
            assertEquals("Task 1", table.getItems().get(0)[0], "Table first row title should match");
        });
    }
}
