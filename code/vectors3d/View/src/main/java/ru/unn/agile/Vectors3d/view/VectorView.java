package ru.unn.agile.Vectors3d.view;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.scene.control.TextFormatter;

import ru.unn.agile.Vectors3d.viewmodel.VectorViewModel;
//import ru.unn.agile.Vectors3d.infrastructure.TxtLogger;

public class VectorView {

    @FXML
    void initialize() {

        final List<TextField> textFields = new ArrayList<TextField>()  { {
                add(xTextField);
                add(yTextField);
                add(zTextField);
            } };

        Pattern pattern = Pattern.compile(INPUT_REGEX);

        for (TextField textField : textFields) {
            TextFormatter<String> formatter = new TextFormatter<>(change ->
                pattern.matcher(change.getControlNewText()).matches() ? change : null);
            textField.setTextFormatter(formatter);
        }
        /*final ChangeListener<Boolean> focusChangeListener = (observable, oldValue, newValue)
                -> viewModel.onFocusChanged(oldValue, newValue);*/

        normalizeButton.setOnAction(event -> viewModel.normalize());
    }

    private final ChangeListener<Boolean> focusChangeListener = new ChangeListener<Boolean>() {
        @Override
        public void changed(final ObservableValue<? extends Boolean> observable,
                            final Boolean oldValue, final Boolean newValue) {
            viewModel.onFocusChanged(oldValue, newValue);
        }
    };

    public void setViewModel(final VectorViewModel viewModel) {
        this.viewModel = viewModel;
        xTextField.textProperty().bindBidirectional(this.viewModel.getXProperty());
        xTextField.focusedProperty().addListener(focusChangeListener);
        yTextField.textProperty().bindBidirectional(this.viewModel.getYProperty());
        yTextField.focusedProperty().addListener(focusChangeListener);
        zTextField.textProperty().bindBidirectional(this.viewModel.getZProperty());
        zTextField.focusedProperty().addListener(focusChangeListener);

        normalizeButton.disableProperty().bind(this.viewModel.normalizeDisabledProperty());
    }

    private static final String INPUT_REGEX =
            "[-+]?[0-9]{0,8}(\\.[0-9]{0,8})?([eE][-+]?[0-9]{0,2})?";

    @FXML
    private TextField xTextField;

    @FXML
    private TextField yTextField;

    @FXML
    private TextField zTextField;

    @FXML
    private Button normalizeButton;

    //private VectorViewModel viewModel = new VectorViewModel();
    private VectorViewModel viewModel;
}
