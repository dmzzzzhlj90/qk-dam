package com.qk.dam.sqlbuilder.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**`
 * 表信息
 * @author wangzp
 * @date 2021/11/12 16:02
 * @since 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Table {
    private String name = "";
    private List<Column> columns = new ArrayList<>();
    private String comments = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void addColumn(Column column) {
        this.columns.add(column);
    }


}
