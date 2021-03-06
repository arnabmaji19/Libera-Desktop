package io.github.arnabmaji19.libera.desktop.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

public class AutoCompleteComboBoxListener<T> implements EventHandler<KeyEvent> {

    private final ComboBox<T> comboBox;
    private final ObservableList<T> data;
    private boolean moveCaretToPos = false;
    private int caretPos;

    public AutoCompleteComboBoxListener(final ComboBox<T> comboBox) {
        this.comboBox = comboBox;
        this.data = comboBox.getItems();
        this.comboBox.setEditable(true);
        this.comboBox.setOnKeyReleased(AutoCompleteComboBoxListener.this);
        this.comboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(T t) {
                return t != null ? t.toString() : "";
            }

            @Override
            public T fromString(String s) {
                for (var datum : data)
                    if (datum.toString().equals(s)) return datum;
                return null;
            }
        });
    }

    @Override
    public void handle(KeyEvent event) {


        if (event.getCode() == KeyCode.UP) {
            caretPos = -1;
            moveCaret(comboBox.getEditor().getText().length());
            return;

        } else if (event.getCode() == KeyCode.DOWN) {
            if (!comboBox.isShowing())
                comboBox.show();

            caretPos = -1;
            moveCaret(comboBox.getEditor().getText().length());
            return;
        }

//        if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
//                || event.isControlDown() || event.getCode() == KeyCode.HOME
//                || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
//            return;
//        }

        comboBox.hide();

//        if (event.getCode() == KeyCode.BACK_SPACE) {
//            moveCaretToPos = true;
//            caretPos = comboBox.getEditor().getCaretPosition();
//        } else if (event.getCode() == KeyCode.DELETE) {
//            moveCaretToPos = true;
//            caretPos = comboBox.getEditor().getCaretPosition();
//        }


        ObservableList<T> list = FXCollections.observableArrayList();
        for (T datum : data) {
            if (datum.toString().toLowerCase().startsWith(
                    this.comboBox
                            .getEditor().getText().toLowerCase())) {
                list.add(datum);
            }
        }
        String t = comboBox.getEditor().getText();

        comboBox.setItems(list);
        comboBox.getEditor().setText(t);
        if (!moveCaretToPos) {
            caretPos = -1;
        }
        moveCaret(t.length());
        if (!list.isEmpty()) {
            comboBox.show();
        }
    }

    private void moveCaret(int textLength) {
        if (caretPos == -1)
            comboBox.getEditor().positionCaret(textLength);
        else
            comboBox.getEditor().positionCaret(caretPos);

        moveCaretToPos = false;
    }

}
