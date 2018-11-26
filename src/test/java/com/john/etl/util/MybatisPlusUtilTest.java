package com.john.etl.util;

import org.junit.Test;

/**
 * @Author: 张彦斌
 * @Date: 2018-11-26 11:08
 */
public class MybatisPlusUtilTest {

    @Test
    public void generator() {
        MabatisGeneratorUtil.generator("mission", "t_etl_mission", "com.john.etl", "t_");
    }
}
