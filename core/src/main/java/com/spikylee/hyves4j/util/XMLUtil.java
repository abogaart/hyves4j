/*
 * Copyright 2008 Arthur Bogaart <spikylee at gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.spikylee.hyves4j.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * @author Arthur Bogaart
 */
public class XMLUtil {

    private XMLUtil() {
    }
    
    /**
     * Return all child nodes of the node argument that are an instance of Element  
     * 
     * @param node  Parent node
     * @return All child nodes that are an instance of Element, or null if empty
     */
    public static Collection<Node> getChildElements(Node node) {
        NodeList nodes = node.getChildNodes();
        if (nodes.getLength() == 0) {
            return null;
        }
        List<Node> elements = new ArrayList<Node>(nodes.getLength());
        for (int i = 0; i < nodes.getLength(); i++) {
            Node childNode = nodes.item(i);
            if (childNode instanceof Element) {
                elements.add(childNode);
            }
        }
        return elements;
    }

    /**
     * Get the text value for the specified element.  If the element is null or empty return null
     *
     * @param element The Element
     * @return The value String or null
     */
    public static String getValue(Element element) {
        if (element != null) {
            Node dataNode = element.getFirstChild();
            if (dataNode != null) {
                return ((Text) dataNode).getData();
            }
        }
        return null;
    }
    
    /**
     * If the first childNode of the node argument is a text node, return its value, else return null
     * 
     * @param node The Node
     * @return 
     */
    public static String getValue(Node node) {
        if (node.getFirstChild() != null && node.getFirstChild().getNodeType() == Node.TEXT_NODE) {
            return node.getFirstChild().getNodeValue();
        }
        return null;
    }

    /**
     * Get the first child element with the given name.
     *
     * @param element The parent element
     * @param name The child element name
     * @return The child element or null
     */
    public static Element getChild(Element element, String name) {
        return (Element) element.getElementsByTagName(name).item(0);
    }
    
    /**
     * Lookup an element by nodename in collection nodes
     *
     * @param nodes List of possible candidates
     * @param name Node name to match 
     * @return The first element that matches the name or null 
     */
    public static Element getChild(Collection<Node> nodes, String name) {
        Iterator<Node> it = nodes.iterator();
        while (it.hasNext()) {
            Element element = (Element) it.next();
            if (element.getTagName().equals(name)) {
                return element;
            }
        }
        return null;
    }
    
    /**
     * Lookup an element by nodename in a NodeList
     * 
     * @param nodes List of possible candidates
     * @param name Node name to match 
     * @return The first element that matches the name or null 
     */
    public static Element getChild(NodeList nodes, String name) {
        for(int i=0; i<nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            if (element.getTagName().equals(name)) {
                return element;
            }
        }
        return null;
    }
    
    /**
     * Get the value of the fist child element with the given name.
     *
     * @param element The parent element
     * @param name The child element name
     * @return The child element value or null
     */
    public static String getChildValue(Element element, String name) {
        return getValue(getChild(element, name));
    }
    
    /**
     * Pretty print XML as a String. Uses the TextPrinter to do the markup.
     * @param node Node to be pretty printed
     * @return String containing pretty printed xml of node
     */
    public static String prettyPrintXML(Node node) {
    	return prettyPrintXML(node, new TextPrinter());
    }
    
    /**
     * Pretty print XML as a String by parsing it with XmlPrinter printer
     * @param node Node to be pretty printed
     * @param printer XmlPrinter to pretty print the Node
     * @return String containing pretty printed xml of node
     */
    public static String prettyPrintXML(Node node, XmlPrinter printer) {
        printXML(node, printer);
        return printer.toString();
    }
    
    /**
     * Recursively traverse the node and use the printer to register the 
     * start node, end node and attributes events.  
     * @param node
     * @param printer
     */
    public static void printXML(Node node, XmlPrinter printer) {
        printer.startElement(node);
        if(node.hasChildNodes()) {
            Collection<Node> childNodes = XMLUtil.getChildElements(node);
            for(Node childNode : childNodes) {
                printXML(childNode, printer);
            }
        }
        printer.endElement(node);
    }
    
    /**
     * The XmlPrinter provides an interface for simple pretty printing
     * of xml. It declares a startElement, endElement and setText 
     * @author adcb
     *
     */
	public abstract static class XmlPrinter { 
	    protected StringBuffer sb = new StringBuffer();
	    private int depth = -1;
	    
        public final void startElement(Node node) {
            depth++;
            String nodeName = node.getNodeName();
            printStartElementPrefix(nodeName, depth);
            printStartElementAttributes(node.getAttributes(), depth);
            printStartElementSuffix(nodeName, depth);
            
            String value = XMLUtil.getValue(node);
            if(value != null) {
                printText(value, depth);
            }
        }

        public final void endElement(Node node) {
            printEndElement(node.getNodeName(), depth);
            depth--;
        }

        public abstract void printStartElementAttributes(NamedNodeMap attributes, final int depth);

        public abstract void printStartElementPrefix(String nodeName, final int depth);

        public abstract void printStartElementSuffix(String nodeName, final int depth);

        public abstract void printEndElement(String nodeName, final int depth);

        public abstract void printText(String value, final int depth);
        
        @Override
        public String toString() {
            return sb.toString();
        }
	}
	
	public static class TextPrinter extends XmlPrinter {
	    
        private static final String TAB_SIZE = "  ";

        @Override
        public void printEndElement(String nodeName, final int depth) {
            sb.append('\n');
            sb.append(getPrefix(depth));
            sb.append("</");
            sb.append(nodeName);
            sb.append(">");
        }

        protected String getPrefix(final int depth) {
            String prefix = "";
            for(int i=0;i<depth;i++) {
                prefix += TAB_SIZE;
            }
            return prefix;
        }

        @Override
        public void printStartElementAttributes(NamedNodeMap attributes, final int depth) {
            if(attributes != null) {
                for(int i=0; i<attributes.getLength(); i++) {
                    Node node = attributes.item(i);
                    sb.append(' ').append(node.getNodeName()).append('=').append('"').append(node.getNodeValue()).append('"');
                }
            }
        }

        @Override
        public void printStartElementPrefix(String nodeName, final int depth) {
            sb.append("\n");
            sb.append(getPrefix(depth));
            sb.append('<');
            sb.append(nodeName);
        }

        @Override
        public void printStartElementSuffix(String nodeName, final int depth) {
            sb.append(">");
        }

        @Override
        public void printText(String value, final int depth) {
            sb.append('\n').append(getPrefix(depth + 1)).append(value);
        }
	}
	
}
