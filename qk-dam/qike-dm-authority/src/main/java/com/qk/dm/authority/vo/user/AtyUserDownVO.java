package com.qk.dm.authority.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/15 14:59
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtyUserDownVO {
    @NotBlank(message = "域必填！")
    String realm;
    @NotEmpty(message = "用户id集合必填！")
    List<String> userIds;
}
