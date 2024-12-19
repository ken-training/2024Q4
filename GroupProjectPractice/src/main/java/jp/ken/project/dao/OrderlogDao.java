package jp.ken.project.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import jp.ken.project.model.OrderlogModel;

@Component
public class OrderlogDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PlatformTransactionManager transactionManager;

    private RowMapper<OrderlogModel> orderlogMapper = new BeanPropertyRowMapper<OrderlogModel>(OrderlogModel.class);

	// 未発送オーダーの必要な情報をまるごと取得できるメソッド(返り値: List<OrderlogModel>)
	public List<OrderlogModel> getUnShipOrderlog() {
        String sql = "SELECT od.order_detail_id, od.order_id, o.order_date, od.product_id,"
        		+ "p.product_name, od.product_num, od.temp_amount, o.use_points,"
        		+ "o.customer_id, c.customer_name, o.ship_id, s.ship_name, s.ship_address,"
        		+ "o.payment_methods, od.status "
        		+ "FROM t_order_datails od "
        		+ "LEFT JOIN t_orders o ON od.order_id = o.order_id "
        		+ "LEFT JOIN t_customers c ON o.customer_id = c.customer_id "
        		+ "LEFT JOIN t_products p ON p.product_id = od.product_id "
        		+ "LEFT JOIN t_shipping s ON s.ship_id = o.ship_id "
        		+ "WHERE od.status = '0';";
        try {
        	List<OrderlogModel> orderlogList = (List<OrderlogModel>) jdbcTemplate.query(sql, orderlogMapper);
        	return orderlogList;
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;  // 見つからない場合は null を返す
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // 見つからない場合は null を返す
        }
	}

	// 発送済とキャンセル済みオーダーの必要な情報をまるごと取得できるメソッド(返り値: List<OrderlogModel>)
	public List<OrderlogModel> getShipOrderlog() {
        String sql = "SELECT od.order_detail_id, od.order_id, o.order_date, od.product_id,"
        		+ "p.product_name, od.product_num, od.temp_amount, o.use_points,"
        		+ "o.customer_id, c.customer_name, o.ship_id, s.ship_name, s.ship_address,"
        		+ "o.payment_methods, od.status, od.ship_date "
        		+ "FROM t_order_datails od "
        		+ "LEFT JOIN t_orders o ON od.order_id = o.order_id "
        		+ "LEFT JOIN t_customers c ON o.customer_id = c.customer_id "
        		+ "LEFT JOIN t_products p ON p.product_id = od.product_id "
        		+ "LEFT JOIN t_shipping s ON s.ship_id = o.ship_id "
        		+ "WHERE od.status = '1' OR od.status = '2';";
        try {
        	List<OrderlogModel> orderlogList = (List<OrderlogModel>) jdbcTemplate.query(sql, orderlogMapper);
        	return orderlogList;
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;  // 見つからない場合は null を返す
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // 見つからない場合は null を返す
        }
	}

	// ステータスを発送済に変更するためのメソッド
	public int editStatusShipped(Date ship_date, Integer order_detail_id) {

        TransactionStatus transactionStatus = null;
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        // トランザクション開始
        transactionStatus = transactionManager.getTransaction(transactionDefinition);

		String sql = "UPDATE t_order_datails SET status = '1', ship_date = ? WHERE order_detail_id = ?;";
        Object[] parameters = { ship_date, order_detail_id }; // 発送済みのためステータスは１
        try {
        	int numOfRow = jdbcTemplate.update(sql, parameters);
        	if (numOfRow == 1) {
        		transactionManager.commit(transactionStatus);
        	}
        	return numOfRow;
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(transactionStatus);
            return -1;
        }
	}

	// ステータスを注文取消に変更するためのメソッド
	public int editStatusCancel(Integer order_detail_id) {

		TransactionStatus transactionStatus = null;
		DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		// トランザクション開始
		transactionStatus = transactionManager.getTransaction(transactionDefinition);

		String sql = "UPDATE t_order_datails SET status = '2' WHERE order_detail_id = ?;";
		Object[] parameters = { order_detail_id }; // キャンセルのためステータスは2
		try {
			int numOfRow = jdbcTemplate.update(sql, parameters);
			if (numOfRow == 1) {
				transactionManager.commit(transactionStatus);
			}
			return numOfRow;
		} catch (Exception e) {
			e.printStackTrace();
			transactionManager.rollback(transactionStatus);
			return -1;
		}
	}


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public List<OrderlogModel> getOrderHistoryByCustomerId(int customerId) {
	    String sql = "SELECT od.order_detail_id, od.order_id, o.order_date, od.product_id, "
	               + " p.product_name, od.product_num, od.temp_amount, o.use_points, "
	               + " o.customer_id, s.ship_name, s.ship_address, "
	               + " o.payment_methods, od.status, od.ship_date "
	               + "FROM t_customers c "
	               + "JOIN t_orders o ON c.customer_id = o.customer_id "
	               + "JOIN t_order_datails od ON o.order_id = od.order_id "
	               + "LEFT JOIN t_products p ON p.product_id = od.product_id "
	               + "LEFT JOIN t_shipping s ON s.ship_id = o.ship_id "
	               + "WHERE c.customer_id = ?";  // customer_idを条件として使用
        Object[] parameters = { customerId };

	    try {
	        // SQLにcustomerIdを渡して注文履歴を取得
	        List<OrderlogModel> orderlogList = jdbcTemplate.query(sql, parameters, orderlogMapper);
	        return orderlogList;
	    } catch (EmptyResultDataAccessException e) {
	        e.printStackTrace();
	        return null;  // 注文履歴が見つからない場合はnullを返す
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;  // その他の例外が発生した場合もnullを返す
	    }
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
