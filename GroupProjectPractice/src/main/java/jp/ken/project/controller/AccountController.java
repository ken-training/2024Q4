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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.ken.project.dao.CustomerDao;
import jp.ken.project.group.GroupOrder;
import jp.ken.project.model.CustomerModel;
import jp.ken.project.model.UpdateFormModel;


@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private CustomerDao customerDao;

	// マイページ画面へ遷移
	@RequestMapping(method = RequestMethod.GET)
	public String get(Model model, HttpSession session) {
		// セッションから顧客情報を取得
		CustomerModel sessionCustomer = (CustomerModel) session.getAttribute("customerModel");

		// 顧客情報がセッションに保存されていない場合（例えば、ログインしていない場合）の処理
		if (sessionCustomer == null) {
			return "redirect:/login"; // ログイン画面にリダイレクト
		}

		// 顧客情報がセッションにある場合、その情報を使ってモデルに設定
		CustomerModel cModel = new CustomerModel();

		// セッションから取得した氏名を設定
		cModel.setCustomer_name(sessionCustomer.getCustomer_name());
		// セッションから取得したフリガナを設定
		cModel.setCustomer_phonetic(sessionCustomer.getCustomer_phonetic());
		// セッションから取得したメールアドレスを設定
		cModel.setMail(sessionCustomer.getMail());
		// セッションから取得した郵便番号を設定
		cModel.setZip(sessionCustomer.getZip());
		// セッションから取得した住所を設定
		cModel.setAddress(sessionCustomer.getAddress());
		// セッションから取得した電話番号を設定
		cModel.setPhone(sessionCustomer.getPhone());
		// セッションから取得した生年月日を設定
		cModel.setBirthday(sessionCustomer.getBirthday());
		// セッションから取得したマスク化クレジットカード番号を設定
		cModel.setMasked_creditcard_num(sessionCustomer.getMasked_creditcard_num());
		// セッションから取得したカード有効期限を設定
		cModel.setCreditcard_exp(sessionCustomer.getCreditcard_exp());

		model.addAttribute("customerModel", cModel);
	    return "account"; // マイページ画面へ遷移
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
	public String delete(Model model, CustomerModel customerModel, HttpSession session) {
		// セッションから顧客情報を取得
		CustomerModel sessionCustomer = (CustomerModel) session.getAttribute("customerModel");

		if (sessionCustomer != null) {
			// セッションからメールアドレスを取得し、customerModelに設定
			customerModel.setMail(sessionCustomer.getMail());
			// 元のメールアドレス（退会前のメールアドレス）を設定
			customerModel.setOriginalMail(sessionCustomer.getMail());
			// 退会処理の前にユニークなメールアドレスを生成
			String newMail = getUniqueMail(customerModel.getMail()); // ユニークなメールアドレスを生成
			customerModel.setMail(newMail); // 新しいメールアドレスを設定
			// 退会処理を実行
			int numberOfRow = customerDao.updateIsRemove(customerModel); // 退会処理
			if (numberOfRow == 1) {
				session.invalidate();  // セッションを無効化（ログアウト）
				return "redirect:/top"; // 退会処理後、トップページにリダイレクト
			} else {
				return "redirect:/top"; // 退会処理が失敗した場合もトップページにリダイレクト
			}
		} else {
			// セッションに顧客情報がない場合、エラーハンドリング
			return "redirect:/login"; // ログイン画面にリダイレクト（セッションが切れている場合など）
		}
	}

	// メールアドレスをユニークにするための関数
	private String getUniqueMail(String originalMail) {
		String newMail = originalMail;
		int i = 1;
		// 同じメールアドレスが存在しないかをチェック
		while (customerDao.isMailExist(newMail)) {
			newMail = originalMail + i; // 末尾に番号を付ける
			i++;
		}
		return newMail;
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
		System.out.println("セッション確認" + customerModel.getCustomer_id());

		// customerModelをupdateFormModelに変換
		updateFormModel = parseUpdateFormModel(customerModel);

		//プルダウン初期値設定
		List<String> birthYearList = getNumberList(thisYear - 125, thisYear);
		birthYearList.add(0, "----");
		List<String> creditExpMList = getNumberList(1, 12);
		List<String> creditExpYList = getNumberList(thisYear, thisYear +10);
		creditExpMList.add(0,"--");
		creditExpYList.add(0,"----");

		// プルダウンで表示する用のリストをモデルに紐づけ
		model.addAttribute("birthYearList", birthYearList);
		model.addAttribute("birthMonthList", getNumberList(1, 12));
		model.addAttribute("birthDayList", getNumberList(1, 31));
		model.addAttribute("creditExpMList", creditExpMList);
		model.addAttribute("creditExpYList", creditExpYList);

		model.addAttribute("updateFormModel", updateFormModel);
		return "accountUpdate";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updatePost(@Validated(GroupOrder.class) @ModelAttribute UpdateFormModel updateFormModel,BindingResult result, Model model,HttpSession session){
//		System.out.println("post");
		// バリデーションエラーがある場合
		if (result.hasErrors()) {
					return "redirect:/account/update";  // エラーがあれば再度入力画面を表示
		// 正常
		} else {

			try {
				// セッションから会員IDを取得
				@SuppressWarnings("unchecked")
				CustomerModel sessionCustomerModel = (CustomerModel)session.getAttribute("customerModel");
				int id = sessionCustomerModel.getCustomer_id();

				// updateFormModelからcustomerModelに変換
				CustomerModel customerModel = parseCustomerModel(updateFormModel);
				customerModel.setCustomer_id(id);

				// sessionの会員情報と今回入力された会員情報を比較する
				Map<String, Object> differences = compareCustomerModels(sessionCustomerModel, customerModel);
				System.out.println(differences.toString());


				// 変更箇所があった場合
				if (!differences.isEmpty()) {
					// 顧客情報をデータベースに登録
					sessionCustomerModel = customerDao.updateCustomer(id, differences);
					session.setAttribute("customerModel", sessionCustomerModel);
				}

				return "redirect:/account";  // 成功時に遷移するビュー

			} catch (IllegalAccessException e) {
				return "redirect:/account";  // 登録に失敗した場合、再度エラーメッセージを表示
				// どうしよ
			}
		}
//		return "accountUpdate";
	}

	// プルダウン用の数字のリストを作成するメソッド
	private List<String> getNumberList(int start, int end){
		List<String> numberList = new ArrayList<String>();

		// start～endまでの数字をリストの中に格納する
		for(int i = start; i<= end; i++) {
//			if (i < 10) {
//				numberList.add("0" + Integer.toString(i));
//			} else {
//				numberList.add(Integer.toString(i));
//			}
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
			System.out.println("words[0]" + words[0]);
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
			updateFormModel.setCreditExpY("20" + words[1]);
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
		if(!updateFormModel.getZip1().isEmpty() && !updateFormModel.getZip2().isEmpty()) {
			customerModel.setZip(updateFormModel.getZip1() + "-" + updateFormModel.getZip2());	// 郵便番号
		}
		if(!updateFormModel.getPrefecture().isEmpty() && !updateFormModel.getCity().isEmpty()
				 && !updateFormModel.getBlock().isEmpty()) {
			customerModel.setAddress(updateFormModel.getPrefecture() + " " + updateFormModel.getCity()
				+ " " + updateFormModel.getBlock() + " " + updateFormModel.getBuilding());	// 住所
		}
		if(!updateFormModel.getPhone1().isEmpty() && !updateFormModel.getPhone2().isEmpty()
				&& !updateFormModel.getPhone3().isEmpty()) {
			customerModel.setPhone(updateFormModel.getPhone1() + "-" + updateFormModel.getPhone2()
				+ "-" + updateFormModel.getPhone3()); // 電話番号
		}


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

		if(!updateFormModel.getCreditNum1().isEmpty() && !updateFormModel.getCreditNum2().isEmpty()
				&& !updateFormModel.getCreditNum3().isEmpty() && !updateFormModel.getCreditNum4().isEmpty()) {
			// クレジットカード番号
			customerModel.setCreditcard_num(updateFormModel.getCreditNum1() + updateFormModel.getCreditNum2()
											+ updateFormModel.getCreditNum3() + updateFormModel.getCreditNum4());
			// マスク化したクレジットカード番号
			customerModel.setMasked_creditcard_num("**** - **** - **** - " + updateFormModel.getCreditNum4());

		}

		if(!updateFormModel.getCreditExpM().isEmpty() && !updateFormModel.getCreditExpY().isEmpty()
				&& !updateFormModel.getCreditExpM().equals("--") && !updateFormModel.getCreditExpY().equals("----")) {
			// クレジットカード有効期限
			System.out.println("M:" + updateFormModel.getCreditExpM());
			System.out.println("Y:" + updateFormModel.getCreditExpY());

			customerModel.setCreditcard_exp(updateFormModel.getCreditExpM() + "/" + updateFormModel.getCreditExpY().substring(2));
		}

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
            System.out.println("value1："+value1);
            Object value2 = field.get(customerModel);
            System.out.println("value2："+value2);

            // 値が異なれば、差異をリストに追加
            if (value2 != null && value2 != "" && value2 != (Object)0) {
            	if(value1 == null || !value1.equals(value2) ) {
            		String strField = field.toString();
            		String key = strField.substring(strField.lastIndexOf('.') + 1);
            		System.out.println(key);
            		String[] word = strField.split(" ");
            		System.out.println(word[1]);
            		if (word[1] == "java.lang.String") {
            			value2 = "\'" + value2 + "\'";
            		}
            		differences.put(key , value2);
            		System.out.println("リスト追加："+value2);
            	}
            }
        }

        return differences;
    }
}
