package com.wtf.elasticsearch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.elasticsearch.index.query.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.wtf.elasticsearch.bean.Goods;
import com.wtf.elasticsearch.mapper.GoodsMapper;

@SpringBootTest
class TestMapper {

    @Autowired
    GoodsMapper goodsMapper;

    // 添加一条数据 and 修改一条数据
    @Test
    void insertGoods() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = simpleDateFormat.format(new Date());
        Goods goods = new Goods(1025332689, "", "OPPO R17新年版 2500万美颜拍照 6.4英寸水滴屏 光感屏幕指纹 6G+128G 全网通 移动联通电信4G 双卡双待手机",
            37400, 20, 100,
            "https://m.360buyimg.com/mobilecms/s720x720_jfs/t1/10441/9/5525/162976/5c177debEaf815b43/3aa7d4dc182cc4d9.jpg!q70.jpg.webp",
            "https://m.360buyimg.com/mobilecms/s720x720_jfs/t1/10441/9/5525/162976/5c177debEaf815b43/3aa7d4dc182cc4d9.jpg!q70.jpg.webp",
            10, str, str, "10000243333000", 558, "手机", "OPPO", "{'颜色': '王者荣耀定制版', '版本': 'R17'}", 1, 1, 1, 4L);
        System.out.println(goodsMapper.save(goods));
    }

    // 批量插入和批量修改
    @Test
    void insertGoodsList() {

        List<Goods> list = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = simpleDateFormat.format(new Date());
        Goods goods = new Goods(1001332689, "", "OPPO R17新年版 2500万美颜拍照 6.4英寸水滴屏 光感屏幕指纹 6G+128G 全网通 移动联通电信4G 双卡双待手机",
            37400, 10000, 100,
            "https://m.360buyimg.com/mobilecms/s720x720_jfs/t1/10441/9/5525/162976/5c177debEaf815b43/3aa7d4dc182cc4d9.jpg!q70.jpg.webp",
            "https://m.360buyimg.com/mobilecms/s720x720_jfs/t1/10441/9/5525/162976/5c177debEaf815b43/3aa7d4dc182cc4d9.jpg!q70.jpg.webp",
            10, str, str, "10000243333000", 558, "手机", "OPPO", "{'颜色': '王者荣耀定制版', '版本': 'R17'}", 1, 1, 1);

        Goods goods2 = new Goods(1001368912, "", "OPPO R17新年版 2500万美颜拍照 6.4英寸水滴屏 光感屏幕指纹 6G+128G 全网通 移动联通电信4G 双卡双待手机",
            37400, 10000, 100,
            "https://m.360buyimg.com/mobilecms/s720x720_jfs/t1/10441/9/5525/162976/5c177debEaf815b43/3aa7d4dc182cc4d9.jpg!q70.jpg.webp",
            "https://m.360buyimg.com/mobilecms/s720x720_jfs/t1/10441/9/5525/162976/5c177debEaf815b43/3aa7d4dc182cc4d9.jpg!q70.jpg.webp",
            10, str, str, "10000243333000", 558, "手机", "OPPO", "{'颜色': '王者荣耀定制版', '版本': 'R17'}", 1, 1, 1);

        Goods goods3 = new Goods(100001402792L, "", "OPPO R17新年版 2500万美颜拍照 6.4英寸水滴屏 光感屏幕指纹 6G+128G 全网通 移动联通电信4G 双卡双待手机",
            37400, 10000, 100,
            "https://m.360buyimg.com/mobilecms/s720x720_jfs/t1/10441/9/5525/162976/5c177debEaf815b43/3aa7d4dc182cc4d9.jpg!q70.jpg.webp",
            "https://m.360buyimg.com/mobilecms/s720x720_jfs/t1/10441/9/5525/162976/5c177debEaf815b43/3aa7d4dc182cc4d9.jpg!q70.jpg.webp",
            10, str, str, "10000243333000", 558, "手机", "OPPO", "{'颜色': '王者荣耀定制版', '版本': 'R17'}", 1, 1, 1);

        list.add(goods);
        list.add(goods2);
        list.add(goods3);
        goodsMapper.saveAll(list);
    }

    // 删除数据
    @Test
    void updateGoods() {
        // 删除一条
        // goodsMapper.deleteById("1001332689");
        // 删除所有或者传入集合 删除集合中的数据
        goodsMapper.deleteAll();
    }

    // 查询数据
    @Test
    void uqueryGoods() {
        // 查询一条数据
        // Optional<Goods> goods = goodsMapper.findById("100001402792");
        // System.out.println(goods.get());

        // System.out.println("====================================");
        // 查询所有数据
        // Iterable<Goods> goodsAll = goodsMapper.findAll();
        // Iterator<Goods> goodsIterator = goodsAll.iterator();
        // int count=0;
        // while (goodsIterator.hasNext()){
        // Goods goods1 = goodsIterator.next();
        // System.out.println(goods1);
        // count++;
        // }
        // System.out.println(count);

        // System.out.println("====================================");
        // 分页排序
        // page页码 并不是跳过多少数据
        // 返回数
        Pageable pageable = PageRequest.of(1, 100, Sort.by(Sort.Direction.ASC, "num"));
        Page<Goods> goodsPage = goodsMapper.findAll(pageable);
        Iterator<Goods> goodsIterator = goodsPage.iterator();
        int count = 0;
        while (goodsIterator.hasNext()) {
            Goods goods1 = goodsIterator.next();
            System.out.println(goods1);
            count++;
        }
        System.out.println(count);
    }

    @Test
    void exists() {
        // 判断文档是否存在
        boolean exists = goodsMapper.existsById("文档ID");
    }

    // term查询
    @Test
    void termGoods() {
        // 主要用于精确匹配哪些值，比如数字，日期，布尔值或 not_analyzed 的字符串(未经分析的文本数据类型)
        // 搜索前不会再对搜索词进行分词，所以我们的搜索词必须是文档分词集合中的一个。
        TermQueryBuilder termQueryBuilder = new TermQueryBuilder("name", "2018");
        Pageable pageable = PageRequest.of(0, 100);
        Iterable<Goods> goods = goodsMapper.search(termQueryBuilder, pageable);
        Iterator<Goods> goodsIterator = goods.iterator();
        int count = 0;
        while (goodsIterator.hasNext()) {
            Goods goods1 = goodsIterator.next();
            System.out.println(goods1);
            count++;
        }
        System.out.println(count);
    }

    // terms查询
    @Test
    void termsGoods() {
        // terms 跟 term 有点类似，但 terms 允许指定多个匹配条件。
        // 如果某个字段指定了多个值，那么文档需要一起去做匹配 或者关系
        TermsQueryBuilder termsQueryBuilder = new TermsQueryBuilder("name", "2018", "最新", "女鞋");
        Pageable pageable = PageRequest.of(0, 100);
        Iterable<Goods> goods = goodsMapper.search(termsQueryBuilder, pageable);
        Iterator<Goods> goodsIterator = goods.iterator();
        int count = 0;
        while (goodsIterator.hasNext()) {
            Goods goods1 = goodsIterator.next();
            System.out.println(goods1);
            count++;
        }
        System.out.println(count);
    }

    // range查询
    @Test
    void rangelGoods() {
        // 范围查询
        RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("price");
        rangeQueryBuilder.gt(20);
        rangeQueryBuilder.lt(10000);
        Pageable pageable = PageRequest.of(0, 100);
        Iterable<Goods> goods = goodsMapper.search(rangeQueryBuilder, pageable);
        Iterator<Goods> goodsIterator = goods.iterator();
        int count = 0;
        while (goodsIterator.hasNext()) {
            Goods goods1 = goodsIterator.next();
            System.out.println(goods1);
            count++;
        }
        System.out.println(count);
    }

    // exists查询
    @Test
    void existsGoods() {
        // exists 查询可以用于查找文档中是否包含指定字段或没有某个字段，类似于SQL语句中的 IS_NULL 条件
        // 包含这个字段就返回返回这条数据
        ExistsQueryBuilder existsQueryBuilder = new ExistsQueryBuilder("category_name");
        Pageable pageable = PageRequest.of(0, 100);
        Iterable<Goods> goods = goodsMapper.search(existsQueryBuilder, pageable);
        Iterator<Goods> goodsIterator = goods.iterator();
        int count = 0;
        while (goodsIterator.hasNext()) {
            Goods goods1 = goodsIterator.next();
            System.out.println(goods1);
            count++;
        }
        System.out.println(count);
    }

    // match查询
    @Test
    void matchGoods() {
        // match查询会先对搜索词进行分词,分词完毕后再逐个对分词结果进行匹配，因此相比于term的精确搜索，match是分词匹配搜索
        // 如果用 match 下指定了一个确切值，在遇到数字，日期，布尔值或者 not_analyzed 的字符串时，它将为你搜索你给定的值
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("name", "2018年最新女鞋");
        Pageable pageable = PageRequest.of(0, 100);
        Iterable<Goods> goods = goodsMapper.search(matchQueryBuilder, pageable);
        Iterator<Goods> goodsIterator = goods.iterator();
        int count = 0;
        while (goodsIterator.hasNext()) {
            Goods goods1 = goodsIterator.next();
            System.out.println(goods1);
            count++;
        }
        System.out.println(count);
    }

    // bool查询 和 filter 查询
    @Test
    void boolGoods() {
        // bool 查询可以用来合并多个条件查询结果的布尔逻辑，它包含一下操作符：
        // must :: 多个查询条件的完全匹配,相当于 and 。
        // must_not :: 多个查询条件的相反匹配，相当于 not 。
        // should :: 至少有一个查询条件匹配, 相当于 or 。
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        // 搜索2018年价格在1000-2000之内的女鞋 颜色不能是白色的 只能是黑色或者是红色
        RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("price");
        rangeQueryBuilder.lte(2000);
        rangeQueryBuilder.gte(1000);
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("name", "2018女鞋");

        MatchQueryBuilder matchQueryBuilder2 = new MatchQueryBuilder("spec", "蓝色");
        boolQueryBuilder.must(rangeQueryBuilder);
        boolQueryBuilder.must(matchQueryBuilder);
        boolQueryBuilder.mustNot(matchQueryBuilder2);

        MatchQueryBuilder matchQueryBuilder3 = new MatchQueryBuilder("spec", "黑色 红色");
        boolQueryBuilder.must(matchQueryBuilder3);

        TermQueryBuilder termsQueryBuilder = new TermQueryBuilder("num", 10000);
        boolQueryBuilder.filter(termsQueryBuilder);

        // Pageable pageable=PageRequest.of(0,100);
        Iterable<Goods> goods = goodsMapper.search(boolQueryBuilder);
        Iterator<Goods> goodsIterator = goods.iterator();
        int count = 0;
        while (goodsIterator.hasNext()) {
            Goods goods1 = goodsIterator.next();
            System.out.println(goods1);
            count++;
        }
        System.out.println(count);
    }

    // 自定义方法
    @Test
    void testCustom() {
        // List<Goods> goodsList = goodsMapper.findByName("2018新款");
        // for (Goods goods : goodsList) {
        // System.out.println(goods);
        // }
        // 自定义的方法是分词后 and的关系 必须全部满足
        List<Goods> goodsList2 = goodsMapper.findByName("2018新款李白", PageRequest.of(1, 10));
        for (Goods goods : goodsList2) {
            System.out.println(goods);
        }
    }
}
