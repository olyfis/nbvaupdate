package com.olympus.nbva.contracts;

 public class ContractData {
	
	
	private String contractID;
	private String customerName;
	private String commenceDate;
	private long term;
	private String termDate;
	private double equipPayment;
	private double servicePayment;
	private String invoiceCode;
	private String contractStatus;
	private String purOption;
	private int monthsRemain;
	private String finalInvDueDate;
	private String effectiveDate;
	private String termPlusSpan;
	/****************************************************************************************************************************************************/
	public String getTermPlusSpan() {
		return termPlusSpan;
	}
	public void setTermPlusSpan(String termPlusSpan) {
		this.termPlusSpan = termPlusSpan;
	}
	
	
	
	
	
	public String getEffectiveDate() {
		return effectiveDate;
	}
	
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
	
	public String getFinalInvDueDate() {
		return finalInvDueDate;
	}

	public void setFinalInvDueDate(String finalInvDueDate) {
		this.finalInvDueDate = finalInvDueDate;
	}
	
	
	public String getPurOption() {
		return purOption;
	}
	
	public int getMonthsRemain() {
		return monthsRemain;
	}
	public void setMonthsRemain(int monthsRemain) {
		this.monthsRemain = monthsRemain;
	}
	public void setPurOption(String purOption) {
		this.purOption = purOption;
	}
	public String getInvoiceCode() {
		return invoiceCode;
	}
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	
	public String getContractStatus() {
		return contractStatus;
	}
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}
	public String getContractID() {
		return contractID;
	}
	public void setContractID(String contractID) {
		this.contractID = contractID;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getCommenceDate() {
		return commenceDate;
	}
	public void setCommenceDate(String commenceDate) {
		this.commenceDate = commenceDate;
	}
	public long getTerm() {
		return term;
	}
	public void setTerm(long term) {
		this.term = term;
	}
	public String getTermDate() {
		return termDate;
	}
	public void setTermDate(String termDate) {
		this.termDate = termDate;
	}
	public double getEquipPayment() {
		return equipPayment;
	}
	public void setEquipPayment(double equipPayment) {
		this.equipPayment = equipPayment;
	}
	public double getServicePayment() {
		return servicePayment;
	}
	public void setServicePayment(double servicePayment) {
		this.servicePayment = servicePayment;
	}
	
	/****************************************************************************************************************************************************/

	
} // End Class
