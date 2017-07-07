package com.xpp.moblie.entity;

import com.j256.ormlite.field.DatabaseField;

public class BaseCuanhuoQuery {
	//输出
		@DatabaseField(generatedId = true)
		protected int id;
		@DatabaseField
		private String WERKS;//工厂
		@DatabaseField
		private String WERKS_NAME;//工厂描述
		@DatabaseField
		private String LOCCO;//打码号 
		@DatabaseField
		private String DATUM;//生产日期
		@DatabaseField
		private String MATNR;//物料号 
		@DatabaseField
		private String MAKTX;//物料描述
		@DatabaseField
		private String CHARG;//批次
		@DatabaseField
		private String VBELN_VL;//交货单
		@DatabaseField
		private String VBELN_VA;//销售订单 
		@DatabaseField
		private String KUNAG;//售达方 
		@DatabaseField
		private String KUNAG_NAME;//售达方名称
		@DatabaseField
		private String KUNWE;//送达方 
		@DatabaseField
		private String KUNWE_NAME;//送达方名称
		@DatabaseField
		private String PODAT;//POD日期
		public String getWERKS() {
			return WERKS;
		}
		public void setWERKS(String wERKS) {
			WERKS = wERKS;
		}
		public String getWERKS_NAME() {
			return WERKS_NAME;
		}
		public void setWERKS_NAME(String wERKS_NAME) {
			WERKS_NAME = wERKS_NAME;
		}
		public String getLOCCO() {
			return LOCCO;
		}
		public void setLOCCO(String lOCCO) {
			LOCCO = lOCCO;
		}
		public String getDATUM() {
			return DATUM;
		}
		public void setDATUM(String dATUM) {
			DATUM = dATUM;
		}
		public String getMATNR() {
			return MATNR;
		}
		public void setMATNR(String mATNR) {
			MATNR = mATNR;
		}
		public String getMAKTX() {
			return MAKTX;
		}
		public void setMAKTX(String mAKTX) {
			MAKTX = mAKTX;
		}
		public String getCHARG() {
			return CHARG;
		}
		public void setCHARG(String cHARG) {
			CHARG = cHARG;
		}
		public String getVBELN_VL() {
			return VBELN_VL;
		}
		public void setVBELN_VL(String vBELN_VL) {
			VBELN_VL = vBELN_VL;
		}
		public String getVBELN_VA() {
			return VBELN_VA;
		}
		public void setVBELN_VA(String vBELN_VA) {
			VBELN_VA = vBELN_VA;
		}
		public String getKUNAG() {
			return KUNAG;
		}
		public void setKUNAG(String kUNAG) {
			KUNAG = kUNAG;
		}
		public String getKUNAG_NAME() {
			return KUNAG_NAME;
		}
		public void setKUNAG_NAME(String kUNAG_NAME) {
			KUNAG_NAME = kUNAG_NAME;
		}
		public String getKUNWE() {
			return KUNWE;
		}
		public void setKUNWE(String kUNWE) {
			KUNWE = kUNWE;
		}
		public String getKUNWE_NAME() {
			return KUNWE_NAME;
		}
		public void setKUNWE_NAME(String kUNWE_NAME) {
			KUNWE_NAME = kUNWE_NAME;
		}
		public String getPODAT() {
			return PODAT;
		}
		public void setPODAT(String pODAT) {
			PODAT = pODAT;
		}
		@Override
		public String toString() {
			return "BaseCuanhuoQuery [WERKS=" + WERKS + ", WERKS_NAME="
					+ WERKS_NAME + ", LOCCO=" + LOCCO + ", DATUM=" + DATUM
					+ ", MATNR=" + MATNR + ", MAKTX=" + MAKTX + ", CHARG="
					+ CHARG + ", VBELN_VL=" + VBELN_VL + ", VBELN_VA="
					+ VBELN_VA + ", KUNAG=" + KUNAG + ", KUNAG_NAME="
					+ KUNAG_NAME + ", KUNWE=" + KUNWE + ", KUNWE_NAME="
					+ KUNWE_NAME + ", PODAT=" + PODAT + "]";
		}
		
}
