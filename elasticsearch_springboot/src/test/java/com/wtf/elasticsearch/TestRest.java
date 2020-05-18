package com.wtf.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class TestRest {

    @Test
    void testRestInsert() throws IOException {
        //1.连接rest接口
        HttpHost http = new HttpHost("172.16.107.150", 9200, "http");
        RestClientBuilder restClientBuilder = RestClient.builder(http);//rest构建器
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(restClientBuilder);//获取高级客户端对象

        //2.封装请求对象
        //BulkRequest bulkRequest = new BulkRequest(); //用于批量操作
        IndexRequest indexRequest = new IndexRequest("test_rest", "_doc", "1");
        HashMap skuMap = new HashMap();
        skuMap.put("name","法拉利 LaFerrari Aperta");
        skuMap.put("brandName","法拉利");
        skuMap.put("categoryName","超级跑车");
        HashMap spec = new HashMap();
        spec.put("动力","963匹");
        spec.put("扭矩","880N/m");
        spec.put("车长","4975mm");
        spec.put("重量","1250kg");
        skuMap.put("spec",spec);
        skuMap.put("createTime","2017-08-10");
        skuMap.put("price",43000000);
        skuMap.put("saleNum",209);
        skuMap.put("commentNum",6128746);
        indexRequest.source(skuMap);
        //bulkRequest.add(indexRequest); //用于批量操作
        //3.获取响应结果
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        //BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT); //用于批量操作
        int status = indexResponse.status().getStatus();
        System.out.println(status);
        restHighLevelClient.close();
    }

    @Test
    void testRestQueryMatch() throws IOException {
        //1.连接rest接口
        HttpHost http = new HttpHost("172.16.107.150", 9200, "http");
        RestClientBuilder restClientBuilder = RestClient.builder(http);//rest构建器
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(restClientBuilder);//获取高级客户端对象

        //2.封装查询请求
        SearchRequest searchRequest = new SearchRequest("test_rest");
        searchRequest.types("_doc"); //设置查询的类型
        //创建查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); //查询源构建器
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("categoryName", "超级跑车");
        searchSourceBuilder.query(matchQueryBuilder);
        searchRequest.source(searchSourceBuilder);

        //3.获取查询结果
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits searchHits = response.getHits();
        long totalHits = searchHits.getTotalHits();
        System.out.println("记录数:"+totalHits);
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
        restHighLevelClient.close();
    }

    /**
     * BoolQueryBuilder
     */
    @Test
    void testRestQueryBool() throws IOException {
        //1.连接rest接口
        HttpHost http = new HttpHost("172.16.107.150", 9200, "http");
        RestClientBuilder restClientBuilder = RestClient.builder(http);//rest构建器
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(restClientBuilder);//获取高级客户端对象

        //2.封装查询请求
        SearchRequest searchRequest = new SearchRequest("test_rest");
        searchRequest.types("_doc"); //设置查询的类型
        //创建查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); //查询源构建器
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery(); //布尔查询构建器
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", "LaFerrari");
        boolQueryBuilder.must(matchQueryBuilder);
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("brandName", "法拉利");
        boolQueryBuilder.must(termQueryBuilder);
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);

        //3.获取查询结果
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits searchHits = response.getHits();
        long totalHits = searchHits.getTotalHits();
        System.out.println("记录数:" + totalHits);
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
        restHighLevelClient.close();
    }


    @Test
    void testRestQueryfilter() throws IOException {
        //1.连接rest接口
        HttpHost http = new HttpHost("172.16.107.150", 9200, "http");
        RestClientBuilder restClientBuilder = RestClient.builder(http);//rest构建器
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(restClientBuilder);//获取高级客户端对象

        //2.封装查询请求
        SearchRequest searchRequest = new SearchRequest("test_rest");
        searchRequest.types("_doc"); //设置查询的类型
        //创建查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); //查询源构建器
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery(); //布尔查询构建器
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("brandName", "法拉利");
        boolQueryBuilder.filter(termQueryBuilder);
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);

        //3.获取查询结果
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits searchHits = response.getHits();
        long totalHits = searchHits.getTotalHits();
        System.out.println("记录数:" + totalHits);
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
        restHighLevelClient.close();
    }

    @Test
    void testRestQueryAggregation() throws IOException {
        //连接rest接口
        HttpHost http = new HttpHost("1172.16.107.150", 9200, "http");
        RestClientBuilder restClientBuilder = RestClient.builder(http);//rest构建器
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(restClientBuilder);//获取高级客户端对象
        //封装查询请求
        SearchRequest searchRequest = new SearchRequest("test_rest");
        searchRequest.types("_doc"); //设置查询的类型
        //创建查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); //查询源构建器
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("类型名称").field("categoryName");
        searchSourceBuilder.aggregation(aggregation);
        searchSourceBuilder.size(0);
        searchRequest.source(searchSourceBuilder);
        //获取查询结果
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        Aggregations aggregations = response.getAggregations();
        Map<String, Aggregation> map = aggregations.getAsMap();
        Terms terms = (Terms) map.get("类型名称");
        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            System.out.println(bucket.getKeyAsString()+":"+bucket.getDocCount());
        }
        restHighLevelClient.close();


        //高亮设置
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        highlightBuilder.field("name").preTags("<font style='color:red'>").postTags("</font>");
//        searchSourceBuilder.highlighter(highlightBuilder);
    }
}
