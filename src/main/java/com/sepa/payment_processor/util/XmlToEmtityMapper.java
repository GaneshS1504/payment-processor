package com.sepa.payment_processor.util;

import org.springframework.stereotype.Component;

import com.sepa.payment_processor.entity.TransactionSummary;
import com.sepa.payment_processor.model.CdtTrfTxInf;

@Component
public class XmlToEmtityMapper {
		
	public TransactionSummary mapToEntity(CdtTrfTxInf cdtTrfTxInf, String dbtrName,
			String dbtrIban, String dbtrBic, String type, String settlementDate) {
		
		TransactionSummary entity = new TransactionSummary();
		entity.setAmount(cdtTrfTxInf.getAmt().getInstdAmt().getValue());
		entity.setCdtrName(cdtTrfTxInf.getCdtr().getNm());
		entity.setCdtrAddress(cdtTrfTxInf.getCdtr().getAddress().getAddress());
		entity.setCdtrBic(cdtTrfTxInf.getCdtrAgt().getFinInstnId().getBic());
		entity.setCdtrIban(cdtTrfTxInf.getCdtrAcct().getId().getIban());
		entity.setSettlementDate(settlementDate);
		
		entity.setDbtrName(dbtrName);
		entity.setDbtrIban(dbtrIban);
		entity.setDbtrBic(dbtrBic);
		entity.setType(type);
		return entity;
	}
}
