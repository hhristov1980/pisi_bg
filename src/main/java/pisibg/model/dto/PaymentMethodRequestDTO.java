package pisibg.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;


@NoArgsConstructor
@Setter
@Getter
@Component
public class PaymentMethodRequestDTO {

    private String type;

}