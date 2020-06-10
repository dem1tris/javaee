package ru.ivanishkin.javaee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;

import static java.lang.String.format;

public class StaxStreamReaderWrapper implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(StaxStreamReaderWrapper.class.getName());
    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();

    private final XMLStreamReader reader;

    public StaxStreamReaderWrapper(final InputStream is) throws XMLStreamException {
        reader = FACTORY.createXMLStreamReader(is, "UTF-8");
    }

    public XMLStreamReader getReader() {
        return reader;
    }

    @Override
    public void close() {
        if (reader != null) {
            try {
                reader.close();
            } catch (XMLStreamException e) {
                logger.error("Stax error", e);
            }
        }
    }
}
