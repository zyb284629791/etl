package com.john.etl.constant;

/**
 * @Author: 张彦斌
 * @Date: 2018-12-18 15:18
 */
public enum EtlOperStatus {

    /**
     * 清洗状态，0:成功 1:忽略 2: 中间库完整性校验失败 3: 正式库校验失败 4: 插入异常 5: 更新异常 6: 删除异常
     */
    Success(0),
    Ignore(1),
    MidFail(2),
    OfficialFail(3),
    InsertFail(4),
    UpdateFail(5),
    DeleteFail(6);

    private final int status;

    EtlOperStatus(int status) {
        this.status = status;
    }
}
