package com.example.testorderservice.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 
 * @TableName t_order
 */
@TableName(value ="t_order")
public class TOrder implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String orderNo;

    /**
     * 
     */
    private String userId;

    /**
     * 
     */
    private String commodityCode;

    /**
     * 
     */
    private Integer count;

    /**
     * 
     */
    private Double amount;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 
     */
    public String getCommodityCode() {
        return commodityCode;
    }

    /**
     * 
     */
    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    /**
     * 
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * 
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TOrder other = (TOrder) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getCommodityCode() == null ? other.getCommodityCode() == null : this.getCommodityCode().equals(other.getCommodityCode()))
            && (this.getCount() == null ? other.getCount() == null : this.getCount().equals(other.getCount()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getCommodityCode() == null) ? 0 : getCommodityCode().hashCode());
        result = prime * result + ((getCount() == null) ? 0 : getCount().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", userId=").append(userId);
        sb.append(", commodityCode=").append(commodityCode);
        sb.append(", count=").append(count);
        sb.append(", amount=").append(amount);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}