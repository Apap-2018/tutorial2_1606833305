package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.service.JabatanPegawaiService;
import com.apap.tugas1.service.JabatanService;

@Controller
public class jabatanController {
	@Autowired
	JabatanService jabatanService;
	
	@Autowired
	JabatanPegawaiService jabatanPegawaiService;
	
	//NO 5
	@RequestMapping (value = "/jabatan/tambah", method = RequestMethod.GET)
	public String tambahJabatan (Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		return "formTambahJabatan";
	}
	
	@RequestMapping (value = "/jabatan/tambah", method = RequestMethod.POST)
	public String berhasilTambahJabatan (@ModelAttribute JabatanModel jabatan) {
		jabatanService.tambahJabatan(jabatan);
		return "berhasilTambahJabatan";
	}
	
	//NO 6
	@RequestMapping (value = "/jabatan/view", method = RequestMethod.GET)
	public String lihatJabatan (String idJabatan, Model model) {
		JabatanModel jabatan = jabatanService.findJabatanById(Long.parseLong(idJabatan));
		model.addAttribute("jabatan", jabatan);
		return "lihatJabatan";
	}
	
	//NO 7
	@RequestMapping (value = "jabatan/ubah", method = RequestMethod.GET)
	public String ubahJabatan (String idJabatan, Model model) {
		JabatanModel jabatan = jabatanService.findJabatanById(Long.parseLong(idJabatan));
		model.addAttribute("oldJabatan", jabatan);
		return "ubahJabatan";
	}
	
	@RequestMapping (value = "jabatan/ubah", method = RequestMethod.POST)
	public String updateJabatan (@ModelAttribute JabatanModel newJabatan) {
		jabatanService.ubahJabatan(newJabatan);
		return "berhasilUbahJabatan";
	}
	
	//NO 8
	@RequestMapping (value = "jabatan/hapus", method = RequestMethod.POST)
	public String hapusJabatan (String idJabatan, Model model) {
		List <JabatanPegawaiModel> listJabatan = new ArrayList <JabatanPegawaiModel>();
		JabatanModel jabatan = jabatanService.findJabatanById(Long.parseLong(idJabatan));
		listJabatan = jabatan.getJabatanPegawai();
		if (listJabatan.size() != 0) {
			return "gagalHapusJabatan";
		}
		jabatanService.hapusJabatan(Long.parseLong(idJabatan));
		return "berhasilHapusJabatan";
	}
	
	//NO 9
	@RequestMapping (value = "jabatan/viewall", method = RequestMethod.GET)
	public String semuaJabatan (Model model) {
		List <JabatanModel> semuaJabatan = jabatanService.selectAll();
		model.addAttribute("listJabatan", semuaJabatan);
		return "lihatSemuaJabatan";
	}
}