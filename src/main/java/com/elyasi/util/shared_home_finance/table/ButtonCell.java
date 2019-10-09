package com.elyasi.util.shared_home_finance.table;

import de.jensd.fx.glyphs.GlyphIcons;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;


public class ButtonCell<M> implements Callback<TableColumn<M, String>, TableCell<M, String>> {

    public static <M> void setupButtonColumn(TableColumn<M, String> col, GlyphIcons icon, ICallBackCell<M> onClick) {
        col.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        col.setCellFactory(ButtonCell.forTableColumn(onClick , icon));
    }

    private static <T> ButtonCell<T> forTableColumn(ICallBackCell<T> onClick, GlyphIcons icon) {
        return new ButtonCell<>(onClick, icon);
    }

    private ICallBackCell<M> onClick;
    private String text;
    private GlyphIcons icon;

    private ButtonCell(ICallBackCell<M> onClick, String text, GlyphIcons icon) {
        this.onClick = onClick;
        this.text = text;
        this.icon = icon;
    }

    private ButtonCell(ICallBackCell<M> onClick, GlyphIcons icon) {
        this.onClick = onClick;
        this.icon = icon;
        text = "";
    }


    @Override
    public TableCell<M, String> call(final TableColumn<M, String> param) {
        return new TableCell<M, String>() {

            final Button btn = new Button(text, Icons.INSTANCE.createIcon(icon));

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    btn.setOnAction(event -> {
                        onClick.call(getTableView().getItems().get(getIndex()), getIndex());
                    });
                    setGraphic(btn);
                    setText(null);
                }
            }
        };
    }
}
