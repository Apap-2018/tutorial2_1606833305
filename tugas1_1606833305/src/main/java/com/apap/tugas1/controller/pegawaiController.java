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
		if (idProvinsi.equalsIgnoreCase("0")) {
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
		int gajiPegawaiFix = pegawaiService.gajiTertinggiPegawai(pegawai);
		model.addAttribute("nips", pegawai.getNip());
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("gaji",gajiPegawaiFix);
		return "berhasilTambahPegawai";
	}
	
	
	
	//NO 10
	@RequestMapping (value = "pegawai/termuda-tertua", method = RequestMethod.GET)
	public String PegawaiTertuaTermuda (@RequestParam ("idInstansi") String id, Model model) {
		InstansiModel instansi = instansiService.cariInstansiById(Long.parseLong(id));
		List<PegawaiModel> pegUrut = pegawaiService.getPegawaiUrut(instansi);
		
		PegawaiModel pegTua = pegUrut.get(0);
		PegawaiModel pegMuda = pegUrut.get(pegUrut.size()-1);
		int gajiTertinggiPegTua = pegawaiService.gajiTertinggiPegawai(pegTua);
		int gajiTertinggiPegMuda = pegawaiService.gajiTertinggiPegawai(pegMuda);
	
		model.addAttribute("pegTua", pegTua);
		model.addAttribute("pegMuda", pegMuda);
		model.addAttribute("gajiTua", gajiTertinggiPegTua);
		model.addAttribute("gajiMuda", gajiTertinggiPegMuda);
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
	}
}