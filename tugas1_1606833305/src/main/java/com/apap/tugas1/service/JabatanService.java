package com.apap.tugas1.service;

import java.util.List;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;

public interface JabatanService {
	void tambahJabatan (JabatanModel jabatan);
	List <JabatanModel> selectAll ();
	JabatanModel findJabatanById (long id);
	void ubahJabatan (JabatanModel jabatan);
	void hapusJabatan (long id);
	List <PegawaiModel> pagawaiSeJabatan (long idJabatan);
}
