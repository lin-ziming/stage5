package com.pd.service.impl;

import com.pd.pojo.Item;
import com.pd.service.SearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@Service
public class SearchServiceImpl implements SearchService {
    // SolrAutoConfiguration 自动配置类中创建的工具对象
    @Autowired
    private SolrClient solrClient;

    @Override
    public List<Item> search(String key) throws IOException, SolrServerException {
        SolrQuery query = new SolrQuery(key);
        query.setStart(0);
        query.setRows(20);

        QueryResponse response = solrClient.query(query);
        List<Item> list = response.getBeans(Item.class);
        return list;
    }
}
