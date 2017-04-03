/* AdministratorController.java
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Constructors -----------------------------------------------------------
	
	public AdministratorController() {
		super();
	}
		
	// Action-1 ---------------------------------------------------------------		

	@RequestMapping(value="/action-1")
	public ModelAndView action1(@RequestParam("x") double x,@RequestParam("y") double y) {
		ModelAndView result;
				
		result = new ModelAndView("administrator/action-1");
		result.addObject("x",x);
		result.addObject("y",y);
		result.addObject("res",x+y);
		
		return result;
	}
	
	// Action-2 ---------------------------------------------------------------

	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;
		
		// Creamos array de String
		String[] array = new String[10];
		array[0] = "'No llores porque ya se terminó, sonríe porque sucedió.' - Dr. Seuss";
		array[1] = "'Dos cosas son infinitas: el universo y la estupidez humana, y no estoy seguro sobre el universo' - Albert Einstein";
		array[2] = "'Sé tú mismo, todo el mundo ya está en uso.' - Oscar Wilde";
		array[3] = "'Sé el cambio que quieres ver en el mundo.' - Mahatma Gandhi";
		array[4] = "'Yo soy tan inteligente que a veces no entiendo una sola palabra de lo que digo.' - Oscar Wilde";
		array[5] = "'Es mejor permanecer en silencio en el riesgo de ser pensado un tonto, que hablar y eliminar cualquier duda de ello. - Maurice Suiza";
		array[6] = "'Si quieres saber lo que un hombre es como, echar un buen vistazo a la forma en que trata a sus inferiores, no a sus iguales.' - J. K. Rowling";
		array[7] = "'La oscuridad no puede expulsar a la oscuridad: sólo la luz puede hacer que el odio no puede expulsar al odio:. Sólo el amor puede hacer eso.' - Martin Luther King Jr";
		array[8] = "'Para ser uno mismo en un mundo que esté intentando constantemente hacerle algo más es la realización más grande.' - Ralph Waldo Emerson";
		array[9] = "'Si no defiendes algo caerás por cualquier cosa.' - Malcolm X";
		
		// Creamos un número aleatorio
	 	int aleatorio  = (int) (Math.random()*10);
	 	
	 	// Cogemos la frase
		String frase= array[aleatorio];
		// Creamos ModelAndView
		result = new ModelAndView("administrator/action-2");
		
		// Añadimos el objeto frase
		result.addObject("frase", frase);
		return result;
	}
	
}