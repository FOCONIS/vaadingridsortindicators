package com.example.gridsortindicators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("mytheme")
public class MyUI extends UI {

	private static final long serialVersionUID = 1L;

	@Override
    protected void init(VaadinRequest vaadinRequest) {

        final VerticalLayout layout = new VerticalLayout();
        layout.addComponent(new Label("Sort indicators not removed after calling clearSortOrder() on grid with: " + 
        		com.vaadin.shared.Version.getFullVersion()));
        Label issueLabel = new Label("Demonstrate the problem in <a href=\"https://github.com/vaadin/framework/issues/9074\">Vaadin GitHub Issue #9074 - Calling \"clearSortOrder\" in Grid does not clear sort indicators</a>");
        issueLabel.setContentMode(ContentMode.HTML);
        layout.addComponent(issueLabel);

	    // Create new Grid with some sortable entries
        Grid<HashMap<String, String>> grid = new Grid<>("My test grid to sort");

		// Create Clear Sort Order Button
		final Button clearSortOrderButton = new Button("clearSortOrder()");
		clearSortOrderButton.setId("gridButton");
		clearSortOrderButton.addClickListener(event -> {
			grid.clearSortOrder();
		});
		layout.addComponent(clearSortOrderButton);
		layout.setComponentAlignment(clearSortOrderButton, Alignment.BOTTOM_LEFT);
		
		// Fill the grid with data to sort
		List<HashMap<String, String>> rows = new ArrayList<>();
		String FIRST = "Firstname";
		String LAST = "Lastname";
		Random r = new Random();

		// Grid 8 without bean class from https://vaadin.com/forum/#!/thread/16038356/16816582
		for (int i = 0; i < 5; i++) {
			char c = (char)(r.nextInt(26) + 'a');
		    HashMap<String, String> fakeBean = new HashMap<>();
		    fakeBean.put(FIRST, c + "first" + i);
		    fakeBean.put(LAST, c + "last" + i);
		    rows.add(fakeBean);
		  }

		grid.setItems(rows);

		// Add the columns based on the first row
		HashMap<String, String> s = rows.get(0);
		for (Map.Entry<String, String> entry : s.entrySet()) {
		    grid.addColumn(h -> h.get(entry.getKey())).setCaption(entry.getKey());
		}
		
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
