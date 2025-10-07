package com.searchonmath.arxivdownloader.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DownloadResult {
    NONE(0),
    SUCCESSFUL(1),
    ERROR(2),
    NOT_DOWNLOADED(3);

    private Integer value;
}
