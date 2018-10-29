package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanPegawaiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;


@Controller
public class pegawaiController {
	@Autowired 
	PegawaiService pegawaiService;
	
	@Autowired
	InstansiService instansiService;
	
	@Autowired
	JabatanService jabatanService;
	
	@Autowired
	ProvinsiService provinsiService;
	
	@Autowired
	JabatanPegawaiService jabatanPegawaiService;

	
	//HOME
	@RequestMapping ("/")
	public String main (Model model) {
		List <JabatanModel> allJabatan = jabatanService.selectAll();
		List <InstansiModel> allInstansi = instansiService.selectAll();
		model.addAttribute("listJabatan", allJabatan);
		model.addAttribute("listInstansi", allInstansi);
		return "main";
	}
	
	
	
	//NO 1
	@RequestMapping ("/pegawai{nip}")
	public String tampilkan (String nip, Model model) {	
		PegawaiModel pegawai = pegawaiService.cariPegawaiByNip(nip);
		int gajiPegawaiFix = pegawaiService.gajiTertinggiPegawai(pegawai);
		//double gajiTertinggi = 0;
		//for (JabatanPegawaiModel jabatan : pegawai.getJabatanPegawai()) {
			//if (jabatan.getJabatan().getGajiPokok() > gajiTertinggi) {
				//gajiTertinggi = jabatan.getJabatan().getGajiPokok();
			//}
		//}
		//double gaji = gajiTertinggi + (pegawai.getInstansi().getProvinsi().getPresentase_tunjangan() * gajiTertinggi/100);
		//int gajiInt = (int) gaji;
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("gaji", gajiPegawaiFix);
		return "pegawaiView";
	}
	
	
	@RequestMapping (value = "/pegawai/tambah")
	public String tambahPegawai (Model model) {
		List <ProvinsiModel> allProvinsi = provinsiService.selectAll();
		List <JabatanModel> allJabatan = jabatanService.selectAll();
		model.addAttribute("pegawai", new PegawaiModel());
		model.addAttribute("allProvinsi", allProvinsi);
		model.addAttribute("allJabatan", allJabatan);
		return "formPegawai";
	}
	
	
	@RequestMapping (value = "/pegawai/cekInstansi", method = RequestMethod.GET)
	public @ResponseBody Object coba (@RequestParam ("id") String idProvinsi, Model model) {
		List <InstansiModel> instansi = new ArrayList <InstansiModel>();
		System.out.println(idProvinsi);
		if (idProvinsi.equalsIgnoreCase("0")) {
			//System.out.println("masuk 0");
			instansi = instansiService.selectAll();	
		}
		else {
			ProvinsiModel provinsi = provinsiService.findProvinsiById(Long.parseLong(idProvinsi));
			instansi = provinsi.getListInstansi();
		}
		Object inst = instansi;
		return inst;
	}
	
	@RequestMapping (value = "/pegawai/cekJabatan", method = RequestMethod.GET)
	public @ResponseBody Object jabatan (Model model) {
		List <JabatanModel> jabatan = jabatanService.selectAll();
		return jabatan;
	}
	
	
	@RequestMapping (value = "/pegawai/tambah", method = RequestMethod.POST)
	public String tambahPegawai (Model model, @ModelAttribute PegawaiModel pegawai) {
		jabatanPegawaiService.setPegawaiJab(pegawai);
		pegawaiService.setNIP(pegawai);
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("nips", pegawai.getNip());
		return "berhasilTambahPegawai";
	}
	
	
	
	//NO 10
	@RequestMapping (value = "pegawai/termuda-tertua", method = RequestMethod.GET)
	public String PegawaiTertuaTermuda (@RequestParam ("idInstansi") String id, Model model) {
		InstansiModel instansi = instansiService.cariInstansiById(Long.parseLong(id));
		List<PegawaiModel> pegUrut = pegawaiService.getPegawaiUrut(instansi);
		
		PegawaiModel pegTua = pegUrut.get(0);
		PegawaiModel pegMuda = pegUrut.get(pegUrut.size()-1);
		
		//Hitung Tua
		//List <String> namaJabatanPegawaiTua = new ArrayList<String>();
		int gajiTertinggiPegTua = pegawaiService.gajiTertinggiPegawai(pegTua);
		int gajiTertinggiPegMuda = pegawaiService.gajiTertinggiPegawai(pegMuda);
		
		//double gajiTertinggiTua = 0;
		//for (JabatanPegawaiModel jabatan : pegTua.getJabatanPegawai()) {
			//namaJabatanPegawaiTua.add(jabatan.getJabatan().getNama());
			//if (jabatan.getJabatan().getGajiPokok() > gajiTertinggiTua) {
				//gajiTertinggiTua = jabatan.getJabatan().getGajiPokok();
			//}
		//}
		//double gajiTua = gajiTertinggiTua + (pegTua.getInstansi().getProvinsi().getPresentase_tunjangan() * gajiTertinggiTua/100);		
		
		//Hitung Muda
		//List <String> namaJabatanMuda = new ArrayList<String>();
		//double gajiTertinggiMuda = 0;
		
	//	for (JabatanPegawaiModel jabatan : pegMuda.getJabatanPegawai()) {
		//	namaJabatanMuda.add(jabatan.getJabatan().getNama());
			//if (jabatan.getJabatan().getGajiPokok() > gajiTertinggiMuda) {
				//gajiTertinggiMuda = jabatan.getJabatan().getGajiPokok();
			//}
		//}
		//double gajiMuda = gajiTertinggiMuda + (pegMuda.getInstansi().getProvinsi().getPresentase_tunjangan() * gajiTertinggiMuda/100);
		
		model.addAttribute("pegTua", pegTua);
		model.addAttribute("pegMuda", pegMuda);
		//model.addAttribute("listJabatanTua", namaJabatanPegawaiTua);
		model.addAttribute("gajiTua", gajiTertinggiPegTua);
		model.addAttribute("gajiMuda", gajiTertinggiPegMuda);
		//model.addAttribute("listJabatanMuda", namaJabatanMuda);
		model.addAttribute("instansi", instansi);
		model.addAttribute("kotaInstansi", instansi.getProvinsi().getNama());
		return "lihatPegawaiTertua-Termuda";
	}
	

	
	//NO 4
	@RequestMapping (value = "pegawai/cari", method = RequestMethod.GET)
	public String cariPegawaiGet (@RequestParam (value = "idProvinsi", required = false) String idProvinsi,
								@RequestParam (value = "idInstansi", required = false) String idInstansi,
								@RequestParam (value = "idJabatan", required = false) String idJabatan,
								Model model) {
		
		List <ProvinsiModel> provinsi = provinsiService.selectAll();
		List <InstansiModel> instansi = instansiService.selectAll();
		List <JabatanModel> jabatan = jabatanService.selectAll();
		model.addAttribute("provinsi", provinsi);
		model.addAttribute("instansi", instansi);
		model.addAttribute("jabatan", jabatan);
		System.out.println(idProvinsi + idInstansi + idJabatan + "haha");
		List <PegawaiModel> pegawai = new ArrayList<PegawaiModel>();
		
		// cek jika sudah ada yang di isi untuk menolak yang awal masuk kesini
		if(!(idProvinsi == null || idInstansi == null || idJabatan == null)){
			//1. isi provinsi aja
			if ( (!idProvinsi.equalsIgnoreCase("0")) && (idInstansi.equalsIgnoreCase("0")) && (idJabatan.equalsIgnoreCase("0")) ) {
				pegawai = provinsiService.pegawaiSeProvinsi(Long.parseLong(idProvinsi));
			}
			
			//2. instansi aja atau 3. provinsi dan instansi)
			else if ( (idProvinsi.equalsIgnoreCase("0") && (!idInstansi.equalsIgnoreCase("0"))  && idJabatan.equalsIgnoreCase("0")) || 
					( (!idProvinsi.equalsIgnoreCase("0")) && (!idInstansi.equalsIgnoreCase("0")) && idJabatan.equalsIgnoreCase("0") )) {
				pegawai = instansiService.pegawaiSeInstansi(Long.parseLong(idInstansi));
			}
				
			//4. jabatan aja
			else if (idProvinsi.equalsIgnoreCase("0") && idInstansi.equalsIgnoreCase("0") && (!idJabatan.equalsIgnoreCase("0")) ) {
				pegawai = jabatanService.pagawaiSeJabatan(Long.parseLong(idJabatan));
			}
				
			//5. instansi dan jabatan
			else if(idProvinsi.equalsIgnoreCase("0") && (!idInstansi.equalsIgnoreCase("0")) && (!idJabatan.equalsIgnoreCase("0")) ) {
				List <PegawaiModel> pegSementara = instansiService.pegawaiSeInstansi(Long.parseLong(idInstansi));
				//filter yang jabatannya sesuai
				for (PegawaiModel ps : pegSementara) {
					for (JabatanPegawaiModel jb : ps.getJabatanPegawai()) {
						if(jb.getJabatan().getId() == Long.parseLong(idJabatan)) {
							pegawai.add(ps);
						}
					}
				}
			}
				
			//6. provinsi dan jabatan
			else if ( (!idProvinsi.equalsIgnoreCase("0")) && idInstansi.equalsIgnoreCase("0") && (!idJabatan.equalsIgnoreCase("0"))) {
				List <PegawaiModel> pegSementara = provinsiService.pegawaiSeProvinsi(Long.parseLong(idProvinsi));
				for (PegawaiModel peg : pegSementara) {
					for (JabatanPegawaiModel jabPeg : peg.getJabatanPegawai()) {
						if (jabPeg.getJabatan().getId() == Long.parseLong(idJabatan)) {
							pegawai.add(peg);
						}
					}
				}
			}
			
			//7. ketiga tiganya diisi
			else if ((!idProvinsi.equalsIgnoreCase("0")) && (!idInstansi.equalsIgnoreCase("0")) && (!idJabatan.equalsIgnoreCase("0"))){
				List <PegawaiModel> pegSeInstansi = instansiService.pegawaiSeInstansi(Long.parseLong(idInstansi));
				for (PegawaiModel ps : pegSeInstansi) {
					for (JabatanPegawaiModel jabPeg : ps.getJabatanPegawai()) {
						if (jabPeg.getJabatan().getId() == Long.parseLong(idJabatan)) {
							pegawai.add(ps);
						}
					}
				}
			}
		
			model.addAttribute("pegawai", pegawai);
			return "cariPegawai";
		}
		
		else {
			model.addAttribute("pegawai", pegawai);
			return "cariPegawai";
		}
	}
	
	
	//NO 2
	@RequestMapping (value = "pegawai/ubah", method = RequestMethod.GET)
	public String ubahPegawai (@RequestParam (value = "nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.cariPegawaiByNip(nip);
		List <ProvinsiModel> allProvinsi = provinsiService.selectAll();
		List <JabatanModel> allJabatan = jabatanService.selectAll();
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("size", pegawai.getJabatanPegawai().size());
		model.addAttribute("allProvinsi", allProvinsi);
		model.addAttribute("allJabatan", allJabatan);
		return "editPegawai";
	}
	
	
	@RequestMapping (value = "pegawai/ubah", method = RequestMethod.POST)
	public String simpanUbahPegawai (@ModelAttribute PegawaiModel pegawai, Model model) {
		jabatanPegawaiService.setPegawaiJab(pegawai);
		PegawaiModel pegawaiLama = pegawaiService.cariPegawaiById(pegawai.getId());
		pegawaiService.hapusJabatanPegawaiPegawai(pegawaiLama);
		pegawaiService.resetNIP(pegawai);
		pegawaiService.updatePegawai(pegawai, pegawai.getId());
		model.addAttribute("nips", pegawai.getNip());
		return "berhasilUbahPegawai";
		
		
		
		/**
		 * 
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String tanggal = formatter.format(pegawai.getTanggallahir());
		java.util.Date formattedTglLahir =  formatter.parse(tanggal);
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
		String tanggalLahirFormat = format.format(formattedTglLahir);
		
		String tglLahir = tanggalLahirFormat.substring(0, 4) + ""+ tanggalLahirFormat.substring(6); //ddmmyy
		String tanggalBaru ="" + pegawai.getTanggallahir() + "";
		
		InstansiModel instansi = instansiService.cariInstansiById(pegawai.getInstansi().getId());
		List <PegawaiModel> allPegawai = instansi.getPegawai_instansi();
		int indexPegawai = 1;
		for (PegawaiModel peg : allPegawai) {
			String tgl = "" + peg.getTanggallahir() + "";
			
			if (peg.getTahun_masuk().equalsIgnoreCase(pegawai.getTahun_masuk()) && tgl.equalsIgnoreCase(tanggalBaru)) {
				indexPegawai++;
			}
		}
		
		String no_urut = "";
		if (indexPegawai < 10) {
			no_urut = "0" + indexPegawai;
		}
		else {
			no_urut = "" + indexPegawai;
		}
		
		String kodeInstansi = ""+pegawai.getInstansi().getId()+"";
		
		String nip = kodeInstansi + tglLahir + pegawai.getTahun_masuk() + no_urut;
		*/
		//pegawai.setNip(nip);
		//pegawaiService.updatePegawai(pegawai, pegawai.getId());
		
	}
}
