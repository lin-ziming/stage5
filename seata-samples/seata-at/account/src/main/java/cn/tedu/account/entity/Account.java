package cn.tedu.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Haitao
 * @date 2020/12/9 11:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Long id;
    private Long userId;
    private BigDecimal total;
    private BigDecimal used;
    private BigDecimal residue;
    private BigDecimal frozen;

}
