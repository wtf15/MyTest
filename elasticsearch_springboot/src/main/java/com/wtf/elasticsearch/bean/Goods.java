package com.wtf.elasticsearch.bean;

import org.elasticsearch.index.VersionType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "goods", type = "_doc", shards = 1, replicas = 0, createIndex = false,
    useServerConfiguration = true, versionType = VersionType.EXTERNAL)
public class Goods {

    @Id
    private long id;
    @Field(type = FieldType.Keyword)
    private String sn;
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Double)
    private double price;
    @Field(type = FieldType.Integer)
    private int num;
    @Field(type = FieldType.Keyword)
    private int alert_num;
    @Field(type = FieldType.Keyword)
    private String image;
    @Field(type = FieldType.Keyword)
    private String images;
    @Field(type = FieldType.Double)
    private double weight;
    @Field(type = FieldType.Date)
    private String create_time;
    @Field(type = FieldType.Date)
    private String update_time;
    @Field(type = FieldType.Keyword)
    private String spu_id;
    @Field(type = FieldType.Integer)
    private int category_id;
    @Field(type = FieldType.Text)
    private String category_name;
    @Field(type = FieldType.Keyword)
    private String brand_name;
    @Field(type = FieldType.Text)
    private String spec;
    @Field(type = FieldType.Integer)
    private int sale_num;
    @Field(type = FieldType.Integer)
    private int comment_num;
    @Field(type = FieldType.Integer)
    private int status;
    @Version
    private Long version;

    public Goods() {}

    public Goods(long id, String sn, String name, double price, int num, int alert_num, String image, String images,
        double weight, String create_time, String update_time, String spu_id, int category_id, String category_name,
        String brand_name, String spec, int sale_num, int comment_num, int status) {
        this.id = id;
        this.sn = sn;
        this.name = name;
        this.price = price;
        this.num = num;
        this.alert_num = alert_num;
        this.image = image;
        this.images = images;
        this.weight = weight;
        this.create_time = create_time;
        this.update_time = update_time;
        this.spu_id = spu_id;
        this.category_id = category_id;
        this.category_name = category_name;
        this.brand_name = brand_name;
        this.spec = spec;
        this.sale_num = sale_num;
        this.comment_num = comment_num;
        this.status = status;
    }

    public Goods(long id, String sn, String name, double price, int num, int alert_num, String image, String images,
        double weight, String create_time, String update_time, String spu_id, int category_id, String category_name,
        String brand_name, String spec, int sale_num, int comment_num, int status, Long version) {
        this.id = id;
        this.sn = sn;
        this.name = name;
        this.price = price;
        this.num = num;
        this.alert_num = alert_num;
        this.image = image;
        this.images = images;
        this.weight = weight;
        this.create_time = create_time;
        this.update_time = update_time;
        this.spu_id = spu_id;
        this.category_id = category_id;
        this.category_name = category_name;
        this.brand_name = brand_name;
        this.spec = spec;
        this.sale_num = sale_num;
        this.comment_num = comment_num;
        this.status = status;
        this.version = version;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Goods{" + "id=" + id + ", sn='" + sn + '\'' + ", name='" + name + '\'' + ", price=" + price + ", num="
            + num + ", alert_num=" + alert_num + ", image='" + image + '\'' + ", images='" + images + '\'' + ", weight="
            + weight + ", create_time='" + create_time + '\'' + ", update_time='" + update_time + '\'' + ", spu_id='"
            + spu_id + '\'' + ", category_id=" + category_id + ", category_name='" + category_name + '\''
            + ", brand_name='" + brand_name + '\'' + ", spec='" + spec + '\'' + ", sale_num=" + sale_num
            + ", comment_num=" + comment_num + ", status=" + status + ", version=" + version + '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getAlert_num() {
        return alert_num;
    }

    public void setAlert_num(int alert_num) {
        this.alert_num = alert_num;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getSpu_id() {
        return spu_id;
    }

    public void setSpu_id(String spu_id) {
        this.spu_id = spu_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public int getSale_num() {
        return sale_num;
    }

    public void setSale_num(int sale_num) {
        this.sale_num = sale_num;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}