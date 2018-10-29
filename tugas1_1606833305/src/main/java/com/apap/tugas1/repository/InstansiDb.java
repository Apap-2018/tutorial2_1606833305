package com.apap.tugas1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.apap.tugas1.model.InstansiModel;

@Repository
public interface InstansiDb extends JpaRepository<InstansiModel, Long>{
	InstansiModel findInstansiById (long id_instansi);
}
