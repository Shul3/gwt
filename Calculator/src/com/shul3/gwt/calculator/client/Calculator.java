package com.shul3.gwt.calculator.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;



public class Calculator implements EntryPoint {

	private VerticalPanel mainPanel = new VerticalPanel();
    private FlexTable resultsFlexTable = new FlexTable();
    private HorizontalPanel addPanel = new HorizontalPanel();
    private TextBox newSymbolTextBox = new TextBox();
    private TextBox newSymbolTextBox2 = new TextBox();
    private TextBox newSymbolTextBox3 = new TextBox();
    private Button addCalculateButton = new Button("Calculate");
    private Label lastUpdatedLabel = new Label();
    private ArrayList<String> stocks = new ArrayList<String>();
    private String[] results = new String[3];
    

	  /**
	   * Entry point method.
	   */
	  public void onModuleLoad() {
		// Create table for stock data.
		    resultsFlexTable.setText(0, 0, "Operations");
		    resultsFlexTable.setText(0, 1, "Log");
		    resultsFlexTable.setText(0, 2, "Remove");
		   
		    
		 // Add styles to elements in the stock list table.
		    resultsFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
		    resultsFlexTable.addStyleName("watchList");
		    
		 // Assemble Add Stock panel.
		    addPanel.add(newSymbolTextBox);
		    addPanel.add(newSymbolTextBox2);
		    addPanel.add(newSymbolTextBox3);
		    addPanel.add(addCalculateButton);

		    // Assemble Main panel.
		    mainPanel.add(resultsFlexTable);
		    
		    mainPanel.add(lastUpdatedLabel);
		    mainPanel.add(addPanel);

		 // Associate the Main panel with the HTML host page.
		    RootPanel.get("resultsList").add(mainPanel);
		    
		    lastUpdatedLabel.setText("_________Number 1___________|"
		    						+"____Operation( +, -, /, *)______|"
		    						+"_________Number 2___________");
		    
	    // TODO Move cursor focus to the input box.
		    newSymbolTextBox.setFocus(true);
		    
				    
		 // Listen for mouse events on the Add button.
		    addCalculateButton.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		        calculation(results);
		      }
		    });
		    
		 // Listen for keyboard events in the input box 1.
		      newSymbolTextBox.addKeyDownHandler(new KeyDownHandler() {
		        public void onKeyDown(KeyDownEvent event) {
		          if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
		        	 String temp=newSymbolTextBox.getText().toUpperCase().trim(); 
		        	 	
		            addResult(1, temp);
		            results[0] = temp;
		            newSymbolTextBox2.setFocus(true);
		          }
		        }
		      });
		   
		   // Listen for keyboard events in the input box 2.
		      newSymbolTextBox2.addKeyDownHandler(new KeyDownHandler() {
			        public void onKeyDown(KeyDownEvent event) {
			          if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			        	String temp2= newSymbolTextBox2.getText().toUpperCase().trim(); 
			        	 	
			            addResult(2,temp2 );
			            results[1] = temp2;
			            newSymbolTextBox3.setFocus(true);
			          }
			        }
			      });
		      
		   // Listen for keyboard events in the input box 3.  
		      newSymbolTextBox3.addKeyDownHandler(new KeyDownHandler() {
			        public void onKeyDown(KeyDownEvent event) {
			          if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			        	String temp3 = newSymbolTextBox3.getText().toUpperCase().trim(); 
			        	 	
			            addResult(3, temp3) ;
			            results[2] = temp3;
			            newSymbolTextBox.setFocus(true);
			          }
			        }
			      });
	  }
	  
	  
	  // Add input to FlexTable. 
	  
	  private void addResult(int x, String s) {
		  final String symbol = s;
		  
		  if (x == 1 || x == 3) {
			  if (x==1) newSymbolTextBox.setFocus(true);
			  if (x==3) newSymbolTextBox3.setFocus(true);
		      if (!s.matches("^[0-9\\.]{1,10}$")) {
		    	  Window.alert("'" + s + "' is not a valid symbol.");
			      newSymbolTextBox.selectAll();
			      return;
			  }
		  } 
		  else if (x ==2) {
			  
		      newSymbolTextBox2.setFocus(true);
		      if (!s.matches("^[+-/*]{1}$")) {
			        Window.alert("'" + s + "' is not a valid symbol.");
			        newSymbolTextBox2.selectAll();
			        
			        return;
			      }
		      
		  }
		  	  
  
	      newSymbolTextBox.setText("");
	      newSymbolTextBox2.setText("");
	      newSymbolTextBox3.setText("");

	      addToTable(symbol);
	     
	  
	  }  
	  
	  // Calculation from inputs
	  private void calculation(String[] resAr) {
		  String symbol = "";
		 
		  float n1 = Float.parseFloat(resAr[0]);
		  float n2 = Float.parseFloat(resAr[2]);
		  
		  switch(resAr[1]) {
	         case "-" :
	        	symbol = Float.toString(n1 - n2); 
	            break;
	         case "+" :
	        	 symbol = Float.toString(n1 + n2); 
		         break; 
	         case "*" :
	        	 symbol = Float.toString(n1 * n2);
	            break;
	         case "/" :
	        	 if(n2==0) {
	        		 Window.alert("'" + n2 + "' can not equal to zero!!");
	        		 return;
	        	 }
	        	 symbol = Float.toString(n1 / n2);;
	        	 break;
		  }
		  
		  symbol = "Answer: " + symbol;
		  addToTable(symbol);
		  
		  for(int i=0; i< resAr.length; i++) resAr[i] = "";				  
		  
	  }
	  
	  private void addToTable(final String s)
	  {
		// Add the results to the Flex table.
	      int row = resultsFlexTable.getRowCount();
	      stocks.add(s);
	      resultsFlexTable.setText(row, 0, s);
	     
	      
	   // Add a button to remove  results from the Flex table.
	      Button removeStockButton = new Button("x");
	      removeStockButton.addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	          int removedIndex = stocks.indexOf(s);
	          stocks.remove(removedIndex);
	          resultsFlexTable.removeRow(removedIndex + 1);
	        }
	      });
	      resultsFlexTable.setWidget(row, 3, removeStockButton);
	  }
	  
	  }
	 
	
	