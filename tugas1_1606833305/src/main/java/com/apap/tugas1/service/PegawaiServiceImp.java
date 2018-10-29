package com.apap.tugas1.service;

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
		// TODO Auto-generated method stub
		return pegawaiDb.findAll();
	}

	@Override
	public List<PegawaiModel> getPegawaiUrut(InstansiModel instansi) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByInstansiOrderByTanggallahirAsc(instansi);
	}

	@Override
	public void deletePegawaiByNip(String nip) {
		PegawaiModel pegawai = pegawaiDb.findPegawaiByNip(nip);
		pegawaiDb.delete(pegawai);	
	}

	@Override
	public PegawaiModel cariPegawaiById(long id) {
		// TODO Auto-generated method stub
		return pegawaiDb.findPegawaiById(id);
	}

	@Override
	public void updatePegawai(PegawaiModel pegawaiBaru, long id) {
		// TODO Auto-generated method stub
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
		List <PegawaiModel> pegawaiSeInstansi = pegawai.getInstansi().getPegawai_instansi();
		int indexSama = 1;
		for (PegawaiModel peg : pegawaiSeInstansi) {
			if (pegawai.getTahun_masuk().equals(peg.getTahun_masuk()) && pegawai.getTanggallahir().equals(peg.getTanggallahir())) {
				indexSama ++;
			}
		}
		
		String noUrut = "";
		if (indexSama < 10) {
			noUrut = "0" + indexSama;
		}
		else {
			noUrut = "" + indexSama;
		}
		
		String tgl = "" + pegawai.getTanggallahir() + "";
		String tglDMY = tgl.substring(8,10) + tgl.substring(5,7) + tgl.substring(2,4);
		String nip = pegawai.getInstansi().getId() + tglDMY + pegawai.getTahun_masuk() + noUrut;
		pegawai.setNip(nip);		
	}

	@Override
	public void resetNIP(PegawaiModel pegawai) {
		PegawaiModel peg = pegawaiDb.findPegawaiById(pegawai.getId());
		
		if ( (!pegawai.getInstansi().equals(peg.getInstansi())) || (!pegawai.getTahun_masuk().equalsIgnoreCase(peg.getTahun_masuk())) || (!pegawai.getTanggallahir().equals(peg.getTanggallahir())) ) {
			setNIP (pegawai);
			//System.out.println("masuk sini ganti nip" +  pegawai.getNip() + "nip baru" );
		}
		
		//PegawaiModel pegawai = pegawaiDb.findPegawaiById(id);
		
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
