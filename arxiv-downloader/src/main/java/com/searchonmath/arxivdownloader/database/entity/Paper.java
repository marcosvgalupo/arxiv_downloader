package com.searchonmath.arxivdownloader.database.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(schema = "airflow", name = "oai")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Paper implements Serializable {

    @Id
    private String id;

    private String identifier;

    private LocalDateTime datestamp;

    private LocalDateTime created;

    private LocalDateTime updated;

    private String authors;

    private String title;

    private String categories;

    @Column(name="report_no")
    private String reportNo;

    @Column(name="journal_ref")
    private String journalRef;

    private String doi;

    @Column(name = "abstract")
    private String resume;

    private String comments;

    private Integer downloadResult;

}
