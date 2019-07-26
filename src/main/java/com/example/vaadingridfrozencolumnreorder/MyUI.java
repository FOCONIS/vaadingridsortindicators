package com.example.vaadingridfrozencolumnreorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("mytheme")
public class MyUI extends UI {

	private static final long serialVersionUID = 1L;

	@Override
    protected void init(VaadinRequest vaadinRequest) {

        final VerticalLayout layout = new VerticalLayout();
        layout.addComponent(new Label("Frozen columns can be reordered by dragging the first frozen column behind the second column: " + 
        		com.vaadin.shared.Version.getFullVersion()));
        Label issueLabel = new Label("Demonstrate the problem in GitHub issue #10546 <a href=\"https://github.com/vaadin/framework/issues/10546\">grid column frozen column reorder issue with SelectionMode.MULTI</a>");
        issueLabel.setContentMode(ContentMode.HTML);
        layout.addComponent(issueLabel);

	    // Create new Grid
        Grid<HashMap<String, String>> grid = new Grid<>("My test grid to reorder columns");
		
		// Fill the grid with data to sort
		List<HashMap<String, String>> rows = new ArrayList<>();
		String FIRST = "Frozen Column (Should not be reordered)";
		String LAST = "Last Name Column";

		// Grid for Vaadin 8 without bean class from https://vaadin.com/forum/#!/thread/16038356/16816582
		for (int i = 0; i < 20; i++) {
		    HashMap<String, String> fakeBean = new HashMap<>();
		    fakeBean.put(FIRST, "first" + i);
		    fakeBean.put(LAST, "last" + i);
		    rows.add(fakeBean);
		  }

		grid.setItems(rows);

		// Add the columns based on the first row
		HashMap<String, String> s = rows.get(0);
		for (Map.Entry<String, String> entry : s.entrySet()) {
		    grid.addColumn(h -> h.get(entry.getKey())).setCaption(entry.getKey()).setId(entry.getKey());
		}
		grid.getColumn(LAST).setHidable(true);
		grid.setSelectionMode(SelectionMode.MULTI);
		// without the selector column the issue cannot be observed !
		// grid.setSelectionMode(SelectionMode.NONE);
		grid.setFrozenColumnCount(1);
		grid.setColumnReorderingAllowed(true);
        grid.setSizeFull();
 
        layout.addComponent(grid);
        layout.setMargin(true);
        layout.setSpacing(true);
        
        setContent(layout);
        
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
		private static final long serialVersionUID = 1L;
    }
    
}
