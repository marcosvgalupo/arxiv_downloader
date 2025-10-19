package com.searchonmath.arxivglobal.database.repository;

import com.searchonmath.arxivglobal.database.entity.Paper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PaperRepository extends CrudRepository<Paper, String> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE oai SET co_src = :coSrc WHERE id = :id", nativeQuery = true)
    void savePaperCoSrc(@Param("id") String id, @Param("coSrc") byte[] coSrc);

}
