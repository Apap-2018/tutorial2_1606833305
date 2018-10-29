package com.apap.tugas1.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table (name ="jabatan_pegawai")
public class JabatanPegawaiModel implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	 	 

	@ManyToOne
	@JoinColumn(name = "id_jabatan")
	private JabatanModel jabatan;
	 
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_pegawai")
	private PegawaiModel pegawai;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public JabatanModel getJabatan() {
		return jabatan;
	}

	public void setJabatan(JabatanModel jabatan) {
		this.jabatan = jabatan;
	}

	public PegawaiModel getPegawai() {
		return pegawai;
	}

	public void setPegawai(PegawaiModel pegawai) {
		this.pegawai = pegawai;
	}	
}