package jp.ken.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
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

	//新規登録
		public int registCustomer(CustomerModel customerModel) {
			//t_customersテーブルに新しいレコードを挿入するためのINSERT文
			String sql = "INSERT INTO t_customers(customer_name, mail, password) VALUES(?, ?, ?)";
			//挿入するデータを設定。customer_name、mail、passwordを取得し、セットする
			Object[] parameters = {customerModel.getCustomer_name(), customerModel.getMail(), customerModel.getPassword()};
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


		//退会処理
		public int updateIsRemove(CustomerModel customerModel) {
			//SQL文を作成。t_customersテーブルで、指定されたメールアドレスのレコードに対してis_removeカラムを更新する。
			String sql = "UPDATE t_customers SET is_remove = ? WHERE mail = ?";
			//is_removeに「1」を設定し、対象のメールアドレスを指定する。
			Object[] parameters = {"1",customerModel.getMail()};
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
}
