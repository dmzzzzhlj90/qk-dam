package com.qk.dam.authority.common.vo.group;

import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
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
public class AtyGroupInfoVO {
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 路径
     */
    private String path;
    /**
     * 属性
     */
    private Map<String, List<String>> attributes;
    /**
     * 用户列表
     */
    private List<AtyUserInfoVO> members;
}
