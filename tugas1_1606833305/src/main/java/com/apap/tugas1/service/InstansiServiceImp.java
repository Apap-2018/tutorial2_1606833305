package com.apap.tugas1.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.InstansiDb;

@Service
@Transactional
public class InstansiServiceImp implements InstansiService{
	@Autowired
	private InstansiDb instansiDb;

	@Override
	public InstansiModel cariInstansiById(long id_instansi) {
		return instansiDb.findInstansiById(id_instansi);
	}

	@Override
	public List<InstansiModel> selectAll() {
		return instansiDb.findAll();
	}

	@Override
	public List<PegawaiModel> pegawaiSeInstansi(long idInstansi) {
		InstansiModel instansi = instansiDb.findInstansiById(idInstansi);
		List <PegawaiModel> allPegawai = new ArrayList <>();
		
		for (PegawaiModel p : instansi.getPegawai_instansi()) {
			allPegawai.add(p);
		}
		return allPegawai;
	}	
}