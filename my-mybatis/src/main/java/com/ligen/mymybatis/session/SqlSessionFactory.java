package com.ligen.mymybatis.session;

import com.ligen.mymybatis.config.Configuration;
import com.ligen.mymybatis.config.MapperStatement;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;

/**
 * 1.初始化的时候加载配置信息
 * 2.生成sessionfactory
 */
public class SqlSessionFactory {

    private final Configuration configuration = new Configuration();

    private static final String MAPPER_CONFIG_LOCATION = "mappers";
    private static final String DB_CONFIG_LOCATION = "db.properties";
    private static final String CONFIG_LOCATION = "mybatis-config.xml";

    public SqlSessionFactory() {
        loadConfig();
        loadDb();
        loadMappers();
    }

    private void loadConfig() {

        URL resource = SqlSessionFactory.class.getClassLoader().getResource(CONFIG_LOCATION);
        File file = new File(resource.getFile());
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();

//        configuration.setJdbcDriver(p.getProperty("jdbc.driver"));
//        configuration.setJdbcUrl(p.getProperty("jdbc.url"));
//        configuration.setJdbcUsername(p.getProperty("jdbc.username"));
//        configuration.setJdbcPassword(p.getProperty("jdbc.password"));

        Element mappers = root.element("mappers");
        List<Element> mapper = mappers.elements("mapper");
        for (Element m : mapper) {
            Attribute mapperSource = m.attribute("resource");
            URL url = SqlSessionFactory.class.getClassLoader().getResource(mapperSource.getValue());
            File f = new File(url.getFile());
                loadMapper(f);
        }
    }

    private void loadDb() {
        InputStream dbin = SqlSessionFactory.class.getClassLoader().getResourceAsStream(DB_CONFIG_LOCATION);
        Properties p = new Properties();
        try {
            p.load(dbin);
        } catch (IOException e) {
            e.printStackTrace();
        }
        configuration.setJdbcDriver(p.getProperty("jdbc.driver"));
        configuration.setJdbcUrl(p.getProperty("jdbc.url"));
        configuration.setJdbcUsername(p.getProperty("jdbc.username"));
        configuration.setJdbcPassword(p.getProperty("jdbc.password"));

    }
    private void loadMappers() {
        URL resource = null;
        resource = SqlSessionFactory.class.getClassLoader().getResource(MAPPER_CONFIG_LOCATION);
        File mappers = new File(resource.getFile());
        if (mappers.isDirectory()) {
            File[] files = mappers.listFiles();
            for (File f : files) {
                loadMapper(f);
            }
        }
    }

    private void loadMapper(File f) {
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(f);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        //获得根节点元素对象
        Element root = document.getRootElement();
        String namespace = root.attribute("namespace").getData().toString();
        //获取select子节点
        List<Element> selects = root.elements("select");
        for (Element element : selects) {
            MapperStatement ms = new MapperStatement();
            String id = element.attribute("id").getData().toString();
            String sourceId = namespace + "." + id;
            ms.setNamespace(namespace);
            ms.setId(id);
            ms.setResultType(element.attribute("resultType").getData().toString());
            ms.setSql(element.getData().toString());
            configuration.getStatementMap().put(sourceId, ms);
        }
    }

    public SqlSession openSession() {
            return new DefaultSqlSession(configuration);
    }

}
