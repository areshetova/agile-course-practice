package ru.unn.agile.Vectors3d.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ru.unn.agile.Vectors3d.Model.Vector3d;

import java.util.List;

public class Vectors3dViewModel {

    public Vectors3dViewModel() {
        init();
    }

    public Vectors3dViewModel(final ILogger logger) {
        this.setLogger(logger);

        init();
    }

    private final VectorViewModel firstVectorViewModel = new VectorViewModel();
    private final VectorViewModel secondVectorViewModel = new VectorViewModel();
    private final ResultNumberViewModel resultNumberViewModel = new ResultNumberViewModel();
    private final VectorViewModel resultVectorViewModel = new VectorViewModel();

    private void init() {
        BooleanBinding isCalculationAvailable = new BooleanBinding() {
            {
                super.bind(firstVectorViewModel.normalizeDisabledProperty(),
                        secondVectorViewModel.normalizeDisabledProperty());
            }

            @Override
            protected boolean computeValue() {
                return !firstVectorViewModel.normalizeDisabledProperty().get()
                        && !secondVectorViewModel.normalizeDisabledProperty().get();
            }
        };
        calculationIsNotAvailable.bind(isCalculationAvailable.not());
        //isInputChanged = true;
    }

    public VectorViewModel getFirstVectorViewModel() {
        return firstVectorViewModel;
    }

    public VectorViewModel getSecondVectorViewModel() {
        return secondVectorViewModel;
    }

    public ResultNumberViewModel getResultNumberViewModel() {
        return resultNumberViewModel;
    }

    public VectorViewModel getResultVectorViewModel() {
        return resultVectorViewModel;
    }

    public BooleanProperty calculationIsNotAvailableProperty() {
        return calculationIsNotAvailable;
    }

    public final boolean isCalculationDisabled() {
        return calculationIsNotAvailable.get();
    }

    public void calculateDotProduct() {
        logger.log(calculateDotProductLogMessage());
        updateLogs();
        Vector3d firstVector = firstVectorViewModel.getVector3d();
        Vector3d secondVector = secondVectorViewModel.getVector3d();
        Double result = firstVector.dotProduct(secondVector);
        resultNumberViewModel.setResult(result);
    }

    public void calculateCrossProduct() {
        logger.log(calculateCrossProductLogMessage());
        updateLogs();
        Vector3d firstVector = firstVectorViewModel.getVector3d();
        Vector3d secondVector = secondVectorViewModel.getVector3d();
        Vector3d result = firstVector.crossProduct(secondVector);
        resultVectorViewModel.setVector3d(result);
    }

    public final class LogMessages {
        public static final String DOT_PRODUCT_WAS_PRESSED = "Dot product. ";
        public static final String CROSS_PRODUCT_WAS_PRESSED = "Cross product. ";
        public static final String NORMALIZE_WAS_PRESSED = "Normalization. ";
        public static final String EDITING_FINISHED = "Updated input. ";

        private LogMessages() { }
    }

    public StringProperty logsProperty() {
        return logs;
    }

    public final void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
        this.firstVectorViewModel.setLogger(logger);
        this.secondVectorViewModel.setLogger(logger);
        this.resultVectorViewModel.setLogger(logger);
    }
    public List<String> getLog() {
        return logger.getLog();
    }
    public final String getLogs() {
        return logs.get();
    }

    private void updateLogs() {
        List<String> logString = logger.getLog();
        String newLog = new String("");
        for (String log : logString) {
            newLog += log + "\n";
        }
        logs.set(newLog);
    }

    private void logInputParams() {
        if (!isInputChanged) {
            return;
        }
        logger.log(editingFinishedLogMessage());
        isInputChanged = false;
    }

    private String returnArguments() {
        return "Arguments" + ": V1 = {" + firstVectorViewModel.getXProperty().get()
                + ", " + firstVectorViewModel.getYProperty().get()
                + ", " + firstVectorViewModel.getZProperty().get()
                + "}; V2 = {" + secondVectorViewModel.getXProperty().get()
                + ", " + secondVectorViewModel.getYProperty().get()
                + ", " + secondVectorViewModel.getZProperty().get() + "}.";
    }

    private String calculateDotProductLogMessage() {
        return LogMessages.DOT_PRODUCT_WAS_PRESSED + returnArguments();
    }

    private String calculateCrossProductLogMessage() {
        return LogMessages.CROSS_PRODUCT_WAS_PRESSED + returnArguments();
    }

    private String editingFinishedLogMessage() {
        return LogMessages.EDITING_FINISHED
                + "Input arguments are: ["
                + firstVectorViewModel.getXProperty().get() + "; "
                + firstVectorViewModel.getYProperty().get() + "; "
                + firstVectorViewModel.getZProperty().get() + "; "
                + secondVectorViewModel.getXProperty().get() + "; "
                + secondVectorViewModel.getYProperty().get() + "; "
                + secondVectorViewModel.getZProperty().get() + "]";
    }

    private final BooleanProperty calculationIsNotAvailable = new SimpleBooleanProperty();

    private boolean isInputChanged;
    private ILogger logger;
    private final StringProperty logs = new SimpleStringProperty();
}
