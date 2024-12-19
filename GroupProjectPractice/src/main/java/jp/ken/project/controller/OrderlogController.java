package jp.ken.project.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.ken.project.dao.OrderlogDao;
import jp.ken.project.model.CustomerModel;
import jp.ken.project.model.EmployeeLoginModel;
import jp.ken.project.model.OrderlogModel;

@Controller
public class OrderlogController {

	@Autowired
	private OrderlogDao orderlogDao;

	// 未発送の注文履歴画面へ遷移
	@RequestMapping(value = "/orderlog/unshipped", method = RequestMethod.GET)
	public String toUnShippedOrderlog(Model model, HttpServletResponse response, HttpSession session,
			@RequestParam(value = "message", required = false) String message){
		EmployeeLoginModel employeeLoginModel = (EmployeeLoginModel) session.getAttribute("employeeLoginModel");

		// 従業員がログインしてない場合はログインページへ
		if(employeeLoginModel == null) {
			return "redirect:/emplogin";
		}

	    // キャッシュを無効化
	    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Expires", 0);

		@SuppressWarnings("unchecked")
		List<OrderlogModel> orderlogList = (List<OrderlogModel>) orderlogDao.getUnShipOrderlog();
		model.addAttribute("orderlogList", orderlogList);

		// ステータス欄のプルダウン配列作成
		String[] statusList = {"未発送", "発送済", "注文取消"};
		model.addAttribute("statusList", statusList);

		// リダイレクト元からmessageを渡されていたらモデルに追加
		if(message != null && !message.isEmpty()) {
			model.addAttribute("message", message);
		}

		return "unshipOrderlog";
	}

	// 発送済みとキャンセル済みの注文履歴画面へ遷移
	@RequestMapping(value = "/orderlog/shipped", method = RequestMethod.GET)
	public String toShippedOrderlog(Model model, HttpServletResponse response,HttpSession session){

		EmployeeLoginModel employeeLoginModel = (EmployeeLoginModel) session.getAttribute("employeeLoginModel");

		// 従業員がログインしてない場合はログインページへ
		if(employeeLoginModel == null) {
			return "redirect:/emplogin";
		}

	    // キャッシュを無効化
	    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Expires", 0);

		@SuppressWarnings("unchecked")
		List<OrderlogModel> orderlogList = (List<OrderlogModel>) orderlogDao.getShipOrderlog();
		model.addAttribute("orderlogList", orderlogList);

		//ステータスをフラグから文字列へ
		String[] statusList = {"未発送", "発送済", "注文取消"};
		model.addAttribute("statusList", statusList);
		return "shippedOrder";

	}



	// 発送済みと注文取消しの処理を実施し、未発送の画面へ戻る
	@RequestMapping(value = "/orderlog/edit", method = RequestMethod.POST)
	public String editOrder(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "order_detail_id", required = false) int order_detail_id
			){

		String redirectPath = "/top";

		// リクエストパラメータを取得
//		String status = (String) request.getAttribute("status");
//		Integer order_detail_id = (Integer) request.getAttribute("order_detail_id");
		System.out.println("status : " + status);
		System.out.println("order_detail_id : " + order_detail_id);


	    // 遷移元URLからリダイレクト先のパスを指定
	    String product_referer = request.getHeader("Referer");
        // 最後の "/" の位置を取得
        int lastSlashIndex = product_referer.lastIndexOf("/");
        // "?" の位置を取得
        int questionMarkIndex = product_referer.indexOf("?", lastSlashIndex);
        // "?" が見つからなかった場合、文字列の終わりまでを取る
        if (questionMarkIndex == -1) {
            questionMarkIndex = product_referer.length();
        }
        // 最後の "/" から "?" または文字列の終わりまでを抜き出す
        String partOfUrl = product_referer.substring(lastSlashIndex, questionMarkIndex);
        // top or searchから飛んできた時はセッションにURL登録
	    if(partOfUrl.equals("/customer")) {
	    	redirectPath = "redirect:/orderlog/customer";
	    }else if(partOfUrl.equals("/unshipped"))
	    	redirectPath = "redirect:/orderlog/unshipped";

		int numOfRow = 0;
		if(status != null) {
			// 未発送が渡されたときは何もしない
			if(status.equals("未発送")) {
				return redirectPath;
			// 発送済み操作
			}else if(status.equals("発送済")) {
		        // 今日の日付を取得
				Calendar calendar = Calendar.getInstance();
		        // 日付を"yyyy/MM/dd"の形式で取得
		        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		        String formattedDate = dateFormat.format(calendar.getTime());
		        // LocalDate を java.sql.Date に変換
		        Date order_date = Date.valueOf(formattedDate);
		        numOfRow = orderlogDao.editStatusShipped(order_date, order_detail_id);
			// 注文取消操作
			}else if(status.equals("注文取消")) {
				 numOfRow = orderlogDao.editStatusCancel(order_detail_id);
			}
			// 失敗していたらリダイレクト先にエラーを返す
			if(numOfRow == 1) {
				redirectAttributes.addAttribute("message", "注文詳細ID：" + order_detail_id + "のステータスを" + status +"に更新しました。");
//				return "redirect:/orderlog/unshipped";
				return redirectPath;
			}else {
				redirectAttributes.addAttribute("message", "エラーが発生しました。再実行してください。");
				return redirectPath;
			}

		}else {
			// 何もせずに戻る
			return redirectPath;
		}
	}

	//　会員の発注履歴画面表示
	@RequestMapping(value = "/orderlog/customer", method = RequestMethod.GET)
	public String toCustomerOrderlog(Model model, HttpServletResponse response, HttpSession session,
			@RequestParam(value = "message", required = false) String message){
		CustomerModel customerModel = (CustomerModel) session.getAttribute("customerModel");

		// 従業員がログインしてない場合はログインページへ
		if(customerModel == null) {
			return "redirect:/login";
		}

	    // キャッシュを無効化
	    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Expires", 0);

		@SuppressWarnings("unchecked")
		List<OrderlogModel> orderlogList = (List<OrderlogModel>) orderlogDao.getOrderHistoryByCustomerId(customerModel.getCustomer_id());
		model.addAttribute("orderlogList", orderlogList);

		// ステータス配列作成
		String[] statusList = {"未発送", "発送済み", "注文取消"};
		model.addAttribute("statusList", statusList);

		// リダイレクト元からmessageを渡されていたらモデルに追加
		if(message != null && !message.isEmpty()) {
			model.addAttribute("message", message);
		}

		return "orderHistory";
	}

}
