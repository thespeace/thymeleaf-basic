package thespeace.thymeleaf.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource ms;

    /**
     * <h2>메시지 단순 테스트</h2>
     * {@code locale} 정보가 없으면 {@code basename}에서 설정한 기본 이름 메시지 파일을 조회한다.<br>
     * {@code basename}으로 {@code messages}를 지정 했으므로 {@code messages.properties}파일에서 데이터 조회한다.
     */
    @Test
    void helloMessage() {
        String result = ms.getMessage("hello", null, null); //code: hello, args: null, locale: null
        assertThat(result).isEqualTo("안녕");
    }

    /**
     * <h2>메시지가 없는 경우</h2>
     * 메시지가 없는 경우에는 {@code NoSuchMessageException}이 발생한다.
     */
    @Test
    void notFoundMessageCode() {
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    /**
     * <h2>메시지가 없는 경우, 기본 메시지</h2>
     * 메시지가 없어도 기본 메시지( defaultMessage )를 사용하면 기본 메시지가 반환된다.
     */
    @Test
    void notFoundMessageCodeDefaultMessage() {
        String result = ms.getMessage("no_code", null, "기본 메시지", null);
        assertThat(result).isEqualTo("기본 메시지");
    }

    /**
     * <h2>매개변수 사용</h2>
     * 다음 메시지의 {0} 부분은 매개변수를 전달해서 치환할 수 있다.<br>
     * {@code hello.name=안녕 {0}} -> {@code Spring}단어를 매개변수로 전달 -> {@code 안녕 Spring}
     */
    @Test
    void argumentMessage() {
        String result = ms.getMessage("hello.name", new Object[]{"Spring"}, null);
        assertThat(result).isEqualTo("안녕 Spring");
    }

    /**
     * <h2>국제화 파일 선택 1</h2>
     * locale 정보를 기반으로 국제화 파일을 선택한다
     * <ul>
     *     <li>{@code Locale}이 {@code en_US}의 경우 {@code messages_en_US} -> {@code messages_en} -> {@code messages} 순서로 찾는다</li>
     *     <li>{@code Locale}에 맞추어 구체적인 것이 있으면 구체적인 것을 찾고, 없으면 디폴트를 찾는다고 이해하면 된다.</li>
     * </ul>
     * Locale 정보가 없는 경우 Locale.getDefault() 을 호출해서 시스템의 기본 로케일을 사용!<br>
     * ex) {@code locale = null}인 경우 -> 시스템 기본 {@code locale}이 `ko_KR` 이므로 {@code messages_ko.properties} 조회 시도 ->
     *     조회 실패 -> {@code messages.properties} 조회
     */
    @Test
    void defaultLang() {
        assertThat(ms.getMessage("hello", null, null)).isEqualTo("안녕"); //locale 정보가 없으므로 messages 를 사용.
        assertThat(ms.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕"); //locale 정보가 있지만, message_ko 가 없으므로 messages 를 사용.
    }

    /**
     * <h2>국제화 파일 선택 2</h2>
     */
    @Test
    void enLang() {
        //locale 정보가 Locale.ENGLISH 이므로 messages_en 을 찾아서 사용.
        assertThat(ms.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello");
    }


}
