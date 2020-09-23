package homebook3;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface IDAO<T, K> {

	public boolean isExists(K key, Connection conn) throws Exception;
	public boolean insert(T t, Connection conn) throws Exception;
	public boolean delete(K key, Connection conn) throws Exception;
	public boolean update(T t, Connection conn) throws Exception;
	public T select(K key, Connection conn) throws Exception;
	public List<T> selectByCondition(Map<Object, Object> conditionMap, Connection conn) throws Exception;
	public List<T> selectAll(Connection conn) throws Exception;
	public boolean search(T t, Connection conn) throws Exception;
	public void close(Connection conn) throws Exception;
}
