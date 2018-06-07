import static spark.Spark.get;

import spark.servlet.SparkApplication;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;


public class HelloWorld implements SparkApplication {

    public static void main(String[] args) {
        new HelloWorld().init();
    }

    @Override
    public void init() {
        DBManager manager = new DBManager();

        get("/", (request, response)
                -> "Hello from SparkJava running on GAE Standard Java8 runtime.");

        get("/hello/:name", (request, response) -> {
            return "SparkJava running on GAE Java8 says: Hello " + request.params(":name");
        });
        get("/posts", (req, res) -> {
            return manager.getAllPosts();
        });
    }

    // Use Servlet annotation to define the Spark filter without web.xml:
    @WebFilter(
            filterName = "SparkInitFilter", urlPatterns = {"/*"},
            initParams = {
                    @WebInitParam(name = "applicationClass", value = "HelloWorld")
            })
    public static class SparkInitFilter extends spark.servlet.SparkFilter {
    }
}
