package com.apap.tugas1.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;

@Service
@Transactional
public class PegawaiServiceImp implements PegawaiService{
	@Autowired 
	private PegawaiDb pegawaiDb;

	@Autowired
	private JabatanPegawaiService jabatanPegawaiService;
	
	public PegawaiModel cariPegawaiByNip(String nip) {
		return pegawaiDb.findPegawaiByNip(nip);
	}

	@Override
	public void addPegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
	}

	@Override
	public List<PegawaiModel> selectAllPegawai() {
		return pegawaiDb.findAll();
	}

	@Override
	public List<PegawaiModel> getPegawaiUrut(InstansiModel instansi) {
		return pegawaiDb.findByInstansiOrderByTanggallahirAsc(instansi);
	}

	@Override
	public void deletePegawaiByNip(String nip) {
		PegawaiModel pegawai = pegawaiDb.findPegawaiByNip(nip);
		pegawaiDb.delete(pegawai);	
	}

	@Override
	public PegawaiModel cariPegawaiById(long id) {
		return pegawaiDb.findPegawaiById(id);
	}

	@Override
	public void updatePegawai(PegawaiModel pegawaiBaru, long id) {
		PegawaiModel pegawaiLama = pegawaiDb.findPegawaiById(id);
		pegawaiLama.setInstansi(pegawaiBaru.getInstansi());
		pegawaiLama.setJabatanPegawai(pegawaiBaru.getJabatanPegawai());
		pegawaiLama.setNama(pegawaiBaru.getNama());
		pegawaiLama.setNip(pegawaiBaru.getNip());
		pegawaiLama.setTahun_masuk(pegawaiBaru.getTahun_masuk());
		pegawaiLama.setTanggallahir(pegawaiBaru.getTanggallahir());
		pegawaiLama.setTempat_lahir(pegawaiBaru.getTempat_lahir());
		pegawaiDb.save(pegawaiLama);
	}

	@Override
	public void hapusJabatanPegawaiPegawai(PegawaiModel pegawai) {
		List <JabatanPegawaiModel> listJabatan = jabatanPegawaiService.findJabatanPegawaiByPegawai(pegawai);
		for (JabatanPegawaiModel jab : listJabatan) {
			jabatanPegawaiService.hapusJabatanPegawai(jab);
		}				
	}

	@Override
	public void setNIP(PegawaiModel pegawai) {		
		List <PegawaiModel> pegUrutIDAsc = pegawaiDb.findByInstansiOrderByIdAsc(pegawai.getInstansi());
		List <PegawaiModel> pegSama = new ArrayList<>();
		for (PegawaiModel pg : pegUrutIDAsc) {
			if (pegawai.getTahun_masuk().equals(pg.getTahun_masuk()) && pegawai.getTanggallahir().equals(pg.getTanggallahir())) {
				pegSama.add(pg);
			}
		}
		
		int noUrutInt = 0;
		if (pegSama.isEmpty()) {
			noUrutInt = 1;
		}
		else {
			String nip = pegSama.get(pegSama.size()-1).getNip();
			String noUrut = nip.substring(14, 16);
			noUrutInt = Integer.parseInt(noUrut) + 1;
		}

		String noUrutBaru = "";
		if (noUrutInt < 10) {
			noUrutBaru = "0" + noUrutInt;
		}
		else {
			noUrutBaru = "" + noUrutInt;
		}
		String tgl = "" + pegawai.getTanggallahir() + "";
		String tglDMY = tgl.substring(8,10) + tgl.substring(5,7) + tgl.substring(2,4);
		String nipFix = pegawai.getInstansi().getId() + tglDMY + pegawai.getTahun_masuk() + noUrutBaru;
		pegawai.setNip(nipFix);		
	}

	@Override
	public void resetNIP(PegawaiModel pegawai) {
		PegawaiModel peg = pegawaiDb.findPegawaiById(pegawai.getId());
		
		if ( (!pegawai.getInstansi().equals(peg.getInstansi())) || (!pegawai.getTahun_masuk().equalsIgnoreCase(peg.getTahun_masuk())) || (!pegawai.getTanggallahir().equals(peg.getTanggallahir())) ) {
			setNIP (pegawai);
		}	
	}

	@Override
	public int gajiTertinggiPegawai(PegawaiModel pegawai) {
		double gajiTertinggi = 0;
		for (JabatanPegawaiModel jabatan : pegawai.getJabatanPegawai()) {
			if (jabatan.getJabatan().getGajiPokok() > gajiTertinggi) {
				gajiTertinggi = jabatan.getJabatan().getGajiPokok();
			}
		}
		double gaji = gajiTertinggi + (pegawai.getInstansi().getProvinsi().getPresentase_tunjangan() * gajiTertinggi/100);
		int gajiInt = (int) gaji;
		return gajiInt;
	}
}
