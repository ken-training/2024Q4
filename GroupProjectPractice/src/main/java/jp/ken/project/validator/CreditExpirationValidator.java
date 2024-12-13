package jp.ken.project.validator;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.ken.project.annotation.CreditExpiration;

public class CreditExpirationValidator implements ConstraintValidator<CreditExpiration, String>{

    @Override
    public void initialize(CreditExpiration constraintAnnotation) {
        // 初期化処理は特に必要なし
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            LocalDate now = LocalDate.now();	// 現在の日付を取得
            int year = now.getYear();			//現在の年を取得
            int month = now.getMonthValue();			//現在の月を取得

            //2024 + 12 →  202412
            String nowYm = String.valueOf(year) + String.valueOf(month);

            /* 現在と入力値文字列を大小比較し、
            		現在 <= 入力値 であるなら true を返す	*/

            return Integer.parseInt(nowYm) <= Integer.parseInt(value);
        } catch (Exception e) {
            return false; // フォーマットエラーの場合は無効とする
        }
    }
}