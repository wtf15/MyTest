package com.wtf.elasticsearch;

import com.alibaba.fastjson.JSONObject;
import com.wtf.elasticsearch.bean.ChildItem;
import com.wtf.elasticsearch.bean.Item;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.InternalNumericMetricsAggregation;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.stats.extended.ExtendedStatsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHitsAggregationBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.ScrolledPage;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

@SpringBootTest
public class TestElasticsearchTemplate {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    void createIndex(){
        //创建索引及映射
        elasticsearchTemplate.createIndex(Item.class);
        //创建映射
//        elasticsearchTemplate.putMapping(Item.class);
    }


    @Test
    void putMegacorp() {
        List<String> list=new ArrayList<>();
        list.add("sports");
        list.add("music");
        ChildItem childItem=new ChildItem(100,"taibai");
        Item item1=new Item(6L,"John","男",25,list,childItem);
        Item item2=new Item(7L,"Jane","Smith",32, Arrays.asList("music"),childItem);
        Item item3=new Item(8L,"Douglas","Fir",35,Arrays.asList("forestry"),childItem);
        IndexQuery indexQuery1 = new IndexQueryBuilder().withId(String.valueOf(item1.getLd())).withObject(item1).build();
        IndexQuery indexQuery2 = new IndexQueryBuilder().withId(String.valueOf(item2.getLd())).withObject(item2).build();
        IndexQuery indexQuery3 = new IndexQueryBuilder().withId(String.valueOf(item3.getLd())).withObject(item3).build();
        elasticsearchTemplate.bulkIndex(Arrays.asList(indexQuery1,indexQuery2,indexQuery3));
    }

    @Test
    void testPage() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withIndices("test_springdata_es")
                .withPageable(PageRequest.of(0, 10))
                .build();

        ScrolledPage<Item> scroll = elasticsearchTemplate.startScroll(1000, searchQuery, Item.class);
        String scrollId = scroll.getScrollId();
        List<Item> content = scroll.getContent();
        content.forEach(item->{
            System.out.println(item);
        });
        System.out.println(scrollId);
        elasticsearchTemplate.clearScroll(scrollId);
    }


    //平均最大最小求和聚合
    @Test
    void testAggregation(){
        AvgAggregationBuilder avgAggregationBuilder = AggregationBuilders.avg("taibai").field("price");
//        MaxAggregationBuilder maxAggregationBuilder = AggregationBuilders.max("taibai").field("price");
        SearchQuery searchQuery = new NativeSearchQueryBuilder().addAggregation(avgAggregationBuilder)
                .withIndices("goods")
                .withTypes("_doc")
                .build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {

            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });
        Aggregation aggregation1 = aggregations.asList().get(0);
        InternalNumericMetricsAggregation.SingleValue singleValue= (InternalNumericMetricsAggregation.SingleValue) aggregation1;
        double value = singleValue.value();
        System.out.println(value);
    }

    //去重
    @Test
    void testAggregationCardinality(){
        CardinalityAggregationBuilder aggregationBuilder = AggregationBuilders.cardinality("taibai").field("price");
        SearchQuery searchQuery = new NativeSearchQueryBuilder().addAggregation(aggregationBuilder)
                .withIndices("goods")
                .withTypes("_doc")
                .build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {

            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });
        Aggregation aggregation1 = aggregations.getAsMap().get("taibai");
        InternalNumericMetricsAggregation.SingleValue singleValue= (InternalNumericMetricsAggregation.SingleValue) aggregation1;
        double value = singleValue.value();
        System.out.println(value);
    }


    //扩展查询
    @Test
    void testAggregationextended_stats(){
        ExtendedStatsAggregationBuilder extendedStatsAggregationBuilder = AggregationBuilders.extendedStats("taibai").field("price");
        SearchQuery searchQuery = new NativeSearchQueryBuilder().addAggregation(extendedStatsAggregationBuilder)
                .withIndices("goods")
                .withTypes("_doc")
                .build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {

            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });
        Aggregation aggregation1 = aggregations.getAsMap().get("taibai");
        InternalNumericMetricsAggregation.MultiValue multiValue= (InternalNumericMetricsAggregation.MultiValue ) aggregation1;
        System.out.println(multiValue.value("max"));
    }


    //terms词聚合
    @Test
    void testAggregationTerms(){
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("taibai").field("price");
        SearchQuery searchQuery = new NativeSearchQueryBuilder().addAggregation(termsAggregationBuilder)
                .withIndices("goods")
                .withTypes("_doc")
                .build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {

            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });
        Aggregation aggregation1 = aggregations.getAsMap().get("taibai");
        Terms term1 = (Terms)aggregation1;
        List<? extends Terms.Bucket> buckets = term1.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            System.out.println(bucket.getKey()+"||"+bucket.getDocCount());
        }
    }

    //top_hits最高匹配权值聚合
    @Test
    void testAggregationtop_hits(){
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("taibai").field("price");
        TopHitsAggregationBuilder topHitsAggregationBuilder = AggregationBuilders.topHits("top").size(3);
        termsAggregationBuilder.subAggregation(topHitsAggregationBuilder);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().addAggregation(termsAggregationBuilder)
                .withIndices("goods")
                .withTypes("_doc")
                .build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {

            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });
        Aggregation aggregation1 = aggregations.getAsMap().get("taibai");
        Terms term1 = (Terms)aggregation1;
        List<? extends Terms.Bucket> buckets = term1.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            System.out.println(bucket.getKey()+"||"+bucket.getDocCount());
            Aggregation aggregation = bucket.getAggregations().getAsMap().get("top");
            TopHits topHits= (TopHits) aggregation;
            Iterator<SearchHit> iterator = topHits.getHits().iterator();
            while (iterator.hasNext()){
                SearchHit next = iterator.next();
                Object object=JSONObject.parse(next.getSourceAsString());
                System.out.println(object);
            }
        }
    }


    @Test
    void testAggregationRange(){
        RangeAggregationBuilder rangeAggregationBuilder = AggregationBuilders.range("taibai").field("age")
                .addRange(20,30).addRange(30,40).addRange(40,50);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().addAggregation(rangeAggregationBuilder)
                .withIndices("bank")
                .withTypes("_doc")
                .build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {

            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });
        Aggregation aggregation1 = aggregations.getAsMap().get("taibai");
        Range range = (Range)aggregation1;
        List<? extends Range.Bucket> buckets = range.getBuckets();
        for (Range.Bucket bucket : buckets) {
            System.out.println(bucket.getKeyAsString()+"--"+bucket.getDocCount());
        }
    }


    @Test
    void testAggregationLast(){
        RangeAggregationBuilder rangeAggregationBuilder = AggregationBuilders.range("age_range").field("age")
                .addRange(20,30).addRange(30,40).addRange(40,50);
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("gender_group").field("gender.keyword");
        AvgAggregationBuilder aggregationBuilder = AggregationBuilders.avg("balance_avg").field("balance");
        termsAggregationBuilder.subAggregation(aggregationBuilder);
        rangeAggregationBuilder.subAggregation(termsAggregationBuilder);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().addAggregation(rangeAggregationBuilder)
                .withIndices("bank")
                .withTypes("_doc")
                .build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {

            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });
        Aggregation aggregation1 = aggregations.getAsMap().get("age_range");
        Range range = (Range)aggregation1;
        List<? extends Range.Bucket> buckets = range.getBuckets();
        for (Range.Bucket bucket : buckets) {
            System.out.println(bucket.getKeyAsString()+"--"+bucket.getDocCount());
            Aggregation gender_group = bucket.getAggregations().getAsMap().get("gender_group");
            Terms terms=(Terms)gender_group;
            List<? extends Terms.Bucket> buckets1 = terms.getBuckets();
            for (Terms.Bucket bucket1 : buckets1) {
                System.out.println(bucket1.getKeyAsString()+"--"+bucket1.getDocCount());
                Aggregation balance_avg = bucket1.getAggregations().getAsMap().get("balance_avg");
                Avg avg= (Avg) balance_avg;
                System.out.println(avg.getValue());
            }
        }
    }



}
