//カスタムアノテーションの作成

package jp.ken.project.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import jp.ken.project.validator.CreditExpirationValidator;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Constraint(validatedBy = CreditExpirationValidator.class)	// バリデータを指定
public @interface CreditExpiration {
	String message() default "有効期限切れです"; // デフォルトメッセージ
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
