package cn.stockanalysis.dao;

import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractBatchDao<T> implements BatchDao<T> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    protected EntityManager em;
 
    @Override
    @Transactional
    public int batchInsert(Iterable<T> entities) {
        try {
        	Iterator<T> it = entities.iterator();
        	while(it.hasNext()) {
        		T entity = it.next();
        		em.persist(entity);
        	}
        	em.flush();
        	em.clear();
            logger.info("batch insert data success");
            return 1;
        } catch (Exception e) {
            logger.error("batch insert data failure");
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    @Transactional
    public int batchUpdate(Iterable<T> entities) {
        try {
        	Iterator<T> it = entities.iterator();
        	while(it.hasNext()) {
        		T entity = it.next();
        		em.merge(entity);
        	}
        	em.flush();
        	em.clear();
            logger.info("batch insert data success");
            return 1;
        } catch (Exception e) {
            logger.error("batch insert data failure");
            e.printStackTrace();
            return 0;
        }
    }
}
