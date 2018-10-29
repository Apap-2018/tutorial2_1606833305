package com.apap.tugas1.service;

import java.util.List;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;

public interface JabatanPegawaiService {
	List <JabatanPegawaiModel> findJabatanPegawaiByJabatan (long idJabatan);
	List <JabatanPegawaiModel> selectAll ();
	List <JabatanPegawaiModel> findJabatanPegawaiByPegawai (PegawaiModel pegawai);
	void hapusJabatanPegawai (JabatanPegawaiModel jab);
	void setPegawaiJab (PegawaiModel pegawai);
}
