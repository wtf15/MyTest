package com.wtf.elasticsearch.bean;

import java.util.List;

import org.elasticsearch.index.VersionType;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "test_springdata_es", type = "_doc", shards = 1, replicas = 0, createIndex = true,
    useServerConfiguration = false, versionType = VersionType.EXTERNAL)
public class Item {
    @Id
    private long ld;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Keyword)
    private String sex;

    @Field(type = FieldType.Integer)
    private Integer age;

    @Field(type = FieldType.Keyword)
    private List<String> interest;

    @Field(type = FieldType.Object)
    private ChildItem childItem;

    public Item() {}

    public Item(long ld, String name, String sex, Integer age, List<String> interest, ChildItem childItem) {
        this.ld = ld;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.interest = interest;
        this.childItem = childItem;
    }

    @Override
    public String toString() {
        return "Item{" + "ld=" + ld + ", name='" + name + '\'' + ", sex='" + sex + '\'' + ", age=" + age + ", interest="
            + interest + ", childItem=" + childItem + '}';
    }

    public long getLd() {
        return ld;
    }

    public void setLd(long ld) {
        this.ld = ld;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<String> getInterest() {
        return interest;
    }

    public void setInterest(List<String> interest) {
        this.interest = interest;
    }

    public ChildItem getChildItem() {
        return childItem;
    }

    public void setChildItem(ChildItem childItem) {
        this.childItem = childItem;
    }
}
