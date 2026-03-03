package fr.uha.hassenforder.cage.turtle.interpreter.renderer;

import fr.uha.hassenforder.ast.Helper;
import fr.uha.hassenforder.ast.Node;
import fr.uha.hassenforder.ast.evaluator.Evaluator;
import fr.uha.hassenforder.ast.evaluator.LazyEvaluator;
import fr.uha.hassenforder.cage.turtle.interpreter.LogoContext;
import fr.uha.hassenforder.cage.turtle.interpreter.view.GraphicTurtle;

public class LogoRender {

	private LogoContext context;
	
	public LogoRender(LogoContext context) {
		super();
		this.context = context;
	}
	

	public void render (GraphicTurtle gt) {
		Evaluator eval = new LazyEvaluator (context.getVista(), context.getFactory());
		Painter painter = new Painter (gt, eval);
		LogoPainter logoPainter = new LogoPainter (eval, painter);
		Node bloc = context.getFactory().createNodeBloc();
		if (Helper.exist (context.getVista(), LogoContext.getInitSegmentName())) {
			bloc.getChildren().add(context.getFactory().createNodeFunctionCall(
					context.getFactory().createNodeVariable(LogoContext.getInitSegmentName()), null)
				);
		}
		logoPainter.begin();
		logoPainter.visit(bloc);
		logoPainter.end();
	}

}
