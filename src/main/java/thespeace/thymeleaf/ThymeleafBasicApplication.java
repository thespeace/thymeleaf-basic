package thespeace.thymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication
public class ThymeleafBasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThymeleafBasicApplication.class, args);
	}

	/**
	 * <h2>스프링 메시지 소스 설정</h2>
	 * 스프링은 기본적인 메시지 관리 기능을 제공한다.<br>
	 * 메시지 관리 기능을 사용하려면 스프링이 제공하는 {@code MessageSource}를 스프링 빈으로 등록하면 되는데,
	 * {@code MessageSource}는 인터페이스이다. 따라서 구현체인 {@code ResourceBundleMessageSource}를 스프링 빈으로 등록하면 된다.<br><br>
	 * <ul>
	 *     <li>`messages` 로 지정하면 `messages.properties` 파일을 읽어서 사용한다.</li>
	 *     <li>추가로 국제화 기능을 적용하려면 `messages_en.properties` , `messages_ko.properties` 와 같
	 * 			이 파일명 마지막에 언어 정보를 주면된다. 만약 찾을 수 있는 국제화 파일이 없으면
	 * 			`messages.properties` (언어정보가 없는 파일명)를 기본으로 사용한다.</li>
	 *     <li>파일의 위치는 `/resources/messages.properties` 에 두면 된다.</li>
	 *     <li>여러 파일을 한번에 지정할 수 있다. 여기서는 messages , errors 둘을 지정했다.</li>
	 * </ul>
	 */
/*	@Bean
	public MessageSource messageSource() { //직접 등록
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("messages", "errors");
		messageSource.setDefaultEncoding("utf-8"); //defaultEncoding : 인코딩 정보를 지정한다. utf-8 을 사용하면 된다.
		return messageSource;
	}*/
	//하지만 스프링 부트를 사용하면 스프링 부트가 `MessageSource`를 자동으로 스프링 빈으로 등록한다.
}
