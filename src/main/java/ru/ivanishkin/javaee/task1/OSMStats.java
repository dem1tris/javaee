package ru.ivanishkin.javaee.task1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.ivanishkin.javaee.StaxStreamReaderWrapper;
import ru.ivanishkin.javaee.task1.model.UidMarks;
import ru.ivanishkin.javaee.task1.model.UserChanges;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class OSMStats {
    private static final Logger logger = LogManager.getLogger(OSMStats.class.getName());
    private final Map<String, Long> uidMarks = new HashMap<>();
    private final Map<String, Long> userChanges = new HashMap<>();
    private long nodes = 0;
    private final String path;

    public OSMStats(String path) {
        this.path = path;
    }

    public void calculateAndPrint() {
        try (StaxStreamReaderWrapper processor = new StaxStreamReaderWrapper(Files.newInputStream(Path.of(path)))) {
            XMLStreamReader reader = processor.getReader();
            while (reader.hasNext()) {
                int event = reader.next();
                if (event == XMLEvent.START_ELEMENT && "node".equals(reader.getLocalName())) {
                    nodes++;
                    int userNameIndex = -1;
                    int uidIndex = -1;
                    for (int i = 0; i < reader.getAttributeCount(); i++) {
                        final String attributeName = reader.getAttributeLocalName(i);
                        if ("user".equals(attributeName)) {
                            userNameIndex = i;
                        }

                        if ("uid".equals(attributeName)) {
                            uidIndex = i;
                        }
                    }

                    if (userNameIndex == -1) {
                        logger.error("Property 'user' not found");
                    } else {
                        String user = reader.getAttributeValue(userNameIndex);
                        if (userChanges.containsKey(user)) {
                            long userChanges = this.userChanges.get(user);
                            this.userChanges.put(user, ++userChanges);
                        } else {
                            userChanges.put(user, 0L);
                        }
                    }

                    if (uidIndex == -1) {
                        logger.error("Property 'uid' not found");
                    } else {
                        String uid = reader.getAttributeValue(uidIndex);
                        if (uidMarks.containsKey(uid)) {
                            long marks = uidMarks.get(uid);
                            uidMarks.put(uid, ++marks);
                        } else {
                            uidMarks.put(uid, 0L);
                        }
                    }
                }
            }
        } catch (XMLStreamException | IOException e) {
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
                .map((entry) -> new UidMarks(entry.getKey(), entry.getValue()))
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
