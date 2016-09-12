package com.nbl.common.constants;

/**
 * 用于定义错误码和错误信息 <br>
 * 错误码规则：应用类型分类{2位}+错误类型分类{1位}+错误序号{3位}<br>
 * 应用类型分类：<br>
 * BU——zlebank-energy-business<br>
 * 错误类型分类：<br>
 * C-信息校验类<br>     
 * B-业务信息规则类<br> 
 * D-数据库操作类<br>   
 * T-系统间通讯类<br>   
 * E-其他系统异常类
 * @version 1.0.0
 * @author AlanMa
 *
 */
public class ErrorCode {
    /**
     * 产品ID不符合规范
     */
    public static final String BUC001 = "BUC001|产品ID不符合规范";
    /**
     * 平台无此产品[%s]
     */
    public static final String BUC002 = "BUC002|平台无此产品[%s]";
    /**
     * 此订单【[%s]】状态为【[%s]】不能支付
     */
    public static final String BUC003 = "BUC003| 此订单【[%s]】状态为【[%s]】不能支付";
    /**
     * 订单金额与支付金额不符
     */
    public static final String BUC004 = "BUC004| 订单金额与支付金额不符";
    /**
     * 通过条件[%s]查询实体[%s]为空
     */
    public static final String BUD001 = "BUC001|通过条件[%s]查询实体[%s]为空";
    /**
     * 产品信息不存在ProductId：[%s]
     */
    public static final String BUD002 = "BUD002|产品信息不存在ProductId：[%s]";
    /**
     * 更新交易订单状态异常
     */
    public static final String BUD003 = "BUD003|更新交易订单状态异常";
    /**
     * 产品信息特新信息配置缺失
     */
    public static final String BUD004 = "BUD004|产品信息特新信息配置缺失";
    /**
     * 此产品[%s]已售馨
     */
    public static final String BUB001 = "BUB001|此产品[%s]已售馨";
    /**
     * 未到起售时间[%s]
     */
    public static final String BUB002 = "BUB002|未到起售时间[%s]";
    /**
     * 本次购买商品数量超限
     */
    public static final String BUB003 = "BUB003|本次购买商品数量超限";
    /**
     * 商品库存不足
     */
    public static final String BUB004 = "BUB004|商品库存不足";
    /**
     * 商品超额解锁
     */
    public static final String BUB005 = "BUB005|商品超额解锁";
    /**
     * 商品库存不足
     */
    public static final String BUB006 = "BUB006|商品库存不足";
    /**
     * 商品状态非募集中尚不支持购买
     */
    public static final String BUB007 = "BUB007|商品状态非募集中尚不支持购买";
    /**
     * 时间转换异常
     */
    public static final String BUE001 = "BUE001|时间转换异常";
    /**
     * 产品特色信息查询异常
     */
    public static final String BUE002 = "BUE002|产品特色信息查询异常";
    
}
