package com.baizhi.util;

import com.baizhi.entity.Poet;
import com.baizhi.entity.Poetry;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;

public class SearchIndexUtil {

    public static Map<String, Object> queryTss(int nowPage, int pageSize,String userImport,String field) throws IOException, ParseException, InvalidTokenOffsetsException {
        Map<String, Object> map = new HashMap<String,Object>();
        //索引路径
        FSDirectory fsDirectory = FSDirectory.open(Paths.get("E:\\index\\tss"));
        IndexReader indexRead = DirectoryReader.open(fsDirectory);
        IndexSearcher indexSearcher = new IndexSearcher(indexRead);
        //查询解析器对象 作用解析查询表达式
        //查询的范围
        String[] fields;
        if("all".equals(field)){
            fields = new String[]{"content", "title", "author"};
        }else{
            fields = new String[]{field};
        }
        Query query = new MultiFieldQueryParser(fields,new IKAnalyzer()).parse(userImport);
        //分页设置
        TopDocs topDocs = null;
        if (nowPage <= 1) {
            //第一页
            topDocs = indexSearcher.search(query, pageSize);
        } else if (nowPage > 1) {
            //不是第一页必须获得上一页的最后一条记录的ScoreDoc
            topDocs = indexSearcher.search(query, (nowPage - 1) * pageSize);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            ScoreDoc scoreDoc = scoreDocs[scoreDocs.length - 1];
            //使用 将上一页的最后一个ScoreDoc对象放入
            topDocs = indexSearcher.searchAfter(scoreDoc, query, pageSize);
        }

        //创建高亮器对象
        QueryScorer scorer = new QueryScorer(query);
        //自定义的高亮样式
        Formatter formatter = new SimpleHTMLFormatter("<span style=\"color:red\">", "</span>");
        Highlighter highlighter = new Highlighter(formatter, scorer);

        //符合条件的查询结果
        int totalHits = topDocs.totalHits;
        //取出文档的数组
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        ArrayList<Poetry> poetries = new ArrayList<>();
        //遍历
        for (ScoreDoc scoreDoc : scoreDocs) {
            //文档的id
            int docID = scoreDoc.doc;
            Document document = indexRead.document(docID);
            //创建诗歌对象
            Poetry poetry = new Poetry();
            poetry.setId(document.get("id"));
            //获得高亮的最佳片段
            String title = highlighter.getBestFragment(new IKAnalyzer(), "*", document.get("title"));
            if(title==null){
                //没有符合的高亮将原内容填充
                poetry.setTitle(document.get("title"));
            }else{
                //符合则填充高亮后的内容
                poetry.setTitle(title);
            }

            //获得高亮的最佳片段
            String content = highlighter.getBestFragment(new IKAnalyzer(), "*", document.get("content"));
           if(content==null){
               poetry.setContent(document.get("content"));
           }else{
               poetry.setContent(content);
           }
            //获得高亮的最佳片段
            String author = highlighter.getBestFragment(new IKAnalyzer(), "*", document.get("author"));
            //创建诗人对象
            Poet poet = new Poet();
            if(author==null){
                poet.setName(document.get("author"));
            }else{
                poet.setName(author);
            }
            poetry.setPoet(poet);
            poetries.add(poetry);

        }
        map.put("total",totalHits);
        map.put("rows",poetries);
        return map;
    }
}
