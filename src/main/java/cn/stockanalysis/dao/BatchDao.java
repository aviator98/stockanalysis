package cn.stockanalysis.dao;

public interface BatchDao<T>  {	
    /**
     * 批量插入数据
     * @param list
     */
	public int batchInsert(Iterable<T> entities);
	
	/**
	 * 批量更新数据
	 * @param list
	 */
    public int batchUpdate(Iterable<T> entities);
}
