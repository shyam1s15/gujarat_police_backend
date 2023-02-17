package com.shyam.gujarat_police.io.read;

import com.shyam.gujarat_police.exceptions.CustomException;
import com.shyam.gujarat_police.io.ExcelDataObject;

public interface ExcelReadProcessor<Sheet> {

    ExcelDataObject processSheet(Sheet sheet, ExcelDataObject obj) throws CustomException;

    void initMethod(String firebaseNode);
}
