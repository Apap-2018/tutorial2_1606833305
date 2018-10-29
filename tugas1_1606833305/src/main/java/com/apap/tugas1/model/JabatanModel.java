package com.apap.tugas1.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table (name = "jabatan")
public class JabatanModel implements Serializable{
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Size (max = 255)
	@Column (name = "nama", nullable = false)
	private String nama;
	
	@NotNull
	@Size (max = 255)
	@Column (name = "deskripsi", nullable = false)
	private String deskripsi;
	
	@NotNull
	@Column (name = "gajiPokok", nullable = false)
	private Double gajiPokok;
	
	@JsonIgnore
	@OneToMany(mappedBy = "jabatan")
	private List<JabatanPegawaiModel> jabatanPegawai;
	
	private int jumlahPegawai;
	

	public int getJumlahPegawai() {
		return jumlahPegawai;
	}

	public void setJumlahPegawai(int jumlahPegawai) {
		this.jumlahPegawai = jumlahPegawai;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getDeskripsi() {
		return deskripsi;
	}

	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}

	public Double getGajiPokok() {
		return gajiPokok;
	}

	public void setGajiPokok(Double gajiPokok) {
		this.gajiPokok = gajiPokok;
	}

	public List<JabatanPegawaiModel> getJabatanPegawai() {
		return jabatanPegawai;
	}

	public void setJabatanPegawai(List<JabatanPegawaiModel> jabatanPegawai) {
		this.jabatanPegawai = jabatanPegawai;
	}	
}