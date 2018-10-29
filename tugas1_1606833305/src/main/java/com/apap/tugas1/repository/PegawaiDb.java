package com.apap.tugas1.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

@Repository
public interface PegawaiDb extends JpaRepository<PegawaiModel, Long>{
	PegawaiModel findPegawaiByNip (String nip);
	PegawaiModel findPegawaiById (long id);
	List <PegawaiModel> findByInstansiOrderByTanggallahirAsc (InstansiModel instansi);
	List<PegawaiModel> findByInstansi(InstansiModel instansi);
	List <PegawaiModel> findByInstansiOrderByIdAsc (InstansiModel instansi);
}

