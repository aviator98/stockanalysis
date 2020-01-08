package cn.stockanalysis.domain;

import java.time.LocalDate;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(DailyTrade.class)
public class DailyTrade_ {
	public static volatile SingularAttribute<DailyTrade, String> code;
	public static volatile SingularAttribute<DailyTrade, LocalDate> tradeDate;
}
