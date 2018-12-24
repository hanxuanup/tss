package com.baizhi;

import com.baizhi.entity.Poetry;
import com.baizhi.service.PoertyService;
import com.baizhi.util.SearchIndexUtil;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TssApplicationTests {
    @Autowired
    private PoertyService poertyService;
    @Test
    public void contextLoads() throws IOException {
        FSDirectory fsDirectory = FSDirectory.open(Paths.get("E:\\index\\tss"));
        IndexWriter indexWriter = new IndexWriter(fsDirectory, new IndexWriterConfig(new IKAnalyzer()));
        List<Poetry> poetries = poertyService.queryAll();
        Document document = null;
        for (Poetry poetry : poetries) {
            document = new Document();
            document.add(new StringField("id", poetry.getId(), Field.Store.YES));
            document.add(new StringField("author", poetry.getPoet().getName(), Field.Store.YES));
            document.add(new TextField("title", poetry.getTitle(), Field.Store.YES));
            document.add(new TextField("content",poetry.getContent(), Field.Store.YES));
            indexWriter.addDocument(document);
        }
        indexWriter.commit();
        indexWriter.close();
    }
    @Test
    public void test2() throws IOException, ParseException, InvalidTokenOffsetsException {
//
//        Map<Integer, List<Poetry>> map = SearchIndexUtil.queryTss(1, 5, "李白");
//        Set<Integer> integers = map.keySet();
//        for (Integer integer : integers) {
//            List<Poetry> poetries = map.get(integer);
//            for (Poetry poetry : poetries) {
//                System.out.println(poetry);
//                System.out.println(poetry.getPoet().getName());
//            }
//        }

    }
    @Test
    public void test3() {

    }
}

