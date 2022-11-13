package com.shyam.gujarat_police.helper;

import com.shyam.gujarat_police.entities.Designation;
import com.shyam.gujarat_police.entities.Police;
import com.shyam.gujarat_police.entities.PoliceStation;
import com.shyam.gujarat_police.exceptions.ExcelException;
import com.shyam.gujarat_police.services.DesignationService;
import com.shyam.gujarat_police.services.PoliceStationService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

@Component
public class ExcelHelper {
    static Logger logger = Logger.getLogger(ExcelHelper.class.getName());
    public static final int POLICE_DESIGNATION = 1;
    public static final int POLICE_FULLNAME = 2;
    public static final int POLICE_BUCKLE_NUMBER = 3;
    public static final int POLICE_MOBILE_NUMBER = 4;
    public static final int POLICE_STATION = 5;
    public static final int POLICE_DISTRICT = 6;
    public static final int POLICE_GENDER = 7;
    public static final int POLICE_AGE = 8;
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "designation", "fullName", "buckleNumber", "number", "policeStationName",
            "district", "gender", "age" };
    static String SHEET = "SHEET1";

    @Autowired
    private DesignationService designationService;

    @Autowired
    private PoliceStationService policeStationService;

    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public List<Police> excelToTutorials(InputStream is) {

        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Police> policeListFromExcel = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Police police = new Police();

                int cellIdx = 1;
                try {
                    while (cellsInRow.hasNext()) {
                        DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale
                        Cell currentCell = cellsInRow.next();
                        String cellValue = formatter.formatCellValue(currentCell); //Returns the formatted value of a cell as a String regardless of the cell type.


                        switch (cellIdx) {
                            case POLICE_DESIGNATION -> {
                                Designation designation = designationService.getDesignationByName(cellValue);
                                police.setDesignation(designation);
                            }
                            case POLICE_FULLNAME -> police.setFullName(cellValue);
                            case POLICE_BUCKLE_NUMBER -> police.setBuckleNumber(cellValue);
                            case POLICE_MOBILE_NUMBER -> police.setNumber(cellValue);
                            case POLICE_STATION -> {
                                PoliceStation policeStation = policeStationService.readSpecificByName(cellValue);
                                police.setPoliceStation(policeStation);
                            }
                            case POLICE_DISTRICT -> police.setDistrict(cellValue);
                            case POLICE_GENDER -> police.setGender(cellValue);
                            case POLICE_AGE -> police.setAge(Integer.parseInt(cellValue));
                            default -> {
                                logger.info("Unknown cell type: " + currentCell.getStringCellValue());
                            }
                        }
                        cellIdx++;
                    }
                } catch (Exception e) {
                    throw new ExcelException(e.getMessage() + " : " + e.getCause() + " : " + e.getLocalizedMessage() + " : " + Arrays.toString(e.getStackTrace()));
                }
                policeListFromExcel.add(police);
            }

            workbook.close();

            return policeListFromExcel;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
