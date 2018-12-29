package com.john.etl.mybatisplus.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.AbstractSqlInjector;
import com.baomidou.mybatisplus.core.injector.methods.Insert;
import com.baomidou.mybatisplus.extension.injector.methods.*;
import com.john.etl.mybatisplus.method.InsertWithId;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: 张彦斌
 * @Date: 2018-12-26 11:02
 */
public class InsertWithIdInjector extends AbstractSqlInjector {


    @Override
    public List<AbstractMethod> getMethodList() {
        return Stream.of(
                new Insert(),
                new InsertWithId(),
                new LogicDelete(),
                new LogicDeleteByMap(),
                new LogicDeleteById(),
                new LogicDeleteBatchByIds(),
                new LogicUpdate(),
                new LogicUpdateById(),
                new LogicSelectById(),
                new LogicSelectBatchByIds(),
                new LogicSelectByMap(),
                new LogicSelectOne(),
                new LogicSelectCount(),
                new LogicSelectMaps(),
                new LogicSelectMapsPage(),
                new LogicSelectObjs(),
                new LogicSelectList(),
                new LogicSelectPage()
        ).collect(Collectors.toList());
    }
}
