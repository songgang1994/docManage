package com.dm.entity;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

import com.dm.dto.BaseDto;

/**
 * 自定义数据项 Entity
 * @author 张丽
 *
 */

public class DocDetailCustomInfoEntity extends BaseDto {
	/*
	 * 文档编号
	 */
	private String documentCode;
	/*
	 * 字段名1
	 */
	private String customeItem1;
	/*
	 * 字段名2
	 */
	private String customeItem2;
	/*
	 * 字段名3
	 */
	private String customeItem3;
	/*
	 * 字段名4
	 */
	private String customeItem4;
	/*
	 * 字段名5
	 */
	private String customeItem5;
	/*
	 * 字段名6
	 */
	private String customeItem6;
	/*
	 * 字段名7
	 */
	private String customeItem7;
	/*
	 * 字段名8
	 */
	private String customeItem8;
	/*
	 * 字段名9
	 */
	private String customeItem9;
	/*
	 * 字段名10
	 */
	private String customeItem10;
	/*
	 * 字段名11
	 */
	private String customeItem11;
	/*
	 * 字段名12
	 */
	private String customeItem12;
	/*
	 * 字段名13
	 */
	private String customeItem13;
	/*
	 * 字段名14
	 */
	private String customeItem14;
	/*
	 * 字段名15
	 */
	private String customeItem15;
	/*
	 * 字段名16
	 */
	private String customeItem16;
	/*
	 * 字段名17
	 */
	private String customeItem17;
	/*
	 * 字段名18
	 */
	private String customeItem18;
	/*
	 * 字段名19
	 */
	private String customeItem19;
	/*
	 * 字段名20
	 */
	private String customeItem20;
	/*
	 * 字段名21
	 */
	private String customeItem21;
	/*
	 * 字段名22
	 */
	private String customeItem22;
	/*
	 * 字段名23
	 */
	private String customeItem23;
	/*
	 * 字段名24
	 */
	private String customeItem24;
	/*
	 * 字段名25
	 */
	private String customeItem25;
	/*
	 * 字段名26
	 */
	private String customeItem26;
	/*
	 * 字段名27
	 */
	private String customeItem27;
	/*
	 * 字段名28
	 */
	private String customeItem28;
	/*
	 * 字段名29
	 */
	private String customeItem29;
	/*
	 * 字段名30
	 */
	private String customeItem30;
	/*
	 * 字段名31
	 */
	private String customeItem31;
	/*
	 * 字段名32
	 */
	private String customeItem32;
	/*
	 * 字段名33
	 */
	private String customeItem33;
	/*
	 * 字段名34
	 */
	private String customeItem34;
	/*
	 * 字段名35
	 */
	private String customeItem35;
	/*
	 * 字段名36
	 */
	private String customeItem36;
	/*
	 * 字段名37
	 */
	private String customeItem37;
	/*
	 * 字段名38
	 */
	private String customeItem38;
	/*
	 * 字段名39
	 */
	private String customeItem39;
	/*
	 * 字段名40
	 */
	private String customeItem40;
	/*
	 * 字段名41
	 */
	private String customeItem41;
	/*
	 * 字段名42
	 */
	private String customeItem42;
	/*
	 * 字段名43
	 */
	private String customeItem43;
	/*
	 * 字段名44
	 */
	private String customeItem44;
	/*
	 * 字段名45
	 */
	private String customeItem45;
	/*
	 * 字段名46
	 */
	private String customeItem46;
	/*
	 * 字段名47
	 */
	private String customeItem47;
	/*
	 * 字段名48
	 */
	private String customeItem48;
	/*
	 * 字段名49
	 */
	private String customeItem49;
	/*
	 * 字段名50
	 */
	private String customeItem50;

	public String getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}
	public String getCustomeItem1() {
		return customeItem1;
	}
	public void setCustomeItem1(String customeItem1) {
		this.customeItem1 = customeItem1;
	}
	public String getCustomeItem2() {
		return customeItem2;
	}
	public void setCustomeItem2(String customeItem2) {
		this.customeItem2 = customeItem2;
	}
	public String getCustomeItem3() {
		return customeItem3;
	}
	public void setCustomeItem3(String customeItem3) {
		this.customeItem3 = customeItem3;
	}
	public String getCustomeItem4() {
		return customeItem4;
	}
	public void setCustomeItem4(String customeItem4) {
		this.customeItem4 = customeItem4;
	}
	public String getCustomeItem5() {
		return customeItem5;
	}
	public void setCustomeItem5(String customeItem5) {
		this.customeItem5 = customeItem5;
	}
	public String getCustomeItem6() {
		return customeItem6;
	}
	public void setCustomeItem6(String customeItem6) {
		this.customeItem6 = customeItem6;
	}
	public String getCustomeItem7() {
		return customeItem7;
	}
	public void setCustomeItem7(String customeItem7) {
		this.customeItem7 = customeItem7;
	}
	public String getCustomeItem8() {
		return customeItem8;
	}
	public void setCustomeItem8(String customeItem8) {
		this.customeItem8 = customeItem8;
	}
	public String getCustomeItem9() {
		return customeItem9;
	}
	public void setCustomeItem9(String customeItem9) {
		this.customeItem9 = customeItem9;
	}
	public String getCustomeItem10() {
		return customeItem10;
	}
	public void setCustomeItem10(String customeItem10) {
		this.customeItem10 = customeItem10;
	}
	public String getCustomeItem11() {
		return customeItem11;
	}
	public void setCustomeItem11(String customeItem11) {
		this.customeItem11 = customeItem11;
	}
	public String getCustomeItem12() {
		return customeItem12;
	}
	public void setCustomeItem12(String customeItem12) {
		this.customeItem12 = customeItem12;
	}
	public String getCustomeItem13() {
		return customeItem13;
	}
	public void setCustomeItem13(String customeItem13) {
		this.customeItem13 = customeItem13;
	}
	public String getCustomeItem14() {
		return customeItem14;
	}
	public void setCustomeItem14(String customeItem14) {
		this.customeItem14 = customeItem14;
	}
	public String getCustomeItem15() {
		return customeItem15;
	}
	public void setCustomeItem15(String customeItem15) {
		this.customeItem15 = customeItem15;
	}
	public String getCustomeItem16() {
		return customeItem16;
	}
	public void setCustomeItem16(String customeItem16) {
		this.customeItem16 = customeItem16;
	}
	public String getCustomeItem17() {
		return customeItem17;
	}
	public void setCustomeItem17(String customeItem17) {
		this.customeItem17 = customeItem17;
	}
	public String getCustomeItem18() {
		return customeItem18;
	}
	public void setCustomeItem18(String customeItem18) {
		this.customeItem18 = customeItem18;
	}
	public String getCustomeItem19() {
		return customeItem19;
	}
	public void setCustomeItem19(String customeItem19) {
		this.customeItem19 = customeItem19;
	}
	public String getCustomeItem20() {
		return customeItem20;
	}
	public void setCustomeItem20(String customeItem20) {
		this.customeItem20 = customeItem20;
	}
	public String getCustomeItem21() {
		return customeItem21;
	}
	public void setCustomeItem21(String customeItem21) {
		this.customeItem21 = customeItem21;
	}
	public String getCustomeItem22() {
		return customeItem22;
	}
	public void setCustomeItem22(String customeItem22) {
		this.customeItem22 = customeItem22;
	}
	public String getCustomeItem23() {
		return customeItem23;
	}
	public void setCustomeItem23(String customeItem23) {
		this.customeItem23 = customeItem23;
	}
	public String getCustomeItem24() {
		return customeItem24;
	}
	public void setCustomeItem24(String customeItem24) {
		this.customeItem24 = customeItem24;
	}
	public String getCustomeItem25() {
		return customeItem25;
	}
	public void setCustomeItem25(String customeItem25) {
		this.customeItem25 = customeItem25;
	}
	public String getCustomeItem26() {
		return customeItem26;
	}
	public void setCustomeItem26(String customeItem26) {
		this.customeItem26 = customeItem26;
	}
	public String getCustomeItem27() {
		return customeItem27;
	}
	public void setCustomeItem27(String customeItem27) {
		this.customeItem27 = customeItem27;
	}
	public String getCustomeItem28() {
		return customeItem28;
	}
	public void setCustomeItem28(String customeItem28) {
		this.customeItem28 = customeItem28;
	}
	public String getCustomeItem29() {
		return customeItem29;
	}
	public void setCustomeItem29(String customeItem29) {
		this.customeItem29 = customeItem29;
	}
	public String getCustomeItem30() {
		return customeItem30;
	}
	public void setCustomeItem30(String customeItem30) {
		this.customeItem30 = customeItem30;
	}
	public String getCustomeItem31() {
		return customeItem31;
	}
	public void setCustomeItem31(String customeItem31) {
		this.customeItem31 = customeItem31;
	}
	public String getCustomeItem32() {
		return customeItem32;
	}
	public void setCustomeItem32(String customeItem32) {
		this.customeItem32 = customeItem32;
	}
	public String getCustomeItem33() {
		return customeItem33;
	}
	public void setCustomeItem33(String customeItem33) {
		this.customeItem33 = customeItem33;
	}
	public String getCustomeItem34() {
		return customeItem34;
	}
	public void setCustomeItem34(String customeItem34) {
		this.customeItem34 = customeItem34;
	}
	public String getCustomeItem35() {
		return customeItem35;
	}
	public void setCustomeItem35(String customeItem35) {
		this.customeItem35 = customeItem35;
	}
	public String getCustomeItem36() {
		return customeItem36;
	}
	public void setCustomeItem36(String customeItem36) {
		this.customeItem36 = customeItem36;
	}
	public String getCustomeItem37() {
		return customeItem37;
	}
	public void setCustomeItem37(String customeItem37) {
		this.customeItem37 = customeItem37;
	}
	public String getCustomeItem38() {
		return customeItem38;
	}
	public void setCustomeItem38(String customeItem38) {
		this.customeItem38 = customeItem38;
	}
	public String getCustomeItem39() {
		return customeItem39;
	}
	public void setCustomeItem39(String customeItem39) {
		this.customeItem39 = customeItem39;
	}
	public String getCustomeItem40() {
		return customeItem40;
	}
	public void setCustomeItem40(String customeItem40) {
		this.customeItem40 = customeItem40;
	}
	public String getCustomeItem41() {
		return customeItem41;
	}
	public void setCustomeItem41(String customeItem41) {
		this.customeItem41 = customeItem41;
	}
	public String getCustomeItem42() {
		return customeItem42;
	}
	public void setCustomeItem42(String customeItem42) {
		this.customeItem42 = customeItem42;
	}
	public String getCustomeItem43() {
		return customeItem43;
	}
	public void setCustomeItem43(String customeItem43) {
		this.customeItem43 = customeItem43;
	}
	public String getCustomeItem44() {
		return customeItem44;
	}
	public void setCustomeItem44(String customeItem44) {
		this.customeItem44 = customeItem44;
	}
	public String getCustomeItem45() {
		return customeItem45;
	}
	public void setCustomeItem45(String customeItem45) {
		this.customeItem45 = customeItem45;
	}
	public String getCustomeItem46() {
		return customeItem46;
	}
	public void setCustomeItem46(String customeItem46) {
		this.customeItem46 = customeItem46;
	}
	public String getCustomeItem47() {
		return customeItem47;
	}
	public void setCustomeItem47(String customeItem47) {
		this.customeItem47 = customeItem47;
	}
	public String getCustomeItem48() {
		return customeItem48;
	}
	public void setCustomeItem48(String customeItem48) {
		this.customeItem48 = customeItem48;
	}
	public String getCustomeItem49() {
		return customeItem49;
	}
	public void setCustomeItem49(String customeItem49) {
		this.customeItem49 = customeItem49;
	}
	public String getCustomeItem50() {
		return customeItem50;
	}
	public void setCustomeItem50(String customeItem50) {
		this.customeItem50 = customeItem50;
	}

	public void setField(String fieldName, String value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		String pattern = "^CUSTOME_ITEM([1-9]|([1-5][0-9]))$";
		if (Pattern.compile(pattern).matcher(fieldName).find()) {
			String f = "customeItem" + fieldName.substring(12);
			Field field = this.getClass().getDeclaredField(f);
			field.setAccessible(true);
			field.set(this, value);
		}
	}
	
	public String getField(String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		String pattern = "^CUSTOME_ITEM([1-9]|([1-5][0-9]))$";
		if (Pattern.compile(pattern).matcher(fieldName).find()) {
			String f = "customeItem" + fieldName.substring(12);
			Field field = this.getClass().getDeclaredField(f);
			field.setAccessible(true);
			return (String) field.get(this);
		}
		throw new IllegalArgumentException("INVALID FIELD NAME: " + fieldName);
	}

}
