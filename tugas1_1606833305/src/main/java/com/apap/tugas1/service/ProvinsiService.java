package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;

public interface ProvinsiService {
	List <ProvinsiModel> selectAll();
	ProvinsiModel findProvinsiById(long id);
	List <PegawaiModel> pegawaiSeProvinsi (Long idProvinsi);
}
