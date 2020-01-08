package cn.stockanalysis.repository;

import org.springframework.data.jpa.domain.Specification;

import cn.stockanalysis.domain.DailyTrade;
import cn.stockanalysis.domain.DailyTrade_;

import java.time.LocalDate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class DailyTradeSpec {
	public static Specification<DailyTrade> getSpec(final String code, final LocalDate startDate, final LocalDate endDate) {
        return new Specification<DailyTrade>() {
            @Override
            public Predicate toPredicate(Root<DailyTrade> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate p1 = null;
                if(code != null) {
                    Predicate p2 = cb.equal(root.get(DailyTrade_.code), code);
                    p1 = p2;
                }

                if(startDate!=null) {
                    Predicate p2 = cb.greaterThanOrEqualTo(root.get(DailyTrade_.tradeDate), startDate);
                    if(p1 != null) {
                        p1 = cb.and(p1,p2);
                    } else {
                        p1 = p2;
                    }
                }
                
                if(endDate != null) {
                    Predicate p2 = cb.lessThanOrEqualTo(root.get(DailyTrade_.tradeDate), endDate);
                    if(p1 != null) {
                        p1 = cb.and(p1,p2);
                    } else {
                        p1 = p2;
                    }
                }

                return p1;
            }
        };
    }

	public static Specification<DailyTrade> getSpec(final LocalDate startDate, final LocalDate endDate) {
        return new Specification<DailyTrade>() {

            @Override
            public Predicate toPredicate(Root<DailyTrade> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate p1 = null;
                if(startDate!=null) {
                    Predicate p2 = cb.greaterThanOrEqualTo(root.get(DailyTrade_.tradeDate), startDate);
                    if(p1 != null) {
                        p1 = cb.and(p1,p2);
                    } else {
                        p1 = p2;
                    }
                }

                if(endDate!=null) {
                    Predicate p2 = cb.lessThanOrEqualTo(root.get(DailyTrade_.tradeDate), endDate);
                    if(p1 != null) {
                        p1 = cb.and(p1,p2);
                    } else {
                        p1 = p2;
                    }
                }

                return p1;
            }
        };
    }
}
