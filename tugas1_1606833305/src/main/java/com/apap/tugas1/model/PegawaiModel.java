package com.apap.tugas1.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table (name = "pegawai")
public class PegawaiModel implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "nip", nullable = false, unique = true)
	private String nip;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "nama", nullable = false)
	private String nama;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "tempat_lahir", nullable = false)
	private String tempat_lahir;
	
	@NotNull
	@Column (name = "tanggal_lahir", nullable = false)
	private Date tanggallahir;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "tahun_masuk", nullable = false)
	private String tahun_masuk;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name = "id_instansi", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private InstansiModel instansi;
	
	@OneToMany (mappedBy = "pegawai", cascade = CascadeType.ALL)
	private List<JabatanPegawaiModel> jabatanPegawai;
	 
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getTempat_lahir() {
		return tempat_lahir;
	}

	public void setTempat_lahir(String tempat_lahir) {
		this.tempat_lahir = tempat_lahir;
	}

	public Date getTanggallahir() {
		return tanggallahir;
	}

	public void setTanggallahir(Date tanggallahir) {
		this.tanggallahir = tanggallahir;
	}

	public String getTahun_masuk() {
		return tahun_masuk;
	}

	public void setTahun_masuk(String tahun_masuk) {
		this.tahun_masuk = tahun_masuk;
	}

	public InstansiModel getInstansi() {
		return instansi;
	}

	public void setInstansi(InstansiModel instansi) {
		this.instansi = instansi;
	}

	public List<JabatanPegawaiModel> getJabatanPegawai() {
		return jabatanPegawai;
	}

	public void setJabatanPegawai(List<JabatanPegawaiModel> jabatanPegawai) {
		this.jabatanPegawai = jabatanPegawai;
	}
}