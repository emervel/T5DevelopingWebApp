package com.toysforshots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by guopm on 21/05/2017.
 * Implementa el controlador de errores para poder tratarlos y redirigir a la vista que quieres. Muy bueno
 * Por defecto, si no tienes esto implementado Spring redirige a una pagina de error suya, aunque si ponemos directamente
 * error.html en el raiz cogera esa
 */
@Controller
public class CustomErrorController implements ErrorController {

    /**
     * Para el mapping
     */
    private static final String ERROR_PATH = "/error";
    /**
     * Para la vista
     */
    private static final String ERROR_TEMPLATE = "customError";

    private final ErrorAttributes errorAttributes;

    /**
     * Metodo para inyectar los objetos ErrorAtributes
     * @param errorAttributes
     */
    @Autowired
    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    /**
     * Mapping "/error" a la vista customError
     * @param model esta clase es el contenedor con los datos que pasamos a la vista
     * @param request la request
     * @return vista a la que nos redirige par mostrar los datos
     */
    @RequestMapping(ERROR_PATH)
    public String error(Model model, HttpServletRequest request) {

        // {error={timestamp=Mon Nov 02 12:40:50 EST 2015, status=404, error=Not Found, message=No message available, path=/foo}}
        Map<String, Object> error = getErrorAttributes(request, true);

        model.addAttribute("timestamp", error.get("timestamp"));
        model.addAttribute("status", error.get("status"));
        model.addAttribute("error", error.get("error"));
        model.addAttribute("message", error.get("message"));
        model.addAttribute("path", error.get("path"));

        //logging
        //send notification
        //etc
        return ERROR_TEMPLATE;
    }

    /**
     * Metodo que tenemos que sobreescribir al implementar el CustomErrorController. Si no tuviesemos esta clase implementada
     * nos redirigiria a una pagina de error propia de Spring. Al implementar y sobreescribir, podemos decir cual es la vista por defecto
     * que en este caso es una redireccion al mapping justo anterior
     * @return
     */
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    /**
     * Mapping /404 que recibe los errores de pagina no encontrada y que fue definido la clase de arranque de Spring ThymeleafApplication
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/404")
    public String pageNotFound(Model model, HttpServletRequest request) {
        model.addAttribute("error", getErrorAttributes(request, true));
        return "404";
    }

    /**
     * Metodo que recupera de la request los atributos de error. Esto es un metodo generico que usamos para el error 404
     * @param request
     * @param includeStackTrace
     * @return
     */
    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return this.errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }
}
