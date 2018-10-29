package com.apap.tugas1.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;

@Repository
public interface JabatanPegawaiDb extends JpaRepository<JabatanPegawaiModel, Long>{
	List <JabatanPegawaiModel> findJabatanPegawaiByJabatan (long idJabatan);
	List <JabatanPegawaiModel> findByPegawai(PegawaiModel model);
}
