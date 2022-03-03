package com.qk.dm.authority.vo.clientrole;

import com.qk.dm.authority.vo.user.AtyUserInfoVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author shenpj
 * @date 2022/2/22 16:54
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtyClientRoleInfoVO {
    private String id;
    private String name;
    private String description;
    private Map<String, List<String>> attributes;
    private List<AtyUserInfoVO> members;
}
