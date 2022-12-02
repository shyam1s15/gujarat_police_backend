package com.shyam.gujarat_police.services;

import com.shyam.gujarat_police.entities.Police;
import com.shyam.gujarat_police.entities.PoliceStation;
import com.shyam.gujarat_police.helper.ExcelHelper;
import com.shyam.gujarat_police.repositories.PoliceRepository;
import com.shyam.gujarat_police.repositories.PoliceStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

    @Autowired
    PoliceRepository policeRepository;

    @Autowired
    private ExcelHelper excelHelper;

    @Autowired
    PoliceStationRepository policeStationRepository;


    public void savePoliceFromExcel(MultipartFile file) throws IOException {
        List<Police> policeListFromExcel = excelHelper.excelToPolice(file.getInputStream());
        policeRepository.saveAll(policeListFromExcel);
    }

    public void savePoliceStationFromExcel(MultipartFile file) throws IOException{
        List<PoliceStation> policeStationListFromExcel = excelHelper.excelToPoliceStation(file.getInputStream());
        policeStationRepository.saveAll( policeStationListFromExcel );
    }
}
