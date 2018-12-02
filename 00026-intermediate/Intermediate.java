import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Dialog;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Callback;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.math.BigDecimal;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Dailyprogrammer: 26 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/qzip3/3162012_challenge_26_intermediate/
 */
public class Intermediate extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private TableView<Employee> table;

    @Override
    public void start(Stage stage) {
        table = new TableView<Employee>();
        table.getItems().addAll(loadEmployees());

        TableColumn<Employee, String> nameColumn = new TableColumn<Employee, String>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
        table.getColumns().add(nameColumn);

        TableColumn<Employee, Integer> ageColumn = new TableColumn<Employee, Integer>("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("age"));
        table.getColumns().add(ageColumn);

        TableColumn<Employee, BigDecimal> salaryColumn = new TableColumn<Employee, BigDecimal>("Salary (per hour)");
        salaryColumn.setCellValueFactory(new PropertyValueFactory<Employee, BigDecimal>("salary"));
        table.getColumns().add(salaryColumn);

        nameColumn.prefWidthProperty().bind(
            table.widthProperty().subtract(
                salaryColumn.widthProperty().add(ageColumn.widthProperty())));

        Button addBtn = new Button("Add");
        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addEmployee();
            }
        });

        Button editBtn = new Button("Edit");
        editBtn.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());
        editBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                editEmployee(table.getSelectionModel().getSelectedItem());
            }
        });

        Button deleteBtn = new Button("Delete");
        deleteBtn.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());
        deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteEmployee(table.getSelectionModel().getSelectedItem());
            }
        });

        HBox buttons = new HBox();
        buttons.setSpacing(5);
        buttons.getChildren().addAll(addBtn, editBtn, deleteBtn);

        VBox box = new VBox();
        box.setSpacing(5);
        box.setPadding(new Insets(10));
        VBox.setVgrow(table, Priority.ALWAYS);
        box.getChildren().addAll(buttons, table);

        stage.setTitle("Employees");
        stage.setWidth(400);
        stage.setHeight(600);
        stage.setScene(new Scene(box));
        stage.show();
    }

    private void addEmployee() {
        Optional<Employee> result = showEmployeeForm(null);
        Employee newEmployee = result.get();
        if (newEmployee != null) {
            table.getItems().add(result.get());
            saveEmployees(table.getItems());
        }
    }

    private void editEmployee(Employee e) {
        Optional<Employee> result = showEmployeeForm(e);
        Employee updatedEmployee = result.get();
        if (updatedEmployee != null) {
            table.getItems().set(table.getItems().indexOf(e), updatedEmployee);
            saveEmployees(table.getItems());
        }
    }

    private void deleteEmployee(Employee e) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete employee");
        confirm.setHeaderText(null);
        confirm.setContentText("Do you really want to delete employee '" + e.getName() + "'?");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.get() == ButtonType.OK) {
            table.getItems().remove(e);
            saveEmployees(table.getItems());
        }
    }

    private void saveEmployees(List<Employee> employees) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("employees.list"));
            for (Employee e : employees) {
                writer.write(String.format("%s;%d;%s", e.getName(), e.getAge(), e.getSalary()));
                writer.write(System.lineSeparator());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) { }
            }
        }
    }

    private List<Employee> loadEmployees() {
        List<Employee> employees = new ArrayList<Employee>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("employees.list"));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(";");
                employees.add(new Employee(parts[0].trim(), Integer.parseInt(parts[1].trim()), new BigDecimal(parts[2].trim())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) { }
            }
        }

        return employees;
    }

    private Optional<Employee> showEmployeeForm(Employee e) {
        Dialog<Employee> dialog = new Dialog<Employee>();
        if (e != null) {
            dialog.setTitle("Edit employee");
        } else {
            dialog.setTitle("Add employee");
        }
        dialog.setHeaderText(null);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        TextField nameField = new TextField();
        if (e != null) {
            nameField.setText(e.getName());
        }

        TextField ageField = new TextField();
        if (e != null) {
            ageField.setText(e.getAge() + "");
        }

        TextField salaryField = new TextField();
        if (e != null) {
            salaryField.setText(e.getSalary() + "");
        }

        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(10));
        pane.add(new Label("Name:"), 0, 0);
        pane.add(nameField, 1, 0);
        pane.add(new Label("Age:"), 0, 1);
        pane.add(ageField, 1, 1);
        pane.add(new Label("Salary:"), 0, 2);
        pane.add(salaryField, 1, 2);
        dialog.getDialogPane().setContent(pane);

        dialog.setResultConverter(new Callback<ButtonType, Employee>() {
            @Override
            public Employee call(ButtonType type) {
                if (type == ButtonType.OK) {
                    return new Employee(
                        nameField.getText(),
                        Integer.parseInt(ageField.getText()),
                        new BigDecimal(salaryField.getText()));
                } else {
                    return null;
                }
            }
        });

        return dialog.showAndWait();
    }

    public static class Employee {

        private String name;
        private int age;
        private BigDecimal salary;

        Employee(String name, int age, BigDecimal salary) {
            this.name = name;
            this.age = age;
            this.salary = salary;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public BigDecimal getSalary() {
            return salary;
        }

        public void setSalary(BigDecimal salary) {
            this.salary = salary;
        }
    }
}
