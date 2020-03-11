package com.olympus.nbva.assets;

public class AssetData {
	private long assetId;
	private String equipType;
	private String customerID; // agreementNum
	private String equipDesc;
	private String Model;
	private String serNum;
	private int qty;
	private String equipAddr1;
	private String equipAddr2; 
	private String equipCity;
	private String equipState;
	private String equipZip;
	private double equipCost;
	private double residAmt;
	private double aRentalAmt;
	private int dispCode;
	private String termDate;
	private double floorPrice;
	private String calcOpt;
	private double rollPrice;
	private double buyPrice;
	private double rtnPrice;

	/****************************************************************************************************************************************************/
	
	
	
	
	
	public String getCalcOpt() {
		return calcOpt;
	}


	public double getRollPrice() {
		return rollPrice;
	}


	public void setRollPrice(double rollPrice) {
		this.rollPrice = rollPrice;
	}


	public double getBuyPrice() {
		return buyPrice;
	}


	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}


	public double getRtnPrice() {
		return rtnPrice;
	}


	public void setRtnPrice(double rtnPrice) {
		this.rtnPrice = rtnPrice;
	}


	public void setCalcOpt(String calcOpt) {
		this.calcOpt = calcOpt;
	}
	
	
	public String getTermDate() {
		return termDate;
	}


	


	public double getFloorPrice() {
		return floorPrice;
	}

	public void setFloorPrice(double floorPrice) {
		this.floorPrice = floorPrice;
	}

	public void setTermDate(String termDate) {
		this.termDate = termDate;
	}
	
	public long getAssetId() {
		return assetId;
	}
	public int getDispCode() {
		return dispCode;
	}
	public void setDispCode(int dispCode) {
		this.dispCode = dispCode;
	}
	public double getEquipCost() {
		return equipCost;
	}
	public void setEquipCost(double equipCost) {
		this.equipCost = equipCost;
	}
	public double getResidAmt() {
		return residAmt;
	}
	public void setResidAmt(double residAmt) {
		this.residAmt = residAmt;
	}
	public double getaRentalAmt() {
		return aRentalAmt;
	}
	public void setaRentalAmt(double aRentalAmt) {
		this.aRentalAmt = aRentalAmt;
	}
	public void setAssetId(long assetId) {
		this.assetId = assetId;
	}
	
	public String getEquipType() {
		return equipType;
	}
	public void setEquipType(String equipType) {
		this.equipType = equipType;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getEquipDesc() {
		return equipDesc;
	}
	public void setEquipDesc(String equipDesc) {
		this.equipDesc = equipDesc;
	}
	public String getModel() {
		return Model;
	}
	public void setModel(String model) {
		Model = model;
	}
	public String getSerNum() {
		return serNum;
	}
	public void setSerNum(String serNum) {
		this.serNum = serNum;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getEquipAddr1() {
		return equipAddr1;
	}
	public void setEquipAddr1(String equipAddr1) {
		this.equipAddr1 = equipAddr1;
	}
	public String getEquipAddr2() {
		return equipAddr2;
	}
	public void setEquipAddr2(String equipAddr2) {
		this.equipAddr2 = equipAddr2;
	}
	public String getEquipCity() {
		return equipCity;
	}
	public void setEquipCity(String equipCity) {
		this.equipCity = equipCity;
	}
	public String getEquipState() {
		return equipState;
	}
	public void setEquipState(String equipState) {
		this.equipState = equipState;
	}
	public String getEquipZip() {
		return equipZip;
	}
	public void setEquipZip(String equipZip) {
		this.equipZip = equipZip;
	}
	

	
	
	/****************************************************************************************************************************************************/

} // End Class
