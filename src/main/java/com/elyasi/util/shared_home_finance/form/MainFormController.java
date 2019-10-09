package com.elyasi.util.shared_home_finance.form;

import com.elyasi.util.shared_home_finance.Main;
import com.elyasi.util.shared_home_finance.table.ButtonCell;
import com.elyasi.util.shared_home_finance.table.MainTableDataModel;
import de.jensd.fx.glyphs.GlyphIcons;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;


public class MainFormController {
    private final Stage stage;
    private static final int WIDTH = 770;
    private static final int HEIGHT = 550;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtAmir;

    @FXML
    private TextField txtRaouf;

    @FXML
    private TextField txtHirbod;

    @FXML
    private CheckBox chkAmir;

    @FXML
    private CheckBox chkRaouf;

    @FXML
    private CheckBox chkHirbod;

    @FXML
    private TableView<MainTableDataModel> table;
    private ObservableList<MainTableDataModel> data;

    @FXML
    private TableColumn<Integer, MainTableDataModel> colNo;

    @FXML
    private TableColumn<BigDecimal, MainTableDataModel> colPrice;

    @FXML
    private TableColumn<BigDecimal, MainTableDataModel> colAmir;

    @FXML
    private TableColumn<BigDecimal, MainTableDataModel> colRaouf;

    @FXML
    private TableColumn<BigDecimal, MainTableDataModel> colHirbod;

    @FXML
    private TableColumn<MainTableDataModel, String> colEdit;

    @FXML
    private TableColumn<MainTableDataModel, String> colDel;

    @FXML
    private TextField txtPriceSum;

    @FXML
    private TextField txtAmirSum;

    @FXML
    private TextField txtRaoufSum;

    @FXML
    private TextField txtHirbodSum;


    public MainFormController() throws IOException {
        stage = Main.getMainStage();
        stage.setMaxHeight(HEIGHT);
        stage.setMaxWidth(WIDTH);

        stage.setTitle("Shared Home Finance");
    }

    public void show() throws IOException {
        stage.setScene(loadFxmlFile());
        stage.show();
    }

    private Scene loadFxmlFile() throws IOException {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/MainForm.fxml"))
        );
        return new Scene(root, WIDTH, HEIGHT);
    }


    @FXML
    public void initialize() {
        // table
        data = FXCollections.observableArrayList();
        table.setItems(data);
        data.addListener(this::onTableRowChange);

        colNo.setCellValueFactory(new PropertyValueFactory<>("no"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colAmir.setCellValueFactory(new PropertyValueFactory<>("amir"));
        colRaouf.setCellValueFactory(new PropertyValueFactory<>("raouf"));
        colHirbod.setCellValueFactory(new PropertyValueFactory<>("hirbod"));
        ButtonCell.setupButtonColumn(colEdit, MaterialDesignIcon.TABLE_EDIT, this::onEdit);
        ButtonCell.setupButtonColumn(colDel, MaterialDesignIcon.DELETE, this::onDelete);


        // text change
        txtPrice.textProperty().addListener(this::txtPriceTextChange);

        // keydown
        txtPrice.setOnKeyPressed(this::onKeyPressed);
        //txtAmir.setOnKeyPressed(this::onKeyPressed);
        //txtRaouf.setOnKeyPressed(this::onKeyPressed);
        //txtHirbod.setOnKeyPressed(this::onKeyPressed);

        // checkbox
        chkAmir.setSelected(true);
        chkRaouf.setSelected(true);
        chkHirbod.setSelected(true);
        chkAmir.selectedProperty().addListener(this::onChkAmirSelectedChange);
        chkRaouf.selectedProperty().addListener(this::onChkRaoufSelectedChange);
        chkHirbod.selectedProperty().addListener(this::onChkHirbodSelectedChange);

        Platform.runLater(() -> {
            txtPrice.requestFocus();
        });
    }

    private void onDelete(MainTableDataModel row, int index) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Payment Remove");
        alert.setHeaderText("Do you wanna delete this payment ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES)
            data.remove(index);
    }

    private void onEdit(MainTableDataModel row, int index) {
        chkAmir.setSelected(row.getAmir().doubleValue() > 0);
        chkRaouf.setSelected(row.getRaouf().doubleValue() > 0);
        chkHirbod.setSelected(row.getHirbod().doubleValue() > 0);

        txtPrice.setText(row.getPrice().toString());
        data.remove(index);
    }


    private void onTableRowChange(ListChangeListener.Change<? extends MainTableDataModel> c) {
        BigDecimal price, amir, raouf, hirbod;
        price = amir = raouf = hirbod = BigDecimal.ZERO;

        for (MainTableDataModel row : table.getItems()) {
            price = price.add(row.getPrice());
            amir = amir.add(row.getAmir());
            raouf = raouf.add(row.getRaouf());
            hirbod = hirbod.add(row.getHirbod());
        }

        txtPriceSum.setText(price.toString());
        txtAmirSum.setText(amir.toString());
        txtRaoufSum.setText(raouf.toString());
        txtHirbodSum.setText(hirbod.toString());
    }


    private void onChkAmirSelectedChange(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        update();
        txtPrice.requestFocus();
    }

    private void onChkRaoufSelectedChange(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        update();
        txtPrice.requestFocus();
    }

    private void onChkHirbodSelectedChange(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        update();
        txtPrice.requestFocus();
    }

    private void addRow() {
        if (txtPrice.getText().isEmpty() || (txtAmir.getText().isEmpty() && txtRaouf.getText().isEmpty() && txtHirbod.getText().isEmpty()))
            return;

        int no = table.getItems().size() + 1;

        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(txtPrice.getText()));

        BigDecimal amir = BigDecimal.ZERO;
        if (!txtAmir.getText().isEmpty())
            amir = BigDecimal.valueOf(Double.parseDouble(txtAmir.getText()));

        BigDecimal raouf = BigDecimal.ZERO;
        if (!txtRaouf.getText().isEmpty())
            raouf = BigDecimal.valueOf(Double.parseDouble(txtRaouf.getText()));

        BigDecimal hirbod = BigDecimal.ZERO;
        if (!txtHirbod.getText().isEmpty())
            hirbod = BigDecimal.valueOf(Double.parseDouble(txtHirbod.getText()));

        data.add(new MainTableDataModel(no, price, amir, raouf, hirbod));
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            btnAddClick();
        }
    }

    private void update() {
        if (txtPrice.getText().isEmpty()) {
            txtAmir.setText("");
            txtRaouf.setText("");
            txtHirbod.setText("");
            return;
        }

        int divide = 0;
        if (chkAmir.isSelected())
            divide++;

        if (chkRaouf.isSelected())
            divide++;

        if (chkHirbod.isSelected())
            divide++;

        if (divide == 0)
            return;

        double eachPerson = Double.parseDouble(txtPrice.getText()) / divide;
        eachPerson = (double) Math.round(eachPerson * 100) / 100;

        if (chkAmir.isSelected())
            txtAmir.setText(String.valueOf(eachPerson));
        else
            txtAmir.setText("0");

        if (chkRaouf.isSelected())
            txtRaouf.setText(String.valueOf(eachPerson));
        else
            txtRaouf.setText("0");

        if (chkHirbod.isSelected())
            txtHirbod.setText(String.valueOf(eachPerson));
        else
            txtHirbod.setText("0");
    }

    private void txtPriceTextChange(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        update();
    }

    private void clear() {
        txtPrice.setText("");
        chkHirbod.setSelected(true);
        chkRaouf.setSelected(true);
        chkAmir.setSelected(true);
    }

    @FXML
    private void btnAddClick() {
        addRow();
        txtPrice.requestFocus();
        clear();
    }
}
