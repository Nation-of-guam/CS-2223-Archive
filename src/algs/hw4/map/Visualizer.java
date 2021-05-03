package algs.hw4.map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

import javax.swing.JPanel;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.SeparateChainingHashST;

/**
 * Given an Information object (containing a Graph and lat/long positional data), this
 * class visualizes the graph, drawing all nodes in blue and edges in think dark grey.
 * 
 * To make this class interesting, you can also:
 * 
 * (a) Hilight a node (or unhilight a node)
 * (b) Hilight an edge (or unhilight an edge).
 * 
 * If this is done within a real-time application, this can be used for animation.
 */
public class Visualizer extends JPanel {

	Information information;

	/** Min Lat/Long and Max Lat/Long values. */
	float minLat = Integer.MAX_VALUE;
	float maxLat = Integer.MIN_VALUE;
	float minLong = Integer.MAX_VALUE;
	float maxLong = Integer.MIN_VALUE;

	// hilighted offset is a bit bigger...
	int hoffset = 4;
	int offset = 2;
	
	float scaleX = 0;
	float scaleY = 0;
	Stroke highlight = new BasicStroke(offset);
	
	// next time on redraw, clear everything.
	boolean clear = false;
	
	/** Highlighted nodes and edges are stored in symbol tables. */
	SeparateChainingHashST<Integer, Color> highlightedNodes = new SeparateChainingHashST<>();
	SeparateChainingHashST<Edge, Color> highlightedEdges = new SeparateChainingHashST<>();
	
	public Visualizer(Information info) {
		this.information = info;
	}
	
	/** Return converted point from node id. */
	public Point node(int id) {
		GPS pos = information.positions.get(id);
		return new Point(x(pos.longitude), y(pos.latitude));
	}
	
	/** Produce the scaled x value for the given longitude. */
	public int x(float longitude) {
		return 5*offset + (int)( (longitude-minLong)*scaleX);
	}
	
	/** 
	 * Produce the scaled y value for the given longitude. Note that the y coordinate
	 * in Java is reverse of the Cartesian/GPS coordinates, so we have to subtract from height.
	 */
	public int y(float latitude) {
		return getHeight() - 5*offset - (int)((latitude-minLat)*scaleY);
	}
	
	/** Even after unhighlighting nodes, the image gets muddled and needs to be refreshed. */
	public void clear() {
		clear = true;
		repaint();
	}
	
	/**
	 * Draw all nodes and edges.
	 * 
	 * When paintComponent is called for the first time, that is when you 
	 * take control and compute the scaling factors for all points to be 
	 * drawn.
	 * 
	 * Thereafter, the minLat,maxLat and minLong,maxLong values have been
	 * computed (with their scaleX and scaleY)
	 */
	@Override
	public void paintComponent(Graphics g) {
		if (clear) {
			Color old = g.getColor();
			g.setColor(getBackground());
			g.fillRect(0,  0, getWidth(), getHeight());
			clear = false;
			g.setColor(old);
		}
		
		if (minLat == Integer.MAX_VALUE && maxLat == Integer.MIN_VALUE) {
			
			// calculate from positional information.
			for (Integer id : information.positions.keys()) {
				GPS pos = information.positions.get(id);
				if (pos.latitude < minLat) { minLat = pos.latitude; }
				if (pos.latitude > maxLat) { maxLat = pos.latitude; }
				if (pos.longitude < minLong) { minLong = pos.longitude; }
				if (pos.longitude > maxLong) { maxLong = pos.longitude; }
			}
			scaleX = (getWidth()-10*offset)/(maxLong - minLong);
			scaleY = (getHeight()-10*offset)/(maxLat - minLat);
		}
		
		// edges in gray (draw FIRST so they don't obscure nodes).
		Graph graph = information.graph;
		g.setColor(Color.darkGray);
		for (int i = 0; i < graph.V(); i++) {
			for (int j : graph.adj(i)) {
				if (j > i) {
					// since graph is undirected, don't draw edges twice
					Point ni = node(i);
					Point nj = node(j);
					g.drawLine(ni.x, ni.y, nj.x, nj.y);
				}
			}
		}
		
		// nodes in blue
		for (Integer id : information.positions.keys()) {
			Point p = node(id);
			g.setColor(Color.blue);
			g.fillOval(p.x-offset, p.y-offset, 2*offset, 2*offset);
		}
		
		// highlighted edges next (but be sure to leave original stroke in place).
		Stroke old = ((Graphics2D)g).getStroke();
		((Graphics2D)g).setStroke(highlight);
		for (Edge e : highlightedEdges.keys()) {
			g.setColor(highlightedEdges.get(e));
			Point ni = node(e.u);
			Point nj = node(e.v);
			g.drawLine(ni.x, ni.y, nj.x, nj.y);
		}
		((Graphics2D)g).setStroke(old);
		
		// highlighted nodes last
		for (Integer id : highlightedNodes.keys()) {
			Point p = node(id);
			g.setColor(highlightedNodes.get(id));
			g.fillOval(p.x-hoffset, p.y-hoffset, 2*hoffset, 2*hoffset);
		}
	}
	
	public void highlightNode(int u, Color color) {
		highlightedNodes.put(u, color);
	}
	
	public void unhighlightNode(int u) {
		highlightedNodes.delete(u);
	}

	public void unhighlightAllNodes() { highlightedNodes = new SeparateChainingHashST<>(); }
	
	public void highlightEdge(int u, int v, Color color) {
		Edge e;
		if (u < v) {
			e = new Edge(u, v);
		} else {
			e = new Edge(v, u);
		}
		highlightedEdges.put(e, color);
	}
	
	public void unhighlightEdge(int u, int v) {
		Edge e;
		if (u < v) {
			e = new Edge(u, v);
		} else {
			e = new Edge(v, u);
		}
		highlightedEdges.delete(e);
	}
	
	public void unhighlightAllEdges() { highlightedEdges = new SeparateChainingHashST<>(); }
}