package com.springboot.spike.service;

public interface GoodsService {

    /**
     * 通过商品编号，查询商品是否有库存
     * @param goodsId
     * @return
     */
    public int queryGoodsInventory(int goodsId);


    /**
     * 用户下单，生成账单信息，最后返回最后的账单的编号，方便进行查询展示使用
     * @param userId
     * @param goodsId
     * @param quantity
     * @param updateTime
     * @return
     */
    public int orderGoodsByUser(int userId, int goodsId, int quantity, long updateTime);


    /**
     * 库存数量预减，数据预先扣除
     * @param goodsId
     * @param preSubQuantity
     * @return
     */
    public int preSubGoodsInventory(int goodsId, int preSubQuantity);


    /**
     * 库存数量添加，数据将预先扣除的数据添加上来，防止用户光下单，不付费
     * @param goodsId
     * @param addQuantity
     * @return
     */
    public int addGoodsInventory(int goodsId, int addQuantity);




}
