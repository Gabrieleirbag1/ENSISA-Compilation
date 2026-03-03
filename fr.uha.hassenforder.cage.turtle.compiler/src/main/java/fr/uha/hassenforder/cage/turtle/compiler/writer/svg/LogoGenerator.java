package fr.uha.hassenforder.cage.turtle.compiler.writer.svg;

import fr.uha.hassenforder.ast.visitor.INodeVisitor;
import fr.uha.hassenforder.logo.visitor.LogoVisitor;

public class LogoGenerator extends LogoVisitor {

	public LogoGenerator(INodeVisitor nodeVisitor, INodeVisitor turtleVisitor) {
		super(nodeVisitor, turtleVisitor);
	}

}
