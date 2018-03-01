package ru.unn.agile.Vectors3d.infrastructure;

import ru.unn.agile.Vectors3d.viewmodel.Vectors3dViewModel;
import ru.unn.agile.Vectors3d.viewmodel.Vectors3dViewModelTests;

public class ViewModelWithTxtLoggerTests extends Vectors3dViewModelTests {
    @Override
    public void setUp() {
        TxtLogger realLogger =
            new TxtLogger("./ViewModelWithTxtLoggerTests.log");
        super.setViewModel(new Vectors3dViewModel(realLogger));
    }
}
