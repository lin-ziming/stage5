package test;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class Test1 {
    String[] a = {
            "3, 华为 - 华为电脑, 爆款",
            "4, 华为手机, 旗舰",
            "5, 联想 - Thinkpad, 商务本",
            "6, 联想手机, 自拍神器"
    };

    @Test
    public void test1() throws IOException {
        // lucene api是底层开发工具，代码非常繁琐
        // 存放索引数据的文件夹
        FSDirectory d = FSDirectory.open(new File("C:\\java\\lucene").toPath());

        // 配置中文分词器
        IndexWriterConfig conf = new IndexWriterConfig(new SmartChineseAnalyzer());

        // 索引输出工具
        IndexWriter writer = new IndexWriter(d, conf);

        // 遍历文档，输出索引
        for (String s : a){
            //"3, 华为 - 华为电脑, 爆款"
            String[] arr = s.split(",");
            //用 Document 封装商品的数据
            //分字段进行封装
            Document doc = new Document();
            doc.add(new LongPoint("id",Long.parseLong(arr[0])));
            doc.add(new StoredField("id",Long.parseLong(arr[0])));
            doc.add(new TextField("title",arr[1], Field.Store.YES));
            doc.add(new TextField("sellPoint",arr[2],Field.Store.YES));
            writer.addDocument(doc);
        }

        writer.flush();
        writer.close();
    }

}
