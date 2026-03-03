package fr.uha.hassenforder.cage.turtle.compiler.renderer;

import fr.uha.hassenforder.ast.visitor.INodeVisitor;
import fr.uha.hassenforder.logo.visitor.LogoVisitor;

public class LogoPainter extends LogoVisitor {

	public LogoPainter(INodeVisitor nodeVisitor, INodeVisitor turtleVisitor) {
		super(nodeVisitor, turtleVisitor);
	}

}
