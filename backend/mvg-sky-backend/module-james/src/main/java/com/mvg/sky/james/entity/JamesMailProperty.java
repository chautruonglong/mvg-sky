package com.mvg.sky.james.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "james_mail_property", indexes = {@Index(name = "index_property_line_number", columnList = "property_line_number")})
public class JamesMailProperty {
    @Id
    @Column(name = "property_id", nullable = false)
    private Long id;

    @Column(name = "property_line_number", nullable = false)
    private Integer propertyLineNumber;

    @Column(name = "property_local_name", nullable = false, length = 500)
    private String propertyLocalName;

    @Column(name = "property_name_space", nullable = false, length = 500)
    private String propertyNameSpace;

    @Column(name = "property_value", nullable = false, length = 1024)
    private String propertyValue;

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "mailbox_id", referencedColumnName = "mailbox_id"), @JoinColumn(name = "mail_uid", referencedColumnName = "mail_uid")})
    private JamesMail jamesMail;

    public JamesMail getJamesMail() {return jamesMail;}

    public void setJamesMail(JamesMail jamesMail) {this.jamesMail = jamesMail;}

    public String getPropertyValue() {return propertyValue;}

    public void setPropertyValue(String propertyValue) {this.propertyValue = propertyValue;}

    public String getPropertyNameSpace() {return propertyNameSpace;}

    public void setPropertyNameSpace(String propertyNameSpace) {this.propertyNameSpace = propertyNameSpace;}

    public String getPropertyLocalName() {return propertyLocalName;}

    public void setPropertyLocalName(String propertyLocalName) {this.propertyLocalName = propertyLocalName;}

    public Integer getPropertyLineNumber() {return propertyLineNumber;}

    public void setPropertyLineNumber(Integer propertyLineNumber) {this.propertyLineNumber = propertyLineNumber;}

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}
}
