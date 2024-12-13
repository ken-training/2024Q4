package jp.ken.project.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import jp.ken.project.model.CartModel;
import jp.ken.project.model.OrderDetailModel;
import jp.ken.project.model.OrderModel;
import jp.ken.project.model.ShippingModel;

@Component
public class ConfirmDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PlatformTransactionManager transactionManager;

    // 発送先情報テーブルへインサート
    public int insertToShipping(ShippingModel shippingModel) {

        String sql = "INSERT INTO t_shipping(ship_name, ship_phonetic, ship_zip, ship_address, ship_phone) VALUES(?, ?, ?, ?, ?);";
        Object[] parameters = {shippingModel.getShip_name(), shippingModel.getShip_phonetic(),
                shippingModel.getShip_zip(), shippingModel.getShip_address(), shippingModel.getShip_phone()};

        int numberOfRow = jdbcTemplate.update(sql, parameters);

        int ship_id = -1;
        if(numberOfRow == 1) {
        	ship_id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        }else {
        	// エラー投げるべき？
        }

        return ship_id;
    }

    // 注文情報テーブルへインサート
    public int insertToOrders(OrderModel orderModel) {

        String sql = "INSERT INTO t_orders(order_date, customer_id, ship_id, total_amount, payment_methods) VALUES(?, ?, ?, ?, ?);";
        Object[] parameters = {orderModel.getOrder_date(), orderModel.getCustomer_id(),
                orderModel.getShip_id(), orderModel.getTotal_amount(), orderModel.getPayment_methods()};

        int numberOfRow = jdbcTemplate.update(sql, parameters);

        int order_id = -1;
        if(numberOfRow == 1) {
        	order_id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        }else {
        	// エラー投げるべき？
        }

        return order_id;

    }

    // 注文情報詳細テーブルへインサート
    public int insertToOrderDetails(OrderDetailModel orderDetailModel) {

        String sql = "INSERT INTO t_order_datails(order_id, product_id, product_num, temp_amount) VALUES(?, ?, ?, ?);";
        Object[] parameters = {orderDetailModel.getOrder_id(), orderDetailModel.getProduct_id(),
                orderDetailModel.getProduct_num(), orderDetailModel.getTemp_amount()};

        return jdbcTemplate.update(sql, parameters);
    }

    // トランザクション処理部分のメソッド（全てのインサート処理を一度にトランザクション内で実行）
    public boolean insertWithTransaction(ShippingModel shippingModel, OrderModel orderModel,
    		 List<CartModel> cartList) {
        TransactionStatus transactionStatus = null;
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();

        try {
            // トランザクション開始
            transactionStatus = transactionManager.getTransaction(transactionDefinition);

            // 発送先情報インサート
            int ship_id = insertToShipping(shippingModel);

            // 注文情報インサート
            orderModel.setShip_id(ship_id);  // 送付先IDを注文情報に設定
            int order_id = insertToOrders(orderModel);

            // 注文詳細インサート
            int numberOfRows = 0;
//            orderDetailModel.setOrder_id(orderId);  // 注文IDを注文詳細情報に設定
    		if (cartList != null && cartList.size() != 0) {
    		    for(CartModel cartmodel : cartList) {
    		    	// 必要情報をOrderModelに格納していく
    		    	OrderDetailModel orderDetailModel = new OrderDetailModel();

    		    	orderDetailModel.setOrder_id(order_id);
    		    	orderDetailModel.setProduct_id(cartmodel.getProduct_id());
    		    	orderDetailModel.setProduct_num(cartmodel.getCount());
    		    	orderDetailModel.setTemp_amount(cartmodel.getPrice() * cartmodel.getCount());

    		    	numberOfRows += insertToOrderDetails(orderDetailModel);
    		    }
    		    System.out.println("numberOfRow : " + numberOfRows);
    		}

//            int numberOfRows = insertToOrderDetails(orderDetailModel);

            // もしすべてのインサートが成功したらコミット
            if (numberOfRows == cartList.size() && order_id > 0 && ship_id > 0) {
                transactionManager.commit(transactionStatus);
                return true;  // 成功
            } else {
                // 失敗した場合はロールバック
                transactionManager.rollback(transactionStatus);
                return false;  // 失敗
            }

        } catch (Exception e) {
            // エラーが発生した場合はロールバック
            if (transactionStatus != null) {
                transactionManager.rollback(transactionStatus);
            }
            e.printStackTrace();
            return false;  // 失敗
        }
    }
}
