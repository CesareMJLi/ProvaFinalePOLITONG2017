package com.OCS.Dao.model;

public class Component {
	private String id;// �ͺ�
    private String name;// ����
    private String type;// ����
    private double price;// �۸� 
    
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

}
