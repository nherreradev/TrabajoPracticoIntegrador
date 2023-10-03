package com.unlam.tpi.helpers;

import java.math.BigDecimal;

public abstract class CalculosHabituales {

	public static Boolean esMasGrandeQue(BigDecimal v1, BigDecimal v2) {
		return (v1 != null && v2 != null) ? v1.compareTo(v2) > 0 : Boolean.FALSE;
	}
}
