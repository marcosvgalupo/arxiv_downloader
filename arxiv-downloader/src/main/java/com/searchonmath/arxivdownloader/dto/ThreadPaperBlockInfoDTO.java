package com.searchonmath.arxivdownloader.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ThreadPaperBlockInfoDTO {

    private Integer startIndex;

    private Integer endIndex;

    private Integer downloadResult;

    private Integer chunkSize;

    private Integer sleep;

}
