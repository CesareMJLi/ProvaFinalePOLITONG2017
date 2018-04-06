package com.OCS.Dao.model;

import java.io.Serializable;

public class Computer implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;// �ͺ�
    private String name;// ��Ʒ��
    private String type;// ��Ʒ����
    private double price;// ��Ʒ�۸� 
    private String picture;// ��ƷͼƬ��ַ
    private String stock;
    private String memory;
    private String color;
    private String size;
    private String graphic;
    private String processor;
    
    
    //��ȡid����
    public String getId() {
        return id;
    }
    //����id����
    public void setId(String id) {
        this.id = id;
    }
    
  //��ȡname����
    public String getName() {
        return name;
    }
    //����name����
    public void setName(String name) {
        this.name = name;
    }
    
  //��ȡtype����
    public String getType() {
        return type;
    }
    //����type����
    public void setType(String type) {
        this.type = type;
    }
    
  //��ȡprice����
    public double getPrice() {
        return price;
    }
    //����price����
    public void setPrice(double price) {
        this.price = price;
    }
    
  //��ȡpicture����
    public String getPicture() {
        return picture;
    }
    //����picture����
    public void setPicture(String picture) {
        this.picture = picture;
    }
    public String getStock() {
        return stock;
    }
    
    public void setStock(String stock) {
        this.stock = stock;
    }
    public String getMemory() {
        return memory;
    }
    
    public void setMemory(String memory) {
        this.memory = memory;
    }
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    public String getSize() {
        return size;
    }
    
    public void setSize(String size) {
        this.size = size;
    }
    public String getGraphic() {
        return graphic;
    }
    
    public void setGraphic(String graphic) {
        this.graphic = graphic;
    }
    public String getProcessor() {
        return processor;
    }
    
    public void setProcessor(String processor) {
        this.processor = processor;
    }


}
