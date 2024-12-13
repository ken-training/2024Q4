package jp.ken.project.controller;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.ken.project.dao.CustomerDao;
import jp.ken.project.model.CustomerModel;
import jp.ken.project.model.UpdateFormModel;


@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private CustomerDao customerDao;

	// マイページ画面へ遷移
	@RequestMapping(method = RequestMethod.GET)
	public String get(Model model){
		CustomerModel cModel = new CustomerModel();

		// (仮)オブジェクトに設定
		cModel.setMail("t.yamada@ken.jp");

		model.addAttribute("customerModel", cModel);

		return "account";
	}

	// submitの内容で次の処理を振り分け
	@RequestMapping(method = RequestMethod.POST)
	public String post(@RequestParam("action") String action,Model model){

		if ("会員情報変更".equals(action)) {
			// 会員情報変更画面へリダイレクト
			return "redirect:/account/update";

		} else if ("退会する".equals(action)) {
			// 退会処理へforward
			return "redirect:/account/delete";

		} else {
			// 未知のアクションの場合マイページへ遷移

			// エラーメッセージを表示
			model.addAttribute("error", "エラーが発生しました。");

			// マイページ画面へ遷移
			return "account";
		}
	}

	// 退会処理
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Model model, CustomerModel customerModel,HttpSession session){
		customerModel.setMail("tamaoki1@yahoo.co.jp");//セッションで値をとってくる 現状は仮の値を入れている
		int numberOfRow = customerDao.updateIsRemove(customerModel); //退会処理を実行
		if (numberOfRow == 1) {
			session.invalidate();  // セッションを無効化（ログアウト）
			return "redirect:/top";
		}else {
			return "redirect:/top";//退会処理がエラーによりできなかった場合
		}
	}

	// 会員情報変更処理
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updateGet(HttpSession session, Model model){
		UpdateFormModel updateFormModel = new UpdateFormModel();

		// 今年が何年かを取得
		Calendar cal = Calendar.getInstance();
		int thisYear = cal.get(Calendar.YEAR);

		// セッションから会員情報(CustomerModel)を取得
		@SuppressWarnings("unchecked")
		CustomerModel customerModel = (CustomerModel)session.getAttribute("customerModel");
//		CustomerModel customerModel = new CustomerModel();	// いったん仮置き
//		customerModel.setMail("oota@gmail.com");

		// 会員情報をupdateFormModelに紐づけ
		updateFormModel = parseUpdateFormModel(customerModel);

		// 誕生日が未設定の場合、デフォルトで今年-30年の数字を表示するよう初期値を設定する
		if (updateFormModel.getBirthYear() == null) {
			updateFormModel.setBirthYear("" + (thisYear -30));
		}

		// プルダウンで表示する用のリストをモデルに紐づけ
		model.addAttribute("birthYearList", getNumberList(thisYear-125, thisYear));
		model.addAttribute("birthMonthList", getNumberList(1, 12));
		model.addAttribute("creditExpMList", getNumberList(1, 12));
		model.addAttribute("creditExpYList", getNumberList(thisYear, thisYear +10));


		model.addAttribute("updateFormModel", updateFormModel);
		return "accountUpdate";
	}

//	@RequestMapping(value = "/update", method = RequestMethod.POST)
//	public String updatePost(@Validated(GroupOrder.class) @ModelAttribute UpdateFormModel updateFormModel,BindingResult result, Model model,HttpSession session){
//		// バリデーションエラーがある場合
//		if (result.hasErrors()) {
//					return "accountUpdate";  // エラーがあれば再度入力画面を表示
//
//		// 正常
//		} else {
//
//			try {
//				// セッションから会員IDを取得
//				@SuppressWarnings("unchecked")
//				CustomerModel sessionCustomerModel = (CustomerModel)session.getAttribute("customerModel");
//				int id = sessionCustomerModel.getCustomer_id();
////				CustomerModel sessionCustomerModel = new CustomerModel();	// いったん仮置き
////				customerModel.setMail("oota@gmail.com");
//
//				// updateFormModelからcustomerModelにデータをコピー
//				CustomerModel customerModel = parseCustomerModel(updateFormModel);
//
//				// sessionの会員情報と今回入力された会員情報を比較する
//				Map<String, Object> differences = compareCustomerModels(sessionCustomerModel, customerModel);
//
//
//				// 顧客情報をデータベースに登録
//				int numberOfRow = customerDao.registCustomer(customerModel);
//				if (numberOfRow == 1) {
//					// 登録が成功した場合
//					return "redirect:/top";  // 成功時に遷移するビュー
//				}
//				model.addAttribute("error", "このメールアドレスは既に登録されています");
//				return "regist";  // 登録に失敗した場合、再度エラーメッセージを表示
//			} catch (IllegalAccessException e) {
//				return "regist";  // 登録に失敗した場合、再度エラーメッセージを表示
//				// どうしよ
//			}
//		}
////		return "accountUpdate";
//	}

	// プルダウン用の数字のリストを作成するメソッド
	private List<String> getNumberList(int start, int end){
		List<String> numberList = new ArrayList<String>();

		// start～endまでの数字をリストの中に格納する
		for(int i = start; i<= end; i++) {
			numberList.add(Integer.toString(i));
		}

		return numberList;
	}

	// CustomerModelをUpdateFormModelに設定
	private UpdateFormModel parseUpdateFormModel(CustomerModel customerModel) {
		UpdateFormModel updateFormModel  = new UpdateFormModel();

		updateFormModel.setCustomer_name(customerModel.getCustomer_name());	// 氏名
		updateFormModel.setCustomer_phonetic(customerModel.getCustomer_phonetic());	// フリガナ
		updateFormModel.setMail(customerModel.getMail());	// メールアドレス
		if (customerModel.getZip() != null) {	// 郵便番号
			String[] words = customerModel.getZip().split("-");
			updateFormModel.setZip1(words[0]);
			updateFormModel.setZip2(words[1]);
		}
		if (customerModel.getAddress() != null) {	// 住所
			String[] words = customerModel.getAddress().split(" ");
			System.out.println(words[0]);
			updateFormModel.setPrefecture(words[0]);
			updateFormModel.setCity(words[1]);
			updateFormModel.setBlock(words[2]);
			if (words.length == 4) {	// 建物名が入力されていた場合
				updateFormModel.setBuilding(words[3]);
			}
		}
		if (customerModel.getPhone() != null) {	// 電話番号
			String[] words = customerModel.getPhone().split("-");
			updateFormModel.setPhone1(words[0]);
			updateFormModel.setPhone2(words[1]);
			updateFormModel.setPhone3(words[2]);
		}
		if (customerModel.getBirthday() != null) {	// 生年月日
			// SimpleDateFormatでフォーマット
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// 日付を文字列に変換
			String formattedString = sdf.format(customerModel.getBirthday());
			String[] words = formattedString.split("-");
			updateFormModel.setBirthYear(words[0]);
			updateFormModel.setBirthMonth(words[1]);
			updateFormModel.setBirthDay(words[2]);
		}
		if (customerModel.getCreditcard_exp() != null) {	// クレジットカード有効期限
			String[] words = customerModel.getCreditcard_exp().split("/");

			updateFormModel.setCreditExpM(words[0]);
			updateFormModel.setCreditExpY(words[1]);
		}

		return updateFormModel;
	}

	// UpdateFormModelをCustomerModelに設定
	private CustomerModel parseCustomerModel(UpdateFormModel updateFormModel) {
		CustomerModel customerModel  = new CustomerModel();

		customerModel.setCustomer_name(updateFormModel.getCustomer_name());	// 氏名
		customerModel.setCustomer_phonetic(updateFormModel.getCustomer_phonetic());	// フリガナ
		customerModel.setMail(updateFormModel.getMail());	// メールアドレス
		customerModel.setPassword(updateFormModel.getPassword());	// パスワード
		customerModel.setZip(updateFormModel.getZip1() + "-" + updateFormModel.getZip2());	// 郵便番号
		customerModel.setAddress(updateFormModel.getPrefecture() + " " + updateFormModel.getCity()
				+ " " + updateFormModel.getBlock() + " " + updateFormModel.getBuilding());	// 住所
		customerModel.setPhone(updateFormModel.getPhone1() + "-" + updateFormModel.getPhone2()
				+ "-" + updateFormModel.getPhone3()); // 電話番号


		try {
			// SimpleDateFormatでフォーマット
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String birthdayStr = updateFormModel.getBirthYear() + "-" + updateFormModel.getBirthMonth() + "-" + updateFormModel.getBirthDay();
			java.util.Date birthdayDate = sdf.parse(birthdayStr);

			// java.util.Dateをjava.sql.Dateに変換
			java.sql.Date birthdaySql = new java.sql.Date(birthdayDate.getTime());

			customerModel.setBirthday(birthdaySql);	// 誕生日
		} catch (ParseException e) {
			e.getStackTrace();
			// どうしよ
		}

		// クレジットカード番号
		customerModel.setCreditcard_num(updateFormModel.getCreditNum1() + updateFormModel.getCreditNum2()
											+ updateFormModel.getCreditNum3() + updateFormModel.getCreditNum4());

		// マスク化したクレジットカード番号
		customerModel.setMasked_creditcard_num("**** - **** - **** - " + updateFormModel.getCreditNum4());

		// クレジットカード有効期限
		customerModel.setCreditcard_exp(updateFormModel.getCreditExpM() + "/" + updateFormModel.getCreditExpM());


		return customerModel;
	}

	// CustomerModelを比較するメソッド
	public static Map<String, Object> compareCustomerModels(Object sessionCustomerModel, Object customerModel) throws IllegalAccessException {
        Map<String, Object> differences = new HashMap<String, Object>();

        // model1 と model2 のクラスが同じであることを確認
        if (!sessionCustomerModel.getClass().equals(customerModel.getClass())) {
            throw new IllegalArgumentException("比較対象のモデルが異なります。同じモデル2つを引数に渡してください。");
        }

        // モデルのフィールドを取得
        Field[] fields = sessionCustomerModel.getClass().getDeclaredFields();

        for (Field field : fields) {
            // フィールドにアクセスできるようにする
            field.setAccessible(true);

            // フィールドの値を取得
            Object value1 = field.get(sessionCustomerModel);
            Object value2 = field.get(customerModel);

            // 値が異なれば、差異をリストに追加
            if (value2 != null && !value1.equals(value2)) {
                differences.put(field.toString(), value2);
            }
        }

        return differences;
    }
}
