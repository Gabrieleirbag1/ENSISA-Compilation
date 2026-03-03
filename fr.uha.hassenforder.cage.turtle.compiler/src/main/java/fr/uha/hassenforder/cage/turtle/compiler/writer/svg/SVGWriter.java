package fr.uha.hassenforder.cage.turtle.compiler.writer.svg;

import fr.uha.hassenforder.ast.Helper;
import fr.uha.hassenforder.ast.Node;
import fr.uha.hassenforder.ast.evaluator.Evaluator;
import fr.uha.hassenforder.ast.evaluator.LazyEvaluator;
import fr.uha.hassenforder.cage.turtle.compiler.LogoContext;
import fr.uha.hassenforder.cage.turtle.compiler.writer.IWriter;

public class SVGWriter implements IWriter {

	private LogoContext context;
	
	public SVGWriter(LogoContext context) {
		super();
		this.context = context;
	}

	public void write (String filename) {
		Evaluator eval = new LazyEvaluator (context.getVista(), context.getFactory());
		SVGGenerator generator = new SVGGenerator (filename, eval);
		LogoGenerator logoGenerator = new LogoGenerator (eval, generator);
		Node bloc = context.getFactory().createNodeBloc();
		if (Helper.exist (context.getVista(), LogoContext.getInitSegmentName())) {
			bloc.getChildren().add(context.getFactory().createNodeFunctionCall(
					context.getFactory().createNodeVariable(LogoContext.getInitSegmentName()), null)
				);
		}
		logoGenerator.begin();
		logoGenerator.visit(bloc);
		logoGenerator.end();
	}

}
