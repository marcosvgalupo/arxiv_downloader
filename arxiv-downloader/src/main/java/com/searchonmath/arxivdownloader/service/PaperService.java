package com.searchonmath.arxivdownloader.service;

import org.springframework.stereotype.Service;

@Service
public interface PaperService {
    void downloadFromArxivAndProcess();
}
