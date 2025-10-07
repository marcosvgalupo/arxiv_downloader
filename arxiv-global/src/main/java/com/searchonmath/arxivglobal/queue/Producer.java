package com.searchonmath.arxivglobal.queue;

import org.springframework.stereotype.Component;

@Component
public interface Producer {

    void send(String message);

}
