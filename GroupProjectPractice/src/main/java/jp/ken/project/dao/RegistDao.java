package jp.ken.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import jp.ken.project.model.CustomerModel;

@Repository
public class RegistDao {


	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private PlatformTransactionManager transactionManager;

	private RowMapper<CustomerModel> customerModelMapper = new BeanPropertyRowMapper<CustomerModel>(CustomerModel.class);

//登録処理
	public int registCustomer(CustomerModel customerModel) {
		String sql = "INSERT INTO t_customers(customer_name, mail, password) VALUES(?, ?, ?)";
		Object[] parameters = {customerModel.getCustomer_name(), customerModel.getMail(), customerModel.getPassword()};
		TransactionStatus transactionStatus = null;
		DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		int numberOfRow = 0;

		try { // トランザクション開始
			transactionStatus = transactionManager.getTransaction(transactionDefinition);// SQL実行
			numberOfRow = jdbcTemplate.update(sql, parameters);// 成功した場合はコミット
			transactionManager.commit(transactionStatus);
		} catch (DataAccessException e) {// データアクセス例外が発生した場合
			if (transactionStatus != null) {
				transactionManager.rollback(transactionStatus);
			}
		 e.printStackTrace();
		} catch (TransactionException e) {// トランザクション関連の例外が発生した場合
			if (transactionStatus != null) {
				transactionManager.rollback(transactionStatus);
			}
		e.printStackTrace();
		} catch (Exception e) { // その他の予期しない例外
			if (transactionStatus != null) {
				transactionManager.rollback(transactionStatus);
		}
		e.printStackTrace();
		} finally { // 最後にトランザクションを確実に終了させる
			if (transactionStatus != null && !transactionStatus.isCompleted()) {
				transactionManager.rollback(transactionStatus);
			}
		}
		return numberOfRow;
	}
}
