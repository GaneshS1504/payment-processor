package com.sepa.payment_processor.model;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public class PmtInf {
	
    @XmlElement(name = "PmtInfId")
    private String pmtInfId;
    @XmlElement(name = "PmtMtd")
    private String pmtMtd;
    @XmlElement(name = "ReqdExctnDt")
    private String reqdExctnDt;
    @XmlElement(name = "Dbtr")
    private Party dbtr;
    @XmlElement(name = "DbtrAcct")
    private Account dbtrAcct;
    @XmlElement(name = "DbtrAgt")
    private Agent dbtrAgt;
    @XmlElement(name = "CdtTrfTxInf")
    private List<CdtTrfTxInf> cdtTrfTxInf;
   
}
