package cn.tedu.order.tx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TxAccountMessage {
    private Long userId;
    private BigDecimal money;
    private String xid;
}
