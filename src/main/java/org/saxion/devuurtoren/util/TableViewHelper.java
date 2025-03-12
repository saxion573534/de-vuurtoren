package org.saxion.devuurtoren.util;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TableViewHelper {
    /***
     * Prepare a tableview to show the given columns
     * @param table the fxml tableview
     * @param columnNames the column headers as an array of string
     */
    public static void prepareTable(TableView table, String[] columnNames) {
        for (int i = 0; i < columnNames.length; i++) {
            final int colIndex = i;
            TableColumn<String[], String> column = new TableColumn<>(columnNames[colIndex]);
            column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[colIndex]));
            table.getColumns().add(column);
        }
    }
}
