package ru.ivanishkin.javaee.task2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.ivanishkin.javaee.StaxStreamReaderWrapper;
import ru.ivanishkin.javaee.task1.model.UidMarks;
import ru.ivanishkin.javaee.task1.model.UserChanges;
import ru.ivanishkin.javaee.task2.model.Node;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class OSMStats {
    private static final Logger logger = LogManager.getLogger(OSMStats.class.getName());
    private final Map<Integer, Long> uidMarks = new HashMap<>();
    private final Map<String, Long> userChanges = new HashMap<>();
    private long nodes = 0;
    private final JAXBContext jaxbContext = JAXBContext.newInstance(Node.class);
    private final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    private final String path;

    public OSMStats(String path) throws JAXBException {
        this.path = path;
    }

    public void calculateAndPrint() {
        try (StaxStreamReaderWrapper processor = new StaxStreamReaderWrapper(Files.newInputStream(Path.of(path)))) {
            XMLStreamReader reader = processor.getReader();
            while (reader.hasNext()) {
                int event = reader.next();
                if (event == XMLEvent.START_ELEMENT && "node".equals(reader.getLocalName())) {
                    nodes++;
                    Node node = (Node) unmarshaller.unmarshal(reader);
                    userChanges.compute(node.getUser(), (k, v) -> (v == null) ? 1 : 1 + v);
                    uidMarks.compute(node.getUid().intValue(), (k, v) -> (v == null) ? 1 : 1 + v);
                }
            }
        } catch (XMLStreamException | IOException | JAXBException e) {
            e.printStackTrace();
            logger.error("Error", e);

        }

        List<UserChanges> sortedUserChangesList = userChanges.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .map((entry) -> new UserChanges(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        List<UidMarks> sortedUidMarksList = uidMarks.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .map((entry) -> new UidMarks(entry.getKey().toString(), entry.getValue()))
                .collect(Collectors.toList());


        logger.info(format("Users with changes: %s", sortedUserChangesList.size()));
        logger.info(format("Nodes with user changes: %s", nodes));


        sortedUserChangesList.stream().limit(50).forEach(userChanges -> {
            logger.info((format("User %s -- changes %s", userChanges.getUser(), userChanges.getChanges())));
        });

        sortedUidMarksList.stream().limit(50).forEach(uidMarks -> {
            logger.info((format("Uid %s -- marks %s", uidMarks.getUid(), uidMarks.getMarks())));
        });
    }
}
