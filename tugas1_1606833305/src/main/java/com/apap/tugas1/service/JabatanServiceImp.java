package com.apap.tugas1.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.JabatanDb;

@Service
@Transactional
public class JabatanServiceImp implements JabatanService{
	
	@Autowired
	private JabatanDb jabatanDb;
	
	@Override
	public void tambahJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);
	}

	@Override
	public List<JabatanModel> selectAll() {
		return jabatanDb.findAll();
	}

	@Override
	public JabatanModel findJabatanById(long id) {
		return jabatanDb.findJabatanById(id);
	}

	@Override
	public void ubahJabatan(JabatanModel jabatan) {
		JabatanModel oldJabatan = jabatanDb.findJabatanById(jabatan.getId());
		oldJabatan.setNama(jabatan.getNama());
		oldJabatan.setDeskripsi(jabatan.getDeskripsi());
		oldJabatan.setGajiPokok(jabatan.getGajiPokok());
		jabatanDb.save(jabatan);
	}

	@Override
	public void hapusJabatan(long id) {
		jabatanDb.deleteById(id);	
	}

	@Override
	public List<PegawaiModel> pagawaiSeJabatan(long idJabatan) {
		List <PegawaiModel> pegawai = new ArrayList <>();
		JabatanModel jab = jabatanDb.findJabatanById(idJabatan);
		for (JabatanPegawaiModel jp : jab.getJabatanPegawai()) {
			pegawai.add(jp.getPegawai());
		}
		return pegawai;
	}	
}
