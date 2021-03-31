package pisibg.model.dto.orderDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import pisibg.model.pojo.Order;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Component
public class OrderDailyReportRequestDTO {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("fromDate")
    private LocalDateTime fromDate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("toDate")
    private LocalDateTime toDate;
    int page;
    int ordersPerPage;
}
