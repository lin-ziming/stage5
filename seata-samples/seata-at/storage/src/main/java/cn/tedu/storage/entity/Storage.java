package cn.tedu.storage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Haitao
 * @date 2020/12/9 14:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Storage {
    private Long id;
    private Long productId;
    private Integer total;
    private Integer used;
    private Integer residue;
    private Integer frozen;
}
