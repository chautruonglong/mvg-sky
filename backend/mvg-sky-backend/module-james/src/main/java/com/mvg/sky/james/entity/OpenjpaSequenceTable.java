package com.mvg.sky.james.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "openjpa_sequence_table")
public class OpenjpaSequenceTable {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "sequence_value")
    private Long sequenceValue;

    public Long getSequenceValue() {return sequenceValue;}

    public void setSequenceValue(Long sequenceValue) {this.sequenceValue = sequenceValue;}

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}
}
