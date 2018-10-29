package com.apap.tugas1.service;

import java.util.List;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	PegawaiModel cariPegawaiByNip (String nip);
	void addPegawai (PegawaiModel pegawai);
	List<PegawaiModel> selectAllPegawai ();
	List<PegawaiModel> getPegawaiUrut(InstansiModel instansi);
	void deletePegawaiByNip (String nip);
	PegawaiModel cariPegawaiById (long id);	
	void updatePegawai (PegawaiModel pegawaiBaru, long id);
	void hapusJabatanPegawaiPegawai (PegawaiModel pegawai);
	void setNIP (PegawaiModel pegawai);
	void resetNIP (PegawaiModel pegawai);
	int gajiTertinggiPegawai (PegawaiModel pegawai);
}
