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

		// リクエストパラメータを取得
//		String status = (String) request.getAttribute("status");
//		Integer order_detail_id = (Integer) request.getAttribute("order_detail_id");
		System.out.println("status : " + status);
		System.out.println("order_detail_id : " + order_detail_id);

		// 遷移元に戻すために取得
//		String referer = request.getHeader("Referer");

		int numOfRow = 0;
		if(status != null) {
			// 未発送が渡されたときは何もしない
			if(status.equals("未発送")) {
				return "redirect:/orderlog/unshipped";
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
				redirectAttributes.addAttribute("message", "注文詳細ID：" + order_detail_id + "のステータスを更新しました。");
				return "redirect:/orderlog/unshipped";
			}else {
				redirectAttributes.addAttribute("message", "エラーが発生しました。再実行してください。");
				return "redirect:/orderlog/unshipped";
			}

		}else {
			// 何もせずに戻る
			return "redirect:/orderlog/unshipped";
		}
	}
}
