package com.john.etl.util;

import com.baomidou.mybatisplus.annotation.IdType;
import org.junit.Test;

/**
 * @Author: 张彦斌
 * @Date: 2018-11-26 11:08
 */
public class MybatisPlusUtilTest {

    @Test
    public void generator() {

        MabatisGeneratorUtil.initBasePackageAndDataSourceAndIdType("com.john.etl.mid","", IdType.ID_WORKER);

        MabatisGeneratorUtil.generator("instance", "t_int_nodes", "MidNodes", "t_");


//         MabatisGeneratorUtil.generator("instance", "t_int_nodes", "MidNodes", "t_");
    }
}
