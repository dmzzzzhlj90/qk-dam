package com.qk.sankuai.inf;

import com.qk.sankuai.inf.leaf.common.Result;

public interface IDGen {
    Result get(String key);
    boolean init();
}
