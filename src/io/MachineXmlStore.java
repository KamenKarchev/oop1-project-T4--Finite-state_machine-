package io;

import exceptions.graph.GraphException;
import exceptions.machine.InvalidAlphabetException;
import exceptions.machine.InvalidStateException;
import exceptions.machine.InvalidSymbolException;
import graph.mdg.MDGNode;
import machine.MachineRegistry;
import machine.MultiedgeFinateLogicMachine;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * XML serializer/deserializer for machine registries.
 *
 * <p><b>Format</b> (пример):</p>
 *
 * <pre>{@code
 * <machines>
 *   <m id="1" reg="(a+b).c">
 *     <i><node name="q1"/></i>
 *     <a><node name="q2"/></a>
 *     <g>
 *       <node name="q1">
 *         <edge from="q1" to="q2">a</edge>
 *         <edge from="q1" to="q2"></edge> <!-- eps -->
 *       </node>
 *       <node name="q2"/>
 *     </g>
 *   </m>
 * </machines>
 * }</pre>
 *
 * <p>
 * Бележка: ε-преход се записва като празно съдържание на {@code <edge>} елемента.
 * </p>
 */
public final class MachineXmlStore {

    /**
     * Зарежда XML файл и връща нов {@link MachineRegistry} със същите ID-та и машини.
     *
     * @param file път към XML файла
     * @return нов регистър, попълнен от файла
     * @throws IOException при I/O проблем или невалиден XML формат
     */
    public MachineRegistry load(Path file) throws IOException {
        try (InputStream in = Files.newInputStream(file)) {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            f.setNamespaceAware(false);
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse(in);
            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();
            if (root == null || !root.getTagName().equals("machines")) {
                throw new IOException("Invalid XML: expected <machines> root");
            }

            MachineRegistry reg = new MachineRegistry();

            NodeList machines = root.getElementsByTagName("m");
            for (int i = 0; i < machines.getLength(); i++) {
                Element mEl = (Element) machines.item(i);
                int id = Integer.parseInt(mEl.getAttribute("id"));
                String rx = mEl.hasAttribute("reg") ? mEl.getAttribute("reg") : null;

                MultiedgeFinateLogicMachine m = loadMachine(mEl, rx);
                reg.put(id, m);
            }

            reg.resetNextIdAfterLoad();
            return reg;
        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException("Failed to parse XML: " + e.getMessage(), e);
        }
    }

    /**
     * Записва целия регистър от машини в XML файл.
     *
     * @param file път към изходния файл
     * @param registry регистър за запис
     * @throws IOException при I/O проблем или грешка при сериализация
     */
    public void save(Path file, MachineRegistry registry) throws IOException {
        try (OutputStream out = Files.newOutputStream(file)) {
            Document doc = newDocument();
            Element root = doc.createElement("machines");
            doc.appendChild(root);

            for (Integer id : registry.ids()) {
                root.appendChild(writeMachine(doc, id, registry.get(id)));
            }

            writeDocument(doc, out);
        }
    }

    /**
     * Записва една машина (с дадено ID) като XML файл.
     *
     * <p>
     * Файлът съдържа root {@code <machines>} с точно един {@code <m>} елемент.
     * </p>
     *
     * @param file път към изходния файл
     * @param id ID на машината (записва се като атрибут {@code id})
     * @param machine машината за запис
     * @throws IOException при I/O проблем или грешка при сериализация
     */
    public void saveSingle(Path file, int id, MultiedgeFinateLogicMachine machine) throws IOException {
        try (OutputStream out = Files.newOutputStream(file)) {
            Document doc = newDocument();
            Element root = doc.createElement("machines");
            doc.appendChild(root);
            root.appendChild(writeMachine(doc, id, machine));
            writeDocument(doc, out);
        }
    }

    private static Document newDocument() throws IOException {
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            return b.newDocument();
        } catch (ParserConfigurationException e) {
            throw new IOException("Failed to create XML document: " + e.getMessage(), e);
        }
    }

    private static void writeDocument(Document doc, OutputStream out) throws IOException {
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            t.transform(new DOMSource(doc), new StreamResult(out));
        } catch (TransformerException e) {
            throw new IOException("Failed to write XML: " + e.getMessage(), e);
        }
    }

    private static Element writeMachine(Document doc, int id, MultiedgeFinateLogicMachine m) throws IOException {
        Element mEl = doc.createElement("m");
        mEl.setAttribute("id", Integer.toString(id));
        if (m.getOriginalRegex() != null && !m.getOriginalRegex().isBlank()) {
            mEl.setAttribute("reg", m.getOriginalRegex());
        }

        try {
            Element iEl = doc.createElement("i");
            for (MDGNode s : m.getStartStates()) {
                Element n = doc.createElement("node");
                n.setAttribute("name", s.getName());
                iEl.appendChild(n);
            }
            mEl.appendChild(iEl);

            Element aEl = doc.createElement("a");
            for (MDGNode s : m.getAcceptStates()) {
                Element n = doc.createElement("node");
                n.setAttribute("name", s.getName());
                aEl.appendChild(n);
            }
            mEl.appendChild(aEl);

            Element gEl = doc.createElement("g");
            // nodes (each node contains its outgoing edges)
            for (MDGNode node : m.getStates()) {
                Element n = doc.createElement("node");
                n.setAttribute("name", node.getName());
                for (graph.mdg.MDGEdge e : node.getEdges()) {
                    Element eEl = doc.createElement("edge");
                    eEl.setAttribute("from", node.getName());
                    eEl.setAttribute("to", e.getTo().getName());
                    if (e.getLabel() != null) {
                        eEl.setTextContent(e.getLabel().toString());
                    }
                    n.appendChild(eEl);
                }
                gEl.appendChild(n);
            }
            mEl.appendChild(gEl);
        } catch (InvalidStateException ex) {
            throw new IOException("Failed to serialize machine: " + ex.getMessage(), ex);
        }
        return mEl;
    }

    private static MultiedgeFinateLogicMachine loadMachine(Element mEl, String rx)
            throws IOException {
        try {
            Element gEl = firstChild(mEl, "g");
            if (gEl == null) {
                throw new IOException("Invalid XML: machine missing <g>");
            }

            // read nodes
            Map<String, MDGNode> nodes = new HashMap<>();
            NodeList nodeEls = gEl.getElementsByTagName("node");
            for (int i = 0; i < nodeEls.getLength(); i++) {
                Element nEl = (Element) nodeEls.item(i);
                String name = nEl.getAttribute("name");
                nodes.put(name, new MDGNode(name));
            }

            // compute alphabet from regex literals + edge labels
            Set<Character> sigma = new HashSet<>();
            if (rx != null && !rx.isBlank()) {
                sigma.addAll(deriveAlphabetFromRegex(rx));
            }

            NodeList edgeEls = gEl.getElementsByTagName("edge");
            for (int i = 0; i < edgeEls.getLength(); i++) {
                Element eEl = (Element) edgeEls.item(i);
                String labelText = eEl.getTextContent();
                Character label = parseLabelOrNull(labelText);
                if (label != null) {
                    sigma.add(label);
                }
            }

            MultiedgeFinateLogicMachine m = new MultiedgeFinateLogicMachine(sigma);
            if (rx != null && !rx.isBlank()) {
                m.setOriginalRegex(rx);
            }

            // add nodes
            for (MDGNode n : nodes.values()) {
                m.addState(n);
            }

            // add start/accept sets
            Element iEl = firstChild(mEl, "i");
            if (iEl != null) {
                NodeList starts = iEl.getElementsByTagName("node");
                for (int i = 0; i < starts.getLength(); i++) {
                    Element nEl = (Element) starts.item(i);
                    String name = nEl.getAttribute("name");
                    m.addStartState(nodes.get(name));
                }
            }

            Element aEl = firstChild(mEl, "a");
            if (aEl != null) {
                NodeList accepts = aEl.getElementsByTagName("node");
                for (int i = 0; i < accepts.getLength(); i++) {
                    Element nEl = (Element) accepts.item(i);
                    String name = nEl.getAttribute("name");
                    m.addAcceptState(nodes.get(name));
                }
            }

            // add edges
            for (int i = 0; i < edgeEls.getLength(); i++) {
                Element eEl = (Element) edgeEls.item(i);
                String fromName = eEl.getAttribute("from");
                String toName = eEl.getAttribute("to");
                Character label = parseLabelOrNull(eEl.getTextContent());
                MDGNode from = nodes.get(fromName);
                MDGNode to = nodes.get(toName);
                if (from == null || to == null) {
                    throw new IOException("Invalid XML: edge references missing node: " + fromName + " -> " + toName);
                }
                m.addTransition(from, to, label);
            }

            return m;
        } catch (GraphException | InvalidStateException | InvalidAlphabetException | InvalidSymbolException e) {
            throw new IOException("Failed to load machine: " + e.getMessage(), e);
        }
    }

    private static Element firstChild(Element parent, String tag) {
        NodeList nl = parent.getElementsByTagName(tag);
        if (nl.getLength() == 0) return null;
        return (Element) nl.item(0);
    }

    private static List<Element> directChildrenByTag(Element parent, String tagName) {
        List<Element> out = new ArrayList<>();
        org.w3c.dom.Node child = parent.getFirstChild();
        while (child != null) {
            if (child.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element el = (Element) child;
                if (tagName.equals(el.getTagName())) {
                    out.add(el);
                }
            }
            child = child.getNextSibling();
        }
        return out;
    }

    private static Character parseLabelOrNull(String text) {
        if (text == null) return null;
        String t = text.trim();
        if (t.isEmpty()) return null; // eps
        if (t.length() >= 2 && t.startsWith("\"") && t.endsWith("\"")) {
            t = t.substring(1, t.length() - 1);
        }
        if (t.isEmpty()) return null;
        return t.charAt(0);
    }

    private static Set<Character> deriveAlphabetFromRegex(String regex) {
        Set<Character> out = new HashSet<>();
        for (int i = 0; i < regex.length(); i++) {
            char c = regex.charAt(i);
            if (c == '+' || c == '.' || c == '*' || c == '(' || c == ')' || Character.isWhitespace(c)) {
                continue;
            }
            if ((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')) {
                out.add(c);
            }
        }
        return out;
    }
}

