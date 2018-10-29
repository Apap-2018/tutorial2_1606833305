package com.apap.tugas1.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.JabatanPegawaiDb;

@Service
@Transactional
public class JabatanPegawaiServiceImp implements JabatanPegawaiService {
	@Autowired
	private JabatanPegawaiDb jabatanPegawaiDb;

	@Override
	public List<JabatanPegawaiModel> findJabatanPegawaiByJabatan(long idJabatan) {
		return jabatanPegawaiDb.findJabatanPegawaiByJabatan(idJabatan);
	}

	@Override
	public List<JabatanPegawaiModel> selectAll() {
		return jabatanPegawaiDb.findAll();
	}

	@Override
	public List<JabatanPegawaiModel> findJabatanPegawaiByPegawai(PegawaiModel pegawai) {
		return jabatanPegawaiDb.findByPegawai(pegawai);
	}

	@Override
	public void hapusJabatanPegawai(JabatanPegawaiModel jab) {
		jabatanPegawaiDb.delete(jab);
		
	}

	@Override
	public void setPegawaiJab(PegawaiModel pegawai) {
		for (JabatanPegawaiModel pegjab : pegawai.getJabatanPegawai()) {
			pegjab.setPegawai(pegawai);
		}
	}
}
