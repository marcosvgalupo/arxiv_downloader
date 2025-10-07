package com.searchonmath.arxivdownloader.database.repository;

import com.searchonmath.arxivdownloader.database.entity.Paper;
import com.searchonmath.arxivdownloader.database.entity.book.PaperBook;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaperRepository extends CrudRepository<Paper, String> {

    @Query(value = PaperBook.FIND_PAPERS_FOR_DOWNLOAD, nativeQuery = true)
    List<Paper> findPapersForDownload(@Param("startIndex") Integer startIndex,
                                      @Param("endIndex") Integer endIndex,
                                      @Param("downloadResult") Integer downloadResult,
                                      @Param("chunkSize") Integer chunkSize);

    @Query(value = PaperBook.FIND_MAX_INDEX, nativeQuery = true)
    Integer findMaxIndex();
}
