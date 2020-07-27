package com.yang.template.model.query;

import com.yang.template.model.common.Pageable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jjyy
 * @apiNote
 * @implNote
 * @since 2019/9/5
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class TestQueryParam extends Pageable {
    private static final long serialVersionUID = 1L;
}
