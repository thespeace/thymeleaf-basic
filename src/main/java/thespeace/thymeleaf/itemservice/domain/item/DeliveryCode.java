package thespeace.thymeleaf.itemservice.domain.item;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <h2>배송 방식</h2>
 * <ul>
 *     <li>FAST: 빠른 배송</li>
 *     <li>NORMAL: 일반 배송</li>
 *     <li>SLOW: 느린 배송</li>
 * </ul>
 */
@Data
@AllArgsConstructor
public class DeliveryCode {

    private String code;
    private String displayName;

}
