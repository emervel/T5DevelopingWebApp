package com.toysforshots.controller;

import com.toysforshots.domain.CustomGenericException;
import com.toysforshots.domain.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;


/**
 * Controller para hacer los mappings
 */
@Controller
@RequestMapping("/posts") //Con esto consigo que todos los mapping debajo tengan este path incluido
public class PostController {

    /**
     * log para escribir
     */
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    /**
     * redirecciona posts/inicio a la vista index
     * @return
     */
    @RequestMapping("/inicio")
    public String home() {
        return "index";
    }

    /**
     * Lanza una excepcion CustomGenericException para que la trate ExceptionHandler
     * @param slug representa un valor del path que podemos utilizar dentro del metodo
     * @return
     * @throws Exception
     */
    @RequestMapping("/get/{slug}")
    public String getPost(@PathVariable(value="slug") String slug) throws Exception{
        Post post = null;
        if( post == null ) throw new CustomGenericException("E888", "No se encuentra el post "+slug);
        return "post";
    }

    /**
     *
     * @param model Son como cajetillas donde pasaremos los atributos que queremos que se usen desde th
     * @return string al nombre de la vista donde nos redirigiremos
     */
    @RequestMapping("/")
    public String list(Model model){
        model.addAttribute("pageTitle","My Custom Page Title");
        model.addAttribute("posts",createPosts());
        return "views/list";
    }

    /**
     * Anotado como manejador de las excepciones de tipo CustomGenericException que es un POJO
     * @param ex excepcion que recibiremos
     * @return Devolvemos un objeto que es el Model con los atributos que pasamos y la vista a donde se los mandamos
     */
    @ExceptionHandler(CustomGenericException.class)
    public ModelAndView handleCustomException(CustomGenericException ex) {

        ModelAndView model = new ModelAndView("views/postError");
        model.addObject("errCode", ex.getErrCode());
        model.addObject("errMsg", ex.getErrMsg());

        return model;

    }

    /**
     * Metodo para crear post
     * @return
     */
    private ArrayList<Post> createPosts(){
        logger.info("inicio createPosts");
        // post 1
        Post post1 = new Post();
        post1.setTitle("My Blog Post 1");
        post1.setPosted(new Date());
        post1.setAuthor("Dan Vega");
        post1.setBody(getPostBody());

        // post 2
        Post post2 = new Post();
        post2.setTitle("My Blog Post 2");
        post2.setPosted(new Date());
        post2.setAuthor("Dan Vega");
        post2.setBody("<p>This post didn't take long to write.");

        ArrayList<Post> posts = new ArrayList<Post>();
        posts.add(post1);
        posts.add(post2);

        return posts;
    }

    /**
     * Metodo para rellenar el cuerpo de un post
     * @return
     */
    private String getPostBody(){
        String body = "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ac finibus mi. Morbi porttitor urna dui, sed volutpat justo rutrum sit amet. Etiam elementum lacus eget malesuada egestas. Sed id lectus arcu. Pellentesque molestie dignissim diam non commodo. Nunc nec aliquet lectus. Ut a accumsan sapien. Pellentesque sit amet sem nisl. Nulla fringilla vulputate mauris, eleifend dapibus libero. Sed eu cursus orci. In hac habitasse platea dictumst. Vestibulum vel vulputate ex. Nam gravida blandit nisl, at luctus mi interdum ut. Pellentesque et pharetra mi. Proin id placerat diam. Ut porttitor risus in leo tincidunt, a iaculis velit maximus.</p>";
        body += "<p>Nunc bibendum sollicitudin ex, vitae lobortis nunc malesuada eget. Maecenas aliquam aliquam elit, nec rutrum justo blandit sit amet. Cras pellentesque egestas nisi at egestas. Donec lacus ipsum, dignissim a accumsan quis, rutrum ac massa. Vivamus sed dolor nisl. Integer convallis, elit sed euismod molestie, purus velit ornare justo, ac maximus diam odio id felis. Maecenas auctor sed quam ac aliquet. Curabitur tempus sed purus sit amet blandit.</p>";
        body += "<p>Curabitur eget arcu elementum, pulvinar odio ut, dictum tellus. In hac habitasse platea dictumst. Proin sagittis a ex id tempor. Proin faucibus risus non pellentesque vulputate. Morbi augue sem, finibus non leo ut, fermentum auctor massa. Mauris nisi felis, posuere ut efficitur eu, gravida nec enim. Curabitur tempus id nulla in iaculis. Mauris varius accumsan nunc ut pellentesque. Sed placerat vel nisl quis ornare. Ut imperdiet felis et massa facilisis, vel vestibulum erat varius. Etiam tristique dignissim leo, congue tincidunt mi finibus vitae. Suspendisse quis risus eget arcu pharetra tincidunt eu non sapien. Cras sollicitudin, lectus non venenatis ullamcorper, eros quam condimentum libero, nec fermentum purus neque eu eros. Nullam eget blandit nunc. Mauris ultricies sed sapien non aliquam. Etiam bibendum lectus scelerisque neque eleifend, non semper nisi condimentum.</p>";
        return body;
    }
}
