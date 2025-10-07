package com.searchonmath.arxivglobal.queue;

import org.springframework.stereotype.Component;

@Component
public interface Consumer {
    void listen(String message);
}
