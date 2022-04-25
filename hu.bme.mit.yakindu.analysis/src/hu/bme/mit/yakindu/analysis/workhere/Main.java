package hu.bme.mit.yakindu.analysis.workhere;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Transition;
import org.yakindu.sct.model.stext.stext.EventDefinition;
import org.yakindu.sct.model.stext.stext.VariableDefinition;
import org.yakindu.sct.model.sgraph.Statechart;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;

public class Main {
	@Test
	public void test() {
		main(new String[0]);
	}
	
	public static void main(String[] args) {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();
		
		// Loading model
		EObject root = manager.loadModel("model_input/example.sct");
		
		// Reading model
		Statechart s = (Statechart) root;
		TreeIterator<EObject> iterator = s.eAllContents();
		ArrayList<String> varNames = new ArrayList<>();
		ArrayList<String> eventNames = new ArrayList<>();
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof State) {
				State state = (State) content;
				System.out.println(state.getName());
				boolean hasOutGoing = false;
				for (Transition t : state.getOutgoingTransitions())
					if ( ! t.getTarget().equals(state))
						hasOutGoing = true;
				if ( ! hasOutGoing)
					System.out.println("trap: " + state.getName());
				if (state.getName() == null || state.getName() == "")
					System.out.println("Should you name this state \"Bubblegum\"?");
			}
			else if (content instanceof Transition) {
                Transition tr = (Transition) content;
                System.out.println(tr.getSource().getName() + " -> "
                + tr.getTarget().getName());
            }
			else if (content instanceof VariableDefinition) {
				VariableDefinition vd = (VariableDefinition) content;
				System.out.println(vd.getName());
				varNames.add(vd.getName());
			}
			else if (content instanceof EventDefinition) {
				EventDefinition ed = (EventDefinition) content;
				System.out.println(ed.getName());
				eventNames.add(ed.getName());
			}

		}
		codePrinter(varNames, eventNames);
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
	static public void codePrinter(ArrayList<String> varNames, ArrayList<String> eventNames) {
		System.out.println("public static void print(IExampleStatemachine s) {");
		for (String name : varNames)
			System.out.println("\tSystem.out.println(\"" 
		+ name.toUpperCase().charAt(0) 
		+ " = \" + s.getSCInterface().get" 
		+ name.toUpperCase().charAt(0) + name.substring(1) 
		+ "());");
		//for (String name : eventNames)
		//	System.out.println("\tSystem.out.println(" 
		//+ "s.getSCInterface().get" 
		//+ name.toUpperCase().charAt(0) + name.substring(1) 
		//+ "());");
		System.out.println("}");
	}
}
