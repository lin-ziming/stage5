package com.pd.service;

import com.pd.pojo.Item;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.List;

public interface SearchService {
    List<Item> search(String key) throws IOException, SolrServerException;
}
