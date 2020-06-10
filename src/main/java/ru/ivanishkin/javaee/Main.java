package ru.ivanishkin.javaee;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ivanishkin.javaee.task2.DatabaseContext;
import ru.ivanishkin.javaee.task2.dao.NodeDAO;
import ru.ivanishkin.javaee.task2.dao.TagDAO;
import ru.ivanishkin.javaee.task2.model.Node;
import ru.ivanishkin.javaee.task2.model.Osm;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.sql.SQLException;
import java.util.List;


public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class.getName());
    private static final int INSERT_AMOUNT = 15000;

    public static void main(String[] args) throws JAXBException {
        logger.info("Hello, World!");
        if (args[0].equals("1")) {
            new ru.ivanishkin.javaee.task1.OSMStats(args[1]).calculateAndPrint();
        } else if (args[0].equals("2.1")) {
            new ru.ivanishkin.javaee.task2.OSMStats(args[1]).calculateAndPrint();
        } else if (args[0].equals("2.2")) {
            dbTask(args[1]);
        } else {
            throw new IllegalArgumentException("First arg must be 1 or 2.1, or 2.2, or 3");
        }
    }

    /*
      Sql insert string: 0m:59s, time in ms - 59561. Insert speed 839.4754957102803 r/s
      Prepared statement: 0m:41s, time in ms - 41680. Insert speed 1199.6161228406909 r/s
      Batch: 0m:1s, time in ms - 1627. Insert speed 30731.407498463428 r/s
     */
    private static void dbTask(final String path) {
        try (DatabaseContext db = new DatabaseContext(
                "jdbc:postgresql://localhost:5432/main",
                "root",
                "12341234"
        )) {
            Osm osm = unmarshal(path);
            NodeDAO nodeDAO = new NodeDAO(db.getConnection());
            nodeDAO.deleteAll();
            TagDAO tagDAO = new TagDAO(db.getConnection());
            tagDAO.deleteAll();
            if (osm != null) {
                List<Node> sublist = osm.getNode().subList(0, INSERT_AMOUNT);
                tagDAO.insertSql(sublist.get(0).getTag());
                printTime("Sql insert string: ",
                        measureTime(() -> logger.info("SQL " + nodeDAO.insertSql(sublist)))
                );
                nodeDAO.deleteAll();
                printTime("Prepared statement: ",
                        measureTime(() -> nodeDAO.insertPreparedStatement(sublist))
                );
                nodeDAO.deleteAll();
                printTime("Batch: ",
                        measureTime(() -> nodeDAO.insertBatch(sublist))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Osm unmarshal(final String path) {
        try {
            File file = new File(path);
            JAXBContext jaxbContext = JAXBContext.newInstance(Osm.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (Osm) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long measureTime(Runnable r) {
        long timeStart = System.currentTimeMillis();
        r.run();
        return System.currentTimeMillis() - timeStart;
    }

    private static void printTime(String label, long time) {
        logger.info(label + String.format(
                "%dm:%ds, time in ms - %d. Insert speed " + INSERT_AMOUNT / (time / 1000.0) + " r/s",
                time / 1000 / 60, time / 1000 % 60, time)
        );
    }
}

// Sql insert string: 3m:15s, time in ms - 195346. Insert speed 153.57365904600042 r/s
// Prepared statement: 3m:4s, time in ms - 184651. Insert speed 162.4686570882367 r/s
// Batch: 0m:1s, time in ms - 1835. Insert speed 16348.773841961853 r/s

