package com.pd.controller;

import com.pd.pojo.Item;
import com.pd.service.SearchService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping("/search/toSearch.html")
    public String search(Model model, String key) throws IOException, SolrServerException {
        List<Item> list = searchService.search(key);
        model.addAttribute("list",list);
        return "/search.jsp";
    }
}
