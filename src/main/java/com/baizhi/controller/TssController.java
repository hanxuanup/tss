package com.baizhi.controller;

import com.baizhi.util.SearchIndexUtil;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/tss")
public class TssController {
    @RequestMapping("/query")
    @ResponseBody
    public  Map<String, Object> queryTss(Integer page, Integer rows, String userImport,String field)  {
        Map<String, Object> map = null;
        try {
            map =  SearchIndexUtil.queryTss(page, rows,userImport,field);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InvalidTokenOffsetsException e) {
            e.printStackTrace();
        }
        return map;
    }
}
