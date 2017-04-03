/* CustomerController.java
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public CustomerController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------

	@RequestMapping("/action-1")
	public ModelAndView action1() {
		ModelAndView result;
		Double[] array = new Double[10];
		String listaSenos = creaListaSenos(array); 
		result = new ModelAndView("customer/action-1");
		
		result.addObject("listaSenos", listaSenos);
		return result;
	}

	private String creaListaSenos(Double[] array) {
		String listaSenos = "<ul>";
		
		for (int i = 0; i < 10; i++) {
			array[i] = Math.sin(i);
			listaSenos += "<li>" + array[i] + "</li>";
		}
		listaSenos += "</ul>";
		return listaSenos;
	}

	
	
	// Action-2 ---------------------------------------------------------------

	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("customer/action-2");
		result.addObject("listaCoordenadas", crearListaCoordenadas());

		return result;
	}
	
	private String crearListaCoordenadas(){
		String listaCoordenadas="<ul>";
		Random rnd= new Random();
		
		for (int i = 0; i < 10; i++) {
			listaCoordenadas += "<li>(" + rnd.nextDouble()*100+" , "+ rnd.nextDouble()*100 + ")</li>";
		}
		
		listaCoordenadas+="</ul>";
		return listaCoordenadas;
	}
}



