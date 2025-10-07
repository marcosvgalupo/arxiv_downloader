package com.searchonmath.arxivdownloader.database.entity.book;

public class PaperBook {
    public static final String FIND_PAPERS_FOR_DOWNLOAD =
            "SELECT * FROM oai2 WHERE `index` >= :startIndex AND `index` < :endIndex AND download_result = :downloadResult LIMIT :chunkSize";

    public static final String FIND_MAX_INDEX = "SELECT MAX(`index`) FROM oai2";
}
