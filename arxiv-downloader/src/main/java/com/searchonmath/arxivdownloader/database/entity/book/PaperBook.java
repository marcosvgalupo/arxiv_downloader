package com.searchonmath.arxivdownloader.database.entity.book;

public class PaperBook {
    public static final String FIND_PAPERS_FOR_DOWNLOAD =
            "SELECT * FROM (" +
                    "  SELECT o.*, ROW_NUMBER() OVER (ORDER BY id) AS rn " +
                    "  FROM oai o " +
                    "  WHERE download_result = :downloadResult" +
                    ") AS ranked " +
                    "WHERE rn >= :startIndex AND rn < :endIndex " +
                    "LIMIT :chunkSize";

    public static final String FIND_MAX_INDEX = "SELECT COUNT(id) FROM oai";
}
