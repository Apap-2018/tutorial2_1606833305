package com.apap.tugas1.service;

import java.util.List;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

public interface InstansiService {
	InstansiModel cariInstansiById (long id_instansi);
	List <InstansiModel> selectAll();
	List <PegawaiModel> pegawaiSeInstansi (long idInstansi);
}
