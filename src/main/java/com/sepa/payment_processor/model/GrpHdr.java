package com.sepa.payment_processor.model;

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
public class GrpHdr {
	
	@XmlElement(name = "MsgId")
    private String msgId;
    @XmlElement(name = "CreDtTm")
    private String creDtTm;
    @XmlElement(name = "NbOfTxs")
    private String nbOfTxs;
    @XmlElement(name = "CtrlSum")
    private String ctrlSum;
    @XmlElement(name = "InitgPty")
    private InitgPty initgPty;
    
}
