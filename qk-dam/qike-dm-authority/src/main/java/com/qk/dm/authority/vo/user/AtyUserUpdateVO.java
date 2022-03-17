package com.qk.dm.authority.vo.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shenpj
 * @date 2022/2/22 11:54
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AtyUserUpdateVO extends AtyUserVO {
    private String userId;
}
