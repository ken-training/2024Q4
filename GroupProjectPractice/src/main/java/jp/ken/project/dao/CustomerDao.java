package jp.ken.project.dao;

import java.util.Map;

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
public class CustomerDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private PlatformTransactionManager transactionManager;

	// Mapper作成
	private RowMapper<CustomerModel> customerMapper = new BeanPropertyRowMapper<CustomerModel>(CustomerModel.class);

	//新規登録
	public int registCustomer(CustomerModel customerModel) {
		//t_customersテーブルに新しいレコードを挿入するためのINSERT文
		String sql = "INSERT INTO t_customers(customer_name, mail, password) VALUES(?, ?, ?)";
		//挿入するデータを設定。customer_name、mail、passwordを取得し、セットする
		Object[] parameters = { customerModel.getCustomer_name(), customerModel.getMail(),
				customerModel.getPassword() };
		//トランザクションの状態を管理する変数。
		TransactionStatus transactionStatus = null;
		DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		int numberOfRow = 0;

		try {
			transactionStatus = transactionManager.getTransaction(transactionDefinition);// トランザクション開始
			numberOfRow = jdbcTemplate.update(sql, parameters);// SQL実行
			transactionManager.commit(transactionStatus);// 成功した場合はコミット
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
		//
		return numberOfRow;
	}

	// 対象メールアドレスがすでにデータベースに存在するか確認するメソッド
	public boolean isMailExist(String mail) {
		String checkSql = "SELECT COUNT(*) FROM t_customers WHERE mail = ?";
		int count = jdbcTemplate.queryForObject(checkSql, new Object[]{mail}, Integer.class);
		return count > 0; // 存在する場合は true を返す
	}

	//退会処理
	public int updateIsRemove(CustomerModel customerModel) {
		//SQL文を作成。t_customersテーブルで、指定されたメールアドレスのレコードに対してis_removeカラムを更新する。
		String sql = "UPDATE t_customers SET is_remove = ?, mail = ? WHERE mail = ?";
		//is_removeに「1」を設定し、対象のメールアドレスを指定する。
		Object[] parameters = { "1", customerModel.getMail(), customerModel.getOriginalMail() };
		//トランザクションの状態を管理する変数。
		TransactionStatus transactionStatus = null;
		DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		int numberOfRow = 0;
		try {
			transactionStatus = transactionManager.getTransaction(transactionDefinition);// トランザクション開始
			numberOfRow = jdbcTemplate.update(sql, parameters);// SQL実行
			transactionManager.commit(transactionStatus);// 成功した場合はコミット
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
		// 更新された行数を返す 想定は1
		return numberOfRow;
	}

	// 変更処理
	public CustomerModel updateCustomer(int customer_id, Map<String, Object> map) {
		// SQL文を作成
		String sql = "UPDATE t_customers SET ";

		// entrySet()を使ってキーと値を取り出す
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();    // キーを取得
            Object value = entry.getValue();  // 値を取得
            sql += key + "='" + value + "',";
        }

        sql = sql.substring(0, sql.length() - 1);
		sql += " WHERE customer_id=?;";

		// パラメータ作成
		Object[] parameters = { customer_id };

		int numberOfRow = jdbcTemplate.update(sql, parameters);
		System.out.println("numOR:"+numberOfRow);

		CustomerModel customerModel = new CustomerModel();
		if(numberOfRow == 1) {
        	customerModel = jdbcTemplate.queryForObject("SELECT * FROM t_customers WHERE customer_id = " + customer_id,  customerMapper);
        }else {
        	// エラー処理
            throw new RuntimeException("更新に失敗しました。");
        }

		return customerModel;
	}
}
