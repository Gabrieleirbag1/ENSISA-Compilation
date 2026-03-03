module fr.uha.hassenforder.cage.turtle.compiler {
    requires fr.uha.page.runtime;
    requires transitive logo;
    requires transitive org.eclipse.emf.common;
    requires transitive org.eclipse.emf.ecore;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
	requires javafx.base;
	requires java.desktop;

    opens fr.uha.hassenforder.cage.turtle.compiler.view to javafx.fxml;
    exports fr.uha.hassenforder.cage.turtle.compiler;
}
