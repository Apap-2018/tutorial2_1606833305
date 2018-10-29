package com.apap.tugas1.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.ProvinsiDb;

@Service
@Transactional
public class ProvinsiServiceImp implements ProvinsiService{
	@Autowired
	ProvinsiDb provinsiDb;
	
	@Override
	public List<ProvinsiModel> selectAll() {
		return provinsiDb.findAll();
	}

	@Override
	public ProvinsiModel findProvinsiById(long id) {
		return provinsiDb.findProvinsiById(id);
	}

	@Override
	public List<PegawaiModel> pegawaiSeProvinsi(Long idProvinsi) {
		ProvinsiModel prov = provinsiDb.findProvinsiById(idProvinsi);
		List <PegawaiModel> allPegawai = new ArrayList<>();
		for (InstansiModel ins : prov.getListInstansi()) {
			for (PegawaiModel peg : ins.getPegawai_instansi()) {
				allPegawai.add(peg);
			}
		}
		return allPegawai;
	}
}