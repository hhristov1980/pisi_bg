package pisibg.model.dto.orderDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import pisibg.model.pojo.OrderStatus;

import javax.persistence.JoinColumn;

@Setter
@Getter
@NoArgsConstructor
@Component
public class OrderEditRequestDTO {
    private int id;
    private int orderStatusId;
    private String address;
    private double grossValue;
    private double discount;
    private double netValue;
    @JsonProperty(value = "isPaid")
    private boolean isPaid;
}
