package com.searchonmath.arxivdownloader.client;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "arxivClient", url = "${config.arxiv-path}")
public interface ArxivFeignClient {

    @GetMapping(value = "/{id}")
    Response getPage(@PathVariable("id") String id);

}
